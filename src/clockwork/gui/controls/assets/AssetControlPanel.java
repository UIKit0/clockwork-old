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
package clockwork.gui.controls.assets;

import java.util.HashMap;

import clockwork.asset.Asset;
import clockwork.gui.UserInterface;
import clockwork.gui.controls.ControlPanel;

public final class AssetControlPanel
extends ControlPanel<Asset, AssetListView, AssetInputControls>
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 7597769514931106537L;
	/**
	 * Instantiate an AssetControlPanel attached to the user interface and
	 * referencing the collection of assets.
	 */
	public AssetControlPanel
	(
		final UserInterface ui,
		final HashMap<String, Asset> assetMap
	)
	{
		super(ui, "Assets", "Asset controls");
		build(new AssetListView(this, assetMap), new AssetInputControls(this));
	}
}
