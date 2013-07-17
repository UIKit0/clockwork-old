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

import java.awt.Container;
import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import clockwork.graphics.camera.Frustum;
import clockwork.gui.component.slider.GUIBoundedSlider;
import clockwork.gui.component.slider.GUILabeledSlider;
import clockwork.gui.component.slider.GUIUnboundedSlider;
import clockwork.gui.controls.ControlPanelInterface;



public final class GUIFrustumView extends GUIObjectView<Frustum>
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1864313381263232674L;
	/**
	 * The viewing frustum's sliders.
	 */
	private final GUILabeledSlider sliders[] = new GUILabeledSlider[4];
	/**
	 * The slider and spinner change listener.
	 */
	private final ChangeListener changeListener;
	/**
	 * Titles.
	 */
	private static final String TITLES[] = new String[]{"YFOV","Aspect","Near","Far"};
	/**
	 * The vertical field of view table.
	 */
	private static Hashtable<Integer, JLabel> YFOV_TABLE = new Hashtable<Integer, JLabel>();
	/**
	 * The aspect ratio table.
	 */
	private static Hashtable<Integer, JLabel> ASPECT_RATIO_TABLE = new Hashtable<Integer, JLabel>();
	/**
	 * The clipping plane table.
	 */
	private static Hashtable<Integer, JLabel> CLIPPING_TABLE = new Hashtable<Integer, JLabel>();
	/**
	 * Build the tables.
	 */
	static
	{
		// The vertical field of view.
		YFOV_TABLE.put(Integer.valueOf(0),   new JLabel("0"));
		YFOV_TABLE.put(Integer.valueOf(45),  new JLabel("45"));
		YFOV_TABLE.put(Integer.valueOf(90),  new JLabel("90"));
		YFOV_TABLE.put(Integer.valueOf(135), new JLabel("135"));
		YFOV_TABLE.put(Integer.valueOf(180), new JLabel("180"));

		// The aspect ratio.
		ASPECT_RATIO_TABLE.put(Integer.valueOf(5000),  new JLabel("1:2"));
		ASPECT_RATIO_TABLE.put(Integer.valueOf(10000), new JLabel("1:1"));
		ASPECT_RATIO_TABLE.put(Integer.valueOf(13333), new JLabel("4:3"));
		ASPECT_RATIO_TABLE.put(Integer.valueOf(17778), new JLabel("16:9"));
		ASPECT_RATIO_TABLE.put(Integer.valueOf(20000), new JLabel("2:1"));

		// The clipping plane.
		CLIPPING_TABLE.put(Integer.valueOf(Frustum.MinimumClippingPlaneDistance), new JLabel("1"));
		CLIPPING_TABLE.put(Integer.valueOf(500), new JLabel("500"));
		CLIPPING_TABLE.put(Integer.valueOf(Frustum.MaximumClippingPlaneDistance), new JLabel("1000"));
	}
	/**
	 * Instantiate a GUIFrustumView attached to a control panel interface.
	 * @param parent the control panel interface.
	 */
	public GUIFrustumView(final ControlPanelInterface parent)
	{
		super("Frustum", "Modify the camera's view frustum");

		changeListener = new ChangeListener()
		{
			@Override
			public void stateChanged(final ChangeEvent e)
			{
				final Container source = ((JComponent)e.getSource()).getParent();
				if (source instanceof ClippingPlaneSlider && sliders[2] != null && sliders[3] != null)
				{
					final GUILabeledSlider hitherSlider = sliders[2];
					final GUILabeledSlider yonSlider = sliders[3];

					// Make sure the clipping plane distances are in the [min, max] range.
					int hither = hitherSlider.read();
					if (hither < Frustum.MinimumClippingPlaneDistance)
						hither = Frustum.MinimumClippingPlaneDistance;
					else if (hither > Frustum.MaximumClippingPlaneDistance)
						hither = Frustum.MaximumClippingPlaneDistance;

					int yon = yonSlider.read();
					if (yon < Frustum.MinimumClippingPlaneDistance)
						yon = Frustum.MinimumClippingPlaneDistance;
					else if (yon > Frustum.MaximumClippingPlaneDistance)
						yon = Frustum.MaximumClippingPlaneDistance;

					// Make sure the hither is never greater than the yon.
					final int delta = yon - hither;
					if (delta < 1)
					{
						if (yon == Frustum.MaximumClippingPlaneDistance)
							hither = Frustum.MaximumClippingPlaneDistance - 1;
						else if (yon == Frustum.MinimumClippingPlaneDistance)
						{
							hither = Frustum.MinimumClippingPlaneDistance;
							yon += 1;
						}
						else
						{
							if (source == sliders[2])
								yon = hither + 1;
							else
								hither = yon - 1;
						}
					}
					hitherSlider.write(hither);
					yonSlider.write(yon);
				}
				parent.update();
			}
		};

		sliders[0] = new GUIUnboundedSlider(TITLES[0], 0, 180, 60, changeListener);
		sliders[0].setLabelDesign(true, 45, 5, YFOV_TABLE);

		sliders[1] = new GUIBoundedSlider(TITLES[1], 10000, 20000, 13333, changeListener);
		sliders[1].setLabelDesign(false, 10000, 1000, ASPECT_RATIO_TABLE);

		sliders[2] = new ClippingPlaneSlider(TITLES[2], 10, changeListener);
		sliders[3] = new ClippingPlaneSlider(TITLES[3], 100, changeListener);

		for (final GUILabeledSlider slider : sliders)
			add(slider);
	}
	/**
	 * @see GUIObjectView#write
	 */
	@Override
	public void write(final Frustum input)
	{
		if (input != null)
		{
			sliders[0].write(input.getVerticalFieldOfView());
			sliders[1].write((int)(input.getAspectRatio() * 10000));
			sliders[2].write(Math.round(input.getHitherPlaneDistance()));
			sliders[3].write(Math.round(input.getYonPlaneDistance()));
		}
	}
	/**
	 * @see GUIObjectView#read
	 */
	@Override
	public Frustum read(Frustum output)
	{
		if (output == null)
			output = new Frustum();

		output.setVerticalFieldOfView(sliders[0].read());
		output.setAspectRatio(sliders[1].read() * 0.0001f);
		output.setHitherPlaneDistance(sliders[2].read());
		output.setYonPlaneDistance(sliders[3].read());

		return output;
	}



	/**
	 * A slider that manipulates clipping plane distances.
	 */
	private final class ClippingPlaneSlider extends GUIUnboundedSlider
	{
		/**
		 * The serial version UID.
		 */
		private static final long serialVersionUID = -9222839036259633620L;
		/**
		 * The constructor.
		 */
		public ClippingPlaneSlider
		(
			final String title,
			final int initial,
			final ChangeListener listener
		)
		{
			super(title, 0, Frustum.MaximumClippingPlaneDistance, initial, listener);
			setLabelDesign(false, 100, 50, CLIPPING_TABLE);
		}
	}
}
