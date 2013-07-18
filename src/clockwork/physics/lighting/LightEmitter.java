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
		Ambient,
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
	private ReflectionModel reflectionModel = ReflectionModel.Phong;
	/**
	 * The type of this light.
	 */
	private LightEmitter.Type type = Type.Ambient;
	/**
	 * The color intensity of the emitted light.
	 */
	private final ColorRGB color = new ColorRGB(0.1, 0.1, 0.1);
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
		this(object, LightEmitter.Type.Ambient);
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
	public ColorRGB calculateFragmentColor(final Fragment fragment, final Material material)
	{
		final ColorRGB intensity = new ColorRGB();

		// Calculate the ambient contribution.
		if (type == LightEmitter.Type.Ambient)
		{
			intensity.r = color.r * material.Ka.r;
			intensity.g = color.g * material.Ka.g;
			intensity.b = color.b * material.Ka.b;
		}
		else
		{


		}
		return intensity;
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
