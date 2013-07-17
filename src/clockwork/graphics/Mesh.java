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

import java.util.ArrayList;

public final class Mesh
{
	/**
	 * The mesh's vertices.
	 */
	private final Vertex[] vertices;
	/**
	 * The mesh's polygon faces.
	 */
	private final PolygonFace[] faces;
	/**
	 * Instantiate a mesh with a set of vertices and faces.
	 */
	public Mesh(final Vertex[] vertices, final PolygonFace[] faces)
	{
		this.faces = faces.clone();
		this.vertices = vertices.clone();
	}
	/**
	 * Instantiate a mesh with a set of vertices and faces.
	 */
	public Mesh(final ArrayList<Vertex> vertices, final ArrayList<PolygonFace> faces)
	{
		this.faces = new PolygonFace[faces.size()];
		this.vertices = new Vertex[vertices.size()];

		for (int i = 0; i < this.faces.length; ++i)
			this.faces[i] = faces.get(i);

		for (int i = 0; i < this.vertices.length; ++i)
			this.vertices[i] = vertices.get(i);
	}
	/**
	 * Instantiate a mesh, copied from another one.
	 */
	public Mesh(final Mesh mesh)
	{
		this(mesh.vertices, mesh.faces);
	}
	/**
	 * Get the vertices.
	 */
	public Vertex[] getVertices()
	{
		return vertices;
	}
	/**
	 * Get the ith vertex.
	 */
	public Vertex getVertex(final int i)
	{
		if (i < vertices.length)
			return vertices[i];
		else
			return null;
	}
	/**
	 * Get the polygon faces.
	 */
	public PolygonFace[] getFaces()
	{
		return faces;
	}
	/**
	 * Get the ith face.
	 */
	public PolygonFace getFace(final int i)
	{
		if (i < faces.length)
			return faces[i];
		else
			return null;
	}
	/**
	 * Copy the mesh data from another mesh.
	 */
	public void copy(final Mesh mesh)
	{
		if (mesh != null)
		{
//			setVertices(mesh.getVertices());
//			setFaces(mesh.getFaces());
		}
	}

	/**
	 * Does the mesh contain vertex and face data?
	 */
	public boolean isEmpty()
	{
		//TODO
		return false;
	}
}