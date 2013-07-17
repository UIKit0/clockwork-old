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

import clockwork.graphics.renderer.RenderProcessingQueue;
import clockwork.scene.SceneGraph.Node;
import clockwork.types.Matrix4Stack;
import clockwork.types.math.Matrix4;
import clockwork.types.math.Orientation;
import clockwork.types.math.Point3f;
import clockwork.types.math.Vector3f;

/**
 * SceneObject's are scene entities that have a position, orientation and scale.
 */
public abstract class SceneObject extends SceneGraph.Branch
{
	/**
	 * The object's position in the world.
	 */
	protected final Point3f position = new Point3f(0, 0, 0);
	/**
	 * The object's orientation.
	 */
	protected final Orientation orientation = new Orientation();
	/**
	 * The object's scale.
	 */
	protected final Vector3f scale = new Vector3f(1.0f, 1.0f, 1.0f);
	/**
	 * Instantiate a SceneObject with a given name, position, orientation, and scale.
	 */
	protected SceneObject
	(
		final String name,
		final Point3f position,
		final Orientation orientation,
		final Vector3f scale
	)
	{
		super(name);
		setPosition(position);
		setOrientation(orientation);
		setScale(scale);
	}
	/**
	 * Instantiate a SceneObject with a given name.
	 */
	protected SceneObject(final String name)
	{
		this(name, new Point3f(), null, Vector3f.Ones());
	}
	/**
	 * Instantiate a SceneObject with a given name and position.
	 */
	protected SceneObject(final String name, final Point3f position)
	{
		this(name, position, null, Vector3f.Ones());
	}
	/**
	 * Instantiate a SceneObject with a given name, position and orientation.
	 */
	protected SceneObject(final String name, final Point3f position, final Orientation orientation)
	{
		this(name, position, orientation, Vector3f.Ones());
	}
	/**
	 * Get the object's position.
	 */
	public Point3f getPosition()
	{
		return position;
	}
	/**
	 * Set the object's position.
	 */
	public void setPosition(final Point3f position)
	{
		this.position.setXYZ(position.getXYZ());
	}
	/**
	 * Add a delta value to the current position.
	 */
	public final void addPosition(final Point3f delta)
	{
		position.x += delta.x;
		position.y += delta.y;
		position.z += delta.z;
	}
	/**
	 * Get the object's orientation.
	 */
	public final Orientation getOrientation()
	{
		return orientation;
	}
	/**
	 * Set the object's orientation.
	 * @param orientation the orientation to set.
	 */
	public void setOrientation(final Orientation orientation)
	{
		if (orientation != null)
		{
			this.orientation.copy(orientation);
			this.orientation.clamp();
		}
	}
	/**
	 * Add a delta value to the current orientation.
	 * @param droll the delta roll value.
	 * @param dyaw the delta yaw value.
	 * @param dpitch the delta pitch value.
	 */
	public final void addOrientation(final double droll, final double dyaw, final double dpitch)
	{
		this.orientation.roll += droll;
		this.orientation.yaw += dyaw;
		this.orientation.pitch += dpitch;
		this.orientation.clamp();
	}
	/**
	 * Get the object's scale.
	 */
	public final Vector3f getScale()
	{
		return scale;
	}
	/**
	 * Set the object's scale.
	 */
	public final void setScale(final Vector3f scale)
	{
		this.scale.setIJK(scale.getIJK());
	}
	/**
	 * Add a delta value to the current scale.
	 */
	public final void addScale(final Vector3f delta)
	{
		scale.add(delta);
	}
	/**
	 * Update the object.
	 */
	@Override
	public void update(final float deltatime)
	{
		// TODO Auto-generated method stub
	}
	/**
	 * @see SceneGraph.Node#buildRenderProcessingQueue
	 */
	@Override
	public void buildRenderProcessingQueue
	(
		final Matrix4 VIEW,
		final Matrix4Stack stack,
		final RenderProcessingQueue queue
	)
	{
		// Calculate the object's current MODEL transformation matrix.
		final Matrix4 CMTM = stack.peek().multiply(Matrix4.Model(position, orientation, scale));

		// Push the CMTM onto the stack.
		stack.push(CMTM);

		// Convert the node's children to Renderables.
		for (final Node child : getChildren())
		{
			if (!child.isPruned())
				child.buildRenderProcessingQueue(VIEW, stack, queue);
		}

		// Before leaving this node, remove the CMTM that was pushed onto the stack.
		stack.pop();
	}
}