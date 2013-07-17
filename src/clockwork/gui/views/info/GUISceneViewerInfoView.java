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

import clockwork.graphics.camera.Viewport;
import clockwork.graphics.camera.projection.Projection;
import clockwork.graphics.renderer.Renderer;
import clockwork.gui.component.GUIDropdown;
import clockwork.gui.component.GUIHint;
import clockwork.gui.controls.ControlPanelInterface;
import clockwork.gui.views.GUIObjectView;
import clockwork.scene.SceneViewer;



public final class GUISceneViewerInfoView extends GUISceneNodeInfoView<SceneViewer>
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -8772628207093666826L;
	/**
	 * The renderer dropdown list.
	 */
	private final GUIDropdown<Renderer.Type> renderers =
	new GUIDropdown<Renderer.Type>("Renderer", Renderer.Type.values());
	/**
	 * The projection dropdown list.
	 */
	private final GUIDropdown<Projection.Type> projections =
	new GUIDropdown<Projection.Type>("Projection", Projection.Type.values());
	/**
	 * A hint to toggle the viewer.
	 */
	private final GUIHint toggleViewerHint =
	new GUIHint("You may enable or disable a viewer by double clicking it");
	/**
	 * TODO Should contain functionality offered by:
	 * The viewport controls.
	 */
//	private final ViewportInputControls viewport = new ViewportInputControls();
	/**
	 * Instantiate a GUIViewerInfoView attached to a control panel interface.
	 * @param parent the control panel interface.
	 */
	public GUISceneViewerInfoView(final ControlPanelInterface parent)
	{
		super(parent, "Viewer", "Viewer information");

		final ItemListener dropdownListItemChangeListener = new ItemListener()
		{
			@Override
			public void itemStateChanged(final ItemEvent e)
			{
				// The event is fired twice: once when an item is deselected and again when a new one
				// is selected. We only want it to fire when a new item is selected.
				if (e.getStateChange() == ItemEvent.SELECTED)
					parent.update();
			}
		};
		renderers.addItemListener(dropdownListItemChangeListener);
		projections.addItemListener(dropdownListItemChangeListener);
		add(renderers);
		add(projections);
//		add(viewport);
		add(toggleViewerHint);
	}
	/**
	 * @see GUIObjectView#write
	 */
	@Override
	public void write(final SceneViewer input)
	{
		if (input != null)
		{
			super.write(input);
			renderers.setSelectedItem(input.getRendererType());
			projections.setSelectedItem(input.getProjectionType());
			//TODO viewport.set(input.getViewport);
		}
	}
	/**
	 * @see GUIObjectView#read
	 */
	@Override
	public SceneViewer read(final SceneViewer output)
	{
		if (output != null)
		{
			super.read(output);
			output.setRenderer(renderers.getSelectedItem());
			output.setProjection(projections.getSelectedItem());
			output.setViewport(new Viewport()); //FIXME
		}
		return output;
	}
}
