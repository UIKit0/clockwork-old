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
package clockwork.graphics.vsd;

import clockwork.graphics.Vertex;
import clockwork.system.RuntimeOptions;
import clockwork.types.math.Point3f;
import clockwork.types.math.Vector3f;

/**
 * Geometry culling techniques.
 */
public final class Culling
{
	/**
	 * Perform backface culling in clip space. This will determine whether a polygon face
	 * is not facing in the direction of the viewpoint.
	 * @see http://content.gpwiki.org/index.php/Backface_culling
	 * @see http://www.gamasutra.com/view/feature/131773/a_compact_method_for_backface_.php
	 * @param vertices a triplet of vertices that make up a triangular polygon face.
	 */
	public static boolean isBackface(final Vertex vertices[])
	{
		if (RuntimeOptions.EnableBackfaceCulling)
		{
			final Point3f p0 = vertices[0].position.toAffine();
			final Point3f p1 = vertices[1].position.toAffine();
			final Point3f p2 = vertices[2].position.toAffine();

			// TODO Explain this better.
			// Calculate the surface normal, i.e. a cross product between any two edges of the triangle.
			final Vector3f e0 = new Vector3f(p0, p1);
			final Vector3f e1 = new Vector3f(p1, p2);

			return e0.cross(e1).k > 0.0f;
		}
		return false;
	}
	/**
	 * Perform frontface culling in clip space. This will determine whether a polygon face
	 * is facing in the direction of the viewpoint.
	 * @param vertices a triplet of vertices that make up a triangular polygon face.
	 */
	public static boolean isFrontface(final Vertex vertices[])
	{
		return !Culling.isBackface(vertices);
	}
}