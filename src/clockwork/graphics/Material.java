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

import clockwork.graphics.color.ColorRGB;

public class Material
{
	/**
	 * The material's shininess.
	 */
	public double shininess = 1.0;
	/**
	 * The material's transparency.
	 */
	public double transparency = 0.0;
	/**
	 * The material's coefficient of reflection for ambient light.
	 */
	public final ColorRGB Ka = new ColorRGB(0.5, 0.5, 0.5);
	/**
	 * The material's coefficient of reflection for diffuse light.
	 */
	public final ColorRGB Kd = new ColorRGB(0.5, 0.5, 0.5);
	/**
	 * The material's coefficient of reflection for specular light.
	 */
	public final ColorRGB Ks = new ColorRGB(0.5, 0.5, 0.5);
	/**
	 * The ambient map.
	 */
	public Texture ambient = null;
	/**
	 * The diffuse map.
	 */
	public Texture diffuse = null;
	/**
	 * The bump map.
	 */
	public Texture bump = null;
	/**
	 * The normal map.
	 */
	public Texture normal = null;
	/**
	 * The specular map.
	 */
	public Texture specular = null;
	/**
	 * The displacement map.
	 */
	public Texture displacement = null;
	/**
	 * Instantiate a material.
	 */
	public Material()
	{}
	/**
	 * Instantiate a material with given diffuse and normal maps.
	 * @param diffuse the material's diffuse map.
	 * @param normal the material's normal map.
	 */
	public Material(final Texture diffuse, final Texture normal)
	{
		this(diffuse, normal, null);
	}
	/**
	 * Instantiate a material with given diffuse, normal and specular maps.
	 * @param diffuse the material's diffuse map.
	 * @param bump the material's normal map.
	 * @param specular the material's specular map.
	 */
	public Material(final Texture diffuse, final Texture bump, final Texture specular)
	{
		this.diffuse = diffuse;
		this.bump = bump;
		this.specular = specular;
	}
}
