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
package clockwork.gui.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import clockwork.gui.component.slider.GUIBoundedSlider;
import clockwork.gui.controls.ControlPanelInterface;
import clockwork.types.math.Vector3f;



public final class GUIScaleView extends GUIObjectView<Vector3f>
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 4711383970733909358L;
	/**
	 * Scaling slider.
	 */
	private final GUIBoundedSlider[] sliders = new GUIBoundedSlider[3];
	/**
	 * Checkbox to enable or disable uniform scaling.
	 */
	private final JCheckBox checkboxToggleUniformScaling = new JCheckBox("Uniform scaling", true);
	/**
	 * Enable or disable uniform scaling.
	 */
	private boolean toggleUniformScaling = checkboxToggleUniformScaling.isSelected();
	/**
	 * XYZ labels.
	 */
	private static final String TITLES[] = new String[]{"X","Y","Z"};
	/**
	 * Instantiate a GUIScaleView attached to a control panel interface.
	 * @param parent the control panel interface.
	 */
	public GUIScaleView(final ControlPanelInterface parent)
	{
		super("Scale", "Modify the scaling");

		final ChangeListener sliderChangeListener = new ChangeListener()
		{
			@Override
			public void stateChanged(final ChangeEvent e)
			{
				if (toggleUniformScaling)
				{
					final Object source = e.getSource();
					if (source instanceof JSlider)
					{
						final JSlider inputSlider = (JSlider)source;
						final int value = inputSlider.getValue();

						for (final GUIBoundedSlider slider : sliders)
							slider.write(value);
					}
				}
				parent.update();
			}
		};
		for (int i = 0; i < sliders.length; ++i)
		{
			sliders[i] = new GUIBoundedSlider(TITLES[i], 0, 200, 100, sliderChangeListener);
			sliders[i].setFeedbackDesign(1, "%.0f%%");
	      add(sliders[i]);
		}

		final ActionListener checkboxActionListener = new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				toggleUniformScaling = checkboxToggleUniformScaling.isSelected();
			}
		};
		checkboxToggleUniformScaling.addActionListener(checkboxActionListener);
		add(checkboxToggleUniformScaling);
	}
	/**
	 * @see GUIObjectView#write
	 */
	@Override
	public void write(final Vector3f input)
	{
		if (input != null)
		{
			sliders[0].write((int)Math.round(input.i * 100));
			sliders[1].write((int)Math.round(input.j * 100));
			sliders[2].write((int)Math.round(input.k * 100));
		}
	}
	/**
	 * @see GUIObjectView#read
	 */
	@Override
	public Vector3f read(Vector3f output)
	{
		if (output == null)
			output = new Vector3f();

		output.i = sliders[0].read() * 0.01;
		output.j = sliders[1].read() * 0.01;
		output.k = sliders[2].read() * 0.01;

		return output;
	}
}