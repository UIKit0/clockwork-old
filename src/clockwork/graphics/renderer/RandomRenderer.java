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
import clockwork.graphics.color.ColorRGBA;

public final class RandomRenderer extends PolygonRenderer
{
	/**
	 * The default constructor.
	 */
	protected RandomRenderer()
	{
		super(Renderer.Type.Random);
	}
	/**
	 * A counter used to generate a new color after 3 vertices.
	 */
	int generateColorCounter = 3;
	/**
	 * The polygon triangle color.
	 */
	int color = ColorRGBA.getRandomColor().merge();
	/**
	 * @see Renderer#vertexProgram.
	 */
	@Override
	public void vertexProgram(final Vertex input, final Vertex output)
	{
		// Call the default implementation to perform matrix transformations.
		super.vertexProgram(input, output);

		// Set a random color for the output vertex.
		output.color.copy(color);

		--generateColorCounter;
		if (generateColorCounter == 0)
		{
			color = ColorRGBA.getRandomColor().merge();
			generateColorCounter = 3;
		}
	}
}
