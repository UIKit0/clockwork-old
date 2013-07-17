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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import clockwork.gui.UserInterface;
import clockwork.scene.Scene;
import clockwork.system.Services;


/**
 * A menubar.
 */
public final class GUIMenubar extends JMenuBar implements ActionListener
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 8827186543552098020L;
	/**
	 * The user interface that this bar will be attached to.
	 */
	private final UserInterface userInterface;
	/**
	 * An instance of the scene.
	 */
	private final Scene scene = Scene.getUniqueInstance();
	/**
	 * The menu.
	 */
	private final JMenu menu = new JMenu("File");
	/**
	 * Menu items.
	 */
	private final JComponent menuComponents[] = new JComponent[]
	{
		new JMenuItem("Load scene", KeyEvent.VK_L),
		new JMenuItem("Save scene", KeyEvent.VK_S),
		new JMenuItem("Clear scene", KeyEvent.VK_C),
		new JSeparator(),
		new JMenuItem("Take screenshot", KeyEvent.VK_T),
		new JSeparator(),
		new JMenuItem("Toggle input", KeyEvent.VK_I),
		new JMenuItem("Toggle output", KeyEvent.VK_O),
		new JMenuItem("Exit", KeyEvent.VK_Q)
	};
	/**
	 * Instantiate a Menubar object with a custom file chooser.
	 */
	public GUIMenubar(final UserInterface userInterface)
	{
		this.userInterface = userInterface;

		menu.setMnemonic(KeyEvent.VK_F);

		for (final JComponent component : menuComponents)
		{
			if (component instanceof JMenuItem)
			{
				final JMenuItem menuItem = (JMenuItem)component;
				menuItem.addActionListener(this);

				// Make the menu item's mnemonic it's accelerator.
				final int mnemonic = menuItem.getMnemonic();
				if (mnemonic != 0)
					menuItem.setAccelerator(KeyStroke.getKeyStroke(mnemonic, ActionEvent.CTRL_MASK));
			}
			menu.add(component);
		}
		add(menu);
	}
	/**
	 * The menubar event handler.
	 */
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		final int mnemonic = ((JMenuItem)e.getSource()).getMnemonic();
		switch (mnemonic)
		{
			case KeyEvent.VK_L:
				loadScene();
				break;
			case KeyEvent.VK_S:
				saveScene();
				break;
			case KeyEvent.VK_C:
				clearScene();
				break;
			case KeyEvent.VK_T:
				screenshot();
				break;
			case KeyEvent.VK_I:
				userInterface.toggleInputVisibility();
				break;
			case KeyEvent.VK_O:
				userInterface.toggleOutputVisibility();
				break;
			case KeyEvent.VK_Q:
				userInterface.exit();
				break;
			default:
				break;
		}
	}
	/**
	 * TODO
	 * Load the scene from a file.
	 */
	private void loadScene()
	{
		final String header = "Warning";
		final String message = "This action is irreversible! Are you sure you " +
		"want to load a new scene? The previous scene will be destroyed.";
		final int reply =
		JOptionPane.showConfirmDialog(null, message, header, JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION)
		{
			// Out with the old, in with the new.
			scene.clear();
			System.out.println("loading scene");

		}

/*
		final JFileChooser fileChooser = userInterface.getFileChooser();
		if (fileChooser.showDialog(controlPanel, "Import") == JFileChooser.APPROVE_OPTION)
		{
			final File file = fileChooser.getSelectedFile();
			try
			{
				Scene.getUniqueInstance().add(AbstractModelReader.read(file));
				controlPanel.update();
			}
			catch (final IOException ioException)
			{
				final String header = "Read error";
				final String message = "Could not read the file " + file.getName();

				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(controlPanel, message, header, JOptionPane.ERROR_MESSAGE);
			}
		}
*/
	}

	/**
	 * TODO
	 * Save the scene to a file.
	 */
	private void saveScene()
	{
		scene.save();
	}
	/**
	 * Clear the scene.
	 */
	private void clearScene()
	{
		final String header = "Warning";
		final String message = "This action is irreversible! Are you sure you want to clear the scene?";
		final int reply =
		JOptionPane.showConfirmDialog(null, message, header, JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION)
			scene.clear();
	}
	/**
	 * Save a screenshot of the scene.
	 */
	private void screenshot()
	{
		Services.Graphics.getDisplay().screenshot();
	}
}