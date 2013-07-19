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
package clockwork.physics.lighting;

import clockwork.graphics.Fragment;
import clockwork.graphics.Material;
import clockwork.graphics.color.ColorRGB;
import clockwork.graphics.renderer.RenderProcessingQueue;
import clockwork.scene.SceneEntityProperty;
import clockwork.scene.SceneGraph;
import clockwork.scene.SceneObject;
import clockwork.types.Matrix4Stack;
import clockwork.types.math.Matrix4;
import clockwork.types.math.Orientation;
import clockwork.types.math.Point3f;
import clockwork.types.math.Vector3f;

public class LightEmitter extends SceneEntityProperty<SceneObject>
{
	/**
	 * The lighting reflection model.
	 */
	public static enum ReflectionModel
	{
		Phong("Phong"),
		BlinnPhong("Blinn-Phong");
		/**
		 * The reflection model's title.
		 */
		private final String title;
		/**
		 * Instantiate a reflection model with a given title.
		 * @param title the reflection model's title.
		 */
		ReflectionModel(final String title)
		{
			this.title = title;
		}
		/**
		 * Convert the reflection model data into a string.
		 */
		@Override
		public String toString()
		{
			return title;
		}
	}
	/**
	 * The type of emitted light.
	 */
	public static enum Type
	{
		Point,
		Spot,
		Directional,
	}
	/**
	 * The light emitter's position which is a reference to the entity's position.
	 */
	private final Point3f position;
	/**
	 * The light emitter's direction which is a reference to the entity's orientation.
	 */
	private final Orientation direction;
	/**
	 * The reflection model used by this light.
	 */
	private ReflectionModel reflectionModel = ReflectionModel.BlinnPhong;
	/**
	 * The type of this light.
	 */
	private LightEmitter.Type type = Type.Spot;
	/**
	 * The color intensity of the emitted light.
	 */
	private final ColorRGB color = new ColorRGB(1.0, 1.0, 1.0);
	/**
	 * Instantiate a light emitter attached to an object and a given type of emission and color.
	 * @param object the object that holds this light property.
	 * @param type the type of light emitted.
	 * @param color the emitter's color.
	 */
	public LightEmitter
	(
		final SceneObject object,
		final LightEmitter.Type type,
		final ColorRGB color
	)
	{
		super("Property::LightEmitter", object);
		this.position = object.getPosition();
		this.direction = object.getOrientation();
		this.type = type;
		this.color.copy(color);
	}
	/**
	 * Instantiate a white light emitter attached to an object and a given type of emission.
	 * @param object the object that holds this light property.
	 * @param type the type of light emitted.
	 */
	public LightEmitter(final SceneObject object, final LightEmitter.Type type)
	{
		this(object, type, null);
	}
	/**
	 * Instantiate an ambient white light emitter attached to an object.
	 * @param object the object that holds this light property.
	 */
	public LightEmitter(final SceneObject object)
	{
		this(object, LightEmitter.Type.Spot);
	}
	/**
	 * Return the color of the light.
	 */
	public ColorRGB getColor()
	{
		return color;
	}
	/**
	 * Set the color of the light.
	 * @param color the color to set.
	 */
	public void setColor(final ColorRGB color)
	{
		this.color.copy(color);
	}
	/**
	 * Return the type of emission.
	 */
	public Type getType()
	{
		return type;
	}
	/**
	 * Set the type of emission.
	 */
	public void setType(final Type type)
	{
		this.type = type;
	}
	/**
	 * Return the reflection model used by this emitter.
	 */
	public LightEmitter.ReflectionModel getReflectionModel()
	{
		return reflectionModel;
	}
	/**
	 * Set the reflection model used by this emitter.
	 * @param model the reflection model to set.
	 */
	public void setReflectionModel(final LightEmitter.ReflectionModel model)
	{
		this.reflectionModel = model;
	}
	/**
	 * Return the color intensity of a given fragment when this light is applied to it.
	 */
	public ColorRGB calculateFragmentColor
	(
		final Point3f viewpoint,
		final Fragment fragment,
		final Material material
	)
	{
		final ColorRGB ambient = new ColorRGB();
		final ColorRGB diffuse = new ColorRGB();
		final ColorRGB specular = new ColorRGB();

		// The surface position P and normal N.
		final Point3f  P = new Point3f(fragment.x, fragment.y, fragment.z);
		final Vector3f N = new Vector3f(fragment.ni, fragment.nj, fragment.nk);

		// The unit vector originating from the surface position, directed towards the viewpoint.
//FIXME Find out why this doesn't work: final Vector3f V = Vector3f.normalise(P.subtract(viewpoint));
		final Vector3f V = Vector3f.normalise(viewpoint.subtract(P));

		// The unit vector originating from the surface position, directed towards this light source.
		final Vector3f L = Vector3f.normalise(P.subtract(this.position));

		// Calculate the light's attenuation factor.
		final double fatt = type != LightEmitter.Type.Directional ?
		getAttenuationFactor(Point3f.distance(P, this.position)) : 1.0;

		// The material's ambient, diffuse and specular colors.
		final ColorRGB Oa = material.ambient != null ?
		ColorRGB.split(material.ambient.getTexel(fragment.u, fragment.v)) : ColorRGB.White;
		final ColorRGB Od = material.diffuse != null ?
		ColorRGB.split(material.diffuse.getTexel(fragment.u, fragment.v)) : ColorRGB.White;
		final ColorRGB Os = material.specular != null ?
		ColorRGB.split(material.specular.getTexel(fragment.u, fragment.v)) : ColorRGB.White;

		// Calculate the ambient contribution.
		ambient.r = material.Ka.r * Oa.r * Od.r;
		ambient.g = material.Ka.g * Oa.g * Od.g;
		ambient.b = material.Ka.b * Oa.b * Od.b;

		// Calculate the diffuse contribution.
		final double diffuseFactor = N.dot(L);
		if (diffuseFactor > 0)
		{
			diffuse.r = diffuseFactor * material.Kd.r * Od.r;
			diffuse.g = diffuseFactor * material.Kd.g * Od.g;
			diffuse.b = diffuseFactor * material.Kd.b * Od.b;

			// Calculate the specular contribution.
			final double specularFactor = getSpecularFactor(N, L, V, material.shininess);
			specular.r = specularFactor * material.Ks.r * Os.r;
			specular.g = specularFactor * material.Ks.g * Os.g;
			specular.b = specularFactor * material.Ks.b * Os.b;
		}
		// Add all contributions.
		return new ColorRGB
		(
			ambient.r + ((diffuse.r + specular.r) * fatt * color.r),
			ambient.g + ((diffuse.g + specular.g) * fatt * color.g),
			ambient.b + ((diffuse.b + specular.b) * fatt * color.b)
		);
	}
	/**
	 * Return this light's specular factor.
	 * @param N the surface normal.
	 * @param L the light's direction vector.
	 * @param V the view vector.
	 * @param shininess the material's shininess.
	 */
	private double getSpecularFactor
	(
		final Vector3f N,
		final Vector3f L,
		final Vector3f V,
		final double shininess
	)
	{
		double gamma = 0;

		if (this.reflectionModel == ReflectionModel.Phong)
		{
			// Phong reflection model.
			final Vector3f R = new Vector3f();// FIXME Vector3f.normalise(new Vector3f(L, N.multiply(2.0 * N.dot(L))));
			gamma = Math.max(0, R.dot(V));
		}
		else if (this.reflectionModel == ReflectionModel.BlinnPhong)
		{
			// Blinn-Phong shading model.
			final Vector3f H = Vector3f.normalise(new Vector3f(L.i + V.i, L.j + V.j, L.k + V.k));
			gamma = N.dot(H);
		}
		return (gamma != 0.0 ? Math.pow(gamma, shininess) : 1.0);
	}

	private final double constantAttenuationCoefficient = 0.2;
	private final double linearAttenuationCoefficient = 0.5;
	private final double quadraticAttenuationCoefficient = 0.8;
	/**
	 * Return the light's attenuation factor.
	 * @param distance the distance between this light and the surface to be lighted.
	 */
	public double getAttenuationFactor(final double distance)
	{
		final double c1 = constantAttenuationCoefficient;
		final double c2 = linearAttenuationCoefficient * distance;
		final double c3 = quadraticAttenuationCoefficient * distance * distance;

		return Math.min(1.0, 1.0 / (c1 + c2 + c3));
	}
	/**
	 * @see SceneGraph.Node#update
	 */
	@Override
	public void update(float dt)
	{}
	/**
	 * @see SceneGraph.Node#buildRenderProcessingQueue
	 */
	@Override
	public void buildRenderProcessingQueue
	(
		final Matrix4 VIEW,
		final Matrix4Stack stack,
		final RenderProcessingQueue queue
	)
	{
		if (!isPruned())
			queue.add(this);
	}
	/**
	 * Return the rigid body's state.
	 */
	@Override
	public String getState()
	{
		String state = "";
		if (isPruned())
			state += "off ";

		final int length = state.length();
		if (length > 2)
			state = state.substring(0, length - 1);
		return state;
	}
}
