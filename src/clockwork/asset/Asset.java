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
package clockwork.asset;

public abstract class Asset
{
	/**
	 * The asset key.
	 */
	private String assetKey = null;
	/**
	 * Return the asset key.
	 */
	public final String getAssetKey()
	{
		return assetKey;
	}
	/**
	 * Return a truncated asset key.
	 */
	public final String getTruncatedAssetKey()
	{
		return assetKey.substring(assetKey.lastIndexOf('/'));
	}
	/**
	 * Set the asset key.
	 * @param key the asset key to set.
	 */
	public final void setAssetKey(final String key)
	{
		this.assetKey = key;
	}
	/**
	 * Convert the asset data into a string.
	 */
	@Override
	public String toString()
	{
		return getTruncatedAssetKey();
	}
}
