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

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import clockwork.gui.component.slider.GUIUnboundedSlider;
import clockwork.gui.controls.ControlPanelInterface;
import clockwork.types.math.Point3f;



public final class GUIPositionView extends GUIObjectView<Point3f>
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -2316201617327108440L;
	/**
	 * Position (translation) sliders for each axis X, Y, and Z.
	 */
	private final GUIUnboundedSlider[] sliders = new GUIUnboundedSlider[3];
	/**
	 * XYZ labels.
	 */
	private static final String TITLES[] = new String[]{"X","Y","Z"};
	/**
	 * Instantiate a GUIPositionView attached to a control panel interface.
	 * @param parent the control panel interface.
	 */
	public GUIPositionView(final ControlPanelInterface parent)
	{
		super("Position", "Modify the position");

		final ChangeListener sliderChangeListener = new ChangeListener()
		{
			@Override
			public void stateChanged(final ChangeEvent e)
			{
				parent.update();
			}
		};
		for (int i = 0; i < sliders.length; ++i)
		{
			sliders[i] = new GUIUnboundedSlider(TITLES[i], -100, 100, sliderChangeListener);
	      add(sliders[i]);
		}
	}
	/**
	 * @see GUIObjectView#write
	 */
	@Override
	public void write(final Point3f input)
	{
		if (input != null)
		{
			sliders[0].write((int)Math.round(input.x));
			sliders[1].write((int)Math.round(input.y));
			sliders[2].write((int)Math.round(input.z));
		}
	}
	/**
	 * @see GUIObjectView#read
	 */
	@Override
	public Point3f read(Point3f output)
	{
		if (output == null)
			output = new Point3f();

		output.x = sliders[0].read();
		output.y = sliders[1].read();
		output.z = sliders[2].read();

		return output;
	}
}
