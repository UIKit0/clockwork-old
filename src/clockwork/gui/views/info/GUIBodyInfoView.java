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
package clockwork.gui.views.info;

import clockwork.gui.component.GUIHint;
import clockwork.gui.controls.ControlPanelInterface;
import clockwork.gui.views.GUIObjectView;
import clockwork.physics.body.RigidBody;


public final class GUIBodyInfoView extends GUISceneNodeInfoView<RigidBody>
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 6329454855216801523L;
	/**
	 * A hint to toggle the light.
	 */
	private final GUIHint toggleBodyHint =
	new GUIHint("You may show or hide a body by double clicking it");
	/**
	 * Instantiate a GUIBodyInfoView attached to a control panel interface.
	 * @param parent the control panel interface.
	 */
	public GUIBodyInfoView(final ControlPanelInterface parent)
	{
		super(parent, "Body", "Rigid body information");
		add(toggleBodyHint);
	}
	/**
	 * @see GUIObjectView#write
	 */
	@Override
	public void write(final RigidBody input)
	{
		if (input != null)
		{
			super.write(input);

			//TODO
		}
	}
	/**
	 * @see GUIObjectView#read
	 */
	@Override
	public RigidBody read(final RigidBody output)
	{
		if (output != null)
		{
			super.read(output);

			//TODO
		}
		return output;
	}
}
