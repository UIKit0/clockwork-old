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
package clockwork.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.util.LinkedHashSet;
import java.util.concurrent.locks.ReentrantLock;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import clockwork.graphics.Framebuffer;
import clockwork.gui.controls.InputControls;
import clockwork.system.Debug;
import clockwork.system.RuntimeOptions;



public final class Display extends JComponent
{
	public static enum Resolution
	{
		QQVGA("QQVGA", 160, 120),
		HQVGA("HQVGA", 240, 160),
		QVGA("QVGA", 320, 240),
		HVGA("HVGA", 480, 320),
		VGA("VGA", 640, 480),
		SVGA("SVGA", 800, 600),
		DVGA("DVGA", 960, 640),
		XGA("XGA", 1024, 768),
		HD("HD", 1280, 720),
		FHD("Full HD", 1920, 1080);
		/**
		 * Instantiate a named resolution with a given width and height.
		 * @param the resolution's name. @see http://en.wikipedia.org/wiki/Graphics_display_resolution
		 * @param width the resolution width.
		 * @param height the resolution height.
		 */
		Resolution(final String name, final int width, final int height)
		{
			this.name = name;
			this.width = width;
			this.height = height;
		}
		/**
		 * The resolution's name.
		 */
		public final String name;
		/**
		 * The resolution's width.
		 */
		public final int width;
		/**
		 * The resolution's height.
		 */
		public final int height;
		/**
		 * Convert the resolution to a Dimension.
		 */
		public Dimension toDimension()
		{
			return new Dimension(width, height);
		}
		/**
		 * Convert the resolution data to a string.
		 */
		@Override
		public String toString()
		{
			return name + " (" + width + "x" + height + ")";
		}
	};
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 4904363064468002443L;
	/**
	 * A mutex to block read or write operations on the display's color buffer.
	 */
	private static final ReentrantLock READWRITE_LOCK = new ReentrantLock();
	/**
	 * The display device's resolution.
	 */
	private Resolution resolution;
	/**
	 * Enable vertical synchronisation.
	 */
	private boolean enableVSYNC;
	/**
	 * The display device's pixel buffer.
	 */
	private BufferedImage colorbuffer;
	/**
	 * The color buffer's raw pixel data.
	 */
	private int rawColorbuffer[];
	/**
	 * An instance of the framebuffer.
	 */
	private Framebuffer framebuffer;
	/**
	 * The font used to print text to the display.
	 */
	private float fontSize;
	/**
	 * Instantiate a Display object with a given dimension.
	 * @param dimension the display's dimension.
	 */
	public Display(final Resolution resolution)
	{
		super();
		this.fontSize = 12;
		this.enableVSYNC = true;
		setResolution(resolution != null ? resolution : Resolution.VGA);
		setOpaque(false);
	}
	/**
	 * Return the framebuffer.
	 */
	public Framebuffer getFramebuffer()
	{
		return framebuffer;
	}
	/**
	 * Set the framebuffer.
	 * @param framebuffer the framebuffer to set.
	 */
	public void setFramebuffer(final Framebuffer framebuffer)
	{
		this.framebuffer = framebuffer;
		framebuffer.resize(this.resolution);
	}
	/**
	 * Get the display device's resolution.
	 */
	public Resolution getResolution()
	{
		return resolution;
	}
	/**
	 * Set the display's resolution.
	 * @param resolution the resolution to set.
	 */
	public void setResolution(final Resolution resolution)
	{
		if (resolution != null && this.resolution != resolution)
		{
			this.resolution = resolution;
			final Dimension dimension = this.resolution.toDimension();

			// Create a new color buffer.
			colorbuffer =
			new BufferedImage(resolution.width, resolution.height, BufferedImage.TYPE_INT_ARGB);
			this.rawColorbuffer = ((DataBufferInt)colorbuffer.getRaster().getDataBuffer()).getData();
			setMinimumSize(dimension);
			setPreferredSize(dimension);

			// FIXME Implement font size change!
			// Calculate the display font size based on the resolution.
			fontSize = 12.0f; //(int)(0.8 * 10);//(16 * resolution.height) / resolution.width;

			// Resize the framebuffer.
			if (framebuffer != null)
				framebuffer.resize(this.resolution);
		}
	}
	/**
	 * Return the display's width.
	 */
	@Override
	public int getWidth()
	{
		return resolution.width;
	}
	/**
	 * Return the display's height.
	 */
	@Override
	public int getHeight()
	{
		return resolution.height;
	}
	/**
	 * Paint the component.
	 */
	@Override
	public void paintComponent(final Graphics graphics)
	{
		super.paintComponent(graphics);

		// Blit the framebuffer's buffer (the draw buffer) to the display's buffer and then
		// draw the display's color buffer.
//		Framebuffer.READWRITE_LOCK.lock();
		Display.READWRITE_LOCK.lock();
		System.arraycopy(framebuffer.getOutput(), 0, rawColorbuffer, 0, rawColorbuffer.length);
		Display.READWRITE_LOCK.unlock();
		graphics.drawImage(colorbuffer, 0, 0, null);
//		Framebuffer.READWRITE_LOCK.unlock();

		// Draw debug information.
		if (RuntimeOptions.EnableDebugInformation)
		{
			graphics.getFont().deriveFont(fontSize);
			graphics.setColor(Color.blue);
			Debug.print(graphics);
		}
		Toolkit.getDefaultToolkit().sync();
		graphics.dispose();
	}
	/**
	 * Take a screenshot of the display and save it to an image file.
	 */
	public void screenshot()
	{
		final String filename = "screenshot";
		final String fileExtension = "png";
		final String fullExtension = ".clockwork.png";
		File file = new File(filename + fullExtension);
		if (file.isFile())
		{
			// Find a file that doesn't exist.
			for (int i = 1;;)
			{
				file = new File(filename + "." + (i++) + fullExtension);
				if (!file.isFile())
					break;
			}
		}

		// Write the PNG file.
		try
		{
			Display.READWRITE_LOCK.lock();
			final boolean ok = ImageIO.write(colorbuffer, fileExtension, file);
			Display.READWRITE_LOCK.unlock();
			JOptionPane.showMessageDialog
			(
				new JFrame(),
				(ok ? "Succesfully wrote '" : "Error writing '") + filename + fullExtension + "'",
				ok ? "Info" : "Error",
				ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE
			);
		}
		catch (final Exception e){}
	}
	/**
	 * Return the available resolutions supported by this display.
	 */
	public LinkedHashSet<Display.Resolution> getAvailableResolutions()
	{
		// Calculate the maximum accepted width and height for a display.
		final Dimension screenResolution = Toolkit.getDefaultToolkit().getScreenSize();
		final int maximumWidth = screenResolution.width - InputControls.DIMENSION.width;
		final int maximumHeight = screenResolution.height - 100;

		final LinkedHashSet<Display.Resolution> resolutions = new LinkedHashSet<Display.Resolution>();
		for (Display.Resolution resolution : Display.Resolution.values())
		{
			if (resolution.height <= maximumHeight && resolution.width <= maximumWidth)
			resolutions.add(resolution);
		}

		return resolutions;
	}
	/**
	 * Enable vertical synchronisation.
	 */
	public void enableVSYNC(final boolean enableVSYNC)
	{
		this.enableVSYNC = enableVSYNC;
	}
	/**
	 * Is vertical synchronisation enabled?
	 */
	public boolean isVSYNCEnabled()
	{
		return enableVSYNC;
	}
}
