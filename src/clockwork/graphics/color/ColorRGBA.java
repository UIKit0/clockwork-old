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



public class ColorRGBA
{
	/**
	 * The color channels.
	 */
	public static enum Channel {R, G, B, A}
	/**
	 * The red channel.
	 */
	public double r = 0.0f;
	/**
	 * The green channel.
	 */
	public double g = 0.0f;
	/**
	 * The blue channel.
	 */
	public double b = 0.0f;
	/**
	 * The alpha channel.
	 */
	public double a = 1.0f;
	/**
	 * Random number generator.
	 */
	private final static Random random = new Random();
	/**
	 * Instantiate a ColorRGBA from a 32-bit integer ARGB value.
	 * @param argb the 32-bit integer value to split.
	 */
	public ColorRGBA(final int argb)
	{
		this.copy(ColorRGBA.split(argb));
	}
	/**
	 * Instantiate a color with specified red, green, blue and alpha channels. Note that
	 * values are in the normalised range [0.0, 1.0].
	 */
	public ColorRGBA(final double red, final double green, final double blue, final double alpha)
	{
		r = red;
		g = green;
		b = blue;
		a = alpha;
	}
	/**
	 * Instantiate an RGBA color. The default color is black.
	 */
	public ColorRGBA()
	{
		this(0.0f, 0.0f, 0.0f, 0.0f);
	}
	/**
	 * Instantiate a color with specified red, green, and blue channels. Note that
	 * values are in the normalised range [0.0, 1.0].
	 */
	public ColorRGBA(final double red, final double green, final double blue)
	{
		this(red, green, blue, 1.0f);
	}
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
			case A:
				return a;
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
			case A:
				a = value;
			break;
		}
	}
	/**
	 * Merge the 3 channels to form a 32 bit integer RGB color.
	 */
	public int merge()
	{
		return ColorRGBA.merge(r, g, b, a);
	}

	/**
	 * Return the 32-bit integer representation of an RGBA color.
	 * @param r the color's red channel.
	 * @param g the color's green channel.
	 * @param b the color's blue channel.
	 * @param a the color's alpha channel.
	 */
	public static int merge(final double r, final double g, final double b, final double a)
	{
		int output = 0;

		// Convert the alpha channel and add it to the return value.
		if (a > 0.0)
			output = (int)Math.min(255, Math.round(a * 255.0f)) << 24;

		// Convert the red channel and add it to the return value.
		if (r > 0.0)
			output |= (int)Math.min(255, Math.round(r * 255.0f)) << 16;

		// Convert the green channel and add it to the return value.
		if (g > 0.0)
			output |= (int)Math.min(255, Math.round(g * 255.0f)) << 8;

		// Convert the blue channel and add it to the return value.
		if (b > 0.0)
			output |= (int)Math.min(255, Math.round(b * 255.0f));

		return output;
	}
	/**
	 * Split a 32-bit integer ARGB value into a ColorRGBA.
	 * @param rgba the ARGB value to split.
	 */
	public static ColorRGBA split(final int argb)
	{
		if (argb == 0)
			return new ColorRGBA();

		final double tmp = 1.0f / 255.0f;
		final double a = ((argb >> 24) & 0xff) * tmp;
		final double r = ((argb >> 16) & 0xff) * tmp;
		final double g = ((argb >>  8) & 0xff) * tmp;
		final double b = (argb         & 0xff) * tmp;

		return new ColorRGBA(r, g, b, a);
	}
	/**
	 * Copy another color's values into this one.
	 * @param that the color to copy.
	 */
	public void copy(final ColorRGBA that)
	{
		if (that != null)
		{
			this.r = that.r;
			this.g = that.g;
			this.b = that.b;
			this.a = that.a;
		}
	}
	/**
	 * Copy another color's values into this one.
	 * @param that the color to copy.
	 */
	public void copy(final int that)
	{
		copy(ColorRGBA.split(that));
	}
	/**
	 * Generate a random color.
	 */
	public static ColorRGBA getRandomColor()
	{
		double r = random.nextFloat();
		double g = random.nextFloat();
		double b = random.nextFloat();

		return new ColorRGBA(r, g, b);
	}
	/**
	 * Multiply the color by a value.
	 */
	public ColorRGBA multiply(final double value)
	{
		return new ColorRGBA
		(
			r * value,
			g * value,
			b * value,
			a * value
		);
	}
	/**
	 * Add a color to this color.
	 */
	public ColorRGBA add(final ColorRGBA that)
	{
		return new ColorRGBA
		(
			r + that.r,
			g + that.g,
			b + that.b,
			a + that.a
		);
	}


	/**
	 * Convert the color into a string.
	 */
	@Override
	public String toString()
	{
		return String.format("RGBA: %1.1f, %1.1f, %1.1f, %1.1f", r, g, b, a);
	}



	public static final ColorRGBA Black	= new ColorRGBA(0.0f, 0.0f, 0.0f);
	public static final ColorRGBA Red	= new ColorRGBA(1.0f, 0.0f, 0.0f);
	public static final ColorRGBA Green	= new ColorRGBA(0.0f, 1.0f, 0.0f);
	public static final ColorRGBA Blue	= new ColorRGBA(0.0f, 0.0f, 1.0f);
	public static final ColorRGBA White = new ColorRGBA(1.0f, 1.0f, 1.0f);


	public static final ColorRGBA BRIGHT_GRAY = new ColorRGBA(0xbf/0xff, 0xbf/0xff, 0xbf/0xff);
	public static final ColorRGBA MEDIUM_GRAY = new ColorRGBA(0x7f/0xff, 0x7f/0xff, 0x7f/0xff);
	public static final ColorRGBA DARK_GRAY = new ColorRGBA(0x3f/0xff, 0x3f/0xff, 0x3f/0xff);
	public static final ColorRGBA AQUA = new ColorRGBA(0x00, 0x8c/0xff, 0x8c/0xff);
}
