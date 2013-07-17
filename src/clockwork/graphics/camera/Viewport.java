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
package clockwork.graphics.camera;

/**
 * A normalised viewport. It's values are independent of the current screen resolution.
 */
public class Viewport
{
	/**
	 * The viewport's horizontal origin.
	 */
	public float x;
	/**
	 * The viewport's vertical origin.
	 */
	public float y;
	/**
	 * The viewport's width.
	 */
	public float width;
	/**
	 * The viewport's height.
	 */
	public float height;
	/**
	 * The default constructor.
	 */
	public Viewport()
	{
		this(0.0f, 0.0f, 1.0f, 1.0f);
	}
	/**
	 * Instantiate a normalised viewport with a given origin, width and height.
	 */
	public Viewport(final float x, final float y, final float width, final float height)
	{
		setXYWH(x, y, width, height);
	}
	/**
	 * Set the viewport's values.
	 */
	public void setXYWH(final float x, final float y, final float width, final float height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	/**
	 * Copy a viewport.
	 */
	public void copy(final Viewport viewport)
	{
		setXYWH(viewport.x, viewport.y, viewport.width, viewport.height);
	}
	/**
	 * Validate the viewport values.
	 */
	public void validate()
	{
		x = Math.min(1.0f, Math.max(0.0f, x));
		y = Math.min(1.0f, Math.max(0.0f, y));
		width = Math.min(1.0f, Math.max(0.0f, width));
		height = Math.min(1.0f, Math.max(0.0f, height));
	}
}