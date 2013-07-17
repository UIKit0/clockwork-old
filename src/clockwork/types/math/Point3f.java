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

/**
 * A point in a 3D world.
 */
public final class Point3f
{
	/**
	 * The point's X coordinate.
	 */
	public double x = 0.0;
	/**
	 * The point's Y coordinate.
	 */
	public double y = 0.0;
	/**
	 * The point's Z coordinate.
	 */
	public double z = 0.0;
	/**
	 * The default constructor.
	 */
	public Point3f()
	{}
	/**
	 * Instantiate a point from a set of X, Y and Z coordinates.
	 * @param x the point's X coordinate.
	 * @param y the point's Y coordinate.
	 * @param z the point's Z coordinate.
	 */
	public Point3f(final double x, final double y, final double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	/**
	 * Instantiate a point copied from another.
	 * @param that the point to copy.
	 */
	public Point3f(final Point3f that)
	{
		copy(that);
	}
	/**
	 * Instantiate a point copied from another vector.
	 * @param that the vector to copy.
	 */
	public Point3f(final Vector3f that)
	{
		copy(that);
	}
	/**
	 * Instantiate a point copied from another homogeneous point.
	 * @param that the homogeneous point to copy.
	 */
	public Point3f(final Point4f that)
	{
		copy(that);
	}
	/**
	 * Set the point's coordinates.
	 * @param XYZ an array of X, Y and Z coordinates.
	 */
	public void setXYZ(final double XYZ[])
	{
		if (XYZ.length == 3)
		{
			this.x = XYZ[0];
			this.y = XYZ[1];
			this.z = XYZ[2];
		}
	}
	/**
	 * Return this point's coordinates in the form of an array.
	 */
	public double[] getXYZ()
	{
		return new double[]{x, y, z};
	}
	/**
	 * Set the point's coordinates.
	 * @param XYZ an array of X, Y, Z and W homogeneous coordinates.
	 */
	public void setXYZW(final double XYZW[])
	{
		if (XYZW.length == 4)
		{
			this.x = XYZW[0] / XYZW[3];
			this.y = XYZW[1] / XYZW[3];
			this.z = XYZW[2] / XYZW[3];
		}
	}
	/**
	 * Set the point's coordinates.
	 * @param point a homogeneous point.
	 */
	public void setXYZW(final Point4f point)
	{
		copy(point);
	}
	/**
	 * Return this point's homogeneous data in the form of an array.
	 */
	public double[] getXYZW()
	{
		return new double[]{x, y, z, 1.0};
	}
	/**
	 * Return this point's homogeneous representation.
	 */
	public Point4f toHomogeneous()
	{
		return new Point4f(x, y, z, 1.0);
	}
	/**
	 * Convert the point to a vector.
	 */
	public Vector3f toVector3f()
	{
		return new Vector3f(x, y, z);
	}
	/**
	 * Copy a point.
	 * @param that the point to copy.
	 */
	public void copy(final Point3f that)
	{
		if (that != null)
		{
			this.x = that.x;
			this.y = that.y;
			this.z = that.z;
		}
	}
	/**
	 * Copy a vector.
	 * @param that the vector to copy.
	 */
	public void copy(final Vector3f that)
	{
		if (that != null)
		{
			this.x = that.i;
			this.y = that.j;
			this.z = that.k;
		}
	}
	/**
	 * Copy a point.
	 * @param that the homogeneous point to copy.
	 */
	public void copy(final Point4f that)
	{
		if (that != null)
		{
			this.x = that.x / that.w;
			this.y = that.y / that.w;
			this.z = that.z / that.w;
		}
	}
	/**
	 * Multiply this point by a scalar.
	 * @param value the value to multiply this point by.
	 */
	public void multiply(final double value)
	{
		this.x *= value;
		this.y *= value;
		this.z *= value;
	}
	/**
	 * Return the result of an addition between this point and a vector.
	 * @param vector the vector to add.
	 */
	public Point3f add(final Vector3f vector)
	{
		final Point3f output = new Point3f(this);
		if (vector != null)
		{
			output.x += vector.i;
			output.y += vector.j;
			output.z += vector.k;
		}
		return output;
	}
	/**
	 * Subtract a point from this point.
	 * @param that the point to subtract.
	 */
	public Vector3f subtract(final Point3f that)
	{
		if (that != null)
			return new Vector3f(this.x - that.x, this.y - that.y, this.z - that.z);
		else
			return new Vector3f(x, y, z);
	}
	/**
	 * Return the euclidean distance from the origin to this point.
	 */
	public double distance()
	{
		return Math.sqrt((x * x) + (y * y) + (z * z));
	}
	/**
	 * Return the euclidean distance between two points.
	 * @param a the origin point.
	 * @param b the end point.
	 * @param that the end point.
	 */
	public static double distance(final Point3f a, final Point3f b)
	{
		double result = 0;
		if (a != null && b != null)
		{
			final double xx = a.x - b.x;
			final double yy = a.y - b.y;
			final double zz = a.z - b.z;

			result = Math.sqrt((xx * xx) + (yy * yy) + (zz * zz));
		}
		return result;
	}
	/**
	 * Multiply a 3D point's coordinates by -1.
	 * @param p the 3D point to convert.
	 */
	public static Point3f negative(final Point3f p)
	{
		return new Point3f(-p.x, -p.y, -p.z);
	}
	/**
	 * Test for equality between two points. Returns true if
	 * two points are equal, false otherwise.
	 * @param that the point to test.
	 */
	public boolean equals(final Point3f that)
	{
		final double tolerance = 0.000001;
		return (that != null) && (Point3f.distance(this, that) < tolerance);
	}
	/**
	 * Convert the point data into a string.
	 */
	@Override
	public String toString()
	{
		return String.format("Point3 <%.3f, %.3f, %.3f>", x, y, z);
	}
}
