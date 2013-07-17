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
package clockwork.gui.controls.scene;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashSet;

import clockwork.gui.actions.GUIActionAdd;
import clockwork.gui.actions.GUIActionAddCamera;
import clockwork.gui.actions.GUIActionAddLight;
import clockwork.gui.actions.GUIActionAddModel3D;
import clockwork.gui.actions.GUIActionAddStanfordBunny;
import clockwork.gui.actions.GUIActionAddSuzanne;
import clockwork.gui.actions.GUIActionAddUtahTeapot;
import clockwork.gui.presentation.AbstractTreeView;
import clockwork.scene.SceneGraph;
import clockwork.scene.SceneViewer;


public final class SceneTreeView extends AbstractTreeView<SceneGraph.Node>
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -6954286694973002884L;
	/**
	 * The scene graph's root node.
	 */
	@SuppressWarnings("unused")
	private final SceneGraph.Root root;
	/**
	 * Instantiate a named SceneTreeView attached to a given control panel
	 * and bound to a scene graph.
	 */
	protected SceneTreeView
	(
		final SceneControlPanel parent,
		final SceneGraph graph
	)
	{
		super(parent, "The Scene Hierarchy", graph.getGUITreeModel());
		this.root = graph.getRoot();
		this.setToggleClickCount(0);
		this.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(final MouseEvent e)
			{
				final int clickCount = e.getClickCount();
				final SceneGraph.Node clickedNode = getSelectedItem();

				// Double-clicking a node prunes it. We should pay attention to viewers though.
				if (clickCount == 2)
				{
					if (clickedNode instanceof SceneViewer)
					{
						// When we double-click a viewer, we activate or deactivate it.
						final SceneViewer viewer = (SceneViewer)clickedNode;
						viewer.toggleActive();
					}
					else
						clickedNode.setPruned(!clickedNode.isPruned());

					// Update the view and the model.
					parent.update();
				}
			}
		});
		LinkedHashSet<GUIActionAdd<?>> addActions = new LinkedHashSet<GUIActionAdd<?>>();
		addActions.add(new GUIActionAddModel3D(parent));
		addActions.add(new GUIActionAddCamera(parent));
		addActions.add(new GUIActionAddLight(parent));
		addActions.add(new GUIActionAddSuzanne(parent));
		addActions.add(new GUIActionAddUtahTeapot(parent));
		addActions.add(new GUIActionAddStanfordBunny(parent));

		setAvailableAddActions(addActions);
	}
}
