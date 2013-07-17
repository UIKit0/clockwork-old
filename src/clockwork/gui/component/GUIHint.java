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

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import clockwork.gui.controls.InputControls;


/**
 * A hint.
 */
public class GUIHint extends JPanel
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 3965853255865544583L;
	/**
	 * The JLabel that will contain and display the hint.
	 */
	private final JLabel label = new JLabel();
	/**
	 * Instantiate a hint.
	 */
	public GUIHint(final String hint)
	{
		super(new FlowLayout(FlowLayout.LEFT));
		setMaximumSize(new Dimension(InputControls.DIMENSION.width, 35));

		label.setFont(label.getFont().deriveFont(Font.ITALIC, 11.5f));
		setHint(hint);

		add(label);
	}
	/**
	 * Set the hint.
	 * @param hint the hint to set.
	 */
	public void setHint(final String hint)
	{
		if (hint != null && hint.length() > 0)
			label.setText("Hint: " + hint);
	}
	/**
	 * Return the hint presented by this component.
	 */
	public String getHint()
	{
		return label.getText();
	}
}
