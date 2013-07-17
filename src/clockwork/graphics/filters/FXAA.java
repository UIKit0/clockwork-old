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
package clockwork.graphics.filters;

import java.awt.Dimension;
import java.awt.geom.Point2D;

import clockwork.graphics.Framebuffer;
import clockwork.graphics.color.ColorRGBA;
import clockwork.system.RuntimeOptions;

/**
 * An implementation of the FXAA 3.11 PC algorithm by Timothy Lottes.
 * FXAA is a local contrast adaptive directional edge blur.
 * @see http://developer.download.nvidia.com/assets/gamedev/files/sdk/11/FXAA_WhitePaper.pdf
 */
public class FXAA
{
	private enum QUALITY
	{
		PRESET_10,
		PRESET_11,
		PRESET_12,
		PRESET_13,
		PRESET_14,
		PRESET_15,

		PRESET_20,
		PRESET_21,
		PRESET_22,
		PRESET_23,
		PRESET_24,
		PRESET_25,
		PRESET_26,
		PRESET_27,
		PRESET_28,
		PRESET_29,

		PRESET_39, // Extreme quality.
	};

	/**
	 * TODO Explain me better.
	 * Quality variables.
	 */
	private static float FXAA_QUALITY__PS  = 0;
	private static float FXAA_QUALITY__P0  = 0;
	private static float FXAA_QUALITY__P1  = 0;
	private static float FXAA_QUALITY__P2  = 0;
	private static float FXAA_QUALITY__P3  = 0;
	private static float FXAA_QUALITY__P4  = 0;
	private static float FXAA_QUALITY__P5  = 0;
	private static float FXAA_QUALITY__P6  = 0;
	private static float FXAA_QUALITY__P7  = 0;
	private static float FXAA_QUALITY__P8  = 0;
	private static float FXAA_QUALITY__P9  = 0;
	private static float FXAA_QUALITY__P10 = 0;
	private static float FXAA_QUALITY__P11 = 0;
	private static float FXAA_QUALITY__P12 = 0;




	/**
	 * Choose the amount of sub-pixel aliasing removal. This can affect sharpness.
	 * 1.00 - upper limit (softer).
	 * 0.75 - default amount of filtering.
	 * 0.50 - lower limit (sharper, less sub-pixel aliasing removal).
	 * 0.25 - almost off.
	 * 0.00 - completely off.
	 */
	private static final float FXAA_SUBPIX = 0.75f;
	/**
	 * The minimum amount of local contrast required to apply the algorithm.
	 * The lower the value, the more the smoothing.
	 * 0.333 - too little (faster).
	 * 0.250 - low quality.
	 * 0.166 - default.
	 * 0.125 - high quality.
	 * 0.063 - overkill (slower).
	 */
	private static float FXAA_EDGE_THRESHOLD = 0.166f;
	/**
	 * This value trims the algorithm from processing darks. The lower the value,
	 * the more the smoothing.
	 * 0.0833 - upper limit (start of visible unfiltered edges).
	 * 0.0625 - visible limit.
	 * 0.0312 - high quality.
	 */
	private static float FXAA_EDGE_THRESHOLD_MIN = 0.0625f;
	/**
	 * Control removal of sub-pixel aliasing. Possible values are:
	 * 1/2 - low removal.
	 * 1/3 - medium removal.
	 * 1/4 - default removal.
	 * 1/8 - high removal.
	 * 0   - complete removal.
	 */
	private static final float FXAA_SUBPIX_TRIM = 1.0f / 4.0f;
	/**
	 * Insure that fine detail is not completely removed. This partly overrides FXAA_SUBPIX_TRIM.
	 * The possible values are:
	 * 3/4 - default amount of filtering.
	 * 7/8 - high amount of filtering.
	 * 1   - no capping of filtering.
	 */
	private static final float FXAA_SUBPIX_CAP = 3.0f / 4.0f;
	/**
	 * TODO Find out what this is.
	 */
	private final static float FXAA_SUBPIX_TRIM_SCALE = 1.0f;
	/**
	 * Explicitly show smoothed edges.
	 */
	private static final boolean FXAA_SHOW_SMOOTH_EDGES = true;



	private static final Point2D.Float FXAA_RCP_FRAME = new Point2D.Float(0, 0);


	static
	{
		setQuality(QUALITY.PRESET_10);
	}


	private static float FXAA_REDUCE_MIN = 1.0f / 128.0f;
	private static float FXAA_REDUCE_MUL = 1.0f / 8.0f;
	private static float FXAA_SPAN_MAX = 8.0f;

	// https://github.com/demoscenepassivist/SocialCoding/blob/master/code_demos_jogamp/shaders/fxaa.fs
	public static void apply(final Framebuffer framebuffer)
	{
		if (!RuntimeOptions.EnableAntialiasing)
			return;

		final Dimension resolution = framebuffer.getResolution();
		final int w = resolution.width;
		final int h = resolution.height;

		// Store the framebuffer's inverse resolution.
		FXAA_RCP_FRAME.x = 1.0f;//1.0f / w;
		FXAA_RCP_FRAME.y = 1.0f;//1.0f / h;

		final int pixels[] = framebuffer.getColorBuffer();
		final Texture texture = new Texture(pixels, w, h);

		for (int i = 0; i < texture.height; ++i)
		{
			for (int j = 0; j < texture.width; ++j)
			{
				// Offset and XY coordinates of the current pixel.
				final int offset = (i * w) + j;
				final Point2D.Float posM = new Point2D.Float(j, i);
				final Point2D.Float tmp = new Point2D.Float(0, 0);

				tmp.x = (posM.x - 1.0f) * FXAA_RCP_FRAME.x;
				tmp.y = (posM.y - 1.0f) * FXAA_RCP_FRAME.y;
				final ColorRGBA rgbNW = texture2D(texture, tmp);
				tmp.x = (posM.x + 1.0f) * FXAA_RCP_FRAME.x;
				tmp.y = (posM.y - 1.0f) * FXAA_RCP_FRAME.y;
				final ColorRGBA rgbNE = texture2D(texture, tmp);
				tmp.x = (posM.x - 1.0f) * FXAA_RCP_FRAME.x;
				tmp.y = (posM.y + 1.0f) * FXAA_RCP_FRAME.y;
				final ColorRGBA rgbSW = texture2D(texture, tmp);
				tmp.x = (posM.x + 1.0f) * FXAA_RCP_FRAME.x;
				tmp.y = (posM.y + 1.0f) * FXAA_RCP_FRAME.y;
				final ColorRGBA rgbSE = texture2D(texture, tmp);
				tmp.x = (posM.x) * FXAA_RCP_FRAME.x;
				tmp.y = (posM.y) * FXAA_RCP_FRAME.y;
				final ColorRGBA rgbM  = texture2D(texture, tmp);


			   float lumaNW = luma(rgbNW);
			   float lumaNE = luma(rgbNE);
			   float lumaSW = luma(rgbSW);
			   float lumaSE = luma(rgbSE);
			   float lumaM  = luma(rgbM);
			   float lumaMin = Math.min(lumaM, Math.min(Math.min(lumaNW, lumaNE), Math.min(lumaSW, lumaSE)));
			   float lumaMax = Math.max(lumaM, Math.max(Math.max(lumaNW, lumaNE), Math.max(lumaSW, lumaSE)));

			   final Point2D.Float dir = new Point2D.Float
			   (
			   	-((lumaNW + lumaNE) - (lumaSW + lumaSE)),
			   	((lumaNW + lumaSW) - (lumaNE + lumaSE))
			   );
			   final float dirReduce =
			   Math.max((lumaNW + lumaNE + lumaSW + lumaSE) * (0.25f * FXAA_REDUCE_MUL),FXAA_REDUCE_MIN);

			   final float rcpDirMin = 1.0f/(Math.min(Math.abs(dir.x), Math.abs(dir.y)) + dirReduce);


			   dir.x = Math.min(FXAA_SPAN_MAX, Math.max(-FXAA_SPAN_MAX, dir.x * rcpDirMin)) * FXAA_RCP_FRAME.x;
			   dir.y = Math.min(FXAA_SPAN_MAX, Math.max(-FXAA_SPAN_MAX, dir.y * rcpDirMin)) * FXAA_RCP_FRAME.y;

			   final Point2D.Float pos0 = new Point2D.Float
			   (
			   	posM.x * FXAA_RCP_FRAME.x + dir.x * (1.0f/3.0f - 0.5f),
			   	posM.y * FXAA_RCP_FRAME.y + dir.y * (1.0f/3.0f - 0.5f)
			   );
			   final Point2D.Float pos1 = new Point2D.Float
			   (
			   	posM.x * FXAA_RCP_FRAME.x + dir.x * (2.0f/3.0f - 0.5f),
			   	posM.y * FXAA_RCP_FRAME.y + dir.y * (2.0f/3.0f - 0.5f)
			   );
			   final ColorRGBA rgbA =
			   texture2D(texture, pos0).add(texture2D(texture, pos1)).multiply(0.5f);


			   final Point2D.Float pos2 = new Point2D.Float
			   (
			   	posM.x * FXAA_RCP_FRAME.x + dir.x * (-0.5f),
			   	posM.y * FXAA_RCP_FRAME.y + dir.y * (-0.5f)
			   );
			   final Point2D.Float pos3 = new Point2D.Float
			   (
			   	posM.x * FXAA_RCP_FRAME.x + dir.x * (0.5f),
			   	posM.y * FXAA_RCP_FRAME.y + dir.y * (0.5f)
			   );
			   final ColorRGBA rgbB =
			   rgbA.multiply(0.5f).add(texture2D(texture, pos2).add(texture2D(texture, pos3)).multiply(0.25f));

			   float lumaB = luma(rgbB);
			   if ((lumaB < lumaMin) || (lumaB > lumaMax))
			   	pixels[offset] = ColorRGBA.merge(rgbA.r, rgbA.g, rgbA.b, 1.0f);
			   else
			   	pixels[offset] = ColorRGBA.merge(rgbB.r, rgbB.g, rgbB.b, 1.0f);
			}
		}
	}

	static ColorRGBA texture2D(final Texture tex, final Point2D.Float position)
	{
		final int x = Math.round(position.x);
		final int y = Math.round(position.y);
		int argb = 0;

		if (x > 0 && x < tex.width && y > 0 && y < tex.height)
			argb = tex.pixels[x + (y * tex.width)];

		return ColorRGBA.split(argb);
	}




	public static void applyX(final Framebuffer framebuffer)
	{
		final Dimension resolution = framebuffer.getResolution();
		final int w = resolution.width;
		final int h = resolution.height;

		// Store the inverse resolution.
		FXAA_RCP_FRAME.x = 1.0f / w;
		FXAA_RCP_FRAME.y = 1.0f / h;

		final int pixels[] = framebuffer.getColorBuffer();
		final Texture texture = new Texture(pixels, w, h);

		for (int i = 0; i < h; ++i)
		{
			for (int j = 0; j < w; ++j)
			{
				final int offset = (i * w) + j;

				final Point2D.Float posM = new Point2D.Float(j, i);
				final ColorRGBA neighborhood[] = texture.getNeighborhood(j, i);

				final ColorRGBA rgbaM  = neighborhood[0];
				final ColorRGBA rgbaN  = neighborhood[1];
				final ColorRGBA rgbaNE = neighborhood[2];
				final ColorRGBA rgbaE  = neighborhood[3];
				final ColorRGBA rgbaSE = neighborhood[4];
				final ColorRGBA rgbaS  = neighborhood[5];
				final ColorRGBA rgbaSW = neighborhood[6];
				final ColorRGBA rgbaW  = neighborhood[7];
				final ColorRGBA rgbaNW = neighborhood[8];

				// Convert pixels into estimated luminance values.
				final float lumaM = (float)rgbaM.a;
//				final float lumaM = luma(pixelM);
				float lumaN       = luma(rgbaN);
				final float lumaE = luma(rgbaE);
				float lumaS       = luma(rgbaS);
				final float lumaW = luma(rgbaW);

				final float maxSM = Math.max(lumaS, lumaM);
				final float minSM = Math.min(lumaS, lumaM);
				final float maxESM = Math.max(lumaE, maxSM);
				final float minESM = Math.min(lumaE, minSM);
				final float maxWN = Math.max(lumaN, lumaW);
				final float minWN = Math.min(lumaN, lumaW);
				final float rangeMax = Math.max(maxWN, maxESM);
				final float rangeMin = Math.min(minWN, minESM);
				final float rangeMaxScaled = rangeMax * FXAA_EDGE_THRESHOLD;
				final float range = rangeMax - rangeMin;
				final float rangeMaxClamped = Math.max(FXAA_EDGE_THRESHOLD_MIN, rangeMaxScaled);
				final boolean earlyExit = range < rangeMaxClamped;
				if (earlyExit)
				{
					framebuffer.discard(offset);
					continue;
				}



				if (true) continue;

				if (FXAA_SHOW_SMOOTH_EDGES)
				{
//					pixels[offset] = 0xffff0000;
					continue;
				}
/**
//				final ColorRGBA pixelNE = new ColorRGBA(offsetNE >= 0 ? pixels[offsetNE] : 0xff000000);
//				final ColorRGBA pixelSE = new ColorRGBA(offsetSE >= 0 ? pixels[offsetSE] : 0xff000000);
//				final ColorRGBA pixelSW = new ColorRGBA(offsetSW >= 0 ? pixels[offsetSW] : 0xff000000);
//				final ColorRGBA pixelNW = new ColorRGBA(offsetNW >= 0 ? pixels[offsetNW] : 0xff000000);
				final float lumaNE = luma(rgbaNE);
				final float lumaSE = luma(rgbaSE);
				final float lumaSW = luma(rgbaSW);
				final float lumaNW = luma(rgbaNW);

				final float lumaNS = lumaN + lumaS;
				final float lumaWE = lumaW + lumaE;
				final float subpixRcpRange = 1.0f / range;
				final float subpixNSWE = lumaNS + lumaWE;
				final float edgeHorz1 = (-2.0f * lumaM) + lumaNS;
				final float edgeVert1 = (-2.0f * lumaM) + lumaWE;

				final float lumaNESE = lumaNE + lumaSE;
				final float lumaNWNE = lumaNW + lumaNE;
				final float edgeHorz2 = (-2.0f * lumaE) + lumaNESE;
				final float edgeVert2 = (-2.0f * lumaN) + lumaNWNE;

				final float lumaNWSW = lumaNW + lumaSW;
				final float lumaSWSE = lumaSW + lumaSE;
				final float edgeHorz4 = (Math.abs(edgeHorz1) * 2.0f) + Math.abs(edgeHorz2);
				final float edgeVert4 = (Math.abs(edgeVert1) * 2.0f) + Math.abs(edgeVert2);
				final float edgeHorz3 = (-2.0f * lumaW) + lumaNWSW;
				final float edgeVert3 = (-2.0f * lumaS) + lumaSWSE;
				final float edgeHorz = Math.abs(edgeHorz3) + edgeHorz4;
				final float edgeVert = Math.abs(edgeVert3) + edgeVert4;

				final float subpixNWSWNESE = lumaNWSW + lumaNESE;
				float lengthSign = FXAA_RCP_FRAME.x;
				final boolean horzSpan = edgeHorz >= edgeVert;
				final float subpixA = subpixNSWE * 2.0f + subpixNWSWNESE;

				if (!horzSpan) lumaN = lumaW;
				if (!horzSpan) lumaS = lumaE;
				if (horzSpan)  lengthSign = FXAA_RCP_FRAME.y;
				final float subpixB = (subpixA * (1.0f/12.0f)) - lumaM;

				final float gradientN = lumaN - lumaM;
				final float gradientS = lumaS - lumaM;
				float lumaNN = lumaN + lumaM;
				final float lumaSS = lumaS + lumaM;
				final boolean pairN = Math.abs(gradientN) >= Math.abs(gradientS);
				final float gradient = Math.max(Math.abs(gradientN), Math.abs(gradientS));
				if(pairN) lengthSign = -lengthSign;
				final float subpixC = saturate(Math.abs(subpixB) * subpixRcpRange);

				final Point2D.Float posB = new Point2D.Float
				(
					posM.x + ((!horzSpan) ? lengthSign * 0.5f : 0),
					posM.y + (( horzSpan) ? lengthSign * 0.5f : 0)
				);
				final Point2D.Float offNP = new Point2D.Float
				(
					(!horzSpan) ? 0.0f : FXAA_RCP_FRAME.x,
					( horzSpan) ? 0.0f : FXAA_RCP_FRAME.y
				);

				final Point2D.Float posN = new Point2D.Float
				(
					posB.x - offNP.x * FXAA_QUALITY__P0,
					posB.y - offNP.y * FXAA_QUALITY__P0
				);
				final Point2D.Float posP = new Point2D.Float
				(
					posB.x + offNP.x * FXAA_QUALITY__P0,
					posB.y + offNP.y * FXAA_QUALITY__P0
				);
				final float subpixD = ((-2.0f) * subpixC) + 3.0f;
				float lumaEndN = luma(FxaaTexTop(texture, posN));
				final float subpixE = subpixC * subpixC;
				float lumaEndP = luma(FxaaTexTop(texture, posP));

				if (!pairN) lumaNN = lumaSS;
				final float gradientScaled = gradient * 0.25f;
				final float lumaMM = lumaM - lumaNN * 0.5f;
				final float subpixF = subpixD * subpixE;
				final boolean lumaMLTZero = lumaMM < 0.0;

				lumaEndN -= lumaNN * 0.5f;
				lumaEndP -= lumaNN * 0.5f;
				boolean doneN = Math.abs(lumaEndN) >= gradientScaled;
				boolean doneP = Math.abs(lumaEndP) >= gradientScaled;
				if (!doneN) posN.x -= offNP.x * FXAA_QUALITY__P1;
				if (!doneN) posN.y -= offNP.y * FXAA_QUALITY__P1;
				boolean doneNP = (!doneN) || (!doneP);
				if (!doneP) posP.x += offNP.x * FXAA_QUALITY__P1;
				if (!doneP) posP.y += offNP.y * FXAA_QUALITY__P1;
				if (doneNP)
				{
					final int pixelEndN = FxaaTexTop(texture, posN);
					final int pixelEndP = FxaaTexTop(texture, posP);

					if (!doneN) lumaEndN = luma(pixelEndN);
					if (!doneP) lumaEndP = luma(pixelEndP);
					if (!doneN) lumaEndN = lumaEndN - lumaNN * 0.5f;
					if (!doneP) lumaEndP = lumaEndP - lumaNN * 0.5f;
					doneN = Math.abs(lumaEndN) >= gradientScaled;
					doneP = Math.abs(lumaEndP) >= gradientScaled;
					if (!doneN) posN.x -= offNP.x * FXAA_QUALITY__P2;
					if (!doneN) posN.y -= offNP.y * FXAA_QUALITY__P2;
					doneNP = (!doneN) || (!doneP);
					if (!doneP) posP.x += offNP.x * FXAA_QUALITY__P2;
					if (!doneP) posP.y += offNP.y * FXAA_QUALITY__P2;

					if (FXAA_QUALITY__PS > 3)
					{
						if (!doneN) lumaEndN = luma(pixelEndN);
						if (!doneP) lumaEndP = luma(pixelEndP);
						if (!doneN) lumaEndN = lumaEndN - lumaNN * 0.5f;
						if (!doneP) lumaEndP = lumaEndP - lumaNN * 0.5f;
						doneN = Math.abs(lumaEndN) >= gradientScaled;
						doneP = Math.abs(lumaEndP) >= gradientScaled;
						if(!doneN) posN.x -= offNP.x * FXAA_QUALITY__P3;
						if(!doneN) posN.y -= offNP.y * FXAA_QUALITY__P3;
						doneNP = (!doneN) || (!doneP);
						if(!doneP) posP.x += offNP.x * FXAA_QUALITY__P3;
						if(!doneP) posP.y += offNP.y * FXAA_QUALITY__P3;

						if (FXAA_QUALITY__PS > 4)
						{
							if (!doneN) lumaEndN = luma(pixelEndN);
							if (!doneP) lumaEndP = luma(pixelEndP);
							if (!doneN) lumaEndN = lumaEndN - lumaNN * 0.5f;
							if (!doneP) lumaEndP = lumaEndP - lumaNN * 0.5f;
							doneN = Math.abs(lumaEndN) >= gradientScaled;
							doneP = Math.abs(lumaEndP) >= gradientScaled;
							if (!doneN) posN.x -= offNP.x * FXAA_QUALITY__P4;
							if (!doneN) posN.y -= offNP.y * FXAA_QUALITY__P4;
							doneNP = (!doneN) || (!doneP);
							if (!doneP) posP.x += offNP.x * FXAA_QUALITY__P4;
							if (!doneP) posP.y += offNP.y * FXAA_QUALITY__P4;

							if (FXAA_QUALITY__PS > 5)
							{
								if (!doneN) lumaEndN = luma(pixelEndN);
								if (!doneP) lumaEndP = luma(pixelEndP);
								if (!doneN) lumaEndN = lumaEndN - lumaNN * 0.5f;
								if (!doneP) lumaEndP = lumaEndP - lumaNN * 0.5f;
								doneN = Math.abs(lumaEndN) >= gradientScaled;
								doneP = Math.abs(lumaEndP) >= gradientScaled;
								if (!doneN) posN.x -= offNP.x * FXAA_QUALITY__P5;
								if (!doneN) posN.y -= offNP.y * FXAA_QUALITY__P5;
								doneNP = (!doneN) || (!doneP);
								if (!doneP) posP.x += offNP.x * FXAA_QUALITY__P5;
								if (!doneP) posP.y += offNP.y * FXAA_QUALITY__P5;

								if (FXAA_QUALITY__PS > 6)
								{
									if (!doneN) lumaEndN = luma(pixelEndN);
									if (!doneP) lumaEndP = luma(pixelEndP);
									if(!doneN) lumaEndN = lumaEndN - lumaNN * 0.5f;
									if(!doneP) lumaEndP = lumaEndP - lumaNN * 0.5f;
									doneN = Math.abs(lumaEndN) >= gradientScaled;
									doneP = Math.abs(lumaEndP) >= gradientScaled;
									if(!doneN) posN.x -= offNP.x * FXAA_QUALITY__P6;
									if(!doneN) posN.y -= offNP.y * FXAA_QUALITY__P6;
									doneNP = (!doneN) || (!doneP);
									if(!doneP) posP.x += offNP.x * FXAA_QUALITY__P6;
									if(!doneP) posP.y += offNP.y * FXAA_QUALITY__P6;

									if (FXAA_QUALITY__PS > 7)
									{
										if (!doneN) lumaEndN = luma(pixelEndN);
										if (!doneP) lumaEndP = luma(pixelEndP);
										if (!doneN) lumaEndN = lumaEndN - lumaNN * 0.5f;
										if (!doneP) lumaEndP = lumaEndP - lumaNN * 0.5f;
										doneN = Math.abs(lumaEndN) >= gradientScaled;
										doneP = Math.abs(lumaEndP) >= gradientScaled;
										if (!doneN) posN.x -= offNP.x * FXAA_QUALITY__P7;
										if (!doneN) posN.y -= offNP.y * FXAA_QUALITY__P7;
										doneNP = (!doneN) || (!doneP);
										if (!doneP) posP.x += offNP.x * FXAA_QUALITY__P7;
										if (!doneP) posP.y += offNP.y * FXAA_QUALITY__P7;

										if (FXAA_QUALITY__PS > 8)
										{
											if (!doneN) lumaEndN = luma(pixelEndN);
											if (!doneP) lumaEndP = luma(pixelEndP);
											if (!doneN) lumaEndN = lumaEndN - lumaNN * 0.5f;
											if (!doneP) lumaEndP = lumaEndP - lumaNN * 0.5f;
											doneN = Math.abs(lumaEndN) >= gradientScaled;
											doneP = Math.abs(lumaEndP) >= gradientScaled;
											if (!doneN) posN.x -= offNP.x * FXAA_QUALITY__P8;
											if (!doneN) posN.y -= offNP.y * FXAA_QUALITY__P8;
											doneNP = (!doneN) || (!doneP);
											if (!doneP) posP.x += offNP.x * FXAA_QUALITY__P8;
											if (!doneP) posP.y += offNP.y * FXAA_QUALITY__P8;

											if(FXAA_QUALITY__PS > 9)
											{
												if (!doneN) lumaEndN = luma(pixelEndN);
												if (!doneP) lumaEndP = luma(pixelEndP);
												if (!doneN) lumaEndN = lumaEndN - lumaNN * 0.5f;
												if (!doneP) lumaEndP = lumaEndP - lumaNN * 0.5f;
												doneN = Math.abs(lumaEndN) >= gradientScaled;
												doneP = Math.abs(lumaEndP) >= gradientScaled;
												if (!doneN) posN.x -= offNP.x * FXAA_QUALITY__P9;
												if (!doneN) posN.y -= offNP.y * FXAA_QUALITY__P9;
												doneNP = (!doneN) || (!doneP);
												if(!doneP) posP.x += offNP.x * FXAA_QUALITY__P9;
												if(!doneP) posP.y += offNP.y * FXAA_QUALITY__P9;

												if (FXAA_QUALITY__PS > 10)
												{
													if (!doneN) lumaEndN = luma(pixelEndN);
													if (!doneP) lumaEndP = luma(pixelEndP);
													if (!doneN) lumaEndN = lumaEndN - lumaNN * 0.5f;
													if (!doneP) lumaEndP = lumaEndP - lumaNN * 0.5f;
													doneN = Math.abs(lumaEndN) >= gradientScaled;
													doneP = Math.abs(lumaEndP) >= gradientScaled;
													if (!doneN) posN.x -= offNP.x * FXAA_QUALITY__P10;
													if (!doneN) posN.y -= offNP.y * FXAA_QUALITY__P10;
													doneNP = (!doneN) || (!doneP);
													if (!doneP) posP.x += offNP.x * FXAA_QUALITY__P10;
													if (!doneP) posP.y += offNP.y * FXAA_QUALITY__P10;

													if (FXAA_QUALITY__PS > 11)
													{
														if (!doneN) lumaEndN = luma(pixelEndN);
														if (!doneP) lumaEndP = luma(pixelEndP);
														if (!doneN) lumaEndN = lumaEndN - lumaNN * 0.5f;
														if (!doneP) lumaEndP = lumaEndP - lumaNN * 0.5f;
														doneN = Math.abs(lumaEndN) >= gradientScaled;
														doneP = Math.abs(lumaEndP) >= gradientScaled;
														if (!doneN) posN.x -= offNP.x * FXAA_QUALITY__P11;
														if (!doneN) posN.y -= offNP.y * FXAA_QUALITY__P11;
														doneNP = (!doneN) || (!doneP);
														if (!doneP) posP.x += offNP.x * FXAA_QUALITY__P11;
														if (!doneP) posP.y += offNP.y * FXAA_QUALITY__P11;

														if(FXAA_QUALITY__PS > 12)
														{
															if (!doneN) lumaEndN = luma(pixelEndN);
															if (!doneP) lumaEndP = luma(pixelEndP);
															if (!doneN) lumaEndN = lumaEndN - lumaNN * 0.5f;
															if (!doneP) lumaEndP = lumaEndP - lumaNN * 0.5f;
															doneN = Math.abs(lumaEndN) >= gradientScaled;
															doneP = Math.abs(lumaEndP) >= gradientScaled;
															if (!doneN) posN.x -= offNP.x * FXAA_QUALITY__P12;
															if (!doneN) posN.y -= offNP.y * FXAA_QUALITY__P12;
															doneNP = (!doneN) || (!doneP);
															if (!doneP) posP.x += offNP.x * FXAA_QUALITY__P12;
															if (!doneP) posP.y += offNP.y * FXAA_QUALITY__P12;
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
				float dstN = posM.x - posN.x;
				float dstP = posP.x - posM.x;
				if (!horzSpan) dstN = posM.y - posN.y;
				if (!horzSpan) dstP = posP.y - posM.y;

				final boolean goodSpanN = (lumaEndN < 0.0) != lumaMLTZero;
				final float spanLength = (dstP + dstN);
				final boolean goodSpanP = (lumaEndP < 0.0) != lumaMLTZero;
				final float spanLengthRcp = 1.0f/spanLength;

				final boolean directionN = dstN < dstP;
				final float dst = Math.min(dstN, dstP);
				final boolean goodSpan = directionN ? goodSpanN : goodSpanP;
				final float subpixG = subpixF * subpixF;
				final float pixelOffset = (dst * (-spanLengthRcp)) + 0.5f;
				final float subpixH = subpixG * FXAA_SUBPIX;

				final float pixelOffsetGood = goodSpan ? pixelOffset : 0.0f;
				final float pixelOffsetSubpix = Math.max(pixelOffsetGood, subpixH);
				if (!horzSpan) posM.x += pixelOffsetSubpix * lengthSign;
				if ( horzSpan) posM.y += pixelOffsetSubpix * lengthSign;
**/
				rgbaM.a = lumaM;
				pixels[offset] = rgbaM.merge();
			}
		}
	}
	/**
	 * Clamp a given value to a [min, max] range.
	 * @param x the value to clamp.
	 * @param min the variable's possible minimum value.
	 * @param max the variable's possible maximum value.
	 */
	private static float clamp(final float x, final float min, final float max)
	{
		return x < min ? min : x > max ? max : x;
	}
	/**
	 * Saturate a value, i.e. clamp it to the [0.0, 1.0] range.
	 * @param value the value to saturate.
	 */
	private static float saturate(final float value)
	{
		return clamp(value, 0.0f, 1.0f);
	}
	/**
	 * Calculate the luminance (brightness) a.k.a luma of an RGBA color in the YUV model.
	 * Y = 0.299R + 0.587G + 0.114B.
	 * @param color an RGBA color.
	 */
	private static float luma(final ColorRGBA color)
	{
		return (float)((0.299f * color.r) + (0.587f * color.g) + (0.114f * color.b));
	}
	private static float luma(final int color)
	{
		return luma(ColorRGBA.split(color));
	}



	private static int FxaaTexTop(final Texture tex, final Point2D.Float point)
	{
		final int x = Math.round(point.x) * 0;
		final int y = Math.round(point.x) * 0;

		if (x >= 0 && x < tex.width && y >= 0 && y < tex.height)
			return tex.pixels[(y * tex.width) + x];
		else
			return 0xff000000;
	}


	private static class Texture
	{
		public final int pixels[];
		public final int width;
		public final int height;

		public Texture(final int pixels[], final int width, final int height)
		{
			this.pixels = pixels;
			this.width = width;
			this.height = height;
		}

		public ColorRGBA[] getNeighborhood(final int x, final int y)
		{
			// Get the offsets of the current pixel and its neighbors.
			final int w = width;
			final int h = height;

			final int offset   = (y * w) + x;
			final int offsetN  = y > 0                  ? offset - w     : -1;
			final int offsetNE = y > 0 && x < w - 1     ? offset - w + 1 : -1;
			final int offsetE  = x < w - 1              ? offset + 1     : -1;
			final int offsetSE = y < h - 1 && x < w - 1 ? offset + w + 1 : -1;
			final int offsetS  = y < h - 1              ? offset + w     : -1;
			final int offsetSW = y < h - 1 && x > 0     ? offset + w - 1 : -1;
			final int offsetW  = x > 0                  ? offset - 1     : -1;
			final int offsetNW = y > 0 && x > 0         ? offset - w - 1 : -1;

			return new ColorRGBA[]
			{
				new ColorRGBA(pixels[offset]),
				new ColorRGBA(offsetN  >= 0 ? pixels[offsetN]  : 0x00000000),
				new ColorRGBA(offsetNE >= 0 ? pixels[offsetNE] : 0x00000000),
				new ColorRGBA(offsetE  >= 0 ? pixels[offsetE]  : 0x00000000),
				new ColorRGBA(offsetSE >= 0 ? pixels[offsetSE] : 0x00000000),
				new ColorRGBA(offsetS  >= 0 ? pixels[offsetS]  : 0x00000000),
				new ColorRGBA(offsetSW >= 0 ? pixels[offsetSW] : 0x00000000),
				new ColorRGBA(offsetW  >= 0 ? pixels[offsetW]  : 0x00000000),
				new ColorRGBA(offsetNW >= 0 ? pixels[offsetNW] : 0x00000000)
			};
		}
	};







	private static void setQuality(final QUALITY quality)
	{
		switch (quality)
		{
			case PRESET_10:
			{
				FXAA_QUALITY__PS = 3;
				FXAA_QUALITY__P0 = 1.5f;
				FXAA_QUALITY__P1 = 3.0f;
				FXAA_QUALITY__P2 = 12.0f;
				break;
			}
			case PRESET_11:
			{
				FXAA_QUALITY__PS = 4;
				FXAA_QUALITY__P0 = 1.0f;
				FXAA_QUALITY__P1 = 1.5f;
				FXAA_QUALITY__P2 = 3.0f;
				FXAA_QUALITY__P3 = 12.0f;
				break;
			}
			case PRESET_12:
			{
				FXAA_QUALITY__PS = 5;
				FXAA_QUALITY__P0 = 1.0f;
				FXAA_QUALITY__P1 = 1.5f;
				FXAA_QUALITY__P2 = 2.0f;
				FXAA_QUALITY__P3 = 4.0f;
				FXAA_QUALITY__P4 = 12.0f;
				break;
			}
			case PRESET_13:
			{
				FXAA_QUALITY__PS = 6;
				FXAA_QUALITY__P0 = 1.0f;
				FXAA_QUALITY__P1 = 1.5f;
				FXAA_QUALITY__P2 = 2.0f;
				FXAA_QUALITY__P3 = 2.0f;
				FXAA_QUALITY__P4 = 4.0f;
				FXAA_QUALITY__P5 = 12.0f;
				break;
			}
			case PRESET_14:
			{
				FXAA_QUALITY__PS = 7;
				FXAA_QUALITY__P0 = 1.0f;
				FXAA_QUALITY__P1 = 1.5f;
				FXAA_QUALITY__P2 = 2.0f;
				FXAA_QUALITY__P3 = 2.0f;
				FXAA_QUALITY__P4 = 2.0f;
				FXAA_QUALITY__P5 = 4.0f;
				FXAA_QUALITY__P6 = 12.0f;
				break;
			}
			case PRESET_15:
			{
				FXAA_QUALITY__PS = 8;
				FXAA_QUALITY__P0 = 1.0f;
				FXAA_QUALITY__P1 = 1.5f;
				FXAA_QUALITY__P2 = 2.0f;
				FXAA_QUALITY__P3 = 2.0f;
				FXAA_QUALITY__P4 = 2.0f;
				FXAA_QUALITY__P5 = 2.0f;
				FXAA_QUALITY__P6 = 4.0f;
				FXAA_QUALITY__P7 = 12.0f;
				break;
			}
			// Low dither presets.
			case PRESET_20:
			{
				FXAA_QUALITY__PS = 3;
				FXAA_QUALITY__P0 = 1.5f;
				FXAA_QUALITY__P1 = 2.0f;
				FXAA_QUALITY__P2 = 8.0f;
				break;
			}
			case PRESET_21:
			{
				FXAA_QUALITY__PS = 4;
				FXAA_QUALITY__P0 = 1.0f;
				FXAA_QUALITY__P1 = 1.5f;
				FXAA_QUALITY__P2 = 2.0f;
				FXAA_QUALITY__P3 = 8.0f;
				break;
			}
			case PRESET_22:
			{
				FXAA_QUALITY__PS = 5;
				FXAA_QUALITY__P0 = 1.0f;
				FXAA_QUALITY__P1 = 1.5f;
				FXAA_QUALITY__P2 = 2.0f;
				FXAA_QUALITY__P3 = 2.0f;
				FXAA_QUALITY__P4 = 8.0f;
				break;
			}
			case PRESET_23:
			{
				FXAA_QUALITY__PS = 6;
				FXAA_QUALITY__P0 = 1.0f;
				FXAA_QUALITY__P1 = 1.5f;
				FXAA_QUALITY__P2 = 2.0f;
				FXAA_QUALITY__P3 = 2.0f;
				FXAA_QUALITY__P4 = 2.0f;
				FXAA_QUALITY__P5 = 8.0f;
				break;
			}
			case PRESET_24:
			{
				FXAA_QUALITY__PS = 7;
				FXAA_QUALITY__P0 = 1.0f;
				FXAA_QUALITY__P1 = 1.5f;
				FXAA_QUALITY__P2 = 2.0f;
				FXAA_QUALITY__P3 = 2.0f;
				FXAA_QUALITY__P4 = 2.0f;
				FXAA_QUALITY__P5 = 3.0f;
				FXAA_QUALITY__P6 = 8.0f;
				break;
			}
			case PRESET_25:
			{
				FXAA_QUALITY__PS = 8;
				FXAA_QUALITY__P0 = 1.0f;
				FXAA_QUALITY__P1 = 1.5f;
				FXAA_QUALITY__P2 = 2.0f;
				FXAA_QUALITY__P3 = 2.0f;
				FXAA_QUALITY__P4 = 2.0f;
				FXAA_QUALITY__P5 = 2.0f;
				FXAA_QUALITY__P6 = 4.0f;
				FXAA_QUALITY__P7 = 8.0f;
				break;
			}
			case PRESET_26:
			{
				FXAA_QUALITY__PS = 9;
				FXAA_QUALITY__P0 = 1.0f;
				FXAA_QUALITY__P1 = 1.5f;
				FXAA_QUALITY__P2 = 2.0f;
				FXAA_QUALITY__P3 = 2.0f;
				FXAA_QUALITY__P4 = 2.0f;
				FXAA_QUALITY__P5 = 2.0f;
				FXAA_QUALITY__P6 = 2.0f;
				FXAA_QUALITY__P7 = 4.0f;
				FXAA_QUALITY__P8 = 8.0f;
				break;
			}
			case PRESET_27:
			{
				FXAA_QUALITY__PS = 10;
				FXAA_QUALITY__P0 = 1.0f;
				FXAA_QUALITY__P1 = 1.5f;
				FXAA_QUALITY__P2 = 2.0f;
				FXAA_QUALITY__P3 = 2.0f;
				FXAA_QUALITY__P4 = 2.0f;
				FXAA_QUALITY__P5 = 2.0f;
				FXAA_QUALITY__P6 = 2.0f;
				FXAA_QUALITY__P7 = 2.0f;
				FXAA_QUALITY__P8 = 4.0f;
				FXAA_QUALITY__P9 = 8.0f;
				break;
			}
			case PRESET_28:
			{
				FXAA_QUALITY__PS = 11;
				FXAA_QUALITY__P0 = 1.0f;
				FXAA_QUALITY__P1 = 1.5f;
				FXAA_QUALITY__P2 = 2.0f;
				FXAA_QUALITY__P3 = 2.0f;
				FXAA_QUALITY__P4 = 2.0f;
				FXAA_QUALITY__P5 = 2.0f;
				FXAA_QUALITY__P6 = 2.0f;
				FXAA_QUALITY__P7 = 2.0f;
				FXAA_QUALITY__P8 = 2.0f;
				FXAA_QUALITY__P9 = 4.0f;
				FXAA_QUALITY__P10 = 8.0f;
				break;
			}
			case PRESET_29:
			{
				FXAA_QUALITY__PS = 12;
				FXAA_QUALITY__P0 = 1.0f;
				FXAA_QUALITY__P1 = 1.5f;
				FXAA_QUALITY__P2 = 2.0f;
				FXAA_QUALITY__P3 = 2.0f;
				FXAA_QUALITY__P4 = 2.0f;
				FXAA_QUALITY__P5 = 2.0f;
				FXAA_QUALITY__P6 = 2.0f;
				FXAA_QUALITY__P7 = 2.0f;
				FXAA_QUALITY__P8 = 2.0f;
				FXAA_QUALITY__P9 = 2.0f;
				FXAA_QUALITY__P10 = 4.0f;
				FXAA_QUALITY__P11 = 8.0f;
				break;
			}
			// Extreme quality.
			case PRESET_39:
			{
				FXAA_QUALITY__PS = 12;
				FXAA_QUALITY__P0 = 1.0f;
				FXAA_QUALITY__P1 = 1.0f;
				FXAA_QUALITY__P2 = 1.0f;
				FXAA_QUALITY__P3 = 1.0f;
				FXAA_QUALITY__P4 = 1.0f;
				FXAA_QUALITY__P5 = 1.5f;
				FXAA_QUALITY__P6 = 2.0f;
				FXAA_QUALITY__P7 = 2.0f;
				FXAA_QUALITY__P8 = 2.0f;
				FXAA_QUALITY__P9 = 2.0f;
				FXAA_QUALITY__P10 = 4.0f;
				FXAA_QUALITY__P11 = 8.0f;
				break;
			}
		}
	}
}
