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
package clockwork.graphics.camera.projection.parallel.oblique;

import clockwork.graphics.camera.Frustum;
import clockwork.graphics.camera.projection.Projection;
import clockwork.types.math.Matrix4;



public abstract class AbstractObliqueProjection extends Projection
{
	// There are two kinds of oblique projections: Cavalier and Cabinet. The differences
	// between the two are the length of the projection of the unit vector Z, and the angle
	// of this projection with the X axis.
	//
	// The length will be denoted as L; L_cavaliere = 1 and L_cabinet = 0.5.
	// The angle will be denoted as alpha; alpha_cavaliere = 45 and alpha_cabinet = 63.4.
	final double L;
	final double alpha;
	final double phi;

	AbstractObliqueProjection(final Projection.Type type, final double L, final double alpha)
	{
		super(type);

		this.L = L;
		this.alpha = alpha * clockwork.types.math.Trigonometry.PIOVER180;
		this.phi = 45.0f * clockwork.types.math.Trigonometry.PIOVER180;
	}


	@Override
	public Matrix4 toMatrix(final Frustum frustum)
	{
		final double znear	 = frustum.getHitherPlaneDistance();
		final double zfar		 = frustum.getYonPlaneDistance();
		final double dz		 = zfar - znear;
		final double aspect	 = frustum.getAspectRatio();
		final double cosPhi   = L * Math.cos(phi);
		final double sinPhi   = L * Math.sin(phi);
		final double tanAlpha =		 Math.tan(alpha);

		final double m11 = 1.0 / aspect;
		final double m31 = -1.0 * (float)(cosPhi/tanAlpha);
		final double m32 = -1.0 * (float)(sinPhi/tanAlpha);
		final double m33 = -2.0/dz;
		final double m41 = -m31;
		final double m42 = -m32;
		final double m43 = -(zfar + znear)/dz;

		return new Matrix4
		(
			new double[]
			{
				m11,  0.0f, 0.0f, 0.0f,
				0.0f, 1.0f, 0.0f, 0.0f,
				m31,  m32,   m33, 0.0f,
				m41,  m42,   m43, 1.0f
			}
		);
	}
}