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
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class GUIFixedSizePanel extends JPanel
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -6044460693928968596L;
	/**
	 * Instantiate a fixed-size panel with a given dimension.
	 */
	public GUIFixedSizePanel(final Dimension dimension)
	{
		super();
		setOpaque(false);

		if (dimension != null)
		{
			setMinimumSize(dimension);
			setPreferredSize(dimension);
			setMaximumSize(dimension);
		}
	}
	/**
	 * Instantiate a fixed-size panel with a given dimension and layout
	 * manager.
	 */
	public GUIFixedSizePanel(final Dimension dimension, final LayoutManager layout)
	{
		this(dimension);
		setLayout(layout);
	}
}
