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
package clockwork.gui.presentation;

import java.awt.BorderLayout;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import clockwork.gui.component.GUITreeModel;
import clockwork.gui.controls.ControlPanel;


/**
 * The AbstractTreeView is a presentation that displays entries in the form of a tree.
 */
public abstract class AbstractTreeView<Entry>
extends AbstractPresentation<Entry>
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 5977774462354301869L;
	/**
	 * The JTree that display's the root node's tree structure.
	 */
	private final JTree treeView;
	/**
	 * Instantiate a named AbstractTreeView attached to a control panel.
	 */
	protected AbstractTreeView
	(
		final ControlPanel<Entry, ?, ?> parent,
		final String title,
		final GUITreeModel treeModel
	)
	{
		super(parent, title);

		this.treeView = new JTree(treeModel);
		treeModel.setJTree(treeView);
		this.treeView.setVisibleRowCount(8);
		this.treeView.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.treeView.setSelectionRow(0);
		this.treeView.addTreeSelectionListener(new TreeSelectionListener()
		{
			@Override
			public void valueChanged(final TreeSelectionEvent e)
			{
				onItemSelected();
			}
		});

		final JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);
		panel.add(new JLabel(getTitle()), BorderLayout.NORTH);
		panel.add(new JScrollPane(this.treeView), BorderLayout.CENTER);
		add(new JScrollPane(panel), BorderLayout.CENTER);
	}
	/**
	 * Initialise the tree view.
	 */
	@Override
	public void initialise()
	{
		// Set the currently selected item as the root node.
		final TreePath path = treeView.getPathForRow(1);
		if (path != null)
			treeView.setSelectionPath(path);
	}
	/**
	 * Return the currently selected item.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final Entry getSelectedItem()
	{
		final DefaultMutableTreeNode node =
		(DefaultMutableTreeNode)treeView.getLastSelectedPathComponent();

		if (node != null)
			return (Entry)node.getUserObject();
		else
			return null;
	}
	/**
	 * Select the next item in the presentation.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final Entry selectNextItem()
	{
		Entry selectedItem = null;

		final DefaultMutableTreeNode selectedNode =
		(DefaultMutableTreeNode)treeView.getLastSelectedPathComponent();

		// Get the next element. If no sibling nodes can be found, return the parent node.
		DefaultMutableTreeNode nextSelectedNode = selectedNode.getNextSibling();
		if (nextSelectedNode == null)
		{
			nextSelectedNode = selectedNode.getPreviousSibling();
			if (nextSelectedNode == null)
				nextSelectedNode = (DefaultMutableTreeNode)selectedNode.getParent();
		}

		// If the candidate node is not null, select it.
		if (nextSelectedNode != null)
		{
			treeView.setSelectionPath(new TreePath(nextSelectedNode.getPath()));
			selectedItem = (Entry)nextSelectedNode.getUserObject();
		}
		return selectedItem;
	}
	/**
	 * @see JTree#setToggleClickCount(int)
	 */
	public final void setToggleClickCount(final int count)
	{
		treeView.setToggleClickCount(count);
	}
	/**
	 * Add a mouse listener.
	 */
	@Override
	public final void addMouseListener(final MouseListener listener)
	{
		if (listener != null)
			treeView.addMouseListener(listener);
	}
}
