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

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Vector;

import clockwork.types.ConcurrentList;


public class RendererFactory
{
	/**
	 * The type of the default renderer.
	 */
	public static Renderer.Type DEFAULT_RENDERER_TYPE = Renderer.Type.Phong;
	/**
	 * The renderers.
	 */
	private static final LinkedHashMap<Renderer.Type, Renderer> renderers;
	/**
	 * Initialise the renderer dictionary.
	 */
	static
	{
		renderers = new LinkedHashMap<Renderer.Type, Renderer>();

		put(Renderer.Type.Points,    new PointsRenderer());
		put(Renderer.Type.Wireframe, new WireframeRenderer());
		put(Renderer.Type.Random,    new RandomRenderer());
		put(Renderer.Type.Depth,     new DepthRenderer());
		put(Renderer.Type.Normals,   new NormalsRenderer());
		put(Renderer.Type.Texture,   new TextureRenderer());
		put(Renderer.Type.Constant,  new ConstantRenderer());
		put(Renderer.Type.Phong,     new PhongRenderer());
		put(Renderer.Type.Cel,       new CelRenderer());
		put(Renderer.Type.Bump,      new BumpRenderer());
		put(Renderer.Type.Deferred,  new DeferredRenderer());
	}
	/**
	 * Add a renderer to the hash table.
	 */
	private static void put(final Renderer.Type key, final Renderer value)
	{
		renderers.put(key, value);
	}
	/**
	 * Get a renderer from the hash table.
	 */
	public static Renderer get(final Renderer.Type key)
	{
		return renderers.get(key);
	}
	/**
	 * Return all renderer types.
	 */
	public static ConcurrentList<Renderer.Type> getRenderers()
	{
		final ConcurrentList<Renderer.Type> types =
		new ConcurrentList<Renderer.Type>();

		for (final Renderer.Type type : renderers.keySet())
			types.add(type);

		return types;
	}
	/**
	 * Get the default renderer.
	 */
	public static Renderer getDefaultRenderer()
	{
		return renderers.get(RendererFactory.DEFAULT_RENDERER_TYPE);
	}
	/**
	 * Return the names of available renderers.
	 */
	public static Vector<String> getAll()
	{
		final Vector<String> names = new Vector<String>(renderers.values().size());

		for (final Renderer.Type key : renderers.keySet())
			names.add(key.toString());

		return names;
	}
	/**
	 * Return the key values.
	 */
	public static Set<Renderer.Type> getKeys()
	{
		return renderers.keySet();
	}
}
