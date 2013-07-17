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

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The ConcurrentList data type is used to prevent memory coherence problems resulting from
 * race conditions in threaded environments. It also explicitly prohibits the insertion of
 * null entries.
 */
public class ConcurrentList<Entry> extends CopyOnWriteArrayList<Entry>
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -7748200891119551230L;
	/**
	 * The default constructor.
	 */
	public ConcurrentList()
	{
		super();
	}
	/**
	 * Instantiate a ConcurrentList holding a copy of a given array of entries.
	 * @param entries an array containing objects to copy and store in the ConcurrentList.
	 */
	public ConcurrentList(final Entry[] entries)
	{
		super(entries);
	}
	/**
	 * Instantiate a ConcurrentList holding a copy of a given collection of entries.
	 * @param entries a collection of entries to copy and store in the ConcurrentList.
	 */
	public ConcurrentList(final Collection<? extends Entry> entries)
	{
		super(entries);
	}
	/**
	 * Add an entry to the ConcurrentList.
	 * @param entry the entry to add to the ConcurrentList.
	 */
	@Override
	public boolean add(final Entry entry)
	{
		if (entry != null)
			return super.add(entry);
		else
			throw new IllegalArgumentException("Insertion of null entry prohibited!");
	}
	/**
	 * Append a collection of entries to this ConcurrentList.
	 * @param collection the collection of entries to add to the ConcurrentList.
	 */
	@Override
	public boolean addAll(final Collection<? extends Entry> collection)
	{
		for (final Entry entry : collection)
			this.add(entry);

		return true;
	}
}