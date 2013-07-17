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
package clockwork.asset.io.reader.model3d;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import clockwork.asset.AssetManager;
import clockwork.asset.io.reader.AssetReader;
import clockwork.graphics.Material;
import clockwork.graphics.Mesh;
import clockwork.graphics.Model3D;
import clockwork.graphics.PolygonFace;
import clockwork.graphics.Vertex;
import clockwork.types.math.Vector3f;

/**
 * A simple 3D model reader for Wavefront OBJ files based on <a href="http://www.martinreddy.net/gfx/3d/OBJ.spec" target="_blank">this specification</a>
 */
public class Model3DReaderOBJ extends Model3DReader
{
	/**
	 * @see AssetReader#parse(FileInputStream)
	 */
	@Override
	protected Model3D parse(final FileInputStream stream) throws IOException
	{
		final Material material = new Material();
		final ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		final ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
		final ArrayList<PolygonFace> faces = new ArrayList<PolygonFace>();
		final ArrayList<Point2D.Double> uvcoords = new ArrayList<Point2D.Double>();
		BufferedReader materialFile = null;

		final BufferedReader input = new BufferedReader(new InputStreamReader(stream));
		if (input != null)
		{
			do
			{
				final String line = input.readLine();
				if (line == null)
				{
					if (materialFile != null)
						materialFile.close();

					break;
				}
				else
				{
					// Skip comments and empty lines.
					if (line.length() == 0 || line.charAt(0) == '#')
						continue;
					else
					{
						final StringTokenizer st = new StringTokenizer(line, " \t");
						final String command = st.nextToken();
						if (command.equals("v"))
						{
							final float x = Float.parseFloat(st.nextToken());
							final float y = Float.parseFloat(st.nextToken());
							final float z = Float.parseFloat(st.nextToken());
							vertices.add(new Vertex(x, y, z));
						}
						else if (command.equals("vt"))
						{
							final double u = Double.parseDouble(st.nextToken());
							final double v = 1.0 - Double.parseDouble(st.nextToken()); //TODO Explain why we reverse this.
							uvcoords.add(new Point2D.Double(u, v));
						}
						else if (command.equals("vn"))
						{
							final float i = Float.parseFloat(st.nextToken());
							final float j = Float.parseFloat(st.nextToken());
							final float k = Float.parseFloat(st.nextToken());
							final Vector3f normal = new Vector3f(i, j, k);
							normal.normalise();
							normals.add(normal);
						}
						else if (command.equals("f"))
						{
							final Vector<Vertex> faceVertices = new Vector<Vertex>();
							final Vector<Point2D.Double> faceUVs = new Vector<Point2D.Double>();
							// Get the face's vertices, normals and UV coordinates.
							while (st.hasMoreTokens())
							{
								final String s = st.nextToken();
								final StringTokenizer st2 = new StringTokenizer(s, "/ \t");

								// Get the vertex index which we'll use to retrieve the vertex.
								// Note that indices can be negative, in which case we need to perform
								// a relative lookup. Also note that index values start at 1 in OBJ files
								// while they start at 0 internally.
								int index = Integer.parseInt(st2.nextToken()) - 1;
								if (index < 0)
									index += vertices.size() + 1;
								final Vertex vertex = vertices.get(index);
								faceVertices.add(vertex);

								// If we have a vt index, parse the vertex's UV texture coordinates.
								if (st2.hasMoreTokens())
								{
									index = Integer.parseInt(st2.nextToken()) - 1;
									if (index < 0)
										index += uvcoords.size() + 1;

									faceUVs.add(uvcoords.get(index));
								}
								// If we have a vn index, parse the vertex's normal.
								if (st2.hasMoreTokens())
								{
									index = Integer.parseInt(st2.nextToken()) - 1;
									if (index < 0)
										index += normals.size() + 1;
									final Vector3f normal = normals.get(index);
									vertex.normal.setIJK(normal);
								}
							}
							// Create the polygon face. If we have more than 3 vertices, triangulate them.
							for (int i = 0; i < faceVertices.size() - 2; ++i)
							{
								final Vertex triangulatedVertices[] = new Vertex[]
								{
									faceVertices.get(0),
									faceVertices.get(i + 1),
									faceVertices.get(i + 2)
								};
								final Point2D.Double triangulatedUVs[] = new Point2D.Double[]
								{
									faceUVs.get(0),
									faceUVs.get(i + 1),
									faceUVs.get(i + 2)
								};
								faces.add(new PolygonFace(triangulatedVertices, triangulatedUVs));
							}
						}
						else if (command.equals("mtllib"))
						{
							materialFile =
							new BufferedReader(new FileReader(getFolderPath() + st.nextToken()));
						}
						else if (command.equals("usemtl"))
							parseMaterial(materialFile, st.nextToken(), material);
						else
							;//System.out.println("Model3DReaderOBJ::Warning - '" + line + "' could not be parsed.");
					}
				}
			} while (true);
		}
		return new Model3D(new Mesh(vertices, faces), material);
	}
	/**
	 * TODO Explain me.
	 */
	private void parseMaterial
	(
		final BufferedReader input,
		final String materialName,
		final Material material
	)
	{
		if (input != null)
		{
			boolean processMaterial = false;
			do
			{
				String line;
				try
				{
					line = input.readLine();
					if (line == null)
						break;
					else
					{
						// Skip comments and empty lines.
						if (line.length() == 0 || line.charAt(0) == '#')
							continue;
						else
						{
							final StringTokenizer st = new StringTokenizer(line, " \t");
							final String command = st.nextToken();
							if (command.equals("newmtl"))
							{
								// We've reached a new material. We can exit the loop.
								if (processMaterial)
									break;
								else if (materialName.equals(st.nextToken()))
									processMaterial = true;
							}
							else if (processMaterial)
							{
								if (command.equalsIgnoreCase("Ka"))
								{
									material.Ka.r = Double.parseDouble(st.nextToken());
									material.Ka.g = Double.parseDouble(st.nextToken());
									material.Ka.b = Double.parseDouble(st.nextToken());
								}
								else if (command.equalsIgnoreCase("Kd"))
								{
									material.Kd.r = Double.parseDouble(st.nextToken());
									material.Kd.g = Double.parseDouble(st.nextToken());
									material.Kd.b = Double.parseDouble(st.nextToken());
								}
								else if (command.equalsIgnoreCase("Ks"))
								{
									material.Ks.r = Double.parseDouble(st.nextToken());
									material.Ks.g = Double.parseDouble(st.nextToken());
									material.Ks.b = Double.parseDouble(st.nextToken());
								}
								else if (command.equalsIgnoreCase("Tr"))
									material.transparency = Double.parseDouble(st.nextToken());
								else if (command.equalsIgnoreCase("Ns"))
									material.shininess = Double.parseDouble(st.nextToken());
								else if (command.equalsIgnoreCase("map_Ka"))
									material.ambient = AssetManager.LoadTexture(getFolderPath() + st.nextToken());
								else if (command.equalsIgnoreCase("map_Kd"))
									material.diffuse = AssetManager.LoadTexture(getFolderPath() + st.nextToken());
								else if (command.equalsIgnoreCase("map_Ks"))
									material.specular = AssetManager.LoadTexture(getFolderPath() + st.nextToken());
								else if (command.equalsIgnoreCase("bump"))
									material.bump = AssetManager.LoadTexture(getFolderPath() + st.nextToken());
								else if (command.equalsIgnoreCase("disp"))
									material.displacement = AssetManager.LoadTexture(getFolderPath() + st.nextToken());
							}
						}
					}
				}
				catch (IOException e){}
			} while (true);
		}
	}
}
