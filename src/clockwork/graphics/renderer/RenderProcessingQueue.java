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

import java.util.PriorityQueue;

import clockwork.physics.lighting.LightEmitter;
import clockwork.types.ConcurrentList;


/**
 * A RenderQueue contains objects that will be passed to the renderer to be processed.
 * These are mainly rigid bodies and their transformations (Renderables), and lights.
 */
public final class RenderProcessingQueue
{
	/**
	 * TODO Implement scene sorting. The priority will be the depth of a renderable object.
	 * Renderable objects.
	 */
	private final PriorityQueue<Renderable> renderables = new PriorityQueue<Renderable>();
	/**
	 * Light emitters.
	 */
	private final ConcurrentList<LightEmitter> lightEmitters = new ConcurrentList<LightEmitter>();
	/**
	 * Clear the content of the queue.
	 */
	public void clear()
	{
		renderables.clear();
		lightEmitters.clear();
	}
	/**
	 * Add a Renderable object to the processing queue.
	 */
	public void add(final Renderable renderable)
	{
		if (renderable != null)
			renderables.add(renderable);
	}
	/**
	 * Return the processing queue's Renderable objects.
	 */
	public PriorityQueue<Renderable> getRenderables()
	{
		return renderables;
	}
	/**
	 * Add a LightEmitter object to the processing queue.
	 */
	public void add(final LightEmitter lightEmitter)
	{
		if (lightEmitter != null)
			lightEmitters.add(lightEmitter);
	}
	/**
	 * Return the processing queue's LightEmitter objects.
	 */
	public ConcurrentList<LightEmitter> getLightEmitters()
	{
		return lightEmitters;
	}
	/**
	 * Return true if the processing queue is empty, false otherwise.
	 */
	public boolean isEmpty()
	{
		return renderables.isEmpty() && lightEmitters.isEmpty();
	}
}
