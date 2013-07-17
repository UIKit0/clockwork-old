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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import clockwork.gui.component.GUIDropdown;
import clockwork.gui.component.GUIHint;
import clockwork.gui.controls.ControlPanelInterface;
import clockwork.gui.views.GUIObjectView;
import clockwork.physics.lighting.LightEmitter;

//TODO Add functionality provided by LightTypeInputComponent(mouseAdapter)
public final class GUILightInfoView extends GUISceneEntityPropertyInfoView<LightEmitter>
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 4412659343896583600L;
	/**
	 * The available types of light.
	 */
	private final GUIDropdown<LightEmitter.Type> lightTypes =
	new GUIDropdown<LightEmitter.Type>("Type", LightEmitter.Type.values());
	/**
	 * A hint to toggle the light.
	 */
	private final GUIHint toggleLightHint =
	new GUIHint("You may turn a light on or off by double clicking it");
	/**
	 * Instantiate a GUILightInfoView attached to a control panel interface.
	 * @param parent the control panel interface.
	 */
	public GUILightInfoView(final ControlPanelInterface parent)
	{
		super(parent, "Light", "Light information");

		lightTypes.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(final ItemEvent e)
			{
				// The event is fired twice: once when an item is deselected and again when a new one
				// is selected. We only want it to fire when a new item is selected.
				if (e.getStateChange() == ItemEvent.SELECTED)
					parent.update();
			}
		});
		add(lightTypes);
		add(toggleLightHint);
	}
	/**
	 * @see GUIObjectView#write
	 */
	@Override
	public void write(final LightEmitter input)
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
	public LightEmitter read(final LightEmitter output)
	{
		if (output != null)
		{
			super.read(output);

			//TODO
		}
		return output;
	}
}
