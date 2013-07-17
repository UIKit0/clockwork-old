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
package clockwork.gui.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemListener;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clockwork.gui.controls.InputControls;


public class GUIDropdown<ObjectType> extends JPanel
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -4669033463051609571L;
	/**
	 * The dropdown list's combobox.
	 */
	private final JComboBox combobox = new JComboBox();
	/**
	 * The dropdown label.
	 */
	private final JLabel label = new JLabel();
	/**
	 * Instantiate a labeled drop-down list with an array of choices.
	 * @param label the drop-down list's label.
	 * @param choices an array of choices.
	 */
	public GUIDropdown(final String label, final ObjectType choices[])
	{
		super(new FlowLayout(FlowLayout.LEFT));
		setMaximumSize(new Dimension(InputControls.DIMENSION.width, 35));
		setBackground(new Color(0, 0, 0, 0));

		// Create the label.
		this.label.setText(label);
		this.label.setFont(this.label.getFont().deriveFont(Font.BOLD));
		add(this.label);

		// Create the combobox.
		setChoices(choices);
		add(combobox);
	}
	/**
	 * Instantiate a labeled drop-down list with a set of choices.
	 * @param label the drop-down list's label.
	 * @param choices a set of choices.
	 */
	public GUIDropdown(final String label, final Set<ObjectType> choices)
	{
		this(label);
		setChoices(choices);
	}
	/**
	 * Instantiate a labeled drop-down list.
	 * @param label the drop-down list's label.
	 */
	public GUIDropdown(final String label)
	{
		this(label, (ObjectType[])null);
	}
	/**
	 * Set the selected item.
	 */
	public void setSelectedItem(final ObjectType object)
	{
		if (object != null)
			combobox.setSelectedItem(object);
	}
	/**
	 * Return the currently selected item.
	 */
	@SuppressWarnings("unchecked")
	public ObjectType getSelectedItem()
	{
		return (ObjectType)combobox.getSelectedItem();
	}
	/**
	 * Set choices.
	 * @param choices an array of choices to set.
	 */
	public void setChoices(final ObjectType choices[])
	{
		if (choices != null)
			combobox.setModel(new DefaultComboBoxModel(choices));
	}
	/**
	 * Set choices.
	 * @param choices a set of choices to set.
	 */
	public void setChoices(final Set<ObjectType> choices)
	{
		if (choices != null)
			combobox.setModel(new DefaultComboBoxModel(choices.toArray()));
	}
	/**
	 * Add an item change listener.
	 * @param listener the listener to add.
	 */
	public void addItemListener(final ItemListener listener)
	{
		if (listener != null)
			combobox.addItemListener(listener);
	}
	/**
	 * Set the font's color.
	 * @param color the font color to set.
	 */
	public void setFontColor(final Color color)
	{
		label.setForeground(color);
	}
}
