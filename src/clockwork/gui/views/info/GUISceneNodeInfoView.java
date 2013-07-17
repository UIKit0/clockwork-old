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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;

import clockwork.gui.component.GUILabeledTextArea;
import clockwork.gui.component.GUILabeledTextField;
import clockwork.gui.controls.ControlPanelInterface;
import clockwork.gui.views.GUIObjectView;
import clockwork.scene.SceneGraph;


/**
 * TODO Explain me.
 */
public abstract class GUISceneNodeInfoView<EntityType extends SceneGraph.Node>
extends GUIObjectView<EntityType>
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 376982096913980591L;
	/**
	 * The text area that shows the node's identifier.
	 */
	private final GUILabeledTextArea identifierTextArea = new GUILabeledTextArea("Identifier");
	/**
	 * The text area that shows the node's memory footprint.
	 */
	private final GUILabeledTextArea memoryTextArea = new GUILabeledTextArea("Memory footprint");
	/**
	 * The text field that shows the node's name.
	 */
	private final GUILabeledTextField nameTextField = new GUILabeledTextField("Name");
	/**
	 * Has a request to change the node's name been made explicitly?
	 */
	private boolean hasNameChanged = false;
	/**
	 * Instantiate a named GUISceneNodeInfoView attached to a control panel interface,
	 * and with a description.
	 * @param parent the control panel interface.
	 * @param title the view's title.
	 * @param description the view's description.
	 */
	protected GUISceneNodeInfoView
	(
		final ControlPanelInterface parent,
		final String title,
		final String description
	)
	{
		super(title, description);
		nameTextField.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				hasNameChanged = true;
				parent.update();
			}
		});
		add(Box.createRigidArea(new Dimension(0, 5))); // Add vertical whitespace.
		add(identifierTextArea);
		add(memoryTextArea);
		add(nameTextField);
	}
	/**
	 * @see GUIObjectView#setValue
	 */
	@Override
	public void write(final EntityType input)
	{
		identifierTextArea.setText(input.getIdentifier().toString());
		memoryTextArea.setText(input.getMemoryFootprint() + " MiB");
		nameTextField.setText(input.getName());
		nameTextField.enableModifyButton(input.isNameEditable());
	}
	/**
	 * @see GUIObjectView#read
	 */
	@Override
	public EntityType read(final EntityType output)
	{
		// If the output is empty, don't create a new instance of EntityType.
		if (output != null)
		{
			if (hasNameChanged)
			{
				output.setName(nameTextField.getText());
				hasNameChanged = false;
			}
		}
		return output;
	}
}
