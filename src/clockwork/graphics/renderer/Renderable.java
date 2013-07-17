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
package clockwork.graphics.renderer;

import clockwork.graphics.Material;
import clockwork.graphics.Model3D;
import clockwork.graphics.PolygonFace;
import clockwork.system.RuntimeOptions;
import clockwork.types.math.Matrix4;

/**
 * A renderable object contains the information needed to render an object.
 */
public final class Renderable implements Comparable<Renderable>
{
	/**
	 * The polygon faces that make up a model's mesh data.
	 */
	public final PolygonFace[] faces;
	/**
	 * The material that defines the mesh's look and feel.
	 */
	public final Material material;
	/**
	 * TODO Describe me correctly.
	 * The current MODEL transformation matrix converts vertices from
	 * object space to world space.
	 */
	protected final Matrix4 CMTM = new Matrix4();
	/**
	 * Instantiate a Renderable with a given model and MODEL transformation matrix.
	 * @param model the model to render.
	 * @param CMTM the model's current MODEL transformation matrix.
	 */
	public Renderable(final Model3D model, final Matrix4 CMTM)
	{
		this.faces = model.getMesh().getFaces();
		this.material = model.getMaterial();

		if (RuntimeOptions.EnableMODEL && CMTM != null)
			this.CMTM.copy(CMTM);
	}
	/**
	 * TODO Implement me.
	 * Compare two Renderable objects.
	 */
	@Override
	public int compareTo(final Renderable that)
	{
		return 0;
	}
}
