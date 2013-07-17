/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Jeremy Othieno.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package clockwork.graphics.renderer;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.PriorityQueue;

import clockwork.graphics.Fragment;
import clockwork.graphics.Framebuffer;
import clockwork.graphics.Material;
import clockwork.graphics.PolygonFace;
import clockwork.graphics.Vertex;
import clockwork.graphics.camera.Viewport;
import clockwork.graphics.color.ColorRGBA;
import clockwork.graphics.vsd.Clipping;
import clockwork.graphics.vsd.Culling;
import clockwork.physics.lighting.LightEmitter;
import clockwork.system.Debug;
import clockwork.system.RuntimeOptions;
import clockwork.system.Services;
import clockwork.types.ConcurrentList;
import clockwork.types.math.Matrix4;
import clockwork.types.math.Point3f;
import clockwork.types.math.Point4f;


public abstract class Renderer
{
	/**
	 * The render modes.
	 */
	public enum Mode
	{
		Points("Points"),
		Wireframe("Wireframe"),
		Polygon("Polygons");
		/**
		 * The mode's title.
		 */
		private final String title;
		/**
		 * Instantiate a render mode with a given title.
		 */
		Mode(final String title)
		{
			this.title = title;
		}
		/**
		 * Return the render mode's title.
		 */
		public String getTitle()
		{
			return title;
		}
	}
	/**
	 * An enumeration of available renderers.
	 */
	public static enum Type
	{
		Points("Points renderer"),
		Wireframe("Wireframe renderer"),
		Random("Random shading renderer"),
		Depth("Depth shading renderer (to implement)"),
		Normals("Normals shading renderer (to implement)"),
		Texture("Texture mapping renderer (to implement)"),
		Constant("Flat shading renderer (to implement)"),
		Phong("Phong shading renderer (to implement)"),
		Cel("Cel shading renderer (to implement)"),
		Bump("Bump mapping renderer (to implement)"),
		Deferred("Deferred renderer (to implement)");
		/**
		 * The type's title.
		 */
		private final String title;
		/**
		 * The default constructor.
		 */
		Type(final String title)
		{
			this.title = title;
		}
		/**
		 * Get the type's title.
		 */
		public String getTitle()
		{
			return title;
		}
		/**
		 * Convert the renderer type to a string.
		 */
		@Override
		public String toString()
		{
			return title;
		}
	}
	/**
	 * The type of renderer.
	 */
	private final Renderer.Type type;
	/**
	 * The default render mode.
	 */
	private final Renderer.Mode mode;
	/**
	 * The framebuffer where the output of the render operation is stored.
	 */
	protected final Framebuffer framebuffer = Services.Graphics.getFramebuffer();
	/**
	 * The Renderable that's currently being processed by the renderer.
	 */
	protected Renderable currentRenderable = null;
	/**
	 * The current viewpoint, a.k.a. the camera's position.
	 */
	protected Point3f viewpoint = null;
	/**
	 * The viewport transformation values.
	 */
	private final Point2D.Double VIEWPORT = new Point2D.Double(1.0, 1.0);
	/**
	 * The current set of lights in the scene.
	 */
	protected ConcurrentList<LightEmitter> lights = null;
	/**
	 * The current matrices.
	 */
	protected static Matrix4 VIEW = null;
	protected static Matrix4 MODEL = null;
	protected static Matrix4 NORMAL = null;
	protected static Matrix4 MODELVIEW = null;
	protected static Matrix4 PROJECTION = null;
	protected static Matrix4 VIEWPROJECTION = null;
	protected static Matrix4 MODELVIEWPROJECTION = null;
	/**
	 * The input material attributes, i.e. lighting coefficients and texture maps.
	 * Texture maps may include diffuse, specular and normal maps.
	 * Instead of storing the material, we store it's attributes so that we reduce the overhead
	 * of calling its accesor methods. This greatly reduces the execution times of both
	 * vertex and especially fragment programs.
	 */
	protected static Material inputMaterial = null;
	/**
	 * The default constructor. Instantiate a Renderer with a given render mode.
	 * @param type the renderer type.
	 * @param mode the default render mode.
	 */
	protected Renderer(final Renderer.Type type, final Renderer.Mode mode)
	{
		this.type = type;
		this.mode = mode;
	}
	/**
	 * Get the renderer's type.
	 */
	public Renderer.Type getType()
	{
		return type;
	}
	/**
	 * Get the current render mode.
	 */
	public Renderer.Mode getMode()
	{
		return mode;
	}
	/**
	 * Prepare the renderer for use. Some renderers change different parameters around
	 * the subsystem before they begin rendering. For example, the depth renderer changes
	 * the clear-color value to white before it can draw depth values, white being far and
	 * black being close.
	 */
	public void prepare()
	{
		framebuffer.setClearColorValue(0xff000000);
		RuntimeOptions.RenderSurfaceNormals = false;
	}
	/**
	 * Apply the render function to a given render context. This method
	 * implements the actual graphics pipeline.
	 * @param context the render context to use.
	 */
	public void apply(final RenderContext context)
	{
		if (context != null)
		{
			final long t0 = System.currentTimeMillis();

			// Set the matrices that are independent of the model.
			VIEW = context.getVIEW();
			PROJECTION = context.getPROJECTION();
			VIEWPROJECTION = PROJECTION.multiply(VIEW);

			// Set the viewer's viewpoint (position).
			this.viewpoint = context.getViewpoint();

			// Set the VIEWPORT transformation.
			final Viewport viewport = context.getViewport();
			if (viewport != null)
			{
				final Dimension resolution = framebuffer.getResolution();
				VIEWPORT.x = resolution.width * viewport.width * 0.5;
				VIEWPORT.y = resolution.height * viewport.height * 0.5;
			}

			// Set debug variables.
			Debug.PolygonCount.set(0);
			Debug.RenderedPolygonCount.set(0);

			// Get the queue of renderables.
			final RenderProcessingQueue queue = context.getRenderProcessingQueue();
			if (!queue.isEmpty())
			{
//FIXME				Framebuffer.READWRITE_LOCK.lock();

				// Set the lights.
				lights = queue.getLightEmitters();
				final PriorityQueue<Renderable> renderables = queue.getRenderables();
				while (!renderables.isEmpty())
				{
					// Get the renderable at the top of the queue.
					currentRenderable = renderables.peek();

					// Set the material attributes.
					setMaterial(currentRenderable.material);

					// Setup the NORMAL, MODEL, MODELVIEW and MODELVIEWPROJECTION transformations.
					MODEL = currentRenderable.CMTM;
					MODELVIEW = VIEW.multiply(MODEL);
					NORMAL = RuntimeOptions.EnableNORMAL ?
					Matrix4.inverse(MODELVIEW).transpose() : new Matrix4();
					MODELVIEWPROJECTION = VIEWPROJECTION.multiply(MODEL);

					// Update debug variables.
					Debug.PolygonCount.getAndAdd(currentRenderable.faces.length);

					// The potentially-visible vertex stream.
					final ArrayList<Vertex> potentiallyVisibleVertices = new ArrayList<Vertex>();

					for (final PolygonFace face : currentRenderable.faces)
					{
						final Vertex inputVertices[] = face.getVertices();
						final Point2D.Double uvcoords[] = face.getTextureCoordinates();
						if (inputVertices != null)
						{
							for (int i = 0; i < inputVertices.length; ++i)
							{
								final Vertex input = inputVertices[i];
								final Vertex output = new Vertex(input);

								// Apply the vertex program to each input vertex.
								vertexProgram(input, output);

								// Set the texture coordinates.
								if (uvcoords != null)
								{
									output.u = uvcoords[i].x;
									output.v = uvcoords[i].y;
								}
								potentiallyVisibleVertices.add(output);
							}
						}
					}

					// Apply clipping to discard vertices that are not in the viewing window.
					final Vertex clippedVertices[] = Clipping.apply(potentiallyVisibleVertices);

					// Perform a perspective-divide and a viewport transformation on each clipped vertex.
					for (final Vertex vertex : clippedVertices)
					{
						final Point4f position = vertex.position;
						final double w = position.w;

						// Perform the perspective-divide which will convert the vertices from clipping
						// coordinate space to normalised device coordinate space.
						position.x /= w;
						position.y /= w;
						position.z /= w;
						position.w  = 1.0f;
					}

					// Perform rasterisation on visible vertices.
					rasterise(clippedVertices);

					// Remove the renderable from the queue.
					renderables.remove();
				}
				// The framebuffer now contains the rendered scene. We can now apply our
				// post-processing filters to it.
				framebuffer.postProcess();
//FIXME				Framebuffer.READWRITE_LOCK.unlock();
			}
			Debug.MillisecondsPerFrame.set(System.currentTimeMillis() - t0);
		}
	}
	/**
	 * The rasterisation operation. This will convert a scene of 3D polygons into a
	 * raster (a rectangular grid of pixels) image.
	 */
	public final void rasterise(final Vertex vertices[])
	{
		// Convert each triplet of vertices into a set of fragments.
		for (int i = 0; i < vertices.length; i += 3)
		{
			final Vertex triangle[] =
			{
				vertices[i    ],
				vertices[i + 1],
				vertices[i + 2]
			};

			// Perform backface culling. If the polygon isn't discarded, convert its vertices into
			// fragments and continue with the rasterisation process.
			if (!Culling.isBackface(triangle))
			{
				final Fragment fragments[] = new Fragment[]
				{
					new Fragment(triangle[0], VIEWPORT.x, VIEWPORT.y),
					new Fragment(triangle[1], VIEWPORT.x, VIEWPORT.y),
					new Fragment(triangle[2], VIEWPORT.x, VIEWPORT.y),
				};

				if (RuntimeOptions.RenderSurfaceNormals)
				{
					for (final Fragment f : fragments)
						drawSurfaceNormal(f);
				}

				// Update debug variables.
				Debug.RenderedPolygonCount.getAndAdd(1);

				// Perform primitive assembly on the fragments.
				primitiveAssembly(fragments);
			}
		}
	}
	/**
	 * The primitive assembly operation creates points, lines or polygons from
	 * fragments. In the case of polygons, missing fragments in the hollows of polygon
	 * triangles are interpolated. These primitives are then passed to the fragment program.
	 * @param fragments a set of 3 fragments that will create a primitive.
	 */
	public abstract void primitiveAssembly(final Fragment fragments[]);
	/**
	 * The vertex program is responsible for transforming a single vertex from
	 * model space to clip space, where it will be clipped and passed onto the
	 * rasteriser.
	 * The default vertex program simply converts the vertex from model space to
	 * clip space. No operations such as lighting are performed on the vertex.
	 * @param input the vertex to transform.
	 * @param output the location where the transformed vertex will be stored.
	 */
	public void vertexProgram(final Vertex input, final Vertex output)
	{
		output.position.setXYZW(MODELVIEWPROJECTION.multiply(input.position));
	}
	/**
	 * The fragment program calculates a color value from a given fragment's attributes. By default,
	 * the program does not perform any calculations and returns the fragment's original color.
	 * @param fragment the fragment from which to calculate a color value.
	 */
	public int fragmentProgram(final Fragment fragment)
	{
		return new ColorRGBA(fragment.r, fragment.g, fragment.b, fragment.a).merge();
	}
	/**
	 * Set the material attributes.
	 * @param material the material attributes to set.
	 */
	public final void setMaterial(final Material material)
	{
		inputMaterial = material;
	}
	/**
	 * Render a fragment's surface normal.
	 * @param fragment the fragment containing the normal to draw.
	 */
	private void drawSurfaceNormal(final Fragment fragment)
	{
		final int color = 0xffff0000;
		final Point P0 = new Point
		(
			(int)Math.round(fragment.x),
			(int)Math.round(fragment.y)
		);
		final Point P1 = new Point
		(
			(int)Math.round(fragment.x + (fragment.ni * 30)),
			(int)Math.round(fragment.y + (fragment.nj * 30))
		);

		int x0 = P0.x; int y0 = P0.y;
		int x1 = P1.x; int y1 = P1.y;

		float y1y0 = y1 - y0;
		float x1x0 = x1 - x0;

		// Methode de base
		float m = y1y0/x1x0;
		float b = y0 - (m * x0);

		framebuffer.setPixel(x0, y0, Float.MIN_VALUE, color);

		if (x1x0 == 0.0f)
		{
			int min = Math.min(y0, y1);
			int max = Math.max(y0, y1);

			for (int y = min; y <= max; y++)
				framebuffer.setPixel(x0, y, Float.MIN_VALUE, color);
		}
		else if (Math.abs(m) < 1)
		{
			int min = Math.min( x0, x1 );
			int max = Math.max( x0, x1 );

			for (int x = min; x <= max; x++)
			{
				final int y = Math.round((m * x) + b);
				framebuffer.setPixel(x, y, Float.MIN_VALUE, color);
			}
		}
		else
		{
			int min = Math.min(y0, y1);
			int max = Math.max(y0, y1);

			for (int y = min; y <= max; y++)
			{
				final int x = Math.round((y - b)/m);
				framebuffer.setPixel(x, y, Float.MIN_VALUE, color);
			}
		}
	}
	/**
	 * Convert the renderer to a string.
	 */
	@Override
	public String toString()
	{
		return type.getTitle();
	}
}