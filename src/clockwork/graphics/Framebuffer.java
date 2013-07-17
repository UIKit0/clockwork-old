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

import java.awt.Dimension;
import java.util.Arrays;

import clockwork.graphics.filters.FXAA;
import clockwork.graphics.renderer.Renderer;
import clockwork.gui.Display;
import clockwork.system.RuntimeOptions;


/**
 * A framebuffer is a collection of 2D array (buffers) that are used as the output
 * destination for the renderer.
 * @see http://www.opengl.org/wiki/Framebuffer
 */
public final class Framebuffer
{
	/**
	 * The available buffer types in a framebuffer.
	 */
	public enum Output
	{
		Color,
		Depth,
		Stencil,
		Accumulation
	}
	/**
	 * The framebuffer's read-write lock. This is used to prevent writing to buffers
	 * while they're being read from, or reading from buffers while they're being
	 * written to, in order to prevent buffer incoherencies from race conditions.
	 */
//	public static final ReentrantLock READWRITE_LOCK = new ReentrantLock();
	/**
	 * The display device.
	 */
	private final Display display;
	/**
	 * The framebuffer's output mode.
	 */
	private Framebuffer.Output outputMode = Output.Color;
	/**
	 * The framebuffer's width and height.
	 */
	private int width = 0;
	private int height = 0;
	/**
	 * The pixel buffer which holds color information for each pixel in the display.
	 */
	private int pbuffer[];
	/**
	 * The color buffer's clear value.
	 */
	private int cbufferClearValue = 0xff000000;
	/**
	 * The depth buffer which holds depth information for each pixel in the display.
	 */
	private double zbuffer[];
	/**
	 * The depth buffer's clear value.
	 */
	private double zbufferClearValue = Double.MAX_VALUE;
	/**
	 * TODO Explain me.
	 * The stencil buffer.
	 */
	private char sbuffer[];
	/**
	 * The stencil buffer's clear value.
	 */
	private char sbufferClearValue = Character.MAX_VALUE;
	/**
	 * TODO Explain me.
	 * The accumulation buffer.
	 */
	private int abuffer[];
	/**
	 * The accumulation buffer's clear value.
	 */
	private int abufferClearValue = 0xff000000;
	/**
	 * The framebuffer's scissor.
	 */
	private final Scissor scissor = new Scissor();
	/**
	 * Instantiate a framebuffer attached to a given display device.
	 * @param display the display device.
	 */
	public Framebuffer(final Display display)
	{
		this.display = display;
		if (this.display != null)
			this.display.setFramebuffer(this);
	}
	/**
	 * Resize the framebuffer.
	 * @param width the framebuffer's new width.
	 * @param height the framebuffer's new height.
	 */
	public void resize(final int width, final int height)
	{
		// Resize iff the resolution is different.
		if (this.width != width || this.height != height)
		{
			this.width = width;
			this.height = height;
			this.scissor.width = width;
			this.scissor.height = height;

			pbuffer = new int[this.width * this.height];
			zbuffer = new double[pbuffer.length];
			abuffer = new int[pbuffer.length];
			sbuffer = new char[pbuffer.length];

			clear();
		}
	}
	/**
	 * Resize the framebuffer.
	 * @param resolution the framebuffer's new resolution.
	 */
	public void resize(final Display.Resolution resolution)
	{
		if (resolution != null && resolution.width != this.width && resolution.height != this.height)
			resize(resolution.width, resolution.height);
	}
	/**
	 * Return the framebuffer's resolution.
	 */
	public Dimension getResolution()
	{
		return new Dimension(width, height);
	}
	/**
	 * Return the framebuffer's width.
	 */
	public int getWidth()
	{
		return width;
	}
	/**
	 * Return the framebuffer's height.
	 */
	public int getHeight()
	{
		return height;
	}
	/**
	 * Return the framebuffer offset for an <x, y> coordinate. If the coordinate is out
	 * of bounds, -1 is returned.
	 */
	private final int getFramebufferOffset(final float x, final float y)
	{
		final int xw = Math.round(x);
		final int yw = Math.round(y);

		return (xw >= 0 && yw >= 0 && xw < width && yw < height) ? xw + (yw * width) : -1;
	}
	/**
	 * Return the array offset for an <x, y> coordinate. If the coordinate is out
	 * of bounds, -1 is returned.
	 */
	private final int getFramebufferOffset(final double x, final double y)
	{
		return getFramebufferOffset((float)x, (float)y);
	}
	/**
	 * Return the framebuffer's pixel buffer.
	 */
	public int[] getColorBuffer()
	{
		return pbuffer;
	}
	/**
	 * Set the color buffer's clear value.
	 * @param value the clear value to set.
	 */
	public void setClearColorValue(final int value)
	{
		this.cbufferClearValue = value;
	}
	/**
	 * Return the color buffer's clear value.
	 */
	public int getClearColorValue()
	{
		return cbufferClearValue;
	}
	/**
	 * Return the depth buffer.
	 */
	public double[] getDepthBuffer()
	{
		return zbuffer;
	}
	/**
	 * Set the depth buffer's clear value.
	 * @param value the clear value to set.
	 */
	public void setClearDepthValue(final double value)
	{
		this.zbufferClearValue = value;
	}
	/**
	 * Return the depth buffer's clear value.
	 */
	public double getClearDepthValue()
	{
		return zbufferClearValue;
	}
	/**
	 * Return the stencil buffer.
	 */
	public char[] getStencilBuffer()
	{
		return sbuffer;
	}
	/**
	 * Set the stencil buffer's clear value.
	 * @param value the clear value to set.
	 */
	public void setClearStencilValue(final char value)
	{
		this.sbufferClearValue = value;
	}
	/**
	 * Return the stencil buffer's clear value.
	 */
	public int getClearStencilValue()
	{
		return sbufferClearValue;
	}
	/**
	 * Return the accumulation buffer.
	 */
	public int[] getAccumulationBuffer()
	{
		return abuffer;
	}
	/**
	 * Set the accumulation buffer's clear value.
	 * @param value the clear value to set.
	 */
	public void setClearAccumulationValue(final int value)
	{
		this.abufferClearValue = value;
	}
	/**
	 * Return the accumulation buffer's clear value.
	 */
	public int getClearAccumulationValue()
	{
		return abufferClearValue;
	}
	/**
	 * Set the framebuffer's output mode. A framebuffer can return pixels, or any of the other
	 * three buffers converted to pixel values.
	 * @param outputMode the output mode to set.
	 */
	public void setOutputMode(final Framebuffer.Output outputMode)
	{
		this.outputMode = outputMode;
	}
	/**
	 * Return the framebuffer's output mode.
	 */
	public Framebuffer.Output getOutputMode()
	{
		return outputMode;
	}
	/**
	 * TODO Complete me.
	 * Return the framebuffer's output.
	 */
	public int[] getOutput()
	{
		// Convert the selected buffer to pixels and store them in the pbuffer.
		switch (outputMode)
		{
			case Accumulation:
			{

			}
			case Depth:
			{

			}
			case Stencil:
			{

			}
			default:break;
		}
		return pbuffer;
	}
	/**
	 * Clear the framebuffer.
	 */
	public void clear()
	{
		Arrays.fill(pbuffer, cbufferClearValue);
		Arrays.fill(zbuffer, zbufferClearValue);
		Arrays.fill(sbuffer, sbufferClearValue);
		Arrays.fill(abuffer, abufferClearValue);
	}
	/**
	 * Write a fragment to the framebuffer iff it passes all fragment tests.
	 * @param renderer a reference to the renderer containing the fragment program implementation.
	 * @param fragment the fragment to write.
	 */
	public void write(final Renderer renderer, final Fragment fragment)
	{
		final int offset = getFramebufferOffset(fragment.x, fragment.y);
		if (fragmentPasses(fragment, offset))
		{
			pbuffer[offset] = renderer.fragmentProgram(fragment);
			zbuffer[offset] = fragment.z;
			abuffer[offset] = abufferClearValue;
			sbuffer[offset] = fragment.stencil;
		}
	}
	/**
	 * Return true if a fragment passes all fragment tests, false otherwise.
	 * @param fragment the fragment to test.
	 * @param offset the index of the framebuffer value to test against.
	 */
	private boolean fragmentPasses(final Fragment fragment, final int offset)
	{
		if (offset < 0) //TODO Remove this test condition when clipping is correctly implemented.
			return false;

		// Get the window coordinates.
		final int xw = (int)Math.round(fragment.x);
		final int yw = (int)Math.round(fragment.y);

		if (RuntimeOptions.EnableScissorTest && !scissor.test(xw, yw)) return false;

		// TODO Implement alpha testing.
		if (RuntimeOptions.EnableAlphaTest)
			;

		// TODO Implement stencil testing.
		if (RuntimeOptions.EnableStencilTest)
			;

		// Perform stencil test. A stencil test passes if the fragment's stencil value is strictly
		// less than the value contained in the stencil buffer; it fails otherwise.
//		if (RuntimeOptions.EnableStencilTest && fragment.stencil >= sbuffer[offset])
//			return;


		if (RuntimeOptions.EnableDepthTest && !(fragment.z < zbuffer[offset])) return false;


		return true;
	}
	/**
	 * Post-processing the framebuffer's contents.
	 */
	public void postProcess()
	{
		FXAA.apply(this);
	}
	/**
	 * Discard a framebuffer value which resets a single element of the framebuffer.
	 * @param offset the offset of the buffer element to discard.
	 */
	public void discard(final int offset)
	{
		pbuffer[offset] = cbufferClearValue;
		zbuffer[offset] = zbufferClearValue;
		abuffer[offset] = abufferClearValue;
		sbuffer[offset] = sbufferClearValue;
	}



	/**
	 * TODO Explain me better.
	 * Draw a pixel at the given coordinates.
	 */
	public void setPixel(int x, int y, float z, int pixel)
	{
		final int offset = getFramebufferOffset(x, y);
		if (offset >= 0 && zbuffer[offset] > z)
		{
			pbuffer[offset] = pixel;
			zbuffer[offset] = z;
			abuffer[offset] = abufferClearValue;
			sbuffer[offset] = sbufferClearValue;
		}
	}
}
