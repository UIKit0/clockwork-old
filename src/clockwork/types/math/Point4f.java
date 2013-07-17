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
 * A homogeneous point.
 */
public class Point4f
{
	/**
	 * The point's X coordinate.
	 */
	public double x = 0.0f;
	/**
	 * The point's Y coordinate.
	 */
	public double y = 0.0f;
	/**
	 * The point's Z coordinate.
	 */
	public double z = 0.0f;
	/**
	 * The point's W coordinate.
	 */
	public double w = 0.0f;
	/**
	 * The default constructor.
	 */
	public Point4f()
	{}
	/**
	 * Instantiate a Point4f with given X, Y and Z coordinates. The W coordinate is set to 1.
	 * @param x the point's X coordinate.
	 * @param y the point's Y coordinate.
	 * @param z the point's Z coordinate.
	 */
	public Point4f(final double x, final double y, final double z)
	{
		setXYZW(x, y, z, 1.0f);
	}
	/**
	 * Instantiate a Point4f with given X, Y, Z and W coordinates.
	 * @param x the point's X coordinate.
	 * @param y the point's Y coordinate.
	 * @param z the point's Z coordinate.
	 * @param w the point's W coordinate.
	 */
	public Point4f(final double x, final double y, final double z, final double w)
	{
		setXYZW(x, y, z, w);
	}
	/**
	 * Return the point's X, Y, and Z coordinates in the form of an array.
	 * Note that they're not divided by the W component.
	 */
	public double[] getXYZ()
	{
		return new double[]{x, y, z};
	}
	/**
	 * Return the point's coordinates in the form of an array.
	 */
	public double[] getXYZW()
	{
		return new double[]{x, y, z, w};
	}
	/**
	 * Set the point's coordinates.
	 * @param x the point's X coordinate.
	 * @param y the point's Y coordinate.
	 * @param z the point's Z coordinate.
	 */
	public void setXYZ(final double x, final double y, final double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = 1.0f;
	}
	/**
	 * Set the point's coordinates.
	 * @param xyz an array of coordinates.
	 */
	public void setXYZ(final double xyz[])
	{
		this.x = xyz[0];
		this.y = xyz[1];
		this.z = xyz[2];
		this.w = 1.0f;
	}
	/**
	 * Copy and store another point's coordinates.
	 * @param that the point to copy from.
	 */
	public void setXYZ(final Point3f that)
	{
		this.setXYZ(that.x, that.y, that.z);
	}
	/**
	 * Set the point's coordinates.
	 * @param x the point's X coordinate.
	 * @param y the point's Y coordinate.
	 * @param z the point's Z coordinate.
	 * @param w the point's W coordinate.
	 */
	public void setXYZW(final double x, final double y, final double z, final double w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	/**
	 * Set the point's coordinates.
	 * @param xyzw an array of coordinates.
	 */
	public void setXYZW(final double xyzw[])
	{
		this.x = xyzw[0];
		this.y = xyzw[1];
		this.z = xyzw[2];
		this.w = xyzw[3];
	}
	/**
	 * Copy and store another point's coordinates.
	 * @param that the point to copy from.
	 */
	public void setXYZW(final Point4f that)
	{
		this.setXYZW(that.x, that.y, that.z, that.w);
	}
	/**
	 * Return this point's affine representation.
	 */
	public Point3f toAffine()
	{
		return new Point3f(x/w, y/w, z/w);
	}
	/**
	 * Convert the point data into a string.
	 */
	@Override
	public String toString()
	{
		return String.format("<%.3f, %.3f, %.3f, %.3f>", x, y, z, w);
	}
}
