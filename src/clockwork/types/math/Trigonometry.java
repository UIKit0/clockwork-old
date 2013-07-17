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
 * A trigonometry math class.
 */
public class Trigonometry
{
	public static final double PIOVER180  = 0.0174532925; // pi/180.
	public static final double PIOVER360  = 0.0087266462;
	public static final double PIUNDER180 = 57.295779513; // 180/pi.
	public static final double PIUNDER360 = 114.59155902;
	/**
	 * Return the trigonometric cotangent of a given angle.
	 * @param angle the angle to calculate.
	 */
	public static double cot(final double angle)
	{
		return 1.0 / Math.tan(angle);
	}
	/**
	 * Convert degrees to radians.
	 * @param degrees the degrees to convert.
	 */
	public static double toRadians(final double degrees)
	{
		return degrees * Trigonometry.PIOVER180;
	}
	/**
	 * Convert radians to degrees.
	 * @param radians the radians to convert.
	 */
	public static double toDegrees(final double radians)
	{
		return radians * PIUNDER180;
	}
}