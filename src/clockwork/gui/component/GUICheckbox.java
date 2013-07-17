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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import clockwork.gui.controls.InputControls;


/**
 * A checkbox with more functionality.
 */
public class GUICheckbox extends JPanel
{
	static public enum LabelPosition
	{
		Left,
		Right;


		public int toSwingConstants()
		{
			return SwingConstants.LEFT;
		}
	};
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 7384363023192433657L;
	/**
	 * The underlying checkbox.
	 */
	private final JCheckBox checkbox = new JCheckBox();
	/**
	 * The option may have a shortcut tip.
	 */
	private final JLabel shortcutHintLabel = new JLabel();
	/**
	 * Instantiate a labeled GUICheckbox with a given shortcut tip, the position of the label
	 * and whether or not the label should be bold.
	 * @param label the checkbox's label.
	 * @param hint the shortcut hint to bypass the use of this checkbox.
	 * @param labelPosition the position of the label relative to the checkbox form.
	 * @param boldLabel true if the label should be bold, false otherwise.
	 */
	public GUICheckbox
	(
		final String label,
		final String hint,
		final GUICheckbox.LabelPosition labelPosition,
		final boolean boldLabel
	)
	{
		super(new FlowLayout(FlowLayout.LEFT));
		setMaximumSize(new Dimension(InputControls.DIMENSION.width, 35));

		checkbox.setText(label);
		checkbox.setHorizontalTextPosition(labelPosition.toSwingConstants());
		setBoldLabel(boldLabel);
		checkbox.setBackground(new Color(0, 0 ,0, 0));

		shortcutHintLabel.setFont(shortcutHintLabel.getFont().deriveFont(Font.ITALIC, 10));
		setShortcutHint(hint);

		add(checkbox);
		add(shortcutHintLabel);
	}
	/**
	 * Instantiate a labeled checkbox.
	 * @param label the checkbox's label.
	 */
	public GUICheckbox(final String label)
	{
		this(label, null, LabelPosition.Left, false);
	}
	/**
	 * Make the checkbox's label bold.
	 * @param value true to make the label bold, false to make it plain.
	 */
	public void setBoldLabel(final boolean value)
	{
		checkbox.setFont(super.getFont().deriveFont(value ? Font.BOLD : Font.PLAIN));
	}
	/**
	 * Set a hint to notify the user of how the use of this checkbox can be bypassed,
	 * usually by a shortcut elsewhere.
	 * @param hint the hint to set.
	 */
	public void setShortcutHint(final String hint)
	{
		if (hint != null && hint.length() > 0)
			shortcutHintLabel.setText("Hint: " + hint);
	}
	/**
	 * Add an action listener to the checkbox.
	 * @param listener the listener to add.
	 */
	public void addActionListener(final ActionListener listener)
	{
		if (listener != null)
			checkbox.addActionListener(listener);
	}


	/**
	 * TODO Explain me.
	 */
	public void setSelected(final boolean selected)
	{
		checkbox.setSelected(selected);
	}
	/**
	 * TODO Explain me.
	 */
	public boolean isSelected()
	{
		return checkbox.isSelected();
	}
}
