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

import java.awt.event.ActionEvent;

import clockwork.gui.controls.scene.SceneControlPanel;
import clockwork.physics.lighting.Light;
import clockwork.physics.lighting.LightEmitter;
import clockwork.scene.Scene;
import clockwork.scene.SceneGraph;
import clockwork.scene.SceneObject;


/**
 * Add a light emitter property to any scene object, or a light object to the scene.
 * If the currently selected item is a scene object, then a light emitter property is added to
 * it. Otherwise, a light object is added to the scene.
 */
public final class GUIActionAddLight extends GUIActionAdd<SceneControlPanel>
{
	/**
	 * The suffix of the next camera to add.
	 */
	private int nextLightSuffix = 0;
	/**
	 * Instantiate a new action.
	 * @param controlPanel the control panel that this action is attached to.
	 */
	public GUIActionAddLight(final SceneControlPanel controlPanel)
	{
		super(controlPanel, "Add a light emitter");
	}
	/**
	 * @see GUIAddAction#onActionPerformed.
	 */
	@Override
	public void onActionPerformed(final ActionEvent e)
	{
		// Add the light to the currently selected item if it's a branch node. If not,
		// add the item to the root.
		final SceneGraph.Node selectedItem = controlPanel.getSelectedItem();
		if (selectedItem instanceof SceneObject)
		{
			final SceneObject object = (SceneObject)selectedItem;
			object.addChild(new LightEmitter(object));
		}
		else
		{
			// Instantiate a new light.
			Scene.getUniqueInstance().add(new Light("Light " + nextLightSuffix));
			++nextLightSuffix;
		}
		controlPanel.update();
	}
}
