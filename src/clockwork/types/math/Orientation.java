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
 * The orientation of an entity.
 */
public class Orientation
{
	/**
	 * TODO Explain me.
	 * The roll rotation angle in degrees.
	 */
	public double roll = 0;
	/**
	 * TODO Explain me.
	 * The yaw rotation angle in degrees.
	 */
	public double yaw = 0;
	/**
	 * TODO Explain me.
	 * The pitch rotation angle in degrees.
	 */
	public double pitch = 0;
	/**
	 * The default constructor.
	 */
	public Orientation()
	{}
	/**
	 * Instantiate an Orientation with given roll, yaw and pitch angles.
	 * @param roll the roll angle in degrees.
	 * @param yaw the yaw angle in degrees.
	 * @param pitch the pitch angle in degrees.
	 */
	public Orientation(final double roll, final double yaw, final double pitch)
	{
		this.roll = roll;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	/**
	 * Copy an orientation.
	 * @param that the orientation to copy.
	 */
	public void copy(final Orientation that)
	{
		if (that != null)
		{
			this.roll = that.roll;
			this.yaw = that.yaw;
			this.pitch = that.pitch;
		}
	}
	/**
	 * Clamp this orientation's angles to the [0, 360[ range.
	 */
	public void clamp()
	{
		roll = (roll + 360.0) % 360.0;
		yaw = (yaw+ 360.0) % 360.0;
		pitch = (pitch + 360.0) % 360.0;
	}
	/**
	 * Convert the orientation to a 3D point value.
	 */
	public Point3f toPoint3f()
	{
		return new Point3f(roll, yaw, pitch);
	}
}
