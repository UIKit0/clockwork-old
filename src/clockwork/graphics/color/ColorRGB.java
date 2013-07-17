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
package clockwork.graphics.color;

import java.util.Random;



public class ColorRGB
{
	/**
	 * The color channels.
	 */
	public static enum Channel {R, G, B}
	/**
	 * The red channel.
	 */
	public double r = 0.0;
	/**
	 * The green channel.
	 */
	public double g = 0.0;
	/**
	 * The blue channel.
	 */
	public double b = 0.0;
	/**
	 * Random number generator.
	 */
	private final static Random random = new Random();
	/**
	 * Instantiate a ColorRGBA from a 32-bit integer RGB value. Note that
	 * each channel is 8 bits long and so therefore only 24 bits are used.
	 * @param rgb the 32-bit integer value to split.
	 */
	public ColorRGB(final int rgb)
	{
		this.copy(ColorRGB.split(rgb));
	}
	/**
	 * Instantiate a color with specified red, green and blue channels. Note that
	 * values are in the normalised range [0.0, 1.0].
	 */
	public ColorRGB(final double red, final double green, final double blue)
	{
		r = red;
		g = green;
		b = blue;
	}
	/**
	 * Instantiate an RGB color. The default color is black.
	 */
	public ColorRGB()
	{}
	/**
	 * Return the value of a specified channel.
	 */
	public double getChannel(final Channel channel)
	{
		switch (channel)
		{
			case R:
				return r;
			case G:
				return g;
			case B:
				return b;
			default:
				return 0;
		}
	}
	/**
	 * Set the value of a specified channel.
	 */
	public void setChannel(final Channel channel, final double value)
	{
		switch (channel)
		{
			case R:
				r = value;
			break;
			case G:
				g = value;
			break;
			case B:
				b = value;
			break;
		}
	}
	/**
	 * Merge the 3 channels to form a 32 bit integer RGB color.
	 */
	public int merge()
	{
		return ColorRGB.merge(r, g, b);
	}
	/**
	 * Return the 32-bit integer representation of an RGB color.
	 * @param r the color's red channel.
	 * @param g the color's green channel.
	 * @param b the color's blue channel.
	 */
	public static int merge(final double r, final double g, final double b)
	{
		int output = 0;

		// Convert the alpha channel and add it to the return value.
		output = 255 << 24;

		// Convert the red channel and add it to the return value.
		if (r > 0.0)
			output |= (int)Math.min(255, Math.round(r * 255.0)) << 16;

		// Convert the green channel and add it to the return value.
		if (g > 0.0)
			output |= (int)Math.min(255, Math.round(g * 255.0)) << 8;

		// Convert the blue channel and add it to the return value.
		if (b > 0.0)
			output |= (int)Math.min(255, Math.round(b * 255.0));

		return output;
	}
	/**
	 * Split a 32-bit integer RGB value into a ColorRGB.
	 * @param rgb the RGB value to split.
	 */
	public static ColorRGB split(final int rgb)
	{
		if (rgb == 0)
			return new ColorRGB();

		final double tmp = 1.0f / 255.0f;
		final double r = ((rgb >> 16) & 0xff) * tmp;
		final double g = ((rgb >>  8) & 0xff) * tmp;
		final double b =  (rgb        & 0xff) * tmp;

		return new ColorRGB(r, g, b);
	}
	/**
	 * Copy another color's values into this one.
	 * @param that the color to copy.
	 */
	public void copy(final ColorRGB that)
	{
		if (that != null)
		{
			this.r = that.r;
			this.g = that.g;
			this.b = that.b;
		}
	}
	/**
	 * Copy another color's values into this one.
	 * @param that the color to copy.
	 */
	public void copy(final int that)
	{
		copy(ColorRGB.split(that));
	}
	/**
	 * Generate a random color.
	 */
	public static ColorRGB getRandomColor()
	{
		double r = random.nextFloat();
		double g = random.nextFloat();
		double b = random.nextFloat();

		return new ColorRGB(r, g, b);
	}
	/**
	 * Multiply the color by a value.
	 */
	public ColorRGB multiply(final double value)
	{
		return new ColorRGB
		(
			r * value,
			g * value,
			b * value
		);
	}
	/**
	 * Add a color to this color.
	 */
	public ColorRGB add(final ColorRGB that)
	{
		return new ColorRGB
		(
			r + that.r,
			g + that.g,
			b + that.b
		);
	}


	/**
	 * Convert the color into a string.
	 */
	@Override
	public String toString()
	{
		return String.format("RGBA: %1.1f, %1.1f, %1.1f", r, g, b);
	}



	public static final ColorRGB Black	= new ColorRGB(0.0f, 0.0f, 0.0f);
	public static final ColorRGB Red	= new ColorRGB(1.0f, 0.0f, 0.0f);
	public static final ColorRGB Green	= new ColorRGB(0.0f, 1.0f, 0.0f);
	public static final ColorRGB Blue	= new ColorRGB(0.0f, 0.0f, 1.0f);
	public static final ColorRGB White = new ColorRGB(1.0f, 1.0f, 1.0f);


	public static final ColorRGB BRIGHT_GRAY = new ColorRGB(0xbf/0xff, 0xbf/0xff, 0xbf/0xff);
	public static final ColorRGB MEDIUM_GRAY = new ColorRGB(0x7f/0xff, 0x7f/0xff, 0x7f/0xff);
	public static final ColorRGB DARK_GRAY = new ColorRGB(0x3f/0xff, 0x3f/0xff, 0x3f/0xff);
	public static final ColorRGB AQUA = new ColorRGB(0x00, 0x8c/0xff, 0x8c/0xff);
}
