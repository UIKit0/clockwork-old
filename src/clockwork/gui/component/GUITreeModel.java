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
package clockwork.gui.component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class GUITreeModel extends DefaultTreeModel
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 5125410643239030307L;
	/**
	 * The JTree that represents this tree model.
	 */
	private JTree jtree = null;
	/**
	 * Instantiate a tree model with a root node.
	 */
	public GUITreeModel(final TreeNode root)
	{
		super(root);
	}
	/**
	 * Set the reference to the JTree that represents this model.
	 */
	public void setJTree(final JTree jtree)
	{
		this.jtree = jtree;
	}
	/**
	 * Expand a tree node.
	 * @param node the node to expand.
	 */
	public void expandNode(final DefaultMutableTreeNode node)
	{
		if (jtree != null && node != null)
			jtree.expandPath(new TreePath(node.getPath()));
	}
}
