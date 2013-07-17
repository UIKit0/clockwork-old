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
package clockwork.graphics;

import clockwork.graphics.renderer.RenderProcessingQueue;
import clockwork.graphics.renderer.Renderable;
import clockwork.scene.SceneEntityProperty;
import clockwork.scene.SceneObject;
import clockwork.types.Matrix4Stack;
import clockwork.types.math.Matrix4;

/**
 * The appearance property describes the look and feel of a rigid body. In reality,
 * this class acts as a wrapper for the {@link Model3D}} class which provides the
 * actual functionality.
 */
public final class Appearance extends SceneEntityProperty<SceneObject>
{
	/**
	 * The entity's 3D model.
	 */
	private Model3D model3D;
	/**
	 * Instantiate a named Appearance attached to a scene object.
	 * @param name the appearance's name.
	 * @param object the scene object that this appearance is attached to.
	 */
	public Appearance(final SceneObject object)
	{
		super("Property::Appearance", object);
	}
	/**
	 * Return the 3D model.
	 */
	public Model3D getModel3D()
	{
		return model3D;
	}
	/**
	 * Set the 3D model.
	 * @param model3D the 3D model to set.
	 */
	public void setModel3D(final Model3D model3D)
	{
		this.model3D = model3D;
	}
	/**
	 * @see {@link SceneEntityProperty#update(float)}}
	 */
	@Override
	public void update(float dt){}
	/**
	 * @see {@link SceneEntityProperty#buildRenderProcessingQueue(Matrix4, Matrix4Stack, RenderProcessingQueue)}}
	 */
	@Override
	public void buildRenderProcessingQueue
	(
		final Matrix4 VIEW,
		final Matrix4Stack stack,
		final RenderProcessingQueue queue
	)
	{
		if (model3D != null)
			queue.add(new Renderable(model3D, stack.peek()));
	}
}