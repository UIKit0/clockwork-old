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
package clockwork.types;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.SwingWorker;

import clockwork.gui.component.GUIProgressBar;

/**
 * TODO Explain me better than this.
 * TODO Inform Oracle that progressbardemo.java has a bug in it (line 126).
 * A task is a piece of execution that runs in the background.
 */
public abstract class Task<Result, Intermediate> extends SwingWorker<Result, Intermediate>
implements PropertyChangeListener
{
	/**
	 * The progress bar that displays a task's progress.
	 */
	public static final GUIProgressBar GUI_PROGRESS_BAR = new GUIProgressBar();
	/**
	 * Instantiate a new task.
	 */
	protected Task()
	{
		addPropertyChangeListener(this);
	}
	/**
	 * @see PropertyChangeListener#propertyChange
	 */
	@Override
	public final void propertyChange(final PropertyChangeEvent e)
	{
		if (e.getPropertyName().equals("progress"))
		{
			GUI_PROGRESS_BAR.setValue((Integer)e.getNewValue());
		}
	}
}
