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
package clockwork.asset.io.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import clockwork.asset.Asset;


public abstract class AssetReader<AssetType extends Asset>
{
	/**
	 * The path to the folder containing the file that is currently being processed.
	 */
	private String folderPath;
	/**
	 * Return the absolute path to the folder that contains
	 * the file currently being processed.
	 */
	protected String getFolderPath()
	{
		return folderPath;
	}
	/**
	 * The read method reads data from a file and passes it to the parser.
	 * If reading was not successful, null is returned. An AssetType object is
	 * returned otherwise.
	 * @param file the file from which to read data.
	 */
	public AssetType read(final File file)
	{
		AssetType output = null;
		if (file != null && file.exists())
		{
			folderPath = file.getAbsolutePath();
			folderPath = folderPath.substring(0, folderPath.lastIndexOf(File.separator) + 1);
			FileInputStream input = null;
			try
			{
				input = new FileInputStream(file);
				if (input != null)
					output = parse(input);
			}
			catch (FileNotFoundException e1)
			{}
			catch (final IOException e)
			{
				System.err.println("Could not parse '" + file.getName() + "'");
			}
			finally
			{
				if (input != null)
				{
					try
					{
						input.close();
					}
					catch (IOException e)
					{
						System.err.println("Could not close '" + file.getName() + "' input stream.");
					}
				}
			}
		}
		return output;
	}
	/**
	 * This method parses file data and returns it in the specified AssetType.
	 * @param stream a file input stream to parse.
	 * @throws IOException
	 */
	protected abstract AssetType parse(final FileInputStream stream) throws IOException;
}
