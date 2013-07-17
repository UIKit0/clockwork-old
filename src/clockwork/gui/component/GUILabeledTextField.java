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
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clockwork.gui.controls.InputControls;


public class GUILabeledTextField extends JPanel
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 112683009836682000L;
	/**
	 * The label.
	 */
	private final JLabel label = new JLabel();
	/**
	 * The text field.
	 */
	private final JTextField textField = new JTextField();
	/**
	 * TODO Explain me better.
	 * A button to modify the text field's value.
	 */
	private final JButton modifyButton = new JButton("Modify");
	/**
	 * TODO Explain me better.
	 * Instantiate a labeled text field with a given label and text.
	 * @param label the label.
	 * @param text the text.
	 */
	public GUILabeledTextField(final String label, final String text)
	{
		super(new FlowLayout(FlowLayout.LEFT));
		super.setMaximumSize(new Dimension(InputControls.DIMENSION.width, 38));
		add(this.label);
		add(this.textField);
		add(this.modifyButton);

		this.label.setFont(this.label.getFont().deriveFont(Font.BOLD));
		this.textField.setSize(new Dimension(InputControls.DIMENSION.width, 24));
		this.textField.setBackground(new Color(50, 50, 50, 255));
		this.textField.setForeground(Color.white);
		this.textField.setCaretColor(Color.white);
		this.textField.setColumns(20);

		if (label != null)
			this.label.setText(label);
		if (text != null)
			this.textField.setText(text);
	}
	/**
	 * Instantiate a labeled text area with a given label.
	 * @param label the label.
	 */
	public GUILabeledTextField(final String label)
	{
		this(label, null);
	}
	/**
	 * Set the label.
	 * @param label the label to set.
	 */
	public void setLabel(final String label)
	{
		this.label.setText(label);
	}
	/**
	 * Return the text in the text field.
	 */
	public String getText()
	{
		return textField.getText();
	}
	/**
	 * Set the text field's text.
	 * @param text the text to set.
	 */
	public void setText(final String text)
	{
		textField.setText(text);
	}
	/**
	 * Add an action listener to the modify button.
	 * @param listener the action listener to add.
	 */
	public void addActionListener(final ActionListener listener)
	{
		if (listener != null)
			modifyButton.addActionListener(listener);
	}
	/**
	 * Can the field value be modified?
	 * @param enable true to enable the 'modify' button, false to disable it.
	 */
	public void enableModifyButton(final boolean enable)
	{
		modifyButton.setEnabled(enable);
	}
}
