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
package clockwork.types;

import java.util.Stack;

import clockwork.types.math.Matrix4;

/**
 * A MatrixStack stores a stack of 4x4 matrices. It explicitly prohibits the
 * insertion of null matrices.
 */
public final class Matrix4Stack extends Stack<Matrix4>
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1553958236608799751L;
	/**
	 * The default constructor.
	 */
	public Matrix4Stack()
	{
		super();
	}
	/**
	 * Push a non-empty matrix onto the stack, and return the matrix.
	 * @param matrix the matrix to push onto the stack.
	 */
	@Override
	public Matrix4 push(final Matrix4 matrix)
	{
		if (matrix != null)
			return super.push(matrix);
		else
			throw new IllegalArgumentException("Insertion of empty matrix prohibited!");
	}
	/**
	 * Remove and return the matrix at the top of the stack.
	 */
	@Override
	public Matrix4 pop()
	{
		if (isEmpty())
			return null;
		else
			return super.pop();
	}
}
