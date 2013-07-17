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
package clockwork.gui.controls.runtime;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import clockwork.gui.UserInterface;
import clockwork.gui.controls.ControlPanelInterface;
import clockwork.physics.lighting.LightEmitter;
import clockwork.scene.Scene;
import clockwork.scene.SceneViewer;
import clockwork.system.RuntimeOptions;



public class RuntimeControlPanel extends JPanel implements ControlPanelInterface
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -6832086974971984806L;
	/**
	 * The constructor.
	 */
	public RuntimeControlPanel(final UserInterface ui)
	{
		super(new GridBagLayout());

		// Set the layout's constraints.
		final GridBagConstraints layoutConstraints = new GridBagConstraints();
		layoutConstraints.gridwidth = 0;
//		layoutConstraints.anchor = GridBagConstraints.PAGE_START;
		layoutConstraints.anchor = GridBagConstraints.NORTHWEST;
//		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.fill = GridBagConstraints.HORIZONTAL;
		layoutConstraints.weightx = 1;
		layoutConstraints.weighty = 1;


		final OptionsComponent components[] = new OptionsComponent[]
		{
			new VisibleSurfaceDeterminationOptionsComponent(this),
			new PerVertexOperationOptionsComponent(this),
			new PerFragmentOperationOptionsComponent(this),
//			new AmbientOcclusionOptionsComponent(this),
			new FramebufferOperationsOptionsComponent(this),
			new LightingOptionsComponent(this)
		};
		for (int i = 0; i < components.length; ++i)
		{
			layoutConstraints.gridx = 0;
			layoutConstraints.gridy = i;
			add(components[i], layoutConstraints);
		}
	}


	private final class PerVertexOperationOptionsComponent extends OptionsComponent
	{
		/**
		 * The serial version UID.
		 */
		private static final long serialVersionUID = -6875669792508501400L;
		/**
		 * Per fragment option check boxes.
		 */
		private final JCheckBox checkboxes[] = new JCheckBox[]
		{
			new JCheckBox("Enable VIEW transformation", RuntimeOptions.EnableVIEW),
			new JCheckBox("Enable MODEL transformation", RuntimeOptions.EnableMODEL),
			new JCheckBox("Enable NORMAL transformation", RuntimeOptions.EnableNORMAL),
			new JCheckBox("Enable PROJECTION transformation", RuntimeOptions.EnablePROJECTION)
		};
		/**
		 * Instantiate a PerVertexOperationOptionsComponent attached to the main control panel.
		 */
		protected PerVertexOperationOptionsComponent(final RuntimeControlPanel parent)
		{
			super(parent, "Per-vertex operations");

			for (final JCheckBox checkbox : checkboxes)
			{
				add(checkbox);
				checkbox.addItemListener(this);
			}
		}
		/**
		 * The event handlers.
		 */
		@Override
		public void itemStateChanged(final ItemEvent e)
		{
			final Object source = e.getSource();
			final boolean selected = e.getStateChange() == ItemEvent.SELECTED;

			boolean isVIEWUpdated = false;
			boolean isPROJECTIONUpdated = false;

			if (source == checkboxes[0])
			{
				RuntimeOptions.EnableVIEW = selected;
				isVIEWUpdated = true;
			}
			else if (source == checkboxes[1])
			{
				RuntimeOptions.EnableMODEL = selected;
			}
			else if (source == checkboxes[2])
			{
				RuntimeOptions.EnableNORMAL = selected;
			}
			else if (source == checkboxes[3])
			{
				RuntimeOptions.EnablePROJECTION = selected;
				isPROJECTIONUpdated = true;
			}

			// Update the viewer.
			final SceneViewer viewer = Scene.getUniqueInstance().getViewer();
			if (viewer != null)
			{
				if (isVIEWUpdated)
					viewer.setUpdatedVIEW(true);
				if (isPROJECTIONUpdated)
					viewer.setUpdatedPROJECTION(true);
			}
			super.itemStateChanged(e);
		}
	}







	private final class PerFragmentOperationOptionsComponent extends OptionsComponent
	{
		/**
		 * The serial version UID.
		 */
		private static final long serialVersionUID = 2565545268886870021L;
		/**
		 * Per fragment option check boxes.
		 */
		private final JCheckBox checkboxes[] = new JCheckBox[]
		{
			new JCheckBox("Enable Scissor Test", RuntimeOptions.EnableScissorTest),
			new JCheckBox("Enable Alpha Test", RuntimeOptions.EnableAlphaTest),
			new JCheckBox("Enable Stencil Test", RuntimeOptions.EnableStencilTest),
			new JCheckBox("Enable Depth Test", RuntimeOptions.EnableDepthTest)
		};
		/**
		 * Instantiate a PerFragmentOperationOptionsComponent attached to the main control panel.
		 */
		protected PerFragmentOperationOptionsComponent(final RuntimeControlPanel parent)
		{
			super(parent, "Per-fragment operations");

			for (final JCheckBox checkbox : checkboxes)
			{
				add(checkbox);
				checkbox.addItemListener(this);
			}
		}
		/**
		 * The event handlers.
		 */
		@Override
		public void itemStateChanged(final ItemEvent e)
		{
			final Object source = e.getSource();
			final boolean selected = e.getStateChange() == ItemEvent.SELECTED;

			if (source == checkboxes[0])      RuntimeOptions.EnableScissorTest = selected;
			else if (source == checkboxes[1]) RuntimeOptions.EnableAlphaTest = selected;
			else if (source == checkboxes[2]) RuntimeOptions.EnableStencilTest = selected;
			else if (source == checkboxes[3]) RuntimeOptions.EnableDepthTest = selected;

			super.itemStateChanged(e);
		}
	}













	private final class VisibleSurfaceDeterminationOptionsComponent extends OptionsComponent
	{
		/**
		 * The serial version UID.
		 */
		private static final long serialVersionUID = -2810863863902218217L;
		/**
		 * Check boxes.
		 */
		private final JCheckBox checkboxes[] = new JCheckBox[]
		{
			new JCheckBox("Render 3D Bounding Boxes", RuntimeOptions.RenderAABBs),
			new JCheckBox("Enable View Frustum Culling", RuntimeOptions.EnableViewFrustumCulling),
			new JCheckBox("Enable Clipping", RuntimeOptions.EnableClipping),
			new JCheckBox("Enable Backface Culling", RuntimeOptions.EnableBackfaceCulling),
			new JCheckBox("Enable Occlusion Culling", RuntimeOptions.EnableOcclusionCulling)
		};
		/**
		 * The default constructor.
		 */
		public VisibleSurfaceDeterminationOptionsComponent(final RuntimeControlPanel parent)
		{
			super(parent, "Visible Surface Determination");

			for (final JCheckBox checkbox : checkboxes)
			{
				add(checkbox);
				checkbox.addItemListener(this);
			}
		}
		/**
		 * The event handlers.
		 */
		@Override
		public void itemStateChanged(final ItemEvent e)
		{
			final Object source = e.getSource();
			final boolean selected = e.getStateChange() == ItemEvent.SELECTED;

			     if (source == checkboxes[0]) RuntimeOptions.RenderAABBs = selected;
			else if (source == checkboxes[1]) RuntimeOptions.EnableViewFrustumCulling = selected;
			else if (source == checkboxes[2]) RuntimeOptions.EnableClipping = selected;
			else if (source == checkboxes[3]) RuntimeOptions.EnableBackfaceCulling = selected;
			else if (source == checkboxes[4]) RuntimeOptions.EnableOcclusionCulling = selected;

			super.itemStateChanged(e);
		}
	}








	private final class FramebufferOperationsOptionsComponent extends OptionsComponent
	{
		/**
		 * The serial version UID.
		 */
		private static final long serialVersionUID = -2810863863902218217L;
		/**
		 * Check boxes.
		 */
		private final JCheckBox checkboxes[] = new JCheckBox[]
		{
			new JCheckBox("Enable Anti-aliasing", RuntimeOptions.EnableAntialiasing),
		};
		/**
		 * The default constructor.
		 */
		public FramebufferOperationsOptionsComponent(final RuntimeControlPanel parent)
		{
			super(parent, "Framebuffer operations");

			for (final JCheckBox checkbox : checkboxes)
			{
				add(checkbox);
				checkbox.addItemListener(this);
			}
		}
		/**
		 * The event handlers.
		 */
		@Override
		public void itemStateChanged(final ItemEvent e)
		{
			final Object source = e.getSource();
			final boolean selected = e.getStateChange() == ItemEvent.SELECTED;

			if (source == checkboxes[0])      RuntimeOptions.EnableAntialiasing = selected;

			super.itemStateChanged(e);
		}
	}







	private final class LightingOptionsComponent extends OptionsComponent
	{
		/**
		 * The serial version UID.
		 */
		private static final long serialVersionUID = 8967404558582557431L;
		/**
		 * Checkboxes.
		 */
		private final JCheckBox checkboxes[] = new JCheckBox[]
		{
			new JCheckBox("Enable Screen Space Ambient Occlusion", false)
		};
		/**
		 * Radio buttons.
		 */
		private final JRadioButton radios[] = new JRadioButton[]
		{
			new JRadioButton("Use Blinn-Phong reflection model(fast)", true),
			new JRadioButton("Use Phong reflection model (accurate)", false),
		};
		/**
		 * The default constructor.
		 */
		public LightingOptionsComponent(final RuntimeControlPanel parent)
		{
			super(parent, "Lighting");


			for (final JCheckBox checkbox : checkboxes)
			{
				add(checkbox);
				checkbox.addItemListener(this);
			}
			for (final JRadioButton radio : radios)
			{
				add(radio);
				radio.addActionListener(this);
			}
		}
		/**
		 * The event handlers.
		 */
		@Override
		public void actionPerformed(final ActionEvent e)
		{
			final Object source = e.getSource();

			if (source instanceof JRadioButton)
			{
				if (source == radios[0])
				{
					radios[1].setSelected(false);
					RuntimeOptions.LightReflectionModel = LightEmitter.ReflectionModel.Phong;
				}
				else if (source == radios[1])
				{
					radios[0].setSelected(false);
					RuntimeOptions.LightReflectionModel = LightEmitter.ReflectionModel.BlinnPhong;
				}
			}
			super.actionPerformed(e);
		}
		@Override
		public void itemStateChanged(final ItemEvent e)
		{
			final Object source = e.getSource();
			final boolean selected = e.getStateChange() == ItemEvent.SELECTED;

			if (source == checkboxes[0])
				RuntimeOptions.EnableScreenSpaceAmbientOcclusion = selected;

			super.itemStateChanged(e);
		}
	}






	@Override
	public void update()
	{
		Scene.RunUpdateTask();
	}



	@Override
	public String getTitle()
	{
		return "Runtime";
	}

	@Override
	public String getDescription()
	{
		return "Configure runtime options";
	}

	@Override
	public Icon getIcon()
	{
		return null;
	}
}
