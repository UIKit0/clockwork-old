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

import clockwork.graphics.Fragment;
import clockwork.graphics.color.ColorRGBA;

public final class DepthRenderer extends PolygonRenderer
{
	/**
	 * The default constructor.
	 */
	protected DepthRenderer()
	{
		super(Renderer.Type.Depth);
	}
	/**
	 * @see Renderer#prepare.
	 */
	@Override
	public void prepare()
	{
		super.prepare();
		framebuffer.setClearColorValue(0xffffffff);
	}
	/**
	 * @see Renderer#fragmentProgram.
	 */
	@Override
	public int fragmentProgram(final Fragment fragment)
	{
		// The fragment's depth value is multiplied by a depth scale and then added to
		// a depth bias. The ColorRGBA.merge function guarantees that this result is clamped to
		// the [0, 1] range before a color is calculated.
		final double depthScale = 1.0;
		final double depthBias = 0.5;
		final double depth = (fragment.z * depthScale) + depthBias;

		return ColorRGBA.merge(depth, depth, depth, 1.0f);
	}
}
