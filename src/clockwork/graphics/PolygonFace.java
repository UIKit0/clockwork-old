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
package clockwork.graphics;

import java.awt.geom.Point2D;

import clockwork.types.math.Point3f;
import clockwork.types.math.Vector3f;

/**
 * A triangular polygon face.
 */
public final class PolygonFace
{
	/**
	 * The set of vertices that make up this polygon face.
	 */
	private Vertex vertices[] = null;
	/**
	 * The set of UV texture coordinates for each vertex that makes up this polygon face.
	 */
	private Point2D.Double uvcoords[] = null;
	/**
	 * The face's surface normal.
	 */
	private final Vector3f normal = new Vector3f();
	/**
	 * Instantiate a polygon face comprised of a triplet of vertices, UV coordinates
	 * and vertex normals.
	 * @param vertices the polygon face's vertices.
	 * @param uvcoords the polygon face's texture coordinates.
	 */
	public PolygonFace(final Vertex vertices[], final Point2D.Double uvcoords[])
	{
		this.vertices = vertices;
		this.uvcoords = uvcoords;

		calculateNormal();
	}
	/**
	 * Instantiate a triangular polygon face comprised of a set of three vertices.
	 * @param vertices the set of vertices that makes up this polygon face.
	 */
	public PolygonFace(final Vertex[] vertices)
	{
		this(vertices, null);
	}
	/**
	 * Instantiate a polygon face from a set of vertices and a given set of indices.
	 * @param vertices a collection of more than 3 vertices.
	 * @param i the index of the first vertex to retrieve from the collection.
	 * @param j the index of the second vertex to retrieve from the collection.
	 * @param k the index of the third vertex to retrieve from the collection.
	 */
	public PolygonFace(final Vertex[] vertices, final int i, final int j, final int k)
	{
		this(new Vertex[]{vertices[i], vertices[j], vertices[k]});
	}
	/**
	 * Calculate, store and return the face's surface normal.
	 */
	public void calculateNormal()
	{
		if (vertices != null)
		{
			final Point3f p0 = new Point3f(vertices[0].position);
			final Point3f p1 = new Point3f(vertices[1].position);
			final Point3f p2 = new Point3f(vertices[2].position);

			final Vector3f v0 = new Vector3f(p0, p1);
			final Vector3f v1 = new Vector3f(p0, p2);

			normal.setIJK(v0.cross(v1));

	/* TODO Verify that commenting this doesn't break anything, i.e. a face is always a triangle.

	 		// If the two vectors are parallel, we have a problem.
			final float theta = Math.abs(v0.produitScalaire(v1) / (v0.getNorme() * v1.getNorme()));
			if (theta != 1.0f)
				normal = v0.produitVectoriel(v1).normalise();
			else
				normal = null;
	*/
		}
	}
	/**
	 * Calculate and return the face's center point.
	 */
	public Point3f calculateCenter()
	{
		Point3f output = new Point3f();
		if (vertices != null)
		{
			final Point3f p0 = new Point3f(vertices[0].position);
			final Point3f p1 = new Point3f(vertices[1].position);
			final Point3f p2 = new Point3f(vertices[2].position);

			output.x = (p0.x + p1.x + p2.x) * 0.333333; // 1/3 = 0.3333333...
			output.y = (p0.y + p1.y + p2.y) * 0.333333;
			output.z = (p0.z + p1.z + p2.z) * 0.333333;
		}
		return output;
	}
	/**
	 * Return the polygon face's vertices.
	 */
	public Vertex[] getVertices()
	{
		return vertices;
	}
	/**
	 * Return the ith vertex.
	 */
	public Vertex getVertex(final int i)
	{
		return (i > 0 && i < 3) ? vertices[i] : null;
	}
	/**
	 * Return the polygon face's texture coordinates.
	 */
	public Point2D.Double[] getTextureCoordinates()
	{
		return uvcoords;
	}
	/**
	 * Return the polygon face's ith texture coordinate.
	 */
	public Point2D.Double getTextureCoordinate(final int i)
	{
		return (i > 0 && i < 3) ? uvcoords[i] : null;
	}
	/**
	 * Return the face's surface normal.
	 */
	public Vector3f getNormal()
	{
		return normal;
	}
}