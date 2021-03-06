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

import clockwork.graphics.camera.Camera;
import clockwork.gui.controls.scene.SceneControlPanel;
import clockwork.scene.Scene;


public final class GUIActionAddCamera extends GUIActionAdd<SceneControlPanel>
{
	/**
	 * The suffix of the next camera to add.
	 */
	private int nextCameraSuffix = 0;
	/**
	 * Instantiate a new action attached to a control panel.
	 * @param controlPanel the control panel that this action is attached to.
	 */
	public GUIActionAddCamera(final SceneControlPanel controlPanel)
	{
		super(controlPanel, "Add a camera");
	}
	/**
	 * @see GUIAddAction#onActionPerformed.
	 */
	@Override
	public void onActionPerformed(final ActionEvent e)
	{
		Scene.getUniqueInstance().add(new Camera("Camera " + nextCameraSuffix));
		++nextCameraSuffix;
		controlPanel.update();
	}
}