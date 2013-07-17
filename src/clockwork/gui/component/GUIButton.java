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

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GUIButton extends JButton
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1160812536634231572L;
	/**
	 * The button's default size.
	 */
	protected final static Dimension defaultDimension = new Dimension(24, 24);
	/**
	 * TODO Explain me.
	 * The constructor.
	 */
	public GUIButton
	(
		final ImageIcon icon,
		final ImageIcon selectedIcon,
		final ImageIcon rolloverIcon,
		final ImageIcon disabledIcon
	)
	{
		super(icon);
		super.setSelectedIcon(selectedIcon);
		super.setRolloverIcon(rolloverIcon);
		super.setDisabledIcon(disabledIcon);
		super.setPreferredSize(defaultDimension);
	}

	/**
	 * TODO Explain me.
	 * The constructor.
	 */
	public GUIButton(final String title)
	{
		super(title);
	}
}
