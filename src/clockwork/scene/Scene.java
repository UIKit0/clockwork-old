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
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import clockwork.graphics.camera.Camera;
import clockwork.graphics.renderer.RenderContext;
import clockwork.graphics.renderer.RenderProcessingQueue;
import clockwork.physics.body.RigidBody;
import clockwork.physics.body.SuzanneRigidBody;
import clockwork.physics.lighting.Light;
import clockwork.system.Services;
import clockwork.types.Matrix4Stack;
import clockwork.types.Task;
import clockwork.types.math.Matrix4;
import clockwork.types.math.Point3f;

/**
 * A Scene is a unique collection of SceneEntities. A Scene implements the
 * Observer Pattern to allow it to be observed by its SceneViewers. This will
 * allow us to trigger SceneViewer methods only when the Scene is updated.
 */
public final class Scene extends Observable
{
	/**
	 * Create and initialise the scene's unique instance.
	 */
	private static Scene UNIQUE_INSTANCE = null;
	/**
	 * The scene graph.
	 */
	private final SceneGraph graph = new SceneGraph();
	/**
	 * The currently active viewer.
	 */
	private SceneViewer viewer = null;
	/**
	 * The default constructor. Since the Scene is a singleton (unique) object, no
	 * other instances of this class should be created. As such, a private constructor
	 * prevents instantiation of this class outside of its own scope.
	 */
	private Scene(){}
	/**
	 * Return the unique instance of the Scene.
	 */
	public static Scene getUniqueInstance()
	{
		if (UNIQUE_INSTANCE == null)
		{
			UNIQUE_INSTANCE = new Scene();
			UNIQUE_INSTANCE.initialise();
		}
		return UNIQUE_INSTANCE;
	}
	/**
	 * Return the scene graph.
	 */
	public SceneGraph getGraph()
	{
		return graph;
	}
	/**
	 * Set the scene's title.
	 * @param title the title to set.
	 */
	public void setTitle(final String title)
	{
		graph.setTitle(title);
	}
	/**
	 * Return the scene's title.
	 */
	public String getTitle()
	{
		return graph.getTitle();
	}
	/**
	 * Add an entity to the scene. This method exists only because the generic method
	 * AbstractListView#addEntry can't cast its parameter to a specific type. This is
	 * a helper function to remedy that problem, despite the fact that it's not very
	 * elegant in regards to polymorphism.
	 * @param entity the entity to add to the scene.
	 */
	public void add(final SceneEntity entity)
	{
		if (entity != null)
		{
			if (entity instanceof RigidBody)        graph.addNode((RigidBody)entity);
			else if (entity instanceof Light)       graph.addNode((Light)entity);
			else if (entity instanceof SceneViewer) add((SceneViewer)entity);
		}
	}
	/**
	 * Set the scene viewer.
	 * @param viewer the scene viewer to set.
	 */
	public void setViewer(final SceneViewer viewer)
	{
		if (viewer != null)
		{
			// Deactivate the previous viewer and set the new one.
			if (this.viewer != null)
				this.viewer.setActive(false);
			this.viewer = viewer;
		}
	}
	/**
	 * Return the currently selected SceneViewer.
	 */
	public SceneViewer getViewer()
	{
		return viewer;
	}
	/**
	 * Does the scene have a viewer?
	 */
	public boolean hasViewer()
	{
		return viewer != null;
	}
	/**
	 * Remove the scene viewer.
	 */
	public void removeViewer()
	{
		if (viewer != null)
			viewer = null;
	}
	/**
	 * Add a viewer to the scene. If no viewer was previously selected, the added viewer
	 * will become the default.
	 * @param viewer the viewer to add to the scene.
	 */
	private void add(final SceneViewer viewer)
	{
		graph.addNode(viewer);
		if (this.viewer == null)
			viewer.setActive(true);
	}
	/**
	 * Initialise the scene. This method can be modified to the user's
	 * desire but it should preferably add at least add one viewer.
	 */
	private void initialise()
	{
		setTitle("World");
		add(new Camera("Camera 1"));
		add(new SuzanneRigidBody());
//		add(new Light("Light 1"));
	}
	/**
	 * Clear the elements in the scene.
	 */
	public void clear()
	{
		removeViewer();
		graph.getRoot().removeChildren();
	}
	/**
	 * Save the scene to a JSON file.
	 */
	public void save()
	{
		final String filename = getTitle();
		final String fullExtension = ".clockwork.json";
		File file = new File(filename + fullExtension);
		if (file.isFile())
		{
			// Find a file that doesn't exist.
			for (int i = 1;;)
			{
				file = new File(filename + "." + (i++) + fullExtension);
				if (!file.isFile())
					break;
			}
		}
		// Write the JSON file.
		try
		{
//FIXME			Scene.READWRITE_LOCK.lock();
			final boolean ok = graph.toJSON(file);
//FIXME			Scene.READWRITE_LOCK.unlock();
			JOptionPane.showMessageDialog
			(
				new JFrame(),
				(ok ? "Succesfully wrote '" : "Error writing '") + filename + fullExtension + "'",
				ok ? "Info" : "Error",
				ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE
			);
		}
		catch (final Exception e){}
	}

	/**
	 * Convert the scene to a set of Renderable objects. This method traverses the
	 * scene's entities while adding anything that can be rendered to a
	 * render queue. The traversal also keeps track of any transformations that
	 * should be applied to the Renderable object.
	 *
	 * @param context the RenderContext containing the render queue and transformation matrices.
	 */
	public void render(final RenderContext context)
	{
		if (context != null)
		{
			final Matrix4 VIEW = context.getVIEW();
			final Matrix4Stack stack = context.getMatrixStack();
			final RenderProcessingQueue queue = context.getRenderProcessingQueue();

			// TODO Explain why this is done.
			// Push the matrix that will translate objects to the origin.
			stack.push(Matrix4.translate(Point3f.negative(viewer.position)));

			// Traverse the scene graph while converting nodes into processing elements, e.g.
			// Renderables then adding them to the render queue. When the queue is built, apply
			// the renderer.
			graph.buildRenderProcessingQueue(VIEW, stack, queue);
			context.applyRenderer();

			// Remove the transformation matrix, or the stack will keep growing.
			stack.pop();
		}
	}
	/**
	 * Run a task in the background that will update the scene.
	 */
	public static void RunUpdateTask()
	{
		final float dt = 0;
		Services.Update(dt);

		new Task<Void, Void>()
		{
			@Override
			protected Void doInBackground() throws Exception
			{
/**
//FIXME				Framebuffer.READWRITE_LOCK.lock();
				GUI_PROGRESS_BAR.setFeedbackInformation("Busy...");
				setProgress(0);
				final float dt = 0;
				final long T0 = System.currentTimeMillis();
				setProgress(50);
				Services.Update(dt);
				setProgress(100);
				final long T1 = System.currentTimeMillis();
				Debug.MillisecondsPerFrame.set((int)(T1 - T0));
				GUI_PROGRESS_BAR.setFeedbackInformation("");
//FIXME				Framebuffer.READWRITE_LOCK.unlock();
**/
				return null;
			}
			/**
			 * When the task is done, repaint the display.
			 * Note that this is safe since it runs in the EDT.
			 */
			@Override
			protected void done()
			{
				GUI_PROGRESS_BAR.setValue(GUI_PROGRESS_BAR.getMinimum());
//FIXME				Framebuffer.READWRITE_LOCK.lock();
				Services.Graphics.flip();
//FIXME				Framebuffer.READWRITE_LOCK.unlock();
			}
		}.execute();
	}
	/**
	 * Notify the Scene's observers that it has been changed. This is a proxy method
	 * that makes the inherited method public, but does not modify its behavior.
	 */
	@Override
	public void setChanged()
	{
		super.setChanged();
	}
}
