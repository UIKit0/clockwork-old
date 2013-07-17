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
package clockwork.gui.component.slider;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GUIBoundedSlider extends GUILabeledSlider
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 2359512228317363334L;
	/**
	 * The text area to show the current slider value.
	 */
	private final JLabel textFeedback = new JLabel();
	/**
	 * A value that the output will be divided by before it's printed.
	 */
	private float textFeedbackDivisor = 1.0f;
	/**
	 * The format of the feedback.
	 */
	private String textFeedbackFormat = "%.0f";
	/**
	 * Instantiate a slider with a given title, bounded range, and
	 * change listener.
	 */
	public GUIBoundedSlider
	(
		final String title,
		final int minimum,
		final int maximum,
		final ChangeListener listener
	)
	{
		this(title, minimum, maximum, 0, listener);
//		final Dimension textFeedbackDimension = textFeedback.getPreferredSize();
//		textFeedbackDimension.width = 70;
//		textFeedback.setPreferredSize(textFeedbackDimension);
	}
	/**
	 * Instantiate a slider with a given title, bounded range, an
	 * initial value, and change listener.
	 */
	public GUIBoundedSlider
	(
		final String title,
		final int minimum,
		final int maximum,
		final int start,
		final ChangeListener listener
	)
	{
		super(title, minimum, maximum, start, listener);

		textFeedback.setText(String.format(textFeedbackFormat, (float)start));
		textFeedback.setBackground(new Color(0, 0, 0, 0));
		add(textFeedback);

		// Add a change listener that synchronises the values betweem the label and the slider.
		addSliderChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(final ChangeEvent e)
			{
				updateFeedback();
			}
		});
	}
	/**
	 * Set the feedback design.
	 * @param float feedbackDivisor divide the output by this value before printing it.
	 * @param format the fomat of the output.
	 */
	public void setFeedbackDesign(final float feedbackDivisor, final String format)
	{
		if (feedbackDivisor != 0)
			textFeedbackDivisor = feedbackDivisor;
		if (format != null)
			this.textFeedbackFormat = format;

		updateFeedback();
	}
	/**
	 * Update the feedback.
	 */
	private void updateFeedback()
	{
		textFeedback.setText(String.format(textFeedbackFormat, slider.getValue() / textFeedbackDivisor));
	}
}
