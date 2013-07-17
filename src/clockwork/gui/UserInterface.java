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
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import clockwork.asset.AssetManager;
import clockwork.gui.component.GUIFileChooser;
import clockwork.gui.component.GUIMenubar;
import clockwork.gui.controls.ControlPanelInterface;
import clockwork.gui.controls.assets.AssetControlPanel;
import clockwork.gui.controls.runtime.RuntimeControlPanel;
import clockwork.gui.controls.scene.SceneControlPanel;
import clockwork.scene.Scene;




/**
 * The Graphical User Interface.
 *
 * @author supranove.
 */
public final class UserInterface extends JPanel
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -1166344807702169161L;
	/**
	 * The window where this user interface is being drawn.
	 */
	private final Window window;
	/**
	 * The file chooser.
	 */
	private final GUIFileChooser fileChooser = new GUIFileChooser(this);
	/**
	 * The menubar.
	 */
	private final GUIMenubar menubar = new GUIMenubar(this);
	/**
	 * The input section.
	 */
	private JPanel inputSection = null;
	/**
	 * The output section.
	 */
	private JPanel outputSection = null;
	/**
	 * The default constructor.
	 * @param window the window where this interface will be drawn.
	 */
	public UserInterface(final Window window)
	{
		this.window = window;
		if (this.window != null)
		{
/**/
			// Set the look and feel.
			try
			{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
			catch (UnsupportedLookAndFeelException e)
			{}
			catch (ClassNotFoundException e)
			{}
			catch (InstantiationException e)
			{}
			catch (IllegalAccessException e)
			{}
//**/
			setOpaque(false);
			this.window.setUserInterface(this);
		}
	}
	/**
	 * Set the user interface's minimum size.
	 * @param dimension the minimum dimension to set.
	 */
	@Override
	public void setMinimumSize(final Dimension dimension)
	{
		if (dimension != null)
		{
			super.setMinimumSize(dimension);
			window.setMinimumSize(dimension);
		}
	}
	/**
	 * Exit the user interface, which closes the window.
	 */
	public void exit()
	{
		window.close();
	}
	/**
	 * Return the file chooser.
	 */
	public GUIFileChooser getFileChooser()
	{
		return fileChooser;
	}
	/**
	 * Build the user interface.
	 */
	public void build()
	{
		if (window != null)
		{
			setLayout(new BorderLayout());

			// Set the menubar.
			window.setJMenuBar(menubar);

			// Attach this user interface to the window.
			window.getContentPane().add(this);

			// Build the GUI's sections and add them.
			inputSection = buildControlPanel();
			outputSection = new DisplayPanel(this);

			add(inputSection, BorderLayout.WEST);
			add(outputSection, BorderLayout.CENTER);

			// Set the minimum interface size as the size of the input section.
			setMinimumSize(inputSection.getMinimumSize());
		}
	}
	/**
	 * Build the control panel, which is a set of tabs each
	 * containing input controls.
	 */
	private JPanel buildControlPanel()
	{
		final Scene scene = Scene.getUniqueInstance();
		final JTabbedPane tabs = new JTabbedPane();
		final JPanel panel = new JPanel(new BorderLayout());
		panel.add(tabs);

		final ControlPanelInterface interfaces[] = new ControlPanelInterface[]
		{
			new AssetControlPanel(this, AssetManager.getAssetMap()),
			new SceneControlPanel(this, scene),
//TODO			new FramebufferControlPanel(this),
			new RuntimeControlPanel(this)
		};
		for (final ControlPanelInterface cpi : interfaces)
			tabs.addTab(cpi.getTitle(), cpi.getIcon(), (JPanel)cpi, cpi.getDescription());

		// Select the scene control panel by default.
		tabs.setSelectedIndex(1);

		return panel;
	}
	/**
	 * Show or hide input controls.
	 */
	public void toggleInputVisibility()
	{
		inputSection.setVisible(!inputSection.isVisible());
		window.pack();
	}
	/**
	 * Show or hide the output.
	 */
	public void toggleOutputVisibility()
	{
		outputSection.setVisible(!outputSection.isVisible());
		window.pack();
	}
}
