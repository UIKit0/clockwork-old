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
package clockwork.scene;

import java.util.UUID;

/**
 * A SceneEntity is everything that exists in a Scene, visible or invisible.
 * Think of SceneEntities as abstract existences, in that we know they exist
 * but we may or may not be able to interact with them (very philosophical, I know).
 */
public abstract class SceneEntity
{
	/**
	 * The entity's universally unique identifier (UUID) which will allow us
	 * to distinguish between entities even if their names are changed.
	 */
	private final UUID identifier;
	/**
	 * The name of the entity.
	 */
	private String name;
	/**
	 * Instantiate a SceneEntity object with a given name.
	 */
	public SceneEntity(final String name)
	{
		this.identifier = UUID.randomUUID();
		this.name = name;
	}
	/**
	 * Get the entity's unique identifier.
	 */
	public final UUID getIdentifier()
	{
		return identifier;
	}
	/**
	 * Get the name of the entity.
	 */
	public final String getName()
	{
		return name;
	}
	/**
	 * Get the name of the entity concatenated to its identifier.
	 */
	public final String getFullName()
	{
		return identifier + " :: " + name;
	}
	/**
	 * Set the name of the object.
	 */
	public void setName(final String name)
	{
		this.name = name;
	}
	/**
	 * Return the entity's state in the form of a string.
	 */
	public String getState()
	{
		return "";
	}
	/**
	 * Convert the entity to a string.
	 */
	@Override
	public final String toString()
	{
		final String state = getState();
		if (state != null && state.length() > 1)
			return name + " [" + state + "]";
		else
			return name;
	}
}
