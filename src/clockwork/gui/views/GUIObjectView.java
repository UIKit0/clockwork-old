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
package clockwork.gui.views;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * A GUIObjectView is a component of the GUI that is charged with representing a
 * specific type of object from the Model (MVC pattern). The GUIObjectView is subject
 * to change from user input but it will not update the Model; it is a Controller's
 * duty to do so when necessary.
 */
public abstract class GUIObjectView<ObjectType> extends JPanel
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 7322831941623062782L;
	/**
	 * The view's title.
	 */
	private final String title;
	/**
	 * The view's description.
	 */
	private final String description;
	/**
	 * Write a value to be displayed by the view.
	 * @param input the value that will be displayed by the component.
	 */
	public abstract void write(final ObjectType input);
	/**
	 * Return the value that's currently being displayed by the view.
	 * @param output the location where the output will be stored. If this value is null,
	 * an object of the appropriate type will be instantiated and used instead.
	 */
	public abstract ObjectType read(ObjectType output);
	/**
	 * Return the value that's currently being displayed by the view.
	 */
	public final ObjectType read()
	{
		return read(null);
	}
	/**
	 * Instantiate the GUIObjectView with a given title and description.
	 */
	protected GUIObjectView(final String title, final String description)
	{
		this.title = title;
		this.description = description;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	/**
	 * Return the view's title.
	 */
	public final String getTitle()
	{
		return title;
	}
	/**
	 * Return the view's description.
	 */
	public final String getDescription()
	{
		return description;
	}
	/**
	 * Add vertical whitespace to the container.
	 * @param the height of the whitespace to add.
	 */
	public final void addVerticalWhitespace(final int height)
	{
		super.add(Box.createRigidArea(new Dimension(0, height)));
	}
}
