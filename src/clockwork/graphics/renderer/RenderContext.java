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

import clockwork.graphics.camera.Viewport;
import clockwork.scene.SceneViewer;
import clockwork.types.Matrix4Stack;
import clockwork.types.math.Matrix4;
import clockwork.types.math.Point3f;

/**
 * A render context contains the information describing how a set of Renderables
 * is handled by a renderer. It also contains transformation matrices that will be
 * applied to the Renderables.
 */
public final class RenderContext
{
	/**
	 * The scene viewer that owns this render context.
	 */
	private final SceneViewer viewer;
	/**
	 * The actual renderer used to draw the scene in this context.
	 */
	private Renderer renderer;
	/**
	 * A queue that will be processed by the renderer.
	 */
	private final RenderProcessingQueue renderQueue = new RenderProcessingQueue();
	/**
	 * A matrix stack to store matrix transformations that will be
	 * applied to scene objects.
	 */
	private final Matrix4Stack matrixStack = new Matrix4Stack();
	/**
	 * The camera's position. This is needed for lighting equations.
	 */
	private final Point3f viewpoint = new Point3f(0, 0, 0);
	/**
	 * TODO Describe me.
	 * The VIEW transformation matrix.
	 */
	private final Matrix4 VIEW = new Matrix4();
	/**
	 * TODO Describe me.
	 * The inverse VIEW transformation matrix.
	 */
//	private final Matrix inverseVIEW = Matrix.getIdentityMatrix(4);
	/**
	 * TODO Describe me.
	 * The PROJECTION transformation matrix.
	 */
	private final Matrix4 PROJECTION = new Matrix4();
	/**
	 * Instantiate a RenderContext that is attached to a scene viewer.
	 */
	public RenderContext(final SceneViewer viewer)
	{
		this.viewer = viewer;
		setRenderer(RendererFactory.getDefaultRenderer());
	}
	/**
	 * Get the viewpoint.
	 */
	public Point3f getViewpoint()
	{
		return viewpoint;
	}
	/**
	 * Set the viewpoint.
	 */
	public void setViewpoint(final Point3f viewpoint)
	{
		this.viewpoint.setXYZ(viewpoint.getXYZ());
	}
	/**
	 * Get the VIEW transformation matrix.
	 */
	public Matrix4 getVIEW()
	{
		return VIEW;
	}
	/**
	 * Set the VIEW transformation matrix.
	 */
	public void setVIEW(final Matrix4 view)
	{
		VIEW.copy(view);
	}
	/**
	 * Get the PROJECTION transformation matrix.
	 */
	public Matrix4 getPROJECTION()
	{
		return PROJECTION;
	}
	/**
	 * Set the PROJECTION transformation matrix.
	 */
	public void setPROJECTION(final Matrix4 projection)
	{
		PROJECTION.copy(projection);
	}
	/**
	 * Get the viewer's viewport.
	 */
	public Viewport getViewport()
	{
		return viewer.getViewport();
	}
	/**
	 * Return the context's render processing queue.
	 */
	public RenderProcessingQueue getRenderProcessingQueue()
	{
		return renderQueue;
	}
	/**
	 * Return the context's matrix stack.
	 */
	public Matrix4Stack getMatrixStack()
	{
		return matrixStack;
	}
	/**
	 * Set the renderer to use.
	 * @param renderer the renderer to set.
	 */
	public void setRenderer(final Renderer renderer)
	{
		if (renderer != null)
		{
			this.renderer = renderer;
			this.renderer.prepare();
		}
	}
	/**
	 * Return the type of renderer used in this context.
	 */
	public Renderer.Type getRendererType()
	{
		return renderer.getType();
	}
	/**
	 * Get the actual renderer used in this context.
	 */
	public Renderer getRenderer()
	{
		return renderer;
	}
	/**
	 * Apply the current renderer in this context.
	 */
	public void applyRenderer()
	{
		renderer.apply(this);
	}
	/**
	 * Convert the render context's data into string format.
	 */
	@Override
	public String toString()
	{
		return "Context: " + viewer;
	}
}
