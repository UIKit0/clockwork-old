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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import clockwork.asset.Asset;
import clockwork.graphics.Texture;
import clockwork.gui.component.GUIButton;
import clockwork.gui.presentation.AbstractListView;
import clockwork.system.Services;
import clockwork.types.ConcurrentList;

/**
 * TODO Document me.
 */
public final class AssetListView extends AbstractListView<Asset, HashMap<String, Asset>>
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 3287982745350287447L;
	/**
	 * A button to trigger asset previews, if possible.
	 */
	private final GUIButton previewButton = new GUIButton("Preview");
	/**
	 * TODO Explain this better.
	 * Instantiate an AssetListView attached to a control panel.
	 */
	protected AssetListView
	(
		final AssetControlPanel parent,
		final HashMap<String, Asset> assetMap
	)
	{
		super(parent, "Assets", new ConcurrentList<Asset>(assetMap.values()));
		super.list.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(final MouseEvent e)
			{
				previewButton.setEnabled(getSelectedItem() instanceof Texture);
			}
		});
		previewButton.setEnabled(false);
		previewButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				final Asset asset = getSelectedItem();
				if (asset instanceof Texture)
					Services.Graphics.showImage((Texture)asset);
			}
		});
		addButton(previewButton);
		showAddButton(false);
		showDeleteButton(false);
	}
}
