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
package clockwork.gui.controls.scene;

import clockwork.graphics.Appearance;
import clockwork.gui.controls.InputControls;
import clockwork.gui.views.GUIColorRGBView;
import clockwork.gui.views.GUIFrustumView;
import clockwork.gui.views.GUIObjectView;
import clockwork.gui.views.GUIOrientationView;
import clockwork.gui.views.GUIPositionView;
import clockwork.gui.views.GUIScaleView;
import clockwork.gui.views.info.GUIAppearanceInfoView;
import clockwork.gui.views.info.GUIBodyInfoView;
import clockwork.gui.views.info.GUILightInfoView;
import clockwork.gui.views.info.GUISceneViewerInfoView;
import clockwork.physics.body.RigidBody;
import clockwork.physics.lighting.Light;
import clockwork.physics.lighting.LightEmitter;
import clockwork.scene.SceneEntityProperty;
import clockwork.scene.SceneGraph;
import clockwork.scene.SceneObject;
import clockwork.scene.SceneViewer;

public final class SceneInputControls extends InputControls<SceneGraph.Node>
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -6608888101435800761L;
	/**
	 * Input components used to modify a specific property of a given entity.
	 */
	private final GUIObjectView<?> views[][];
	/**
	 * Index of view subgroups.
	 */
	private final int OBJECT_VIEWS = 0;
	private final int BODY_VIEWS = 1;
	private final int VIEWER_VIEWS = 2;
	private final int LIGHT_VIEWS = 3;
	private final int APPEARANCE_VIEWS = 4;
	/**
	 * Instantiate a SceneInputControls attached to a given control panel.
	 */
	public SceneInputControls(final SceneControlPanel parent)
	{
		views = new GUIObjectView[][]
		{
			// Views shared by most scene objects.
			new GUIObjectView[]
			{
				new GUIPositionView(parent),
				new GUIOrientationView(parent),
				new GUIScaleView(parent),
				new GUIColorRGBView(parent),
			},
			// Views that modify rigid bodies.
			new GUIObjectView[]
			{
				new GUIBodyInfoView(parent),
			},
			// Views that modify scene viewers.
			new GUIObjectView[]
			{
				new GUISceneViewerInfoView(parent),
				new GUIFrustumView(parent),
			},
			// Views that modify lighting.
			new GUIObjectView[]
			{
				new GUILightInfoView(parent),
				new GUIColorRGBView(parent),
			},
			// Views that modify an object's appearance
			new GUIObjectView[]
			{
				new GUIAppearanceInfoView(parent)
			}
		};
	}
	/**
	 * Build the views based on a specific type of object.
	 */
	private void buildViews(final Class<?> cls)
	{
		removeInputComponents();

		final GUIObjectView<?> genericViews[] = views[0];
		GUIObjectView<?> specificViews[] = null;

		// The number of generic views to add.
		int M = 0;

		if (SceneObject.class.isAssignableFrom(cls))
		{
			// If it is a scene object then by default, add the generic views.
			M = genericViews.length;

			if (RigidBody.class.isAssignableFrom(cls))
				specificViews = views[BODY_VIEWS];
			else if (SceneViewer.class.isAssignableFrom(cls))
			{
				specificViews = views[VIEWER_VIEWS];
				--M; // We can't scale viewers so we ignore the scaling controls.
			}
			else if (Light.class.isAssignableFrom(cls))
				--M; // We can't scale lights so we ignore the scaling controls.
		}
		// If it is a scene property, then only add specific views.
		else if (SceneEntityProperty.class.isAssignableFrom(cls))
		{
			if (LightEmitter.class.isAssignableFrom(cls))
				specificViews = views[LIGHT_VIEWS];
			else if (Appearance.class.isAssignableFrom(cls))
				specificViews = views[APPEARANCE_VIEWS];
		}

		// The number of specific views to add.
		final int N = specificViews != null ? specificViews.length : 0;

		// First, add the specific view that gives information on the object, then add
		// the generic views and the remaining specific views.
		if  (N > 0)                 addInputComponent(specificViews[0]);
		for (int i = 0; i < M; ++i) addInputComponent(genericViews[i]);
		for (int i = 1; i < N; ++i) addInputComponent(specificViews[i]);
	}
	/**
	 * Display the values of a given node.
	 * @param input the node to display.
	 */
	@Override
	public void setValue(final SceneGraph.Node input)
	{
		if (input != null)
		{
			buildViews(input.getClass());

			if (input instanceof SceneObject)
			{
				final SceneObject object = (SceneObject)input;

				((GUIPositionView)views[OBJECT_VIEWS][0]).write(object.getPosition());
				((GUIOrientationView)views[OBJECT_VIEWS][1]).write(object.getOrientation());
				((GUIScaleView)views[OBJECT_VIEWS][2]).write(object.getScale());

				if (input instanceof RigidBody)
				{
					// TODO Complete me.
					final RigidBody body = (RigidBody)input;
					((GUIBodyInfoView)views[BODY_VIEWS][0]).write(body);
				}
				else if (input instanceof SceneViewer)
				{
					final SceneViewer viewer = (SceneViewer)input;
					((GUISceneViewerInfoView)views[VIEWER_VIEWS][0]).write(viewer);
					((GUIFrustumView)views[VIEWER_VIEWS][1]).write(viewer.getFrustum());
				}
			}
			else if (input instanceof SceneEntityProperty<?>)
			{
				if (input instanceof LightEmitter)
				{
					final LightEmitter light = (LightEmitter)input;
					((GUILightInfoView)views[LIGHT_VIEWS][0]).write(light);
					((GUIColorRGBView)views[LIGHT_VIEWS][1]).write(light.getColor());
				}
				else if (input instanceof Appearance)
				{
					final Appearance appearance = (Appearance)input;
					((GUIAppearanceInfoView)views[APPEARANCE_VIEWS][0]).write(appearance);
				}
			}
		}
		else
			setValue();
	}
	/**
	 * @see InputControls#setValue.
	 */
	@Override
	public void setValue()
	{
		// TODO Auto-generated method stub

	}
	/**
	 * Return the value of the selected node.
	 * @param output the location where the value will be stored.
	 */
	@Override
	public void getValue(final SceneGraph.Node output)
	{
		if (output != null)
		{
			if (output instanceof SceneObject)
			{
				final SceneObject object = (SceneObject)output;
				object.setPosition(((GUIPositionView)views[OBJECT_VIEWS][0]).read());
				object.setOrientation(((GUIOrientationView)views[OBJECT_VIEWS][1]).read());
				object.setScale(((GUIScaleView)views[OBJECT_VIEWS][2]).read());

				if (output instanceof RigidBody)
				{
					// TODO Complete me.
					final RigidBody body = (RigidBody)output;
					((GUIBodyInfoView)views[BODY_VIEWS][0]).read(body);
				}
				else if (output instanceof SceneViewer)
				{
					final SceneViewer viewer = (SceneViewer)output;
					((GUISceneViewerInfoView)views[VIEWER_VIEWS][0]).read(viewer);
					viewer.setFrustum(((GUIFrustumView)views[VIEWER_VIEWS][1]).read());
				}
			}
			else if (output instanceof SceneEntityProperty<?>)
			{
				if (output instanceof LightEmitter)
				{
					final LightEmitter light = (LightEmitter)output;
					((GUILightInfoView)views[LIGHT_VIEWS][0]).read(light);
					light.setColor(((GUIColorRGBView)views[LIGHT_VIEWS][1]).read());
				}
			}
		}
	}
}
