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

import clockwork.graphics.color.ColorRGBA;

/**
 * A fragment is the result of a renderer's per-vertex operation applied to a vertex.
 * Primarily, a fragment should contain a vertex's 2D normalised device coordinates (or
 * viewport coordinates), its depth value, its surface normal and a base color that will all
 * be used to determine a color that will be written to the framebuffer.
 */
public class Fragment implements Comparable<Fragment>
{
	/**
	 * The fragment's position in window coordinates and it's depth value.
	 */
	public double x = 0.0;
	public double y = 0.0;
	public double z = 0.0;
	/**
	 * The fragment's mapping coordinates.
	 */
	public double u = 0;
	public double v = 0;
	/**
	 * The fragment's normal vector components.
	 */
	public double ni = 0.0;
	public double nj = 0.0;
	public double nk = 0.0;
	/**
	 * The fragment's normalised color channels.
	 */
	public double r = 1.0;
	public double g = 1.0;
	public double b = 1.0;
	public double a = 1.0;
	/**
	 * The fragment's stencil value.
	 */
	public char stencil = 0;
	/**
	 * The default constructor.
	 */
	public Fragment()
	{}
	/**
	 * Instantiate a fragment from a vertex object.
	 * @param parent the vertex to clone.
	 */
	public Fragment(final Vertex vertex)
	{
		if (vertex != null)
		{
			this.x  = vertex.position.x;
			this.y  = vertex.position.y;
			this.z  = vertex.position.z;
			this.u  = vertex.u;
			this.v  = vertex.v;

			this.ni = vertex.normal.i;
			this.nj = vertex.normal.j;
			this.nk = vertex.normal.k;

			this.r  = vertex.color.r;
			this.g  = vertex.color.g;
			this.b  = vertex.color.b;
			this.a  = vertex.color.a;
		}
	}
	/**
	 * Instantiate a fragment from a vertex object and apply a viewport transformation to
	 * the fragment's 2D position. The viewport transformation will convert the fragment's
	 * position from normalised device coordinate space to viewport space.
	 * @param parent the vertex to clone.
	 * @param vx the the viewport's transformation component on the X axis.
	 * @param vy the the viewport's transformation component on the Y axis.
	 */
	public Fragment(final Vertex vertex, final double vx, final double vy)
	{
		this(vertex);

		x = (this.x + 1.0) * vx;
		y = (this.y + 1.0) * vy;
	}
	/**
	 * Merge the fragment's channels to form a 32-bit color.
	 */
	public int getColor()
	{
		return ColorRGBA.merge(r, g, b, a);
	}
	/**
	 * Interpolate the value of a fragment in between two other fragments.
	 * @param start the first fragment.
	 * @param end the second fragment.
	 * @param p the interpolant.
	 */
	public static Fragment interpolate
	(
		final Fragment start,
		final Fragment end,
		final double p
	)
	{
		final double pp = 1.0 - p;
		final Fragment output = new Fragment();

		output.x = (p * start.x) + (pp * end.x);
		output.y = (p * start.y) + (pp * end.y);
		output.z = (p * start.z) + (pp * end.z);
		output.u = (p * start.u) + (pp * end.u);
		output.v = (p * start.v) + (pp * end.v);
		output.r = (p * start.r) + (pp * end.r);
		output.g = (p * start.g) + (pp * end.g);
		output.b = (p * start.b) + (pp * end.b);
		output.a = (p * start.a) + (pp * end.a);

		output.ni = (p * start.ni) + (pp * end.ni);
		output.nj = (p * start.nj) + (pp * end.nj);
		output.nk = (p * start.nk) + (pp * end.nk);

		// TODO Implement me.
//		fragment.stencil = (char)Math.round((p * start.stencil) + (pp * end.stencil));

		return output;
	}
	/**
	 * Compare two fragments based on their positions.
	 * @param that the fragment with which to compare.
	 */
	@Override
	public int compareTo(final Fragment that)
	{
		if (this.y > that.y)
			return 1;
		else if (this.y == that.y)
		{
			if (this.x > that.x)
				return 2;
			else if (this.x == that.x)
			{
				if (this.z > that.z)
					return 3;
				else
					return -3;
			}
			else
				return -2;
		}
		else
			return -1;
	}
	/**
	 * Convert the fragment data to a string.
	 */
	@Override
	public String toString()
	{
		return "(EXPERIMENTAL) Fragment x: " + x + ", y: " + y;
	}
}
