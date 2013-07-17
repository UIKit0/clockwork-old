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

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;




public final class Window extends JFrame implements WindowListener
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -535343411557289566L;
	/**
	 * The user interface used by the daemon.
	 */
	private UserInterface userInterface = null;
	/**
	 * Instantiate a titled window.
	 * @param title the title of the window.
	 */
	public Window(final String title)
	{
		super(title);

		// Note that normally this should be DISPOSE_ON_CLOSE but the Display's repaint timer
		// doesn't stop when the window is closed. It does so however when we EXIT_ON_CLOSE.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		addWindowListener(this);
		setVisible(true);
//		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	/**
	 * Set the user interface.
	 * @param ui the user interface to set.
	 */
	public void setUserInterface(final UserInterface ui)
	{
		// Set the user interface and build it.
		this.userInterface = ui;
		this.userInterface.build();
		pack();
		repaint();
	}
	/**
	 * Return current user interface.
	 */
	public UserInterface getUserInterface()
	{
		return userInterface;
	}
	/**
	 * Close the window, which exits the daemon.
	 */
	public void close()
	{
		// An event to close the window.
		final WindowEvent closeEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeEvent);
	}
	@Override public void windowActivated(final WindowEvent e){}
	@Override public void windowDeactivated(final WindowEvent e){}
	@Override public void windowOpened(final WindowEvent e){}
	@Override public void windowClosing(final WindowEvent e){}
	@Override public void windowClosed(final WindowEvent e){}
	@Override public void windowIconified(final WindowEvent e){}
	@Override public void windowDeiconified(final WindowEvent e){}
}
