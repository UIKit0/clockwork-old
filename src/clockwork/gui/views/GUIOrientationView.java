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

import clockwork.gui.component.slider.GUIBoundedSlider;
import clockwork.gui.controls.ControlPanelInterface;
import clockwork.types.math.Orientation;



public final class GUIOrientationView extends GUIObjectView<Orientation>
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -1436646074915636049L;
	/**
	 * Orientation (rotation) slider.
	 */
	private final GUIBoundedSlider[] sliders = new GUIBoundedSlider[3];
	/**
	 * Orientation labels.
	 */
	private static final String TITLES[] = new String[]{"Roll","Yaw","Pitch"};
	/**
	 * Instantiate a GUIOrientationView attached to a control panel interface.
	 * @param parent the control panel interface.
	 */
	public GUIOrientationView(final ControlPanelInterface parent)
	{
		super("Orientation", "Modify the orientation");

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
			sliders[i] = new GUIBoundedSlider(TITLES[i], -180, 180, sliderChangeListener);
			sliders[i].setFeedbackDesign(1, "%.0f°");
	      add(sliders[i]);
		}
	}
	/**
	 * @see GUIObjectView#write
	 */
	@Override
	public void write(final Orientation input)
	{
		if (input != null)
		{
			sliders[0].write((int)Math.round(input.roll));
			sliders[1].write((int)Math.round(input.yaw));
			sliders[2].write((int)Math.round(input.pitch));
		}
	}
	/**
	 * @see GUIObjectView#read
	 */
	@Override
	public Orientation read(Orientation output)
	{
		if (output == null)
			output = new Orientation();

		output.roll = sliders[0].read();
		output.yaw = sliders[1].read();
		output.pitch = sliders[2].read();

		return output;
	}
}