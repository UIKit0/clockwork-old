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

import clockwork.graphics.color.ColorRGBA;

/**
 * A 3D euclidean vector.
 */
public final class Vector3f
{
	/**
	 * The vector's I component.
	 */
	public double i = 0.0;
	/**
	 * The vector's J component.
	 */
	public double j = 0.0;
	/**
	 * The vector's K component.
	 */
	public double k = 0.0;
	/**
	 * The default constructor.
	 */
	public Vector3f()
	{}
	/**
	 * Instantiate a vector from a set of I, J and K components.
	 * @param i the vector's I component.
	 * @param j the vector's J component.
	 * @param k the vector's K component.
	 */
	public Vector3f(final double i, final double j, final double k)
	{
		this.i = i;
		this.j = j;
		this.k = k;
	}
	/**
	 * Instantiate a vector copied from another.
	 * @param that the vector to copy.
	 */
	public Vector3f(final Vector3f that)
	{
		copy(that);
	}
	/**
	 * Instantiate a vector resulting from the difference between two points A and B.
	 * The resulting vector is oriented towards B.
	 * @param A the origin point.
	 * @param B the end point.
	 */
	public Vector3f(final Point3f A, final Point3f B)
	{
		this(B.subtract(A));
	}
	/**
	 * Set the vector's components.
	 * @param that a vector containing I, J and K components.
	 */
	public void setIJK(final Vector3f that)
	{
		if (that != null)
		{
			this.i = that.i;
			this.j = that.j;
			this.k = that.k;
		}
	}
	/**
	 * Set the vector's components.
	 * @param IJK an array containing I, J and K components.
	 */
	public void setIJK(final double IJK[])
	{
		if (IJK.length == 3)
		{
			this.i = IJK[0];
			this.j = IJK[1];
			this.k = IJK[2];
		}
	}
	/**
	 * Return this vector's I, J and K components in the form of an array.
	 */
	public double[] getIJK()
	{
		return new double[]{i, j, k};
	}
	/**
	 * Set the vector's components.
	 * @param IJKW an array containing I, J, K and L components.
	 */
	public void setIJKL(final double IJKL[])
	{
		if (IJKL.length == 4)
		{
			this.i = IJKL[0] / IJKL[3];
			this.j = IJKL[1] / IJKL[3];
			this.k = IJKL[2] / IJKL[3];
		}
	}
	/**
	 * Return this vector's I, J, K and L components in the form of an array.
	 * The L component is set to 0.
	 */
	public double[] getIJKL()
	{
		return new double[]{i, j, k, 0.0};
	}
	/**
	 * Copy a vector.
	 * @param that the vector to copy.
	 */
	public void copy(final Vector3f that)
	{
		if (that != null)
		{
			this.i = that.i;
			this.j = that.j;
			this.k = that.k;
		}
	}
	/**
	 * Convert the vector to a point. This simply makes the I, J and K components the
	 * X, Y and Z coordinates of a 3D point, respectively.
	 */
	public Point3f toPoint3f()
	{
		return new Point3f(i, j, k);
	}
	/**
	 * Return a vector filled with ones.
	 */
	public static Vector3f Ones()
	{
		return new Vector3f(1.0, 1.0, 1.0);
	}
	/**
	 * Add another vector to this one.
	 * @param that the vector to add.
	 */
	public void add(final Vector3f that)
	{
		if (that != null)
		{
			this.i += that.i;
			this.j += that.j;
			this.k += that.k;
		}
	}
	/**
	 * Substract a vector from this one.
	 * @param that the vector to substract.
	 */
	public void substract(final Vector3f that)
	{
		if (that != null)
		{
			this.i -= that.i;
			this.j -= that.j;
			this.k -= that.k;
		}
	}
	/**
	 * Multiply (scale) this vector by a value.
	 * @param value the value to scale the vector by.
	 */
	public Vector3f multiply(final double value)
	{
		this.i *= value;
		this.j *= value;
		this.k *= value;

		return this;
	}
	/**
	 * Return the dot product of this vector and another.
	 * @param that the vector that will be multiplied to this one.
	 */
	public double dot(final Vector3f that)
	{
		double result = 0;
		if (that != null)
			result = (this.i * that.i) + (this.j * that.j) + (this.k * that.k);

		return result;
	}
	/**
	 * Return the cross product between this vector and another.
	 * @param that the vector to perform the cross product with.
	 */
	public Vector3f cross(final Vector3f that)
	{
		return new Vector3f
		(
			(this.j * that.k) - (this.k * that.j),
			(this.k * that.i) - (this.i * that.k),
			(this.i * that.j) - (this.j * that.i)
		);
	}
	/**
	 * Return this vector's magnitude/norm/length.
	 */
	public double getMagnitude()
	{
		return Math.sqrt((i * i) + (j * j) + (k * k));
	}
	/**
	 * Normalise this vector.
	 */
	public void normalise()
	{
		double magnitude = getMagnitude();
		if (magnitude != 0.0f)
		{
			magnitude = 1/magnitude;
			this.i *= magnitude;
			this.j *= magnitude;
			this.k *= magnitude;
		}
	}
	/**
	 * Normalise a vector.
	 * @param input the vector to normalise.
	 */
	public static Vector3f normalise(final Vector3f input)
	{
		final Vector3f output = new Vector3f(input.i, input.j, input.k);
		double magnitude = input.getMagnitude();
		if (magnitude != 0.0f)
		{
			magnitude = 1/magnitude;
			output.i *= magnitude;
			output.j *= magnitude;
			output.k *= magnitude;
		}
		return output;
	}
	/**
	 * Return a vector in its opposition direction.
	 * @param input the vector to transform.
	 */
	public static Vector3f negative(final Vector3f input)
	{
		return new Vector3f(-input.i, -input.j, -input.k);
	}
	/**
	 * Check whether this vector is equal to another.
	 * @param that the vector to compare.
	 */
	public boolean equals(final Vector3f that)
	{
		if (this != that)
		{
			final double tolerance = 0.000001f;

			return
			Math.abs(this.i - that.i) < tolerance &&
			Math.abs(this.j - that.j) < tolerance &&
			Math.abs(this.k - that.k) < tolerance;
		}
		return true;
	}
	/**
	 * Convert the vector to an RGBA color.
	 */
	public ColorRGBA toRGBA()
	{
		return new ColorRGBA((i + 1.0) * 0.5, (j + 1.0) * 0.5, (k + 1.0) * 0.5, 1.0);
	}
	/**
	 * Convert the vector data into a string.
	 */
	@Override
	public String toString()
	{
		return String.format("<%.3f, %.3f, %.3f>", i, j, k);
	}
}
