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

import java.io.IOException;

import clockwork.asset.AssetManager;
import clockwork.types.math.Orientation;

public class SuzanneRigidBody extends RigidBody
{
	/**
	 * The default constructor.
	 */
	public SuzanneRigidBody()
	{
		this("Suzanne");
	}
	/**
	 * Instantiate a named cube rigid body.
	 * @param name the rigid body's name.
	 */
	public SuzanneRigidBody(final String name)
	{
		super(name);
		setOrientation(new Orientation(180.0, -30, 0));
		try
		{
			setModel3D(AssetManager.LoadModel3D("assets/models/suzanne/suzanne.obj"));
		}
		catch (IOException e)
		{}
	}
	/**
	 * Add a default state to the rigid body.
	 */
	@Override
	public String getState()
	{
		final String superState = super.getState();
		if (superState != null && superState.length() > 1)
			return String.format("preset %s", superState);
		else
			return "preset";
	}
}