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
package clockwork.scene;

import java.util.Observable;
import java.util.Observer;

import clockwork.graphics.camera.Frustum;
import clockwork.graphics.camera.Viewport;
import clockwork.graphics.camera.projection.Projection;
import clockwork.graphics.camera.projection.ProjectionFactory;
import clockwork.graphics.renderer.RenderContext;
import clockwork.graphics.renderer.Renderer;
import clockwork.graphics.renderer.RendererFactory;
import clockwork.system.Debug;
import clockwork.system.RuntimeOptions;
import clockwork.types.math.Matrix4;
import clockwork.types.math.Orientation;
import clockwork.types.math.Point3f;
import clockwork.types.math.Vector3f;

/**
 * A SceneViewer allows us to view the scene, obviously. A viewer can be anything from
 * a camera to a telescope, but this class handles the important manipulations all
 * viewer's share which are, essentially, accessing matrix transformations.
 * @see http://www.opengl.org/archives/resources/faq/technical/transformations.htm
 */
public abstract class SceneViewer extends SceneObject implements Observer
{
	/**
	 * The Viewer's render context.
	 */
	private final RenderContext renderContext = new RenderContext(this);
	/**
	 * The camera's projection.
	 */
	private Projection projection = ProjectionFactory.getDefaultProjection();
	/**
	 * There can only be a single viewer active at a time. Is this it?
	 */
	private boolean isActive = false;
	/**
	 * The viewer's up vector.
	 */
	protected final Vector3f up = new Vector3f(0, 1, 0);
	/**
	 * The viewer's viewport.
	 */
	private final Viewport viewport = new Viewport();
	/**
	 * Has the VIEW matrix been updated?
	 */
	private boolean isUpdatedVIEW = true;
	/**
	 * Has the PROJECTION matrix been updated?
	 */
	private boolean isUpdatedPROJECTION = true;
	/**
	 * Calculate the VIEW matrix.
	 */
	protected abstract Matrix4 calculateVIEW();
	/**
	 * Return the viewing frustum.
	 */
	public abstract Frustum getFrustum();
	/**
	 * Instantiate a viewer with a given name, position, orientation, and up vector.
	 * @param name the viewer's name.
	 * @param position the viewer's position in the world.
	 * @param orientation the viewer's orientation in the world.
	 * @param up the viewer's up direction.
	 */
	protected SceneViewer
	(
		final String name,
		final Point3f position,
		final Orientation orientation,
		final Vector3f up
	)
	{
		super(name, position, orientation);
		setUp(up);

		// Make this viewer a scene observer.
		Scene.getUniqueInstance().addObserver(this);
	}
	/**
	 * Instantiate a viewer with a given name, position, and orientation.
	 * @param name the viewer's name.
	 * @param position the viewer's position in the world.
	 * @param orientation the viewer's orientation in the world.
	 */
	protected SceneViewer
	(
		final String name,
		final Point3f position,
		final Orientation orientation
	)
	{
		super(name, position, orientation);
		Scene.getUniqueInstance().addObserver(this);
	}
	/**
	 * Activate or deactivate the viewer.
	 * @param activate true to activate the viewer, false otherwise.
	 */
	public final void setActive(final boolean isActive)
	{
		this.isActive = isActive;
		SceneGraph.GUITreeModel.nodeChanged(GUITreeNode);

		// Change the scene's current viewer.
		if (isActive)
		{
			Scene.getUniqueInstance().setViewer(this);
			Debug.ViewerName = this.getName();
		}
		else
		{
			Scene.getUniqueInstance().removeViewer();
			Debug.ViewerName = "NONE";
		}
	}
	/**
	 * Return true if the viewer is active, false otherwise.
	 */
	public final boolean isActive()
	{
		return isActive;
	}
	/**
	 * Activate the viewer if it's deactivated or vice-versa.
	 */
	public void toggleActive()
	{
		setActive(!isActive);
	}
	/**
	 * Set the viewing frustum.
	 * @param frustum the viewing frustum to set.
	 */
	public abstract void setFrustum(final Frustum frustum);
	/**
	 * Get the camera's projection.
	 */
	public final Projection.Type getProjectionType()
	{
		return projection.getType();
	}
	/**
	 * Set the camera's projection.
	 * @param type the type of projection to set.
	 */
	public final void setProjection(final Projection.Type type)
	{
		if (type != null)
			setProjection(ProjectionFactory.get(type));
	}
	/**
	 * Set the camera's projection.
	 * @param projection the projection to set.
	 */
	public final void setProjection(final Projection projection)
	{
		if (projection != null && this.projection != projection)
		{
			this.projection = projection;
			setUpdatedPROJECTION(true);
		}
	}
	/**
	 * Return the type of renderer used by this viewer.
	 */
	public Renderer.Type getRendererType()
	{
		return renderContext.getRendererType();
	}
	/**
	 * Set the renderer used by this viewer.
	 * @param type the type of renderer to set.
	 */
	public final void setRenderer(final Renderer.Type type)
	{
		if (type != null)
			setRenderer(RendererFactory.get(type));
	}
	/**
	 * Set the renderer used by this viewer.
	 * @param renderer the renderer to set.
	 */
	public final void setRenderer(final Renderer renderer)
	{
		renderContext.setRenderer(renderer);
	}
	/**
	 * Set the viewer's position and update it's VIEW matrix.
	 */
	@Override
	public final void setPosition(final Point3f position)
	{
		if (position != null && !this.position.equals(position))
		{
			super.setPosition(position);
			setUpdatedVIEW(true);
		}
	}
	/**
	 * Set the viewer's orientation and update it's VIEW matrix.
	 */
	@Override
	public final void setOrientation(final Orientation orientation)
	{
		if (orientation != null && !this.orientation.equals(orientation))
		{
			super.setOrientation(orientation);
			setUpdatedVIEW(true);
		}
	}
	/**
	 * The Observer's update method, a method triggered by this object's registered
	 * observables. Since all viewer's observe the scene, this method will be called
	 * when the scene has been modified (changed). This, will in turn convert the
	 * scene into a set of renderable objects.
	 *
	 * @param observable the Observable object that triggered the update. This should be
	 * a scene object.
	 * @param unused an unused parameter.
	 */
	@Override
	public void update(final Observable observable, final Object unused)
	{
		if (isActive && observable != null && observable instanceof Scene)
		{
			final Scene scene = (Scene)observable;

			// Update the current VIEW and PROJECTION transformation matrices, if need be.
			if (isUpdatedVIEW)
			{
				renderContext.setVIEW(RuntimeOptions.EnableVIEW ? calculateVIEW() : new Matrix4());
				isUpdatedVIEW = false;
			}
			if (isUpdatedPROJECTION)
			{
				renderContext.setPROJECTION(calculatePROJECTION());
				isUpdatedPROJECTION = false;
			}
			// Convert the scene to a set of renderables, then render it.
			scene.render(renderContext);
		}
	}
	/**
	 * Get the VIEW transformation matrix.
	 */
	public Matrix4 getVIEW()
	{
		return renderContext.getVIEW();
	}
	/**
	 * Get the PROJECTION transformation matrix.
	 */
	public Matrix4 getPROJECTON()
	{
		return renderContext.getPROJECTION();
	}
	/**
	 * Calculate the PROJECTION transform.
	 */
	public Matrix4 calculatePROJECTION()
	{
		return RuntimeOptions.EnablePROJECTION ? projection.toMatrix(getFrustum()) : new Matrix4();
	}
	/**
	 * Does the VIEW matrix need to be updated?
	 */
	public final void setUpdatedVIEW(final boolean updated)
	{
		isUpdatedVIEW = updated;
	}
	/**
	 * Does the PROJECTION matrix need to be updated?
	 */
	public final void setUpdatedPROJECTION(final boolean updated)
	{
		isUpdatedPROJECTION = updated;
	}
	/**
	 * Get the viewer's up direction.
	 */
	public Vector3f getUp()
	{
		return up;
	}
	/**
	 * Set the viewer's up direction.
	 * @param up the direction to set.
	 */
	public void setUp(final Vector3f up)
	{
		this.up.setIJK(up);
	}
	/**
	 * Get the viewer's viewport.
	 */
	public Viewport getViewport()
	{
		return viewport;
	}
	/**
	 * Set the viewer's viewport.
	 * @param viewport the viewport to set.
	 */
	public void setViewport(final Viewport viewport)
	{
		this.viewport.copy(viewport);
		this.viewport.validate();
	}
	/**
	 * Return the viewer's render context.
	 */
	public RenderContext getRenderContext()
	{
		return renderContext;
	}
	/**
	 * Dispose of the viewer.
	 */
	@Override
	public void dispose()
	{
		if (Scene.getUniqueInstance().getViewer() == this)
			Scene.getUniqueInstance().removeViewer();

		super.dispose();
	}
	/**
	 * Convert the viewer data to string format.
	 */
	@Override
	public String getState()
	{
		if (isActive)
			return "active";
		else
			return null;
	}
}
