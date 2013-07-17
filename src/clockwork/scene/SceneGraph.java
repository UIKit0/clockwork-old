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
package clockwork.scene;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.swing.tree.DefaultMutableTreeNode;

import org.javatuples.Pair;

import clockwork.graphics.renderer.RenderProcessingQueue;
import clockwork.gui.component.GUITreeModel;
import clockwork.types.Matrix4Stack;
import clockwork.types.math.Matrix4;

/**
 * A scene graph allows us to define spatial relationships between objects in the scene.
 */
public final class SceneGraph
{
	/**
	 * The graph's root node.
	 */
	private final Root root = new Root();
	/**
	 * A DefaultTreeModel is used to visually represent the graph. Essentially, it triggers
	 * the appropriate events when it has been updated, so that the GUI can be updated as well.
	 * This object should be instantiated by the Root node since it holds the root TreeNode object.
	 */
	protected static GUITreeModel GUITreeModel = null;
	/**
	 * Set the scene graph's title.
	 * @param title the title to set.
	 */
	public void setTitle(final String title)
	{
		root.setName(title);
	}
	/**
	 * Return the scene graph's title.
	 */
	public String getTitle()
	{
		return root.getName();
	}
	/**
	 * Return the graph's root node.
	 */
	public Root getRoot()
	{
		return root;
	}
	/**
	 * Add a node to the graph. The added node's parent will be the graph's root node.
	 * @param node the node to add.
	 */
	public void addNode(final Node node)
	{
		root.addChild(node);
	}
	/**
	 * Remove a node from the graph's root.
	 * @param node the node to remove.
	 */
	public void removeNode(final Node node)
	{
		root.removeChild(node);
	}
	/**
	 * Add a node to a specific branch node.
	 * @param node the node to add.
	 * @param parent the node's parent node.
	 */
	public void addNode(final Node node, final Branch parent)
	{
		parent.addChild(node);
	}
	/**
	 * Remove a node from another node contained in the graph.
	 * @param node the node to remove.
	 * @param parent the node's parent.
	 */
	public void removeNode(final Node node, final Branch parent)
	{
		parent.removeChild(node);
	}
	/**
	 * Update the scene.
	 * @see clockwork.scene.SceneGraph.Root
	 */
	public void update(final float dt)
	{
		root.update(dt);
	}
	/**
	 * Build a render processing queue from the scene entities. This will then be passed onto a
	 * renderer that will process the contents of the queue.
	 * @param VIEW the view transformation matrix.
	 * @param stack a matrix stack holding cumulative model transformations.
	 * @param queue the render queue.
	 */
	public void buildRenderProcessingQueue
	(
		final Matrix4 VIEW,
		final Matrix4Stack stack,
		final RenderProcessingQueue queue
	)
	{
		root.buildRenderProcessingQueue(VIEW, stack, queue);
	}
	/**
	 * Return the scene graph's GUI TreeModel.
	 */
	public final GUITreeModel getGUITreeModel()
	{
		return GUITreeModel;
	}



	/**
	 * All nodes of a scene graph must be scene entities.
	 */
	public static abstract class Node extends SceneEntity
	{
		/**
		 * A DefaultMutableTreeNode is used by Swing to visually represent the graph node.
		 */
		protected final DefaultMutableTreeNode GUITreeNode = new DefaultMutableTreeNode(this);
		/**
		 * The node's parent node.
		 */
		protected Branch parent = null;
		/**
		 * Is this node's name editable?
		 */
		private boolean isNameEditable = true;
		/**
		 * Has this node been pruned from the graph?
		 */
		private boolean isPruned = false;
		/**
		 * Test whether a given node is a child of this node.
		 */
		public abstract boolean isChild(final Node node);
		/**
		 * Update the node's data.
		 * @param dt the time since the last update was made, a.k.a "delta time".
		 */
		public abstract void update(final float dt);
		/**
		 * TODO Make this abstract and implement it for each node.
		 * Return the node's memory footprint in mebibytes (1 MiB = 1024KB).
		 */
//		public abstract float getMemoryFootprint();
		public float getMemoryFootprint()
		{
			return -99999.99f;
		}
		/**
		 * Convert the node into an element that can be processed by a renderer and add it to the
		 * render processing queue.
		 * @param VIEW the VIEW transformation matrix.
		 * @param stack the current matrix stack.
		 * @param queue the render processing queue where the result will be added.
		 */
		public abstract void buildRenderProcessingQueue
		(
			final Matrix4 VIEW,
			final Matrix4Stack stack,
			final RenderProcessingQueue queue
		);
		/**
		 * Instantiate a node with a given name.
		 * @param name the node's name.
		 */
		protected Node(final String name)
		{
			super(name);
		}
		/**
		 * Return the node's DefaultMutableTreeNode.
		 */
		public final DefaultMutableTreeNode getGUITreeNode()
		{
			return GUITreeNode;
		}
		/**
		 * Can we modify this node's name?
		 */
		public final boolean isNameEditable()
		{
			return isNameEditable;
		}
		/**
		 * Toggle the modification of this node's name.
		 */
		public final void setNameEditable(final boolean editable)
		{
			this.isNameEditable = editable;
		}
		/**
		 * Set the name of the node and update the view.
		 */
		@Override
		public final void setName(final String name)
		{
			if (isNameEditable)
			{
				super.setName(name);
				SceneGraph.GUITreeModel.nodeChanged(GUITreeNode);
			}
		}
		/**
		 * Set the node's parent. A null value is accepted and is the equivalent
		 * of removing the node's parent.
		 * @param parent the parent node to set.
		 */
		protected void setParent(final Branch parent)
		{
			this.parent = parent;
		}
		/**
		 * Set the pruned state of the node.
		 * @param pruned true if the node is pruned, false otherwise.
		 */
		public final void setPruned(final boolean pruned)
		{
			isPruned = pruned;
			SceneGraph.GUITreeModel.nodeChanged(GUITreeNode);
		}
		/**
		 * Return true if the node is pruned, false otherwise.
		 */
		public final boolean isPruned()
		{
			return isPruned;
		}
		/**
		 * Dispose of the node and its children.
		 */
		public void dispose()
		{
			if (parent != null)
				parent.removeChild(this);
		}
	}



	/**
	 * A Leaf is a childless node.
	 */
	public static abstract class Leaf extends Node
	{
		/**
		 * Instantiate a leaf node with a given name.
		 * @param name the leaf node's name.
		 */
		protected Leaf(String name)
		{
			super(name);
			GUITreeNode.setAllowsChildren(false);
		}
		/**
		 * Leaf nodes cannot have any children therefore they do not contain other nodes.
		 */
		@Override
		public final boolean isChild(final Node node)
		{
			return false;
		}
	}

	/**
	 * A Branch is a node with children.
	 */
	public static abstract class Branch extends Node
	{
		/**
		 * The branch's child nodes. This is a hashmap to provide unique child nodes based on
		 * node identifiers. The hashmap's key is a <Node name, Node identifier> pair since
		 * node names can be used as queries but are mutable so they cannot guarantee unicity.
		 */
		protected final Map<Pair<String, UUID>, Node> children = new HashMap<Pair<String, UUID>, Node>();
		/**
		 * Instantiate a branch node with a given name.
		 */
		protected Branch(String name)
		{
			super(name);
		}
		/**
		 * Check whether the given node is part of the branch node's children.
		 */
		@Override
		public final boolean isChild(final Node node)
		{
			if (node != null)
			{
				for (final Node child : getChildren())
					if (child == node)
						return true;
			}
			return false;
		}
		/**
		 * Add a child node.
		 * @param node the child node to add.
		 */
		public final void addChild(final Node child)
		{
			if (child != null)
			{
				child.setParent(this);
				children.put(new Pair<String, UUID>(child.getName(), child.getIdentifier()), child);
				GUITreeModel.insertNodeInto(child.getGUITreeNode(), GUITreeNode, 0);
				GUITreeModel.expandNode(GUITreeNode);
			}
		}
		/**
		 * Remove a child node.
		 * @param node the child node to remove.
		 */
		public final void removeChild(final Node child)
		{
			if (child != null)
			{
				child.setParent(null);
				children.remove(child);

				// Remove the node from the presentation.
				GUITreeModel.removeNodeFromParent(child.getGUITreeNode());
			}
		}
		/**
		 * TODO
		 * Remove a branch's child nodes.
		 */
		public void removeChildren()
		{
			System.err.println("Implement Branch#removeChildren");
//			for (final Node child : children)
//			{
//
//			}
		}
		/**
		 * Does this branch node have any children?
		 */
		public final boolean hasChildren()
		{
			return !children.isEmpty();
		}
		/**
		 * Return the branch node's children.
		 */
		public Collection<Node> getChildren()
		{
			return children.values();
		}
		/**
		 * Find and return a child node based on its name. Since nodes can have identical
		 * names, this will return the first node with the queried name.
		 * @param name the child node's name.
		 */
		public Node getChild(final String name)
		{
			if (name != null)
			{
				for (Pair<String, UUID> key : children.keySet())
					if (key.getValue0().equals(name))
						return children.get(key);
			}
			return null;
		}
		/**
		 * Find and return a child node based on its unique identifier.
		 * @param identifier the child node's unique identifier.
		 */
		public Node getChild(final UUID identifier)
		{
			if (identifier != null)
			{
				for (Pair<String, UUID> key : children.keySet())
					if (key.getValue1().equals(identifier))
						return children.get(key);
			}
			return null;
		}
		/**
		 * Find and return a child node based on its name and identifier.
		 * @param name the child node's name.
		 * @param identifier the child node's identifier.
		 */
		public Node getChild(final String name, final UUID identifier)
		{
			if (name != null && identifier != null)
				return children.get(new Pair<String, UUID>(name, identifier));
			else
				return null;
		}
	}



	/**
	 * A Root node is an instantiable branch node. It can only exist once for each
	 * scene graph.
	 */
	public final class Root extends Branch
	{
		/**
		 * Instantiate a Root node. This class cannot be inherited but a protected
		 * constructor prevents objects out of this package's scope from instantiating it.
		 */
		protected Root()
		{
			super("Scene");
			SceneGraph.GUITreeModel = new GUITreeModel(GUITreeNode);
		}
		/**
		 * Update the root node by updating each of its child nodes.
		 * @param dt the time since the last call to update, a.k.a. "delta time".
		 */
		@Override
		public void update(float dt)
		{
			if (hasChildren())
			{
				for (final Node child : getChildren())
				{
					if (!child.isPruned())
						child.update(dt);
				}
			}
		}
		/**
		 * Render each of the node's children.
		 */
		@Override
		public void buildRenderProcessingQueue
		(
			final Matrix4 VIEW,
			final Matrix4Stack stack,
			final RenderProcessingQueue queue
		)
		{
			if (VIEW != null && stack != null && queue != null && hasChildren())
			{
				for (final Node child : getChildren())
				{
					if (!child.isPruned())
						child.buildRenderProcessingQueue(VIEW, stack, queue);
				}
			}
		}
	}
	/**
	 * TODO Implement me.
	 * Convert the graph to the JSON format and store it.
	 * @param file the file where the graph will be stored.
	 */
	public boolean toJSON(File file)
	{
		System.err.println("Implement SceneGraph#toJSON");
		return false;
	}
}
