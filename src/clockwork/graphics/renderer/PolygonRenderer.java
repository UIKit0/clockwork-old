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

import java.util.Arrays;

import clockwork.graphics.Fragment;



public abstract class PolygonRenderer extends Renderer
{
	/**
	 * Instantiate a polygon renderer with a given render type.
	 */
	protected PolygonRenderer(final Renderer.Type type)
	{
		super(type, Renderer.Mode.Polygon);
	}
	/**
	 * A PolygonRenderer creates a triangle from the three fragment it receives.
	 * The triangle's hollow is then filled with interpolated fragment values created
	 * by a method known as scan conversion.
	 */
	@Override
	public final void primitiveAssembly(final Fragment fragments[])
	{
		// Sort the fragments based on their vertical positions, from lowest to highest.
		Arrays.sort(fragments);

		// These three fragments will make up our triangle.
		final Fragment f0 = fragments[0];
		final Fragment f1 = fragments[1];
		final Fragment f2 = fragments[2];

		// If the triangle primitive is not correctly formed for this algorithm, tessellate two
		// new triangle primitives. If it is, proceed with the scan conversion.
		final boolean isTypeC = (f0.y != f1.y && f1.y != f2.y);
		if (isTypeC)
		{
			// Interpolate the new fragment.
			final Fragment fc = Fragment.interpolate(f2, f0, (f1.y - f0.y) / (f2.y - f0.y));

			// Change the fragment's Y value.
			fc.y = f1.y;

			// Create two new triangle primitives from the previous triangle.
			primitiveAssembly(new Fragment[]{f0, f1, fc});
			primitiveAssembly(new Fragment[]{f1, fc, f2});
		}
		else
			scanConversion(f0, f1, f2);
	}
	/**
	 * Perform scan conversion.
	 * @param f0 the first fragment that will make one point of a triangle.
	 * @param f1 the second fragment that will make one point of a triangle.
	 * @param f2 the third fragment that will make one point of a triangle.
	 */
	protected void scanConversion(final Fragment f0, final Fragment f1, final Fragment f2)
//FIXME USE THIS	protected final void scanConversion(final Fragment f0, final Fragment f1, final Fragment f2)
	{
		double xd0, xd1, xf0, xf1;
		double yd0, yd1, yf0, yf1;
		double zd0, zd1, zf0, zf1;

		double rd0, rd1, rf0, rf1;
		double gd0, gd1, gf0, gf1;
		double bd0, bd1, bf0, bf1;
		double ad0, ad1, af0, af1;

		double ud0, ud1, uf0, uf1;
		double vd0, vd1, vf0, vf1;

		double nxd0, nyd0, nzd0;
		double nxd1, nyd1, nzd1;
		double nxf0, nyf0, nzf0;
		double nxf1, nyf1, nzf1;


		// The value of the interpolated fragment.
		final Fragment fi = new Fragment();

		// Triangle type A:
		if (f0.y == f1.y)
		{
			xd0  = f0.x;		xd1  = f2.x;		xf0  = f1.x;		xf1  = xd1;
			yd0  = f0.y;		yd1  = f2.y;		yf0  = f1.y;		yf1  = yd1;
			zd0  = f0.z;		zd1  = f2.z;		zf0  = f1.z;		zf1  = zd1;
			rd0  = f0.r;		rd1  = f2.r;		rf0  = f1.r;		rf1  = rd1;
			gd0  = f0.g;		gd1  = f2.g;		gf0  = f1.g;		gf1  = gd1;
			bd0  = f0.b;		bd1  = f2.b;		bf0  = f1.b;		bf1  = bd1;
			ad0  = f0.a;		ad1  = f2.a;		af0  = f1.a;		af1  = ad1;
			ud0  = f0.u;		ud1  = f2.u;		uf0  = f1.u;		uf1  = ud1;
			vd0  = f0.v;		vd1  = f2.v;		vf0  = f1.v;		vf1  = vd1;
			nxd0 = f0.ni;		nxd1 = f2.ni;		nxf0 = f1.ni;		nxf1 = nxd1;
			nyd0 = f0.nj;		nyd1 = f2.nj;		nyf0 = f1.nj;		nyf1 = nyd1;
			nzd0 = f0.nk;		nzd1 = f2.nk;		nzf0 = f1.nk;		nzf1 = nzd1;
		}

		// Triangle type B:
		else
		{
			xd0  = f1.x;		xd1  = f0.x;		xf0  = f2.x;		xf1  = xd1;
			yd0  = f1.y;		yd1  = f0.y;		yf0  = f2.y;		yf1  = yd1;
			zd0  = f1.z;		zd1  = f0.z;		zf0  = f2.z;		zf1  = zd1;
			rd0  = f1.r;		rd1  = f0.r;		rf0  = f2.r;		rf1  = rd1;
			gd0  = f1.g;		gd1  = f0.g;		gf0  = f2.g;		gf1  = gd1;
			bd0  = f1.b;		bd1  = f0.b;		bf0  = f2.b;		bf1  = bd1;
			ad0  = f1.a;		ad1  = f0.a;		af0  = f2.a;		af1  = ad1;
			ud0  = f1.u;		ud1  = f0.u;		uf0  = f2.u;		uf1  = ud1;
			vd0  = f1.v;		vd1  = f0.v;		vf0  = f2.v;		vf1  = vd1;
			nxd0 = f1.ni;		nxd1 = f0.ni;		nxf0 = f2.ni;		nxf1 = nxd1;
			nyd0 = f1.nj;		nyd1 = f0.nj;		nyf0 = f2.nj;		nyf1 = nyd1;
			nzd0 = f1.nk;		nzd1 = f0.nk;		nzf0 = f2.nk;		nzf1 = nzd1;
		}

		final double dyd = yd1 - yd0;
		final double dyf = yf1 - yf0;

		for (long y = Math.round(f0.y); y < Math.round(f2.y); ++y)
		{
			final double pd  = (y - yd0) / dyd;
			final double ppd = 1.0 - pd;
			final double xd  = (xd0  * ppd) + (xd1  * pd);
			final double zd  = (zd0  * ppd) + (zd1  * pd);
			final double ud  = (ud0  * ppd) + (ud1  * pd);
			final double vd  = (vd0  * ppd) + (vd1  * pd);
			final double rd  = (rd0  * ppd) + (rd1  * pd);
			final double gd  = (gd0  * ppd) + (gd1  * pd);
			final double bd  = (bd0  * ppd) + (bd1  * pd);
			final double ad  = (ad0  * ppd) + (ad1  * pd);
			final double nxd = (nxd0 * ppd) + (nxd1 * pd);
			final double nyd = (nyd0 * ppd) + (nyd1 * pd);
			final double nzd = (nzd0 * ppd) + (nzd1 * pd);

			final double pf  = (y - yf0) / dyf;
			final double ppf = 1.0 - pf;
			final double xf  = (xf0  * ppf) + (xf1  * pf);
			final double zf  = (zf0  * ppf) + (zf1  * pf);
			final double uf  = (uf0  * ppf) + (uf1  * pf);
			final double vf  = (vf0  * ppf) + (vf1  * pf);
			final double rf  = (rf0  * ppf) + (rf1  * pf);
			final double gf  = (gf0  * ppf) + (gf1  * pf);
			final double bf  = (bf0  * ppf) + (bf1  * pf);
			final double af  = (af0  * ppf) + (af1  * pf);
			final double nxf = (nxf0 * ppf) + (nxf1 * pf);
			final double nyf = (nyf0 * ppf) + (nyf1 * pf);
			final double nzf = (nzf0 * ppf) + (nzf1 * pf);

			final double dx = xf - xd;
			for (long x = Math.round(Math.min(xd, xf)); x <= Math.max(xd, xf); ++x)
			{
//				final double p = (x - xd) / dx;
//				final double pp = 1.0 - p;
				final double pp = (x - xd) / dx;
				final double p = 1.0 - pp;

				// Initialise the interpolated fragment.
				fi.x = x;
				fi.y = y;
				fi.z = (p * zd) + (pp * zf);
				fi.u = (p * ud) + (pp * uf);
				fi.v = (p * vd) + (pp * vf);
				fi.r = (p * rd) + (pp * rf);
				fi.g = (p * gd) + (pp * gf);
				fi.b = (p * bd) + (pp * bf);
				fi.a = (p * ad) + (pp * af);

				fi.ni = (p * nxd) + (pp * nxf);
				fi.nj = (p * nyd) + (pp * nyf);
				fi.nk = (p * nzd) + (pp * nzf);

				framebuffer.write(this, fi);
			}
		}
	}
	/**
	 * @see Renderer#fragmentProgram.
	 */
	@Override
	public int fragmentProgram(final Fragment fragment)
	{
		return fragment.getColor();
	}
}
