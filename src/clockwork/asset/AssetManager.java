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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import clockwork.asset.io.reader.AssetReader;
import clockwork.asset.io.reader.TextureReader;
import clockwork.asset.io.reader.model3d.Model3DReader;
import clockwork.asset.io.reader.model3d.Model3DReaderCTM;
import clockwork.asset.io.reader.model3d.Model3DReaderOBJ;
import clockwork.asset.io.writer.AssetWriter;
import clockwork.asset.io.writer.TextureWriter;
import clockwork.asset.io.writer.model3d.Model3DWriter;
import clockwork.asset.io.writer.model3d.Model3DWriterCTM;
import clockwork.asset.io.writer.model3d.Model3DWriterOBJ;
import clockwork.graphics.Model3D;
import clockwork.graphics.Texture;

public class AssetManager
{
	/**
	 * A dictionary of supported file extensions and their respective reader-writer pair.
	 */
	private static final HashMap<String, ReaderWriterPair> supportedFiles;
	static
	{
		supportedFiles = new HashMap<String, ReaderWriterPair>();

		supportedFiles.put("obj",  new ReaderWriterPair(new Model3DReaderOBJ(),  new Model3DWriterOBJ()));
		supportedFiles.put("ctm",  new ReaderWriterPair(new Model3DReaderCTM(),  new Model3DWriterCTM()));
		supportedFiles.put("_tex", new ReaderWriterPair(new TextureReader(),     new TextureWriter()));

//TODO add	supportedFiles.put("json", new ReaderWriterPair(new TextReaderJSON(), new TextWriterJSON()));
	}
	/**
	 * A hashmap of loaded assets. Each asset is identified by its canonical path to prevent
	 * loading multiple identical assets. Note that a canonical path caters to symbolic links too.
	 */
	private static final HashMap<String, Asset> assets = new HashMap<String, Asset>();
	/**
	 * Return all assets.
	 */
	public static HashMap<String, Asset> getAssetMap()
	{
		return assets;
	}
	/**
	 * Load an asset from a file iff it doesn't already exist in memory. If the asset
	 * was previously loaded, then the copy in memory is returned.
	 * @param file the file containing the asset.
	 * @param reader the reader used to parse the file.
	 */
	private static Asset LoadAsset(final File file, final AssetReader<?> reader)
	{
		Asset output = null;
		String assetKey = null;
		try
		{
			assetKey = file.getCanonicalPath();
			if (assets.containsKey(assetKey))
				output = assets.get(assetKey);
			else
			{
				output = reader.read(file);
				if (output != null)
				{
					output.setAssetKey(assetKey);
					assets.put(assetKey, output);
				}
			}
		}
		catch (IOException exception)
		{}

		return output;
	}
	/**
	 * Load a texture from a given file. If the texture already exists in memory
	 * then the copy in memory is returned, otherwise the file is loaded.
	 * @param file the file containing the texture data to load.
	 */
	public static Texture LoadTexture(final File file)
	{
		if (file != null)
		{
			// Use the internal "_tex" extension to retrieve the texture reader.
			final TextureReader reader = (TextureReader)supportedFiles.get("_tex").reader;
			if (reader != null)
				return (Texture)LoadAsset(file, reader);
		}
		return null;
	}
	/**
	 * Load a texture from a given file.
	 * @param filename the name of the file containing the texture data to load.
	 */
	public static Texture LoadTexture(final String filename)
	{
		return IsValidFilename(filename) ? LoadTexture(new File(filename)) : null;
	}
	/**
	 * Write an asset to a given file.
	 * @param asset the asset to write.
	 * @param file the file where the asset will be stored.
	 * @param writer the writer used to write the asset to the file.
	 */
	private static void WriteAsset
	(
		final Asset asset,
		final File file,
		final AssetWriter<Asset> writer
	)
	{
		if (asset != null)
		{
			try
			{
				final long t0 = System.currentTimeMillis();
				writer.write(asset, file);
				final long t1 = System.currentTimeMillis();
				System.out.println(String.format("Wrote '%s' in %.1fs", file.getName(), (t1 - t0)/1000.0));
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * TODO Add more test cases.
	 * Returns true if the given string is a valid filename, false otherwise.
	 * @param filename the name to check.
	 */
	private static boolean IsValidFilename(final String filename)
	{
		return (filename != null);
	}
	/**
	 * TODO Handle exception internally.
	 * Load a 3D model from a given file.
	 * @param file the file containing the 3D model to load.
	 */
	public static Model3D LoadModel3D(final File file) throws IOException
	{
		if (file != null)
		{
			final Model3DReader reader = AssetManager.getModel3DReader(file);
			if (reader != null)
				return (Model3D)LoadAsset(file, reader);
		}
		return null;
	}
	/**
	 * TODO Remove throws.
	 * Load a 3D model from a given file.
	 * @param filename the name of the file containing the 3D model to load.
	 */
	public static Model3D LoadModel3D(final String filename) throws IOException
	{
		return IsValidFilename(filename) ? LoadModel3D(new File(filename)) : null;
	}
	/**
	 * Return the mesh reader that corresponds to a given file containing mesh data.
	 * @param file the file we wish to read.
	 */
	private static Model3DReader getModel3DReader(final File file)
	{
		Model3DReader output = null;
		final String fileExtension = getFileExtension(file);
		if (fileExtension != null)
		{
			final ReaderWriterPair readerWriterPair = supportedFiles.get(fileExtension);
			if (readerWriterPair != null)
				output = (Model3DReader)readerWriterPair.reader;
			else
				System.err.println("*." + fileExtension + " 3D model files are not supported.");
		}
		return output;
	}
	/**
	 * Return the mesh writer that corresponds to a given file extension.
	 * @param fileExtension the extension of the file we wish to write.
	 */
	private static Model3DWriter getModel3DWriter(final String fileExtension)
	{
		Model3DWriter output = null;
		if (fileExtension != null)
		{
			final ReaderWriterPair readerWriterPair = supportedFiles.get(fileExtension);
			if (readerWriterPair != null)
				output = (Model3DWriter)readerWriterPair.writer;
			else
				System.err.println("*." + fileExtension + " 3D model files are not supported.");
		}
		return output;
	}
	/**
	 * Return a file's extension, if it has one.
	 * @param file the file from which to retrieve an extension.
	 */
	private static String getFileExtension(final File file)
	{
		if (file != null)
		{
			String filename;
			try
			{
				filename = file.getCanonicalPath();
				final int i = filename.lastIndexOf('.') + 1;
				if (i > 0)
					return filename.substring(i);
			}
			catch (IOException e)
			{}
		}
		return null;
	}
	/**
	 * A pair structure that holds the reader and writer of a given file type.
	 */
	private static final class ReaderWriterPair
	{
		/**
		 * The reader.
		 */
		public final AssetReader<?> reader;
		/**
		 * The writer.
		 */
		public final AssetWriter<?> writer;
		/**
		 * Instantiate a reader-writer pair.
		 * @param reader the reader.
		 * @param writer the writer.
		 */
		public ReaderWriterPair(final AssetReader<?> reader, final AssetWriter<?> writer)
		{
			this.reader = reader;
			this.writer = writer;
		}
	}
}
