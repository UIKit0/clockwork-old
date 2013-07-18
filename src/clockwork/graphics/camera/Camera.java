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
package clockwork.graphics.camera;

import clockwork.graphics.camera.projection.Projection;
import clockwork.graphics.camera.projection.ProjectionFactory;
import clockwork.physics.lighting.LightEmitter;
import clockwork.scene.SceneViewer;
import clockwork.types.math.Matrix4;
import clockwork.types.math.Orientation;
import clockwork.types.math.Point3f;
import clockwork.types.math.Vector3f;


public class Camera extends SceneViewer
{
	/**
	 * Default values.
	 */
	public static final Point3f DEFAULT_POSITION = new Point3f(0, 0, -2);
	public static final Vector3f DEFAULT_UP = new Vector3f(0, 1, 0);
	/**
	 * The camera's viewing frustum volume.
	 */
	private final Frustum frustum = new Frustum();
	/**
	 * Instantiate a camera with a given position, orientation, viewport,
	 * frustum and projection.
	 */
	public Camera
	(
		final String name,
		final Point3f position,
		final Orientation orientation,
		final Viewport viewport,
		final Frustum frustum,
		final Projection.Type projection
	)
	{
		super(name, position, orientation, DEFAULT_UP);
		setViewport(viewport);
		setFrustum(frustum);
		setProjection(projection);
		addChild(new LightEmitter(this));
	}
	/**
	 * Instantiate a camera with a given position, orientation, viewport and frustum.
	 */
	public Camera
	(
		final String name,
		final Point3f position,
		final Orientation orientation,
		final Viewport viewport,
		final Frustum frustum
	)
	{
		this(name, position, orientation, viewport, frustum, ProjectionFactory.getDefaultProjection().getType());
	}
	/**
	 * Instantiate a camera with a given position, orientation, viewport and projection.
	 */
	public Camera
	(
		final String name,
		final Point3f position,
		final Orientation orientation,
		final Viewport viewport,
		final Projection.Type projection
	)
	{
		this(name, position, orientation, viewport, null, projection);
	}
	/**
	 * Instantiate a camera with a given position, orientation and viewport.
	 */
	public Camera
	(
		final String name,
		final Point3f position,
		final Orientation orientation,
		final Viewport viewport
	)
	{
		this(name, position, orientation, viewport, ProjectionFactory.getDefaultProjection().getType());
	}
	/**
	 * Instantiate a Camera with a given name.
	 */
	public Camera(final String name)
	{
		this(name, DEFAULT_POSITION, null, new Viewport());
	}
	/**
	 * Set the camera's viewing frustum.
	 * @param frustum the frustum volume to set.
	 */
	@Override
	public void setFrustum(final Frustum frustum)
	{
		if (frustum != null && !this.frustum.equals(frustum))
		{
			this.frustum.copy(frustum);
			setUpdatedPROJECTION(true);
		}
	}
	/**
	 * Get the camera's viewing frustum.
	 */
	@Override
	public Frustum getFrustum()
	{
		return frustum;
	}
	/**
	 * Calculate the VIEW transformation matrix.
	 */
	@Override
	protected Matrix4 calculateVIEW()
	{
		return Matrix4.lookAt(position, orientation.toPoint3f(), up);
	}
}