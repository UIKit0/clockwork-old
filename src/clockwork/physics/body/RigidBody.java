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
package clockwork.physics.body;

import clockwork.graphics.Appearance;
import clockwork.graphics.Model3D;
import clockwork.scene.SceneObject;

public class RigidBody extends SceneObject
{
	/**
	 * A reference to the appearance property. This allows us to retrieve
	 * our 3D model when changes need to be made.
	 */
	final Appearance appearance;
	/**
	 * Instantiate a named rigid body with a given 3D model.
	 * @param name the rigid body's name.
	 * @param model3D the rigid body's 3D model.
	 */
	public RigidBody(final String name, final Model3D model3D)
	{
		super(name);
		this.appearance = new Appearance(this);
		this.addChild(this.appearance);

		if (model3D != null)
			this.appearance.setModel3D(model3D);
	}
	/**
	 * Instantiate the rigid body with a given name.
	 */
	public RigidBody(String name)
	{
		this(name, null);
	}
	/**
	 * Return the rigid body's appearance.
	 */
	public Appearance getAppearance()
	{
		return appearance;
	}
	/**
	 * Return the rigid body's 3D model.
	 */
	public Model3D getModel3d()
	{
		return appearance.getModel3D();
	}
	/**
	 * Set the rigid body's 3D model.
	 * @param model3D the 3D model to set.
	 */
	public void setModel3D(final Model3D model3D)
	{
		if (model3D != null)
			appearance.setModel3D(model3D);
	}
	/**
	 * Return the rigid body's state.
	 */
	@Override
	public String getState()
	{
		String state = "";
		if (isPruned())
			state += "hidden ";

		final int length = state.length();
		if (length > 2)
			state = state.substring(0, length - 1);
		return state;
	}
}
