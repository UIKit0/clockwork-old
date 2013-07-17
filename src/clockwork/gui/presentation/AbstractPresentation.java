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
package clockwork.gui.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.LinkedHashSet;

import javax.swing.JPanel;

import clockwork.gui.actions.GUIActionAdd;
import clockwork.gui.component.GUIButton;
import clockwork.gui.component.GUIDropdown;
import clockwork.gui.component.GUIFixedSizePanel;
import clockwork.gui.component.buttons.AddItemButton;
import clockwork.gui.component.buttons.DeleteItemButton;
import clockwork.gui.controls.ControlPanel;
import clockwork.scene.Scene;


/**
 * The AbstractPresentation object allows us to visually present a set of
 * entries to the application's user with the goal of being interacted with.
 */
public abstract class AbstractPresentation<Entry> extends GUIFixedSizePanel
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -3566666988220659626L;
	/**
	 * The panel's dimension.
	 */
	private static final Dimension DIMENSION = new Dimension(450, 290);
	/**
	 * A reference to the unique instance of the scene.
	 */
	protected final Scene scene = Scene.getUniqueInstance();
	/**
	 * The control panel that this presentation is attached to.
	 */
	protected final ControlPanel<Entry, ?, ?> parent;
	/**
	 * The presentation's title.
	 */
	private final String title;
	/**
	 * The button panel.
	 */
	private final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
	/**
	 * A dropdown menu that provides the set of actions describing how
	 * specific items can be added to the scene.
	 */
	private final GUIDropdown<GUIActionAdd<?>> availableAddActions;
	/**
	 * The "add item" button.
	 */
	private final AddItemButton addItemButton;
	/**
	 * The "delete item" button.
	 */
	private final DeleteItemButton deleteItemButton;
	/**
	 * Initialise the presentation.
	 */
	public abstract void initialise();
	/**
	 * Select the next item in the presentation.
	 */
	public abstract Entry selectNextItem();
	/**
	 * Return the currently selected item.
	 */
	public abstract Entry getSelectedItem();
	/**
	 * Instatiate a named presentation attached to a control panel.
	 */
	public AbstractPresentation
	(
		final ControlPanel<Entry, ?, ?> parent,
		final String title
	)
	{
		super(DIMENSION, new BorderLayout());
		this.parent = parent;
		this.title = title;
		this.availableAddActions = new GUIDropdown<GUIActionAdd<?>>("Action");
		this.availableAddActions.setFontColor(Color.white);
		this.addItemButton = new AddItemButton(parent, availableAddActions);
		this.deleteItemButton = new DeleteItemButton(parent);

		// Create add and delete buttons and add them to the layout.
		buttonPanel.setBackground(Color.black);
		buttonPanel.add(availableAddActions);
		buttonPanel.add(addItemButton);
		buttonPanel.add(deleteItemButton);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	/**
	 * Set the actions that can be performed when adding elements to a scene.
	 * @param items a collection of items that can be added to the scene.
	 */
	protected void setAvailableAddActions(final LinkedHashSet<GUIActionAdd<?>> actions)
	{
		if (actions != null)
			availableAddActions.setChoices(actions);
	}
	/**
	 * Return the presentation's title.
	 */
	public final String getTitle()
	{
		return title;
	}
	/**
	 * The action performed when an item in the presentation is selected.
	 */
	protected final void onItemSelected()
	{
		final Entry entry = getSelectedItem();
		if (entry != null)
			parent.setCurrentEntry(entry);
	}
	/**
	 * Add a button to the button panel.
	 * @param button the button to add.
	 */
	public final void addButton(final GUIButton button)
	{
		if (button != null)
			this.buttonPanel.add(button);
	}
	/**
	 * Show or hide the "add item" button.
	 */
	public final void showAddButton(final boolean show)
	{
		addItemButton.setVisible(show);
		availableAddActions.setVisible(show);
	}
	/**
	 * Show or hide the "delete item" button.
	 */
	public final void showDeleteButton(final boolean show)
	{
		deleteItemButton.setVisible(show);
	}
}
