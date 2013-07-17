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

import clockwork.graphics.filters.FXAA;
import clockwork.system.RuntimeOptions;

/**
 * Anti-aliasing filter.
 */
public class Antialiasing
{
	/**
	 * An enumeration of available anti-aliasing algorithms.
	 */
	public static enum Algorithm
	{
		MSAA("Multisample Anti-aliasing (Not implemented)"),
		FXAA("Fast Approximate Anti-aliasing"),
		SMAA("Sub-pixel Morphological Anti-aliasing (Not implemented)"),
		SSAA("Supersample Anti-aliasing (Not implemented)");
		/**
		 * The filter's title.
		 */
		final String title;
		/**
		 * The default constructor.
		 */
		Algorithm(final String title)
		{
			this.title = title;
		}
		/**
		 * Convert an enumeration to a String.
		 */
		@Override
		public String toString()
		{
			return title;
		}
	}
	/**
	 * The current anti-aliasing algorithm.
	 */
	public static Algorithm ALGORITHM = Algorithm.FXAA;
	/**
	 * Apply an anti-aliasing algorithm to the framebuffer.
	 * @param framebuffer the framebuffer where the anti-aliasing will be performed.
	 */
	public static void apply(final Framebuffer framebuffer)
	{
		if (RuntimeOptions.EnableAntialiasing && framebuffer != null)
		{
			switch (ALGORITHM)
			{
				case FXAA:
					FXAA.apply(framebuffer);
					break;
				default:
					break;
			}
		}
	}
}
