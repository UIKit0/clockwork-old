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

import java.awt.Dimension;

import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class GUIUnboundedSlider extends GUILabeledSlider
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -2327105135191070779L;
	/**
	 * The spinner component.
	 */
	private final JSpinner spinner = new JSpinner();
	/**
	 * The spinner component's dimension.
	 */
	private static final Dimension SPINNER_DIMENSION = new Dimension(70, 25);
	/**
	 * Instantiate a slider with a given title, bounded range, and
	 * change listener.
	 */
	public GUIUnboundedSlider
	(
		final String title,
		final int minimum,
		final int maximum,
		final ChangeListener listener
	)
	{
		this(title, minimum, maximum, 0, listener);
	}
	/**
	 * Instantiate a slider with a given title, bounded range, an
	 * initial value, and change listener.
	 */
	public GUIUnboundedSlider
	(
		final String title,
		final int minimum,
		final int maximum,
		final int start,
		final ChangeListener listener
	)
	{
		super(title, minimum, maximum, start);

	   spinner.setMinimumSize(SPINNER_DIMENSION);
	   spinner.setPreferredSize(SPINNER_DIMENSION);
	   spinner.setValue(new Integer(start));
		add(spinner);
		addSpinnerChangeListener(listener);

		// A change listener that synchronises the values between the spinner and the slider.
		final ChangeListener synchronisationListener = new ChangeListener()
		{
			@Override
			public void stateChanged(final ChangeEvent e)
			{
				final Object source = e.getSource();
				if (source instanceof JSpinner)
					slider.setValue(((Integer)spinner.getValue()).intValue());
				else if (source instanceof JSlider)
					spinner.setValue(new Integer(slider.getValue()));
			}
		};
		addSliderChangeListener(synchronisationListener);
		addSpinnerChangeListener(synchronisationListener);
	}
	/**
	 * Add a spinner change listener.
	 */
	public final void addSpinnerChangeListener(final ChangeListener listener)
	{
		spinner.addChangeListener(listener);
	}
	/**
	 * Return the spinner's current value.
	 */
	@Override
	public int read()
	{
		return Integer.parseInt(spinner.getValue().toString());
	}
	/**
	 * Set both the slider and spinner's current value.
	 */
	@Override
	public void write(final int value)
	{
		slider.setValue(value);
		spinner.setValue(new Integer(value));
	}
	/**
	 * Set the slider's label design.
	 */
	@Override
	public void setLabelDesign
	(
		final boolean snapToTicks,
		final int majorTickSpacing,
		final int minorTickSpacing
	)
	{
		super.setLabelDesign(snapToTicks, majorTickSpacing, minorTickSpacing);
		if (snapToTicks)
		{
			final SpinnerNumberModel model =
			new SpinnerNumberModel(slider.getValue(), slider.getMinimum(), slider.getMaximum(), minorTickSpacing);
			spinner.setModel(model);
		}
	}
}