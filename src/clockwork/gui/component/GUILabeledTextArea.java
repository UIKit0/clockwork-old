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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import clockwork.gui.controls.InputControls;


public class GUILabeledTextArea extends JPanel
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
	 * The text area.
	 */
	private final JTextArea textArea = new JTextArea();
	/**
	 * TODO Explain me better.
	 * Instantiate a labeled text area with a given label and text.
	 * @param label the label.
	 * @param text the text.
	 */
	public GUILabeledTextArea(final String label, final String text)
	{
		super(new FlowLayout(FlowLayout.LEFT));
		super.setMaximumSize(new Dimension(InputControls.DIMENSION.width, 28));
		add(this.label);
		add(this.textArea);

		this.label.setFont(this.label.getFont().deriveFont(Font.BOLD));
		this.textArea.setBackground(new Color(0, 0, 0, 0));

		if (label != null)
			this.label.setText(label);
		if (text != null)
			this.textArea.setText(text);
	}
	/**
	 * Instantiate a labeled text area with a given label.
	 * @param label the label.
	 */
	public GUILabeledTextArea(final String label)
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
	 * Set the text.
	 * @param text the text to set.
	 */
	public void setText(final String text)
	{
		this.textArea.setText(text);
	}
}
