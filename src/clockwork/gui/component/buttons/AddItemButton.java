package clockwork.gui.component.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import clockwork.gui.actions.GUIActionAdd;
import clockwork.gui.component.GUIButton;
import clockwork.gui.component.GUIDropdown;
import clockwork.gui.controls.ControlPanel;

public class AddItemButton extends GUIButton
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 7531269125963756786L;
	/**
	 * The button icons.
	 */
	private static ImageIcon icon;
	private static ImageIcon selectedIcon;
	private static ImageIcon rolloverIcon;
	private static ImageIcon disabledIcon;
	static
	{
		try
		{
			icon = new ImageIcon(ImageIO.read(new File("assets/icons/add.jpg")));
			selectedIcon = new ImageIcon(ImageIO.read(new File("assets/icons/add.selected.jpg")));
			rolloverIcon = new ImageIcon(ImageIO.read(new File("assets/icons/add.rollover.jpg")));
			disabledIcon = new ImageIcon(ImageIO.read(new File("assets/icons/add.disabled.jpg")));
		}
		catch (final IOException e)
		{}
	}
	/**
	 * Instantiate a new button attached to a control panel and a dropdown menu.
	 * @param controlPanel the control panel.
	 * @param dropdown the dropdown menu containing add actions.
	 */
	public AddItemButton
	(
		final ControlPanel<?, ?, ?> controlPanel,
		final GUIDropdown<GUIActionAdd<?>> dropdown
	)
	{
		super(icon, selectedIcon, rolloverIcon, disabledIcon);
		super.setToolTipText("Add item");
		this.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				dropdown.getSelectedItem().onActionPerformed(e);
			}
		});
	}
}