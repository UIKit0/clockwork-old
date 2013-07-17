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

import clockwork.graphics.Vertex;



public final class PhongRenderer extends PolygonRenderer
{
	/**
	 * The default constructor.
	 */
	protected PhongRenderer()
	{
		super(Renderer.Type.Phong);
	}

	/**
	 * TODO Implement me correctly!
	 * @see Renderer#vertexProgram.
	 */
	@Override
	public void vertexProgram(final Vertex input, final Vertex output)
	{
		// Calculate the fragment's position.
		output.position.setXYZW(MODELVIEWPROJECTION.multiply(input.position));

		/**
		// TODO Calculate the vertex normal.
//		fragment.setNormal(NORMAL.multiply(vertex.normal));
//		vertex.calculateNormal(NORMAL);
**/

		// Calculate the fragment's surface normal.
		output.normal.setIJK(NORMAL.multiply(input.normal));
	}
}