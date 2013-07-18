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

import java.util.LinkedHashSet;

import clockwork.graphics.color.ColorRGBA;
import clockwork.types.math.Point3f;
import clockwork.types.math.Point4f;
import clockwork.types.math.Vector3f;

public final class Vertex implements Comparable<Vertex>
{
	/**
	 * The vertex's homogenous position.
	 */
	public final Point4f position = new Point4f();
	/**
	 * The vertex's mapping coordinates.
	 */
	public double u = 0;
	public double v = 0;
	/**
	 * The vertex's normal.
	 */
	public final Vector3f normal = new Vector3f(0, 0, 0);
	/**
	 * The vertex's color.
	 */
	public final ColorRGBA color = new ColorRGBA(1.0, 1.0, 1.0);
	/**
	 * The set of polygon faces that this vertex belongs to.
	 */
	private final LinkedHashSet<PolygonFace> faces = new LinkedHashSet<PolygonFace>();
	/**
	 * The default constructor. Note that a vertex cannot be created without
	 * coordinates.
	 */
	private Vertex()
	{}
	/**
	 * Instantiate a new vertex at a given position.
	 */
	public Vertex(final Point3f position)
	{
		this.position.setXYZ(position);
	}
	/**
	 * Instantiate a new vertex at a given position in homogenous coordinates.
	 */
	public Vertex(final Point4f position)
	{
		this.position.setXYZW(position);
	}
	/**
	 * Instantiate a new Vertex at a given position.
	 */
	public Vertex(final float x, final float y, final float z)
	{
		this.position.setXYZ(x, y, z);
	}
	/**
	 * Instantiate a new Vertex at a given position.
	 */
	public Vertex(final float x, final float y, final float z, final float w)
	{
		this.position.setXYZW(x, y, z, w);
	}
	/**
	 * The copy constructor.
	 */
	public Vertex(final Vertex vertex)
	{
		this();
		copy(vertex);
	}
	/**
	 * Copy a vertex.
	 * @param that the vertex to copy.
	 */
	public void copy(final Vertex that)
	{
		position.setXYZW(that.position);
		normal.setIJK(that.normal);
		this.u = that.u;
		this.v = that.v;
		this.color.copy(that.color);
		faces.clear();
		faces.addAll(that.faces);
	}
	/**
	 * Return the polygon faces that contain this vertex.
	 */
	public LinkedHashSet<PolygonFace> getFaces()
	{
		return faces;
	}
	/**
	 * Add a polygon face.
	 */
	public void addFace(final PolygonFace face)
	{
		if (face != null)
			faces.add(face);
	}
	/**
	 * Calculate and store the vertex's normal.
	 */
	public void calculateNormal()
	{
		final float w = 1.0f / faces.size();
		normal.setIJK(new double[]{0, 0, 0});

		for (final PolygonFace face : faces)
		{
			final Vector3f faceNormal = face.getNormal();
			if (faceNormal != null)
				normal.add(faceNormal);
		}
		normal.multiply(w);
	}
	/**
	 * Compare two vertices based on their positions.
	 */
	@Override
	public int compareTo(final Vertex that)
	{
		if (this.position.y > that.position.y)
			return 1;
		else if (this.position.y == that.position.y)
		{
			if (this.position.x > that.position.x)
				return 2;
			else if (this.position.x == that.position.x)
			{
				if (this.position.z > that.position.z)
					return 3;
				else
					return -3;
			}
			else
				return -2;
		}
		else
			return -1;
	}

	/**
	 * Convert the vertex data into string format.
	 */
	@Override
	public String toString()
	{
		return String.format
		(
			"p:%s uv:<%f, %f> n:%s argb:%s",
			position, u, v, normal, Integer.toHexString(color.merge())
		);
	}
}