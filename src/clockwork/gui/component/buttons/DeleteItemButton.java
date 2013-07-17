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
package clockwork.gui.component.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import clockwork.gui.component.GUIButton;
import clockwork.gui.controls.ControlPanel;


public class DeleteItemButton extends GUIButton
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -3319391169010468159L;
	/**
	 * The button icons.
	 */
	private static ImageIcon icon;
	private static ImageIcon selectedIcon;
	private static ImageIcon rolloverIcon;
	private static ImageIcon disabledIcon;
	static
	{
		try
		{
			icon = new ImageIcon(ImageIO.read(new File("assets/icons/delete.jpg")));
			selectedIcon = new ImageIcon(ImageIO.read(new File("assets/icons/delete.selected.jpg")));
			rolloverIcon = new ImageIcon(ImageIO.read(new File("assets/icons/delete.rollover.jpg")));
			disabledIcon = new ImageIcon(ImageIO.read(new File("assets/icons/delete.disabled.jpg")));
		}
		catch (final IOException e)
		{}
	}
	/**
	 * Instantiate a new button attached to a control panel.
	 */
	public DeleteItemButton(final ControlPanel<?, ?, ?> controlPanel)
	{
		super(icon, selectedIcon, rolloverIcon, disabledIcon);
		super.setToolTipText("Delete the selected item");
		this.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				controlPanel.removeSelectedItem();
			}
		});
	}
}
