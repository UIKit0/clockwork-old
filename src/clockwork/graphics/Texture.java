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

import clockwork.asset.Asset;

/**
 * A texture.
 */
public class Texture extends Asset
{
	/**
	 * The array of texture elements.
	 */
	public final int texels[];
	/**
	 * The width of the texture.
	 */
	public final int width;
	/**
	 * The height of the texture.
	 */
	public final int height;
	/**
	 * Instantiate a texture with a given array of texels, width and height.
	 * @param texels the texture elements that will make up this texture.
	 * @param width the texture's width.
	 * @param height the texture's height.
	 */
	public Texture(final int texels[], final int width, final int height)
	{
		this.width = width;
		this.height = height;
		this.texels = texels;
	}

	/**
	 * TODO Explain parameters.
	 * Return the texture element at the given UV coordinate.
	 */
	private int getTexel(int u, int v)
	{
		u = u < width  ? u : width  - 1;
		v = v < height ? v : height - 1;

		return texels[(v * width) + u];
	}
	/**
	 * TODO Explain parameters.
	 * Return the texture element at the given normalised UV coordinate.
	 */
	public int getTexel(final double u, final double v)
	{
		final int ut = (int)Math.round((u < 0.0 ? 0.0 : (u > 1.0 ? 1.0 : u)) * width);
		final int vt = (int)Math.round((v < 0.0 ? 0.0 : (v > 1.0 ? 1.0 : v)) * height);

		return getTexel(ut, vt);
	}
	/**
	 * Convert the texture data to a string.
	 */
	@Override
	public String toString()
	{
		return "texture/" + super.toString();
	}
}
