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
package clockwork.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import clockwork.gui.component.GUICheckbox;
import clockwork.gui.component.GUIDropdown;
import clockwork.scene.Scene;
import clockwork.system.RuntimeOptions;
import clockwork.system.Services;
import clockwork.types.Task;


public class DisplayPanel extends JPanel
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 3058613247853072377L;
	/**
	 * The display.
	 */
	private final Display display;
	/**
	 * Instantiate a DisplayPanel attached to the user interface.
	 * @param ui the user interface.
	 */
	public DisplayPanel(final UserInterface ui)
	{
		super(new BorderLayout());
		display = Services.Graphics.getDisplay();
		add(display, BorderLayout.NORTH);

		final JPanel feedbackPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		feedbackPanel.setOpaque(false);
		add(feedbackPanel);

		// Create a checkbox to toggle vertical synchronisation.
		final GUICheckbox toggleVSYNC = new GUICheckbox("Enable VSYNC");
		toggleVSYNC.setSelected(display.isVSYNCEnabled());
		toggleVSYNC.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				display.enableVSYNC(((JCheckBox)e.getSource()).isSelected());
			}
		});
		feedbackPanel.add(toggleVSYNC);
		final GUICheckbox toggleDebug = new GUICheckbox("Show Debug Information");
		toggleDebug.setSelected(RuntimeOptions.EnableDebugInformation);
		toggleDebug.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				RuntimeOptions.EnableDebugInformation = ((JCheckBox)e.getSource()).isSelected();
				display.repaint();
			}
		});
		feedbackPanel.add(toggleDebug);

		// Create a dropdown list to change the display resolution.
		final GUIDropdown<Display.Resolution> resolutions =
		new GUIDropdown<Display.Resolution>("Resolution", display.getAvailableResolutions());
		resolutions.setSelectedItem(display.getResolution());
		resolutions.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(final ItemEvent e)
			{
				if (e.getStateChange() == ItemEvent.SELECTED)
				{
					display.setResolution((Display.Resolution)((JComboBox)e.getSource()).getSelectedItem());
					display.revalidate();
					Scene.RunUpdateTask();
				}
			}
		});
		feedbackPanel.add(resolutions);
		feedbackPanel.add(Task.GUI_PROGRESS_BAR);
	}
}
