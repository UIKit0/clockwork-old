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
	 * The alpha channel.
	 */
	public double a = 1.0;
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
	{}
	/**
	 * Instantiate a color with specified red, green, and blue channels. Note that
	 * values are in the normalised range [0.0, 1.0].
	 */
	public ColorRGBA(final double red, final double green, final double blue)
	{
		this(red, green, blue, 1.0);
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
			output = (int)Math.min(255, Math.round(a * 255.0)) << 24;

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
	 * Split a 32-bit integer ARGB value into a ColorRGBA.
	 * @param rgba the ARGB value to split.
	 */
	public static ColorRGBA split(final int argb)
	{
		if (argb == 0)
			return new ColorRGBA();

		final double a = ((argb >> 24) & 0xff) * 0.00392156862; // 0.00392156862 = (1/255).
		final double r = ((argb >> 16) & 0xff) * 0.00392156862;
		final double g = ((argb >>  8) & 0xff) * 0.00392156862;
		final double b =  (argb        & 0xff) * 0.00392156862;

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
		double r = random.nextDouble();
		double g = random.nextDouble();
		double b = random.nextDouble();

		return new ColorRGBA(r, g, b);
	}
	/**
	 * Multiply this color by a value.
	 */
	public ColorRGBA multiply(final double value)
	{
		this.r *= value;
		this.g *= value;
		this.b *= value;
		this.a *= value;

		return this;
	}
	/**
	 * Add a color to this color.
	 */
	public ColorRGBA add(final ColorRGBA that)
	{
		if (that != null)
		{
			this.r += that.r;
			this.g += that.g;
			this.b += that.b;
			this.a += that.a;
		}
		return this;
	}
	/**
	 * Convert the color into a string.
	 */
	@Override
	public String toString()
	{
		return String.format("RGBA: %1.1f, %1.1f, %1.1f, %1.1f", r, g, b, a);
	}



	public static final ColorRGBA Black	= new ColorRGBA(0.0, 0.0, 0.0);
	public static final ColorRGBA Red	= new ColorRGBA(1.0, 0.0, 0.0);
	public static final ColorRGBA Green	= new ColorRGBA(0.0, 1.0, 0.0);
	public static final ColorRGBA Blue	= new ColorRGBA(0.0, 0.0, 1.0);
	public static final ColorRGBA White = new ColorRGBA(1.0, 1.0, 1.0);
}
