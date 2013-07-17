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

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

/**
 * A progress bar panel. It is comprised of an actual progress bar and feedback area
 * that details the progress.
 */
public final class GUIProgressBar extends JPanel
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -7265133189325955672L;
	/**
	 * The JProgressBar.
	 */
	private final JProgressBar progressbar = new JProgressBar();
	/**
	 * The JTextArea for feedback text.
	 */
	private final JTextArea textFeedback = new JTextArea();
	/**
	 * The default constructor.
	 */
	public GUIProgressBar()
	{
		setLayout(new FlowLayout());
		this.add(progressbar);
		this.add(textFeedback);
		textFeedback.setBackground(new Color(0, 0, 0, 0));
	}
	/**
	 * Set the progress.
	 * @param progress the progress value to set.
	 */
	public void setValue(final int progress)
	{
		progressbar.setValue(progress);
	}
	/**
	 * Set the feedback information.
	 * @param info the feedback to set.
	 */
	public void setFeedbackInformation(final String info)
	{
		textFeedback.setText(info);
	}
	/**
	 * Return the progress bar's minimum value.
	 */
	public int getMinimum()
	{
		return progressbar.getMinimum();
	}
	/**
	 * Return the progress bar's maximum value.
	 */
	public int getMaximum()
	{
		return progressbar.getMaximum();
	}
}
