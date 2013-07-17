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
package clockwork.types.math;

import java.util.Arrays;

import clockwork.graphics.camera.Frustum;

/**
 * A 4x4 matrix.
 */
public final class Matrix4
{
	/**
	 * The underlying matrix data.
	 */
	private final double data[];
	/**
	 * Instantiate a 4x4 identity matrix.
	 */
	public Matrix4()
	{
		data = new double[]
		{
			1.0, 0.0, 0.0, 0.0,
			0.0, 1.0, 0.0, 0.0,
			0.0, 0.0, 1.0, 0.0,
			0.0, 0.0, 0.0, 1.0
		};
	}
	/**
	 * Instantiate a 4x4 matrix from a set of data.
	 */
	public Matrix4(final double data[])
	{
		this();
		if (data != null && data.length == 16)
			System.arraycopy(data, 0, this.data, 0, 16);
	}
	/**
	 * Instantiate a 4x4 matrix from another 4x4 matrix.
	 */
	public Matrix4(final Matrix4 that)
	{
		this();
		if (that != null)
			copy(that);
	}
	/**
	 * Return a 4x4 matrix filled with zeros.
	 */
	public static Matrix4 getZeros()
	{
		return new Matrix4(new double[]
		{
			0.0, 0.0, 0.0, 0.0,
			0.0, 0.0, 0.0, 0.0,
			0.0, 0.0, 0.0, 0.0,
			0.0, 0.0, 0.0, 0.0
		});
	}
	/**
	 * Return a 4x4 matrix filled with ones.
	 */
	public static Matrix4 getOnes()
	{
		return new Matrix4(new double[]
		{
			1.0, 1.0, 1.0, 1.0,
			1.0, 1.0, 1.0, 1.0,
			1.0, 1.0, 1.0, 1.0,
			1.0, 1.0, 1.0, 1.0
		});
	}
	/**
	 * Fill the matrix with a given value.
	 * @param value the value that will fill the matrix data.
	 */
	public void fill(final double value)
	{
		Arrays.fill(data, value);
	}
	/**
	 * Return the matrix's underlying data.
	 */
	public double[] getData()
	{
		return data;
	}
	/**
	 * Multiply this matrix with another.
	 */
	public Matrix4 multiply(final Matrix4 that)
	{
		double newdata[] = null;
		if (that == null)
			newdata = this.data;
		else
		{
			newdata = new double[16];

			for (int i = 0; i < 4; ++i)
			{
				int offset = i * 4;
				for (int j = 0; j < 4; ++j)
				{
					newdata[offset] = 0.0f;
					for (int k = 0; k < 4; ++k)
						newdata[offset] += this.get(i, k) * that.get(k, j);

					++offset;
				}
			}
		}
		return new Matrix4(newdata);
	}
	/**
	 * Multiply this matrix with a 4D point.
	 */
	public Point4f multiply(final Point4f point)
	{
		final Point4f result = new Point4f();
		final double pointdata[] = point.getXYZW();
		final double newdata[] = new double[]{0.0f, 0.0f, 0.0f, 0.0f};

		for (int i = 0; i < 4; ++i)
		{
			for (int j = 0; j < 4; ++j)
				newdata[i] += get(i, j) * pointdata[j];
		}
		result.setXYZW(newdata);
		return result;
	}
	/**
	 * Multiply this matrix with a 3D point.
	 */
	public Point3f multiply(final Point3f point)
	{
		final Point3f result = new Point3f();
		final double pointdata[] = point.getXYZW();
		final double newdata[] = new double[]{0.0f, 0.0f, 0.0f, 0.0f};

		for (int i = 0; i < 4; ++i)
		{
			for (int j = 0; j < 4; ++j)
				newdata[i] += get(i, j) * pointdata[j];
		}
		result.setXYZW(newdata);
		return result;
	}
	/**
	 * Multiply this matrix with a 3D vector.
	 */
	public Vector3f multiply(final Vector3f vector)
	{
		final Vector3f result = new Vector3f();
		final double vectordata[] = vector.getIJKL();
		final double newdata[] = new double[]{0.0f, 0.0f, 0.0f, 0.0f};

		for (int i = 0; i < 4; ++i)
		{
			for (int j = 0; j < 4; ++j)
				newdata[i] += get(i, j) * vectordata[j];
		}
		result.setIJKL(newdata);
		return result;
	}
	/**
	 * Return the matrix's transpose.
	 */
	public Matrix4 transpose()
	{
		final double newdata[] = new double[16];
		for (int i = 0; i < 4; ++i)
		{
			for (int j = 0; j < 4; ++j)
			{
				final int offset = (i * 4) + j;
				final int oldOffset = (j * 4) + i;

				newdata[offset] = data[oldOffset];
			}
		}
		return new Matrix4(newdata);
	}
	/**
	 * TODO Optimise: Replace get(i, j).
	 * Return the inverse of the given matrix, courtesy of http://stackoverflow.com/q/2624422
	 * @param input the matrix whose inverse we wish to calculate.
	 */
	public static Matrix4 inverse(final Matrix4 input)
	{
		final Matrix4 output = new Matrix4(input);
		if (input != null)
		{
			final double s0 = input.get(0, 0) * input.get(1, 1) - input.get(1, 0) * input.get(0, 1);
			final double s1 = input.get(0, 0) * input.get(1, 2) - input.get(1, 0) * input.get(0, 2);
			final double s2 = input.get(0, 0) * input.get(1, 3) - input.get(1, 0) * input.get(0, 3);
			final double s3 = input.get(0, 1) * input.get(1, 2) - input.get(1, 1) * input.get(0, 2);
			final double s4 = input.get(0, 1) * input.get(1, 3) - input.get(1, 1) * input.get(0, 3);
			final double s5 = input.get(0, 2) * input.get(1, 3) - input.get(1, 2) * input.get(0, 3);

			final double c5 = input.get(2, 2) * input.get(3, 3) - input.get(3, 2) * input.get(2, 3);
			final double c4 = input.get(2, 1) * input.get(3, 3) - input.get(3, 1) * input.get(2, 3);
			final double c3 = input.get(2, 1) * input.get(3, 2) - input.get(3, 1) * input.get(2, 2);
			final double c2 = input.get(2, 0) * input.get(3, 3) - input.get(3, 0) * input.get(2, 3);
			final double c1 = input.get(2, 0) * input.get(3, 2) - input.get(3, 0) * input.get(2, 2);
			final double c0 = input.get(2, 0) * input.get(3, 1) - input.get(3, 0) * input.get(2, 1);

			final double invdet = 1.0 / (s0 * c5 - s1 * c4 + s2 * c3 + s3 * c2 - s4 * c1 + s5 * c0);
			final double m[] = output.data;

		    m[0]  = ( input.get(1, 1) * c5 - input.get(1, 2) * c4 + input.get(1, 3) * c3) * invdet;
		    m[1]  = (-input.get(0, 1) * c5 + input.get(0, 2) * c4 - input.get(0, 3) * c3) * invdet;
		    m[2]  = ( input.get(3, 1) * s5 - input.get(3, 2) * s4 + input.get(3, 3) * s3) * invdet;
		    m[3]  = (-input.get(2, 1) * s5 + input.get(2, 2) * s4 - input.get(2, 3) * s3) * invdet;

		    m[4]  = (-input.get(1, 0) * c5 + input.get(1, 2) * c2 - input.get(1, 3) * c1) * invdet;
		    m[5]  = ( input.get(0, 0) * c5 - input.get(0, 2) * c2 + input.get(0, 3) * c1) * invdet;
		    m[6]  = (-input.get(3, 0) * s5 + input.get(3, 2) * s2 - input.get(3, 3) * s1) * invdet;
		    m[7]  = ( input.get(2, 0) * s5 - input.get(2, 2) * s2 + input.get(2, 3) * s1) * invdet;

		    m[8]  = ( input.get(1, 0) * c4 - input.get(1, 1) * c2 + input.get(1, 3) * c0) * invdet;
		    m[9]  = (-input.get(0, 0) * c4 + input.get(0, 1) * c2 - input.get(0, 3) * c0) * invdet;
		    m[10] = ( input.get(3, 0) * s4 - input.get(3, 1) * s2 + input.get(3, 3) * s0) * invdet;
		    m[11] = (-input.get(2, 0) * s4 + input.get(2, 1) * s2 - input.get(2, 3) * s0) * invdet;

		    m[12] = (-input.get(1, 0) * c3 + input.get(1, 1) * c1 - input.get(1, 2) * c0) * invdet;
		    m[13] = ( input.get(0, 0) * c3 - input.get(0, 1) * c1 + input.get(0, 2) * c0) * invdet;
		    m[14] = (-input.get(3, 0) * s3 + input.get(3, 1) * s1 - input.get(3, 2) * s0) * invdet;
		    m[15] = ( input.get(2, 0) * s3 - input.get(2, 1) * s1 + input.get(2, 2) * s0) * invdet;
		}
		return output;
	}
	/**
	 * Return a translation matrix.
	 */
	public static Matrix4 translate(final double x, final double y, final double z)
	{
		return new Matrix4
		(
			new double[]
			{
				1.0, 0.0, 0.0, x,
				0.0, 1.0, 0.0, y,
				0.0, 0.0, 1.0, z,
				0.0, 0.0, 0.0, 1.0
			}
		);
	}
	/**
	 * Return a translation matrix.
	 */
	public static Matrix4 translate(final Point3f p)
	{
		return Matrix4.translate(p.x, p.y, p.z);
	}
	/**
	 * Return a scaling matrix.
	 */
	public static Matrix4 scale(final double x, final double y, final double z)
	{
		return new Matrix4
		(
			new double[]
			{
				  x, 0.0, 0.0, 0.0,
				0.0,   y, 0.0, 0.0,
				0.0, 0.0,   z, 0.0,
				0.0, 0.0, 0.0, 1.0
			}
		);
	}
	/**
	 * Return a scaling matrix.
	 */
	public static Matrix4 scale(final Vector3f s)
	{
		return Matrix4.scale(s.i, s.j, s.k);
	}
	/**
	 * Return a rotation matrix for a given orientation.
	 * @param roll the roll angle in degrees.
	 * @param yaw the yaw angle in degrees.
	 * @param pitch the pitch angle in degrees.
	 */
	public static Matrix4 rotate(final double roll, final double yaw, final double pitch)
	{
		// Convert degrees to radians.
		final double alpha = Math.toRadians(roll);
		final double gamma = Math.toRadians(yaw);
		final double theta = Math.toRadians(pitch);

		final double csx = Math.cos(alpha);
		final double csy = Math.cos(gamma);
		final double csz = Math.cos(theta);
		final double snx = Math.sin(alpha);
		final double sny = Math.sin(gamma);
		final double snz = Math.sin(theta);

		final double M[][] = new double[][]
		{
			{	// Rotation around the X axis.
				 1.0, 0.0, 0.0, 0.0,
				 0.0, csx,-snx, 0.0,
				 0.0, snx, csx, 0.0,
				 0.0, 0.0, 0.0, 1.0
			},
			{	// Rotation around the Y axis.
				 csy, 0.0, sny, 0.0,
				 0.0, 1.0, 0.0, 0.0,
				-sny, 0.0, csy, 0.0,
				 0.0, 0.0, 0.0, 1.0
			},
			{	// Rotation around the Z axis.
				 csz,-snz, 0.0, 0.0,
				 snz, csz, 0.0, 0.0,
				 0.0, 0.0, 1.0, 0.0,
				 0.0, 0.0, 0.0, 1.0
			}
		};
		return new Matrix4(M[0]).multiply(new Matrix4(M[2]).multiply(new Matrix4(M[1])));
	}
	/**
	 * Return a rotation matrix for a given orientation.
	 * @param orientation the orientation containing rotation angles.
	 */
	public static Matrix4 rotate(final Orientation orientation)
	{
		return Matrix4.rotate(orientation.roll, orientation.yaw, orientation.pitch);
	}
	/**
	 * Copy the content of another matrix into this one.
	 * @param that the matrix to copy.
	 */
	public void copy(final Matrix4 that)
	{
		if (that != null)
			System.arraycopy(that.data, 0, this.data, 0, 16);
	}
	/**
	 * Return the matrix value at the given indices.
	 * @param i the row index.
	 * @param j the column index.
	 */
	public double get(final int i, final int j)
	{
		final int offset = (i * 4) + j;
		if (offset < 16)
			return data[offset];
		else
			return Float.NaN;
	}
	/**
	 * Set the matrix value at the given indices.
	 * @param i the row index.
	 * @param j the column index.
	 */
	public void set(final int i, final int j, final double value)
	{
		final int offset = (i * 4) + j;
		if (offset < 16)
			data[offset] = value;
	}
	/**
	 * Calculate a perspective matrix.
	 * @param frustum a camera's viewing frustum containing a vertical field of view, an aspect
	 * ratio, and distances to the near and far clipping planes.
	 */
	public static Matrix4 perspective(final Frustum frustum)
	{
		if (frustum == null)
			return new Matrix4(); // An identity matrix.
		else
		{
			final double fovy = frustum.getVerticalFieldOfView();
			final double aspect = frustum.getAspectRatio();
			final double znear = frustum.getHitherPlaneDistance();
			final double zfar = frustum.getYonPlaneDistance();

			return Matrix4.perspective(fovy, aspect, znear, zfar);
		}
	}
	/**
	 * Calculate a perspective matrix.
	 * @param fovy the frustum's vertical field of view.
	 * @param aspect the frustum's aspect ratio.
	 * @param znear the distance to the frustum's near clipping plane.
	 * @param zfar the distance to the frustum's far clipping plane.
	 */
	public static Matrix4 perspective
	(
		final double fovy,
		final double aspect,
		final double znear,
		final double zfar
	)
	{
		final double znear2 = znear * 2.0f;
		final double dz = zfar - znear;

		final double top = Math.tan(Trigonometry.PIOVER360 * fovy);
		final double bottom = -top;
		final double height = top - bottom;
		final double right = top * aspect;
		final double left = bottom * aspect;
		final double width = right - left;

		final double a = znear2 / width;
		final double b = znear2 / height;
		final double c = -(zfar + znear) / dz;
		final double d = (-2.0f * zfar * znear) / dz;

		return new Matrix4
		(
			new double[]
			{
				  a, 0.0, 0.0, 0.0,
				0.0,   b, 0.0, 0.0,
				0.0, 0.0,   c,-1.0,
				0.0, 0.0,   d, 0.0
			}
		);
	}
	/**
	 * Calculate a lookAt matrix.
	 * @param position the position of the viewer.
	 * @param target the point of interest, i.e. the point we're looking at.
	 * @param up a vector that defines which direction is up.
	 */
	public static Matrix4 lookAt
	(
		final Point3f position,
		final Point3f target,
		final Vector3f up
	)
	{
		final Vector3f P = position.toVector3f();
		final Vector3f Z = Vector3f.normalise(new Vector3f(target, position));
		final Vector3f X = Vector3f.normalise(Z.cross(Vector3f.normalise(up)));
		final Vector3f Y = X.cross(Z);
		final Point3f  D = new Point3f(-X.dot(P), -Y.dot(P), -Z.dot(P));
		return new Matrix4
		(
			new double[]
			{
				X.i, Y.i, Z.i, 0.0,
				X.j, Y.j, Z.j, 0.0,
				X.k, Y.k, Z.k, 0.0,
				D.x, D.y, D.z, 1.0
			}
		);
	}
	/**
	 * Convert the matrix data to a string.
	 */
	@Override
	public String toString()
	{
		final StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 4; ++i)
		{
			for (int j = 0; j < 4; ++j)
			{
				buffer.append(get(i, j));
				buffer.append('\t');
			}
			buffer.append('\n');
		}
		return buffer.toString();
	}
	/**
	 * TODO Document parameters.
	 * Calculate a model transformation matrix.
	 */
	public static Matrix4 Model(final Point3f p, final Orientation o, final Vector3f s)
	{
		return Matrix4.translate(p).multiply(Matrix4.rotate(o).multiply(Matrix4.scale(s)));
	}
}
