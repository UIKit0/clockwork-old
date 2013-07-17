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

import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import clockwork.graphics.color.ColorRGB;
import clockwork.gui.component.slider.GUIBoundedSlider;
import clockwork.gui.controls.ControlPanelInterface;


/**
 * The GUIColorView displays RGBA colors.
 */
public class GUIColorRGBView extends GUIObjectView<ColorRGB>
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 6309701400961716286L;
	/**
	 * Slider labels.
	 */
	private static final String SLIDER_LABEL[] = new String[]{"Red", "Green", "Blue"};
	/**
	 * Color sliders.
	 */
	private final GUIBoundedSlider sliders[] = new GUIBoundedSlider[3];
	/**
	 * The color table.
	 */
	private static final Hashtable<Integer, JLabel> COLOR_TABLE = new Hashtable<Integer, JLabel>();
	static
	{
		COLOR_TABLE.put(Integer.valueOf(0), new JLabel("0.0"));
		COLOR_TABLE.put(Integer.valueOf(500), new JLabel("0.5"));
		COLOR_TABLE.put(Integer.valueOf(1000), new JLabel("1.0"));
	}
	/**
	 * Instantiate a GUIColorView attached to a control panel interface.
	 * @param parent the control panel interface.
	 */
	public GUIColorRGBView(final ControlPanelInterface parent)
	{
		super("Color", "Modify the color");

		final ChangeListener sliderChangeListener = new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				parent.update();
			}
		};
		for (int i = 0; i < sliders.length; ++i)
		{
			sliders[i] = new GUIBoundedSlider(SLIDER_LABEL[i], 0, 1000, 500, sliderChangeListener);
			sliders[i].setLabelDesign(false, 500, 100, COLOR_TABLE);
			sliders[i].setFeedbackDesign(1000, "%.2f");
			add(sliders[i]);
		}
	}
	/**
	 * @see GUIObjectView#write
	 */
	@Override
	public void write(final ColorRGB input)
	{
		if (input != null)
		{
			sliders[0].write((int)Math.round(input.r * 255.0f));
			sliders[1].write((int)Math.round(input.g * 255.0f));
			sliders[2].write((int)Math.round(input.b * 255.0f));
		}
	}
	/**
	 * @see GUIObjectView#read
	 */
	@Override
	public ColorRGB read(ColorRGB output)
	{
		if (output == null)
			output = new ColorRGB();

		output.r = sliders[0].read() * 0.00392f; // 1/255 = 0.00392156862
		output.g = sliders[1].read() * 0.00392f;
		output.b = sliders[2].read() * 0.00392f;

		return output;
	}
}
