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
package clockwork.system;

import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import clockwork.gui.Display;

public final class Debug
{
	/**
	 * The number of objects in the scene.
	 */
	public static AtomicInteger ObjectCount = new AtomicInteger(0);
	/**
	 * The number of polygons in the scene.
	 */
	public static AtomicInteger PolygonCount = new AtomicInteger(0);
	/**
	 * The number of rendered polygons in the scene.
	 */
	public static AtomicInteger RenderedPolygonCount = new AtomicInteger(0);
	/**
	 * The number of milliseconds it takes to draw a frame.
	 */
	public static AtomicLong MillisecondsPerFrame = new AtomicLong(0);
	/**
	 * The current display's refresh rate.
	 */
	public static int RefreshRate = 0;
	/**
	 * The name of the current viewer.
	 */
	public static String ViewerName;
	/**
	 * The static initializer.
	 */
	static
	{
		// Get the device's refresh rate.
		GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		if (devices.length > 0)
			Debug.RefreshRate = devices[0].getDisplayMode().getRefreshRate();
	}

	/**
	 * Convert the debug information to a String.
	 */
	public static String string()
	{
		final Display.Resolution resolution = Services.Graphics.getDisplay().getResolution();
		final int renderedPolygonCount = RenderedPolygonCount.get();
		final int polygonCount = PolygonCount.get();
		final long msPerFrame = MillisecondsPerFrame.get();
		final int framesPerSecond = (int)(1000.0 / msPerFrame);
		final float cullPercentage =
		polygonCount > 0 ? (1.0f - ((float)renderedPolygonCount / (float)polygonCount)) * 100.0f : 0;

		return String.format
		(
			"VIEWER: %s\n" +
			"%d x %d @%d Hz\n" +
			"LAST RENDER TIME: %d MS (%d FPS)\n" +
			"VISIBLE OBJECTS: %d\n" +
			"TOTAL TRIANGLE POLYGON COUNT: %d\n" +
			"RENDERED TRIANGLE POLYGON COUNT: %d (%.1f %%)\n",
			ViewerName.toUpperCase(),
			resolution.width, resolution.height, RefreshRate,
			msPerFrame, framesPerSecond,
			ObjectCount.get(),
			polygonCount,
			renderedPolygonCount, cullPercentage
		);
	}
	/**
	 * Print the debug information.
	 */
	public static void print(final Graphics graphics)
	{
		final int yOffset = 20;
		final int x = 10;
		int y = 20;

		for (final String str : Debug.string().split("\n"))
		{
			graphics.drawString(str, x, y);
			y += yOffset;
		}
	}
	/**
	 * FIXME Find the correct stack trace element.
	 * Print the calling method.
	 */
	public static void printCaller()
	{
		System.out.println("CALLER: " + Thread.currentThread().getStackTrace()[3]);
	}
}