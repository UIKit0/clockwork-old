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
package clockwork.graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JDialog;
import javax.swing.JPanel;

import clockwork.gui.component.GUILabeledTextArea;

public class ImageViewer extends JDialog
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -4956674227726678341L;
	/**
	 * TODO Set a maximum width and height in case the image is too large to fit the screen.
	 * Instantiate an image viewer with a given array of
	 * pixels, an image width and an image height.
	 */
	public ImageViewer(final int pixels[], final int width, final int height)
	{
		super((Frame)null, "Image Viewer", true);
		final Dimension dimension = new Dimension(width, height);

		final JPanel imageInfoPanel = new JPanel(new FlowLayout());
		imageInfoPanel.add(new GUILabeledTextArea("Width", "" + width));
		imageInfoPanel.add(new GUILabeledTextArea("Height", "" + height));

		final JPanel wrapper = new JPanel(new BorderLayout());
		wrapper.add(new ImagePanel(dimension, pixels), BorderLayout.NORTH);
		wrapper.add(imageInfoPanel, BorderLayout.SOUTH);

		super.add(wrapper);
		super.setResizable(false);
		super.pack();
		super.setVisible(true);
	}

	private class ImagePanel extends JPanel
	{
		/**
		 * The serial version UID.
		 */
		private static final long serialVersionUID = -7136505331750234290L;
		/**
		 * The buffered image created from pixels.
		 */
		private final BufferedImage image;
		/**
		 * Instantiate an image panel with a given image resolution and pixels.
		 */
		protected ImagePanel(final Dimension resolution, final int pixels[])
		{
			super();
			this.image = GraphicsSubsystem.CreateBufferedImage(pixels, resolution);
			super.setMinimumSize(resolution);
			super.setPreferredSize(resolution);
			super.setMaximumSize(resolution);
		}
		/**
		 * Paint the component.
		 */
		@Override
		public void paintComponent(final Graphics graphics)
		{
			super.paintComponent(graphics);
			graphics.drawImage(image, 0, 0, null);
			Toolkit.getDefaultToolkit().sync();
			graphics.dispose();
		}
	}
}
