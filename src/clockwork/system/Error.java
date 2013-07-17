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

/**
 * The error enumeration consists of all possible errors that can be returned by functions
 * defined in this software. I'd rather use this than ambiguous integers or booleans.
 */
public enum Error
{
	None("No error"),
	CouldNotReadFile("Could not read the file"),
	CouldNotWriteFile("Could not write the file"),
	MethodNotImplemented("Unimplemented method called");
	/**
	 * The description of the error.
	 */
	private final String description;
	/**
	 * Instantiate an Error with a description.
	 * @param description the description of the error.
	 */
	Error(final String description)
	{
		this.description = description;
	}
	/**
	 * Convert the error to a string.
	 */
	@Override
	public String toString()
	{
		return description;
	}
}
