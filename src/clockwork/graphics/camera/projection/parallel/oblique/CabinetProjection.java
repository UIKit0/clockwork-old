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
package clockwork.graphics.camera.projection.parallel.oblique;

import clockwork.graphics.camera.projection.Projection;


// There are two kinds of oblique projections: Cavalier and Cabinet. The differences
// between the two are the length of the projection of the unit vector Z, and the angle
// of this projection with the X axis.
//
// The length will be denoted as L; L_cavaliere = 1 and L_cabinet = 0.5.
// The angle will be denoted as alpha; alpha_cavaliere = 45 and alpha_cabinet = 63.4.

public class CabinetProjection extends AbstractObliqueProjection
{
	public CabinetProjection()
	{
		super(Projection.Type.Cabinet, 1.0, 63.4);
	}
}