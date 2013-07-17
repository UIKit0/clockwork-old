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

/**
 * TODO Explain me better.
 */
public class Scissor
{
	/**
	 * The scissor's left position.
	 */
	public int left = 0;
	/**
	 * The scissor's bottom position.
	 */
	public int bottom = 0;
	/**
	 * The scissor's width.
	 */
	public int width = 0;
	/**
	 * The scissor's height.
	 */
	public int height = 0;
	/**
	 * Perform the scissor test for a given XY coordinates.
	 * @param x the X coordinate.
	 * @param y the Y coordinate.
	 */
	public boolean test(final int x, final int y)
	{
		return left <= x && x < (left + width) && bottom <= y && y < (bottom + height);
	}
}
