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
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import clockwork.gui.Display;
import clockwork.system.Subsystem;

public final class GraphicsSubsystem extends Subsystem
{
	/**
	 * Instantiate a VGA display device. Note that the smaller the device, the
	 * smaller the memory footprint and render time. Suppose the framebuffer
	 * contains 32-bit color, depth and accumulator buffers; and an 8-bit stencil
	 * buffer, all of which share the same resolution as the display device.
	 * A 640x480 resolution will use (((32 * 3) + 8) * 640 * 480) ~ 30.4MB, while
	 * a 800x600 resolution will use (((32 * 3) + 8) * 800 * 600) ~ 47.6MB, and a
	 * 1024x768 resolution will use (((32 * 3) + 8) * 1024 * 768) = 78MB!
	 * The render time is reduced since the number of operations performed during
	 * clear and blit phases is smaller.
	 */
	private final Display display = new Display(Display.Resolution.SVGA);
	/**
	 * Create the default framebuffer with a resolution identical to the display device.
	 */
	private final Framebuffer framebuffer = new Framebuffer(display);
	/**
	 * Return the display device.
	 */
	public Display getDisplay()
	{
		return display;
	}
	/**
	 * Return the framebuffer.
	 */
	public Framebuffer getFramebuffer()
	{
		return framebuffer;
	}
	/**
	 * Render the scene.
	 * @param dt the time elapsed since the last render.
	 */
	@Override
	public void update(final float dt)
	{
		framebuffer.clear();
		if (display != null)
		{
			// If the scene has been changed by another subsystem, notify its viewers.
			// This will, in turn, update the render contexts held by the scene viewers,
			// then render the scene to the framebuffer and apply post-processing filters.
			if (scene.hasChanged())
				scene.notifyObservers(dt);
		}
	}
	/**
	 * Cleanup the graphics subsystem when done.
	 */
	@Override
	public void dispose()
	{}
	/**
	 * Flip the front and back buffers.
	 */
	public void flip()
	{
		display.repaint();
	}
	/**
	 * Display a texture, which is considered an image.
	 * @param texture the texture to view.
	 */
	public void showImage(final Texture texture)
	{
		if (texture != null)
		{
			new ImageViewer(texture.texels, texture.width, texture.height);
		}
	}
	/**
	 * Create a BufferedImage from an array of pixels.
	 * @param pixels the pixels that will fill the BufferedImage.
	 * @param resolution the resolution of the BufferedImage.
	 */
	public static BufferedImage CreateBufferedImage(final int[] pixels, final Dimension resolution)
	{
		BufferedImage output = null;
		if (pixels != null && resolution != null)
		{
			output = new BufferedImage(resolution.width, resolution.height, BufferedImage.TYPE_INT_ARGB);
			final int destination[] = ((DataBufferInt)output.getRaster().getDataBuffer()).getData();
			System.arraycopy(pixels, 0, destination, 0, destination.length);
		}
		return output;
	}
}
