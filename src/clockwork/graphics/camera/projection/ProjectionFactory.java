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
package clockwork.graphics.camera.projection;

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Vector;

import clockwork.graphics.camera.projection.parallel.oblique.CabinetProjection;
import clockwork.graphics.camera.projection.parallel.oblique.CavalierProjection;
import clockwork.graphics.camera.projection.perspective.PerspectiveProjection;





public class ProjectionFactory
{
	/**
	 * The default projection type.
	 */
	private static Projection.Type DEFAULT_PROJECTION = Projection.Type.Perspective;
	/**
	 * The renderers.
	 */
	private static final LinkedHashMap<Projection.Type, Projection> projections;
	/**
	 * Initialise the renderer dictionary.
	 */
	static
	{
		projections = new LinkedHashMap<Projection.Type, Projection>();

//		put(Projection.Type.Orthographic,	new OrthographicalProjection());
		put(Projection.Type.Cavaliere,		new CavalierProjection());
		put(Projection.Type.Cabinet,			new CabinetProjection());
		put(Projection.Type.Perspective,		new PerspectiveProjection());
	}
	/**
	 * Add a renderer to the hash table.
	 */
	private static void put(final Projection.Type key, final Projection value)
	{
		projections.put(key, value);
	}
	/**
	 * Get a renderer from the hash table.
	 */
	public static Projection get(final Projection.Type key)
	{
		return projections.get(key);
	}
	/**
	 * Get the default renderer.
	 */
	public static Projection getDefaultProjection()
	{
		return projections.get(ProjectionFactory.DEFAULT_PROJECTION);
	}
	/**
	 * Return the key values.
	 */
	public static Set<Projection.Type> getKeys()
	{
		return projections.keySet();
	}
	/**
	 * Return the names of available renderers.
	 */
	public static Vector<String> getAll()
	{
		final Vector<String> names = new Vector<String>(projections.values().size());

		for (final Projection.Type key : projections.keySet())
			names.add(key.toString());

		return names;
	}
}
