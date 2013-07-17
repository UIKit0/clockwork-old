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
package clockwork.graphics.camera;

public final class Frustum
{
	/**
	 * Default values.
	 */
	public static final float DefaultAspectRatio = 4.0f / 3.0f;
	public static final float DefaultVerticalFieldOfView = 45.0f;
	public static final float DefaultHitherPlaneDistance = 10.0f;
	public static final float DefaultYonPlaneDistance = 200.0f;
	/**
	 * The minimum clipping plane distance.
	 */
	public static final int MinimumClippingPlaneDistance = 1;
	/**
	 * The maximum clipping plane distance.
	 */
	public static final int MaximumClippingPlaneDistance = 1000;
	/**
	 * The camera's vertical field of view.
	 */
	private float verticalFieldOfView = DefaultVerticalFieldOfView;
	/**
	 * The camera's aspect ratio.
	 */
	private float aspectRatio = DefaultAspectRatio;
	/**
	 * The distance to the near clipping plane.
	 */
	private float hitherPlaneDistance = DefaultHitherPlaneDistance;
	/**
	 * The distance to the far clipping plane.
	 */
	private float yonPlaneDistance = DefaultYonPlaneDistance;


//	TODO
//	public float near;
//	public float far;
//	public float top;
//	public float bottom;
//	public float left;
//	public float right;


	/**
	 * Instantiate a viewing frustum from a vertical field of view, aspect ratio,
	 * hither and yon plane distances.
	 */
	public Frustum
	(
		final float verticalFieldOfView,
		final float aspectRatio,
		final float hitherPlaneDistance,
		final float yonPlaneDistance
	)
	{
		this.verticalFieldOfView = verticalFieldOfView;
		this.aspectRatio = aspectRatio;
		this.hitherPlaneDistance = hitherPlaneDistance;
		this.yonPlaneDistance = yonPlaneDistance;
	}
	/**
	 * The default constructor.
	 */
	public Frustum()
	{}
	/**
	 * Set the camera's aspect ratio.
	 * @param aspectRatio the aspect ratio to set.
	 */
	public void setAspectRatio(final float aspectRatio)
	{
		this.aspectRatio = aspectRatio;
	}
	/**
	 * Get the camera's aspect ratio.
	 */
	public float getAspectRatio()
	{
		return aspectRatio;
	}
	/**
	 * Set the camera's field of view.
	 * @param fieldOfView the field of view to set.
	 */
	public void setVerticalFieldOfView(final float fieldOfView)
	{
		this.verticalFieldOfView = fieldOfView;
	}
	/**
	 * Get the camera's field of view.
	 */
	public int getVerticalFieldOfView()
	{
		return Math.round(verticalFieldOfView);
	}
	/**
	 * Set the distance to the near clipping plane.
	 */
	public void setHitherPlaneDistance(final float hitherPlaneDistance)
	{
		this.hitherPlaneDistance = hitherPlaneDistance;
	}
	/**
	 * Get the distance to the near clipping plane.
	 */
	public float getHitherPlaneDistance()
	{
		return hitherPlaneDistance;
	}
	/**
	 * Set the distance to the far clipping plane.
	 */
	public void setYonPlaneDistance(final float yonPlaneDistance)
	{
		this.yonPlaneDistance = yonPlaneDistance;
	}
	/**
	 * Get the distance to the far clipping plane.
	 */
	public float getYonPlaneDistance()
	{
		return yonPlaneDistance;
	}
	/**
	 * Copy another viewing frustum volume's attributes to this object.
	 * @param that the viewing frustum to copy.
	 */
	public void copy(final Frustum that)
	{
		if (that != null)
		{
			this.verticalFieldOfView = that.verticalFieldOfView;
			this.aspectRatio = that.aspectRatio;
			this.hitherPlaneDistance = that.hitherPlaneDistance;
			this.yonPlaneDistance = that.yonPlaneDistance;
		}
	}
	/**
	 * Check whether two frustums are equal.
	 * @param that the frustum to compare to.
	 */
	public boolean equals(final Frustum that)
	{
		if (that != null)
		{
			return
			this.verticalFieldOfView == that.verticalFieldOfView &&
			this.aspectRatio == that.aspectRatio &&
			this.hitherPlaneDistance == that.hitherPlaneDistance &&
			this.yonPlaneDistance == that.yonPlaneDistance;
		}
		return false;
	}
}
