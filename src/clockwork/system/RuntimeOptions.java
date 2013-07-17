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

import clockwork.physics.lighting.LightEmitter;


/**
 * The application's runtime options.
 */
public final class RuntimeOptions
{
	public static boolean EnableDebugInformation = true;
	public static boolean EnableProfiler = false;
	/**
	 * Rendering options.
	 */
	public static boolean RenderSurfaceNormals = false;
	public static boolean EnableLighting = true;
	public static boolean RenderAABBs = false;
	/**
	 * Visible surface determination.
	 */
	public static boolean EnableViewFrustumCulling = false;
	public static boolean EnableBackfaceCulling = true;
	public static boolean EnableOcclusionCulling = false;
	public static boolean EnableClipping = false;
	/**
	 * Per-vertex operations.
	 */
	public static boolean EnableVIEW = true;
	public static boolean EnableMODEL = true;
	public static boolean EnableNORMAL = true;
	public static boolean EnablePROJECTION = true;
	/**
	 * Per-fragment operations.
	 */
	public static boolean EnableScissorTest = false;
	public static boolean EnableAlphaTest = false;
	public static boolean EnableStencilTest = false;
	public static boolean EnableDepthTest = true;
	/**
	 * Lighting options.
	 */
	public static LightEmitter.ReflectionModel LightReflectionModel =
	LightEmitter.ReflectionModel.Phong;

	/**
	 * Framebuffer operations.
	 */
	public static boolean EnableAntialiasing = false;
	public static boolean EnableAmbientOcclusion = false;
	public static boolean EnableScreenSpaceAmbientOcclusion = false;
	public static boolean EnableBlending = false;
}
