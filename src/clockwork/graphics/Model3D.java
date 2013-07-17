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

import clockwork.asset.Asset;

/**
 * A Model3D defines a rigid body's shape, look and feel.
 */
public final class Model3D extends Asset
{
	/**
	 * The model's mesh geometry, i.e. its shape.
	 */
	private Mesh mesh = null;
	/**
	 * The model's material, i.e. its look and feel.
	 */
	private Material material = null;
	/**
	 * Instantiate a 3D model with a given mesh and material.
	 * @param mesh the 3D model's mesh data.
	 * @param material the 3D model's material.
	 */
	public Model3D(final Mesh mesh, final Material material)
	{
		this.mesh = mesh;
		this.material = material;
	}
	/**
	 * Instantiate a 3D model with a given mesh.
	 * @param mesh the 3D model's mesh data.
	 */
	public Model3D(final Mesh mesh)
	{
		this(mesh, null);
	}
	/**
	 * Return the mesh data.
	 */
	public Mesh getMesh()
	{
		return mesh;
	}
	/**
	 * Set the 3D model's mesh data.
	 * @param mesh the mesh data to set.
	 */
	public void setMesh(final Mesh mesh)
	{
		if (mesh != null)
			this.mesh = mesh;
	}
	/**
	 * Return the 3D model's material data.
	 */
	public Material getMaterial()
	{
		return material;
	}
	/**
	 * Set the 3D model's material.
	 * @param material the material to set.
	 */
	public void setMaterial(final Material material)
	{
		if (mesh != null)
			this.material = material;
	}
	/**
	 * Convert the 3D model data to a string.
	 */
	@Override
	public String toString()
	{
		return "model3d/" + super.toString();
	}
}
