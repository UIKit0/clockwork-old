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
package clockwork.gui.controls.runtime;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.border.Border;


public abstract class OptionsComponent extends JComponent implements ActionListener, ItemListener
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -7284885635062676364L;
	/**
	 * The runtime options panel.
	 */
	protected final RuntimeControlPanel parent;
	/**
	 * The line border.
	 */
	private final Border lineBorder = BorderFactory.createLineBorder(Color.black, 2);
	/**
	 * The default constructor.
	 */
	protected OptionsComponent(final RuntimeControlPanel parent, final String title)
	{
		super();
		this.parent = parent;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createTitledBorder(lineBorder, title));
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		parent.update();
	}

	@Override
	public void itemStateChanged(final ItemEvent e)
	{
		parent.update();
	}
}
