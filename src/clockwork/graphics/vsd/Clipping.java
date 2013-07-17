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
package clockwork.graphics.vsd;

import java.util.ArrayList;

import clockwork.graphics.Vertex;
import clockwork.system.RuntimeOptions;


/**
 * Perform normalised polygon clipping using the Cohen-Sutherland algorithm.
 * @see http://en.wikipedia.org/wiki/Cohen%E2%80%93Sutherland_algorithm
 */
public class Clipping
{
	/**
	 * 3D frustum extremity encodings.
	 * Inside near and far:
	 * 001001 001000 001010
	 * 000001 000000 000010
	 * 000101 000100 000110
	 *
	 * Intersected with near:
	 * 011001 011000 011010
	 * 010001 010000 010010
	 * 010101 010100 010110
	 *
	 * Intersected with far:
	 * 101001 101000 101010
	 * 100001 100000 100010
	 * 100101 100100 100110
	 */
	private static final int TOP    = 0x08;
	private static final int FAR    = 0x20;
	private static final int NEAR   = 0x10;
	private static final int LEFT   = 0x01;
	private static final int RIGHT  = 0x02;
	private static final int BOTTOM = 0x04;
	private static final int CENTER = 0x00;


	/**
	 * Perform clipping.
	 */
	public static Vertex[] apply(final ArrayList<Vertex> input)
	{
		if (RuntimeOptions.EnableClipping)
		{

		}
		return input.toArray(new Vertex[0]);
	}
}