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
package clockwork.graphics.renderer;

import clockwork.graphics.Fragment;

public final class WireframeRenderer extends Renderer
{
	/**
	 * The default constructor.
	 */
	protected WireframeRenderer()
	{
		super(Renderer.Type.Wireframe, Renderer.Mode.Wireframe);
	}
	/**
	 * A WireframeRenderer simply creates edges out of the vertices and uses
	 * a line drawing algorithm to draw lines between the fragments. Pixel
	 * color is determined by the fragment program, as usual.
	 */
	@Override
	public void primitiveAssembly(final Fragment fragments[])
	{
		// Create line primitives and render them.
		for (int i = 0; i < fragments.length; ++i)
		{
			final Fragment f0 = fragments[i];
			final Fragment f1 = fragments[(i + 1) % fragments.length];

			drawline(f0, f1);
		}
	}
	/**
	 * TODO Implement Xiaolin Wu's algorithm, instead of bresenham's.
	 * Draw a line between two fragment points. This is the scan conversion algorithm for lines.
	 * @param f0 the fragment containing the line's origin point.
	 * @param f1 the fragment containing the line's end point.
	 */
	public void drawline(final Fragment f0, final Fragment f1)
	{
		final double dy = f1.y - f0.y;
		final double dx = f1.x - f0.x;

		// Base method.
		final double slope = dy / dx;
		final double b = f0.y - (slope * f0.x);

		// Set the initial pixel.
		framebuffer.write(this, f0);

		if (dx == 0.0f)
		{
			final int min = (int)Math.round(Math.min(f0.y, f1.y));
			final int max = (int)Math.round(Math.max(f0.y, f1.y));

			for (int y = min; y <= max; ++y)
			{
				// Interpolate a new fragment.
				final Fragment fi =
				Fragment.interpolate(f0, f1, (dy == 0.0f ? 0.0f : (float)((y - f0.y) / dy)));

				// Change the fragment's X and Y position.
				fi.x = f0.x;
				fi.y = y;

				framebuffer.write(this, fi);
			}
		}
		else if (Math.abs(slope) < 1)
		{
			final int xmin = (int)Math.round(Math.min(f0.x, f1.x));
			final int xmax = (int)Math.round(Math.max(f0.x, f1.x));

			for (int x = xmin; x <= xmax; ++x)
			{
				// Interpolate a new fragment.
				final Fragment fi =
				Fragment.interpolate(f0, f1, (dx == 0.0f ? 0.0f : (float)((x - f0.x)/dx)));

				// Change the fragment's X and Y position.
				fi.x = x;
				fi.y = Math.round((slope * x) + b);

				framebuffer.write(this, fi);
			}
		}
		else
		{
			final int ymin = (int)Math.round(Math.min(f0.y, f1.y));
			final int ymax = (int)Math.round(Math.max(f0.y, f1.y));

			for (int y = ymin; y <= ymax; ++y)
			{
				// Interpolate a new fragment.
				final Fragment fi =
				Fragment.interpolate(f0, f1, (dy == 0.0f ? 0.0f : (float)((y - f0.y) / dy)));

				// Change the fragment's X and Y position.
				fi.x = Math.round((y - b) / slope);
				fi.y = y;

				framebuffer.write(this, fi);
			}
		}
	}
}
