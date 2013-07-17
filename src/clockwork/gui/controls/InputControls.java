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

import javax.swing.JTabbedPane;

import clockwork.gui.component.GUIFixedSizePanel;
import clockwork.gui.views.GUIObjectView;



public abstract class InputControls<Value> extends GUIFixedSizePanel
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -8740251603403732088L;
	/**
	 * The tabbed pane.
	 */
	private final JTabbedPane tabs = new JTabbedPane();
	/**
	 * The panel's dimension.
	 */
	public static final Dimension DIMENSION = new Dimension(450, 389);
	/**
	 * The constructor.
	 */
	protected InputControls()
	{
		super(DIMENSION, new BorderLayout());
		setOpaque(false);
		add(tabs, BorderLayout.CENTER);
	}
	/**
	 * Return the component's current value.
	 */
	public abstract void getValue(final Value output);
	/**
	 * Set the current value.
	 */
	public abstract void setValue(final Value input);
	/**
	 * Set a default value.
	 */
	public abstract void setValue();
	/**
	 * Add an input component to this set of controls.
	 * @param component the component to add.
	 */
	protected final void addInputComponent(final GUIObjectView<?> component)
	{
		final String title = component.getTitle();
		final String description = component.getDescription();

		tabs.addTab(title, null, component, description);
	}
	/**
	 * Select the component at the given index.
	 */
	protected final void selectInputComponent(final int index)
	{
		tabs.setSelectedIndex(index);
	}
	/**
	 * Remove all the input components from the controls.
	 */
	protected final void removeInputComponents()
	{
		tabs.removeAll();
		validate();
	}
	/**
	 * Return the index of the currently selected tab.
	 */
	public final int getSelectedIndex()
	{
		return tabs.getSelectedIndex();
	}
	/**
	 * Set the currently selected tab.
	 * @param index the index of the tab to set.
	 */
	public final void setSelectedIndex(final int index)
	{
		tabs.setSelectedIndex(index);
	}
	/**
	 * Enable or disable a tab with the given index.
	 * @param index the tab to enable or disable.
	 * @param enabled enable the tab.
	 */
	public final void setEnabledAt(final int index, final boolean enabled)
	{
		tabs.setEnabledAt(index, enabled);
	}
}
