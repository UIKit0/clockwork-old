package clockwork.gui.component;

import java.io.File;

import javax.swing.JFileChooser;

import clockwork.asset.io.reader.model3d.Model3DReader;
import clockwork.gui.UserInterface;

public final class GUIFileChooser extends JFileChooser
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 338257355526184916L;
	/**
	 * The user interface.
	 */
	private final UserInterface userInterface;
	/**
	 * Instantiate a file chooser attached to a user interface.
	 * @param userInterface the user interface to attach this file chooser to.
	 */
	public GUIFileChooser(final UserInterface userInterface)
	{
		this.userInterface = userInterface;
		setCurrentDirectory(new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "assets"));
	}



	/**
	 * TODO Add 3d model file filters to the dialog.
	 * Open a 3D model file.
	 */
	public File openModel3DFile()
	{
		final String supported[] = Model3DReader.SUPPORTED_TYPES;
		return (showDialog(userInterface, "Open 3D model") == JFileChooser.APPROVE_OPTION) ?
		getSelectedFile() : null;
	}
}
