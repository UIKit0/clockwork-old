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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.event.ChangeListener;

public abstract class GUILabeledSlider extends JPanel
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 6422475592741556966L;
	/**
	 * The slider's label.
	 */
	private final JLabel sliderLabel = new JLabel();
	/**
	 * The slider component.
	 */
	protected final JSlider slider = new JSlider();
	/**
	 * Remove the value that hovers over the slider.
	 */
	static
	{
		UIManager.getLookAndFeelDefaults().put("Slider.paintValue", false);
		UIManager.put("Slider.paintValue", false);
	}
	/**
	 * TODO Explain parameters.
	 * Instantiate a labeled slider with a given bounded range, initial value and
	 * a change listener.
	 */
	public GUILabeledSlider
	(
		final String label,
		final int minimum,
		final int maximum,
		final int start,
		final ChangeListener sliderChangeListener
	)
	{
		super(new FlowLayout(FlowLayout.LEFT));
		setOpaque(false);
		sliderLabel.setFont(sliderLabel.getFont().deriveFont(Font.BOLD));
		sliderLabel.setText(label);
		add(sliderLabel);

		slider.setOrientation(JSlider.HORIZONTAL);
		slider.setMinimum(minimum);
		slider.setMaximum(maximum);
		slider.setValue(start);
		slider.addChangeListener(sliderChangeListener);
		slider.setMajorTickSpacing(maximum / 2);
		slider.setMinorTickSpacing(maximum / 10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		final Dimension sliderDimension = slider.getPreferredSize();
		sliderDimension.width = 300;
	   slider.setPreferredSize(sliderDimension);
	   add(slider);
	}
	/**
	 * Instantiate a slider with a given title and bounded range.
	 */
	public GUILabeledSlider
	(
		final String title,
		final int minimum,
		final int maximum,
		final int start
	)
	{
		this(title, minimum, maximum, start, null);
	}
	/**
	 * Add a component to the slider.
	 * @param component the component to add.
	 */
	@Override
	public Component add(final Component component)
	{
		if (component != null)
		{
			final JPanel wrapper = new JPanel();
			wrapper.add(component);
			final Dimension wrapperDimension = wrapper.getPreferredSize();
			wrapperDimension.height = 60;
			wrapper.setPreferredSize(wrapperDimension);
			super.add(wrapper);
		}
		return component;
	}
	/**
	 * Add a slider change listener.
	 */
	public final void addSliderChangeListener(final ChangeListener listener)
	{
		slider.addChangeListener(listener);
	}
	/**
	 * Set the slider's label table.
	 */
	public final void setLabelTable(final Hashtable<Integer, JLabel> table)
	{
		slider.setLabelTable(table);
	}
	/**
	 * Return the current slider value.
	 */
	public int read()
	{
		return slider.getValue();
	}
	/**
	 * Set the slider's current value.
	 */
	public void write(final int value)
	{
		slider.setValue(value);
	}
	/**
	 * Set the slider's label design.
	 */
	public void setLabelDesign
	(
		final boolean snapToTicks,
		final int majorTickSpacing,
		final int minorTickSpacing
	)
	{
		slider.setMinorTickSpacing(minorTickSpacing);
		slider.setMajorTickSpacing(majorTickSpacing);
		slider.setSnapToTicks(snapToTicks);
	}
	/**
	 * Set the slider's label design and label table.
	 */
	public void setLabelDesign
	(
		final boolean snapToTicks,
		final int majorTickSpacing,
		final int minorTickSpacing,
		final Hashtable<Integer, JLabel> labelTable
	)
	{
		setLabelDesign(snapToTicks, majorTickSpacing, minorTickSpacing);
		setLabelTable(labelTable);
	}
}
