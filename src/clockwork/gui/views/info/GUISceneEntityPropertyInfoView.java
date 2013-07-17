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

import clockwork.gui.component.GUILabeledTextArea;
import clockwork.gui.controls.ControlPanelInterface;
import clockwork.gui.views.GUIObjectView;
import clockwork.scene.SceneEntityProperty;


/**
 * TODO Explain me.
 */
public abstract class GUISceneEntityPropertyInfoView<PropertyType extends SceneEntityProperty<?>>
extends GUISceneNodeInfoView<PropertyType>
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 376982096913980591L;
	/**
	 * The text area that shows the node's owner's name.
	 */
	private final GUILabeledTextArea entityNameTextArea = new GUILabeledTextArea("Proprietor");
	/**
	 * Instantiate a named GUISceneEntityPropertyInfoView attached to a control panel interface,
	 * and with a description.
	 * @param parent the control panel interface.
	 * @param title the view's title.
	 * @param description the view's description.
	 */
	protected GUISceneEntityPropertyInfoView
	(
		final ControlPanelInterface parent,
		final String title,
		final String description
	)
	{
		super(parent, title, description);
		add(entityNameTextArea);
	}
	/**
	 * @see GUIObjectView#setValue
	 */
	@Override
	public void write(final PropertyType input)
	{
		super.write(input);
		entityNameTextArea.setText(input.getEntity().getName());
	}
}
