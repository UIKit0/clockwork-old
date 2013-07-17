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
package clockwork.graphics.camera.projection;

import clockwork.graphics.camera.Frustum;
import clockwork.types.math.Matrix4;


//http://www.gamedev.net/topic/515840-cavalier-projection/
//http://paulbourke.net/geometry/classification/
//http://www.gamedev.net/topic/515840-cavalier-projection/
//http://paulbourke.net/geometry/classification/

public abstract class Projection
{
	/**
	 * An enumeration of available projections.
	 */
	public static enum Type
	{
		Orthographic("Orthographic (to implement)"),
		Cabinet("Cabinet 45° (to implement)"),
		Cavaliere("Cavalière 45° (to implement)"),
		Perspective("Perspective (to implement)");
		/**
		 * The type's title.
		 */
		private final String title;
		/**
		 * The default constructor.
		 */
		Type(final String title)
		{
			this.title = title;
		}
		/**
		 * Get the type's title.
		 */
		public String getTitle()
		{
			return title;
		}
		/**
		 * Convert the enumeration data to a string.
		 */
		@Override
		public String toString()
		{
			return title;
		}
	}
	/**
	 * The projection type.
	 */
	private final Projection.Type type;
	/**
	 * The default constructor.
	 */
	protected Projection(final Projection.Type type)
	{
		this.type = type;
	}
	/**
	 * Get the projection type.
	 */
	public final Projection.Type getType()
	{
		return type;
	}
	/**
	 * Convert the projection to a String.
	 */
	@Override
	public final String toString()
	{
		return type.getTitle();
	}
	/**
	 * Get the transformation matrix that defines this projection
	 */
	public abstract Matrix4 toMatrix(final Frustum frustum);
}