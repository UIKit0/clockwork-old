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
package clockwork.gui.actions;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import clockwork.asset.AssetManager;
import clockwork.graphics.Appearance;
import clockwork.graphics.Model3D;
import clockwork.gui.controls.scene.SceneControlPanel;
import clockwork.scene.SceneGraph;
import clockwork.scene.SceneObject;


public class GUIActionAddModel3D extends GUIActionAdd<SceneControlPanel>
{
	/**
	 * Instantiate a new action attached to a control panel.
	 * @param controlPanel the control panel that this action is attached to.
	 */
	public GUIActionAddModel3D(final SceneControlPanel controlPanel)
	{
		super(controlPanel, "Add a 3D model");
	}
	/**
	 * @see GUIAddAction#onActionPerformed.
	 */
	@Override
	public void onActionPerformed(final ActionEvent e)
	{
		final File file = controlPanel.getUserInterface().getFileChooser().openModel3DFile();
		if (file != null)
		{
			try
			{
				final Model3D model3D = AssetManager.LoadModel3D(file);
				if (model3D != null)
				{
					// Attach the 3D model to a scene object. If the object is a rigid
					// body, then we can directly attach the model. Otherwise, we need to
					// create an appearance property for the object first, then attach the
					// model to the object. When all is done, update the scene.
					final SceneGraph.Node selectedItem = controlPanel.getSelectedItem();
					if (selectedItem instanceof SceneObject)
					{
						final SceneObject object = (SceneObject)selectedItem;
						Appearance appearance = (Appearance)object.getChild("Property::Appearance");
						if (appearance == null)
						{
							appearance = new Appearance(object);
							object.addChild(appearance);
						}
						appearance.setModel3D(model3D);
					}
					controlPanel.update();
				}
			}
			catch (final IOException exception)
			{
				final String header = "Read error";
				final String message = "Could not read the file " + file.getName();

				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(controlPanel, message, header, JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
