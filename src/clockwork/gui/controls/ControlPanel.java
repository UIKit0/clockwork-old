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
package clockwork.gui.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import clockwork.gui.UserInterface;
import clockwork.gui.presentation.AbstractPresentation;
import clockwork.scene.Scene;
import clockwork.scene.SceneGraph;


/**
 * A control panel is a way to manipulate entries on-the-fly. It is comprised of a
 * presentation of entries and a set of input controls to manipulate said entries.
 * A presentation could be a list or a tree of entries, for example.
 */
public abstract class ControlPanel
<
	Entry,
	Presentation extends AbstractPresentation<Entry>,
	Controls extends InputControls<Entry>
>
extends JPanel implements ControlPanelInterface
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 225535677760012042L;
	/**
	 * The user interface that this control panel belongs to.
	 */
	private final UserInterface userInterface;
	/**
	 * The control panel's title.
	 */
	private final String title;
	/**
	 * The control panel's description.
	 */
	private final String description;
	/**
	 * The control panel's image icon.
	 */
	private Icon icon;
	/**
	 * The entry presentation.
	 */
	protected Presentation presentation = null;
	/**
	 * The input controls.
	 */
	protected Controls controls = null;
	/**
	 * The currently selected entry.
	 */
	private Entry currentEntry;
	/**
	 * Ignore updates.
	 */
	private boolean ignoreUpdates;
	/**
	 * The slider change listener.
	 */
	private final ChangeListener sliderChangeListener = new ChangeListener()
	{
		@Override
		public void stateChanged(final ChangeEvent e)
		{
			update();
		}
	};
	/**
	 * The constructor.
	 */
	protected ControlPanel
	(
		final UserInterface userInterface,
		final String title,
		final String description
	)
	{
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.userInterface = userInterface;
		this.title = title;
		this.description = description;
		this.icon = null;
		this.presentation = null;
		this.controls = null;
		this.ignoreUpdates = true;
	}
	/**
	 * Build the control panel.
	 */
	protected void build(final Presentation presentation, final Controls controls)
	{
		// Note: Always create the controls before the presentation because the presentation
		// most likely has to access the input controls, but the reverse never happens.
		if (controls != null)
			this.controls = controls;
		if (presentation != null)
		{
			this.presentation = presentation;
			this.presentation.initialise();
			add(this.presentation, BorderLayout.PAGE_START);
			add(Box.createRigidArea(new Dimension(0, 7))); // Add vertical whitespace.
		}
		if (this.controls != null) add(this.controls, BorderLayout.CENTER);
	}
	/**
	 * Return the user interface.
	 */
	public final UserInterface getUserInterface()
	{
		return userInterface;
	}
	/**
	 * Return the control panel's title.
	 */
	@Override
	public final String getTitle()
	{
		return title;
	}
	/**
	 * Return the control panel's description.
	 */
	@Override
	public final String getDescription()
	{
		return description;
	}
	/**
	 * Return the control panel's image icon.
	 */
	@Override
	public final Icon getIcon()
	{
		return icon;
	}
	/**
	 * Set the control panel's image icon.
	 * @param icon the icon to set.
	 */
	protected final void setIcon(final Icon icon)
	{
		this.icon = icon;
	}
	/**
	 * Get the currently selected model.
	 */
	public final Entry getCurrentEntry()
	{
		return currentEntry;
	}
	/**
	 * Set the currently selected entry.
	 * @param entry the entry to set.
	 */
	public final void setCurrentEntry(final Entry entry)
	{
		if (entry != null)
		{
			ignoreUpdates = true;

			this.currentEntry = entry;
			if (controls != null)
				controls.setValue(currentEntry);

			ignoreUpdates = false;
		}
	}
	/**
	 * Update the control panel.
	 */
	@Override
	public final void update()
	{
		if (!ignoreUpdates)
		{
			if (currentEntry == null)
			{
				ignoreUpdates = true;
				controls.setValue();
				ignoreUpdates = false;
			}
			else
				controls.getValue(currentEntry);
			Scene.RunUpdateTask();
		}
	}
	/**
	 * Remove the currently selected entity from the scene.
	 */
	public final void removeSelectedItem()
	{
		final SceneGraph.Node oldSelectedItem = (SceneGraph.Node)presentation.getSelectedItem();
		if (oldSelectedItem instanceof SceneGraph.Root)
		{
			final String header = "Warning";
			final String message = "The root node cannot be deleted!";

			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this, message, header, JOptionPane.WARNING_MESSAGE);
		}
		else
		{
			// Select a new item in the presentation, if any exist.
			final Entry newSelectedItem = presentation.selectNextItem();
			if (newSelectedItem != null)
				setCurrentEntry(newSelectedItem);

			// Remove the previously selected item from the model.
			oldSelectedItem.dispose();

			// Update the system.
			update();
		}
	}
	/**
	 * Return the slider change listener.
	 */
	public ChangeListener getSliderChangeListener()
	{
		return sliderChangeListener;
	}
	/**
	 * Return the currently selected item.
	 */
	public final Entry getSelectedItem()
	{
		return presentation.getSelectedItem();
	}
}
