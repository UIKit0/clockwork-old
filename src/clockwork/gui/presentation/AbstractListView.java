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
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import clockwork.gui.controls.ControlPanel;
import clockwork.types.ConcurrentList;

/**
 * FIXME Refactor this code.
 */
public abstract class AbstractListView<EntryType, CollectionType>
extends AbstractPresentation<EntryType>
{
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 2649115888866646013L;
	/**
	 * The collection of entities shown by this view.
	 */
	protected final ConcurrentList<EntryType> entries;
	/**
	 * The JList that display's this list.
	 */
	protected final JList list;
	/**
	 * The currently selected item in the collection of entries.
	 */
	protected int selectedIndex;
	/**
	 * Instantiate an AbstractListView with a collection of objects.
	 */
	protected AbstractListView
	(
		final ControlPanel<EntryType, ?, ?> parent,
		final String title,
		final ConcurrentList<EntryType> entries
	)
	{
		super(parent, title);
		this.entries = entries;
		this.selectedIndex = -1;

		this.list = new JList(entries.toArray());
		this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.list.setFixedCellHeight(16);
		this.list.setFixedCellWidth(300);
		this.list.setVisibleRowCount(8);
		this.list.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(final MouseEvent e)
			{
				onItemSelected();
			}
		});
		final JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);
		panel.add(new JLabel(getTitle()), BorderLayout.NORTH);
		panel.add(new JScrollPane(list), BorderLayout.CENTER);
		add(new JScrollPane(panel), BorderLayout.CENTER);
	}
	/**
	 * Initialise the list view.
	 */
	@Override
	public void initialise()
	{
		if (!entries.isEmpty())
			setSelectedIndex(0);
	}
	/**
	 * Set the index that will refer to the currently
	 * selected entry.
	 */
	public void setSelectedIndex(final int index)
	{
		selectedIndex = index;
		list.setSelectedIndex(selectedIndex);
		onItemSelected();
	}
	/**
	 * Return the currently selected entry.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final EntryType getSelectedItem()
	{
		return (EntryType)list.getSelectedValue();
	}
	/**
	 * Return the index of the currently selected entry.
	 */
	public final int getSelectedIndex()
	{
		return list.getSelectedIndex();
	}
	/**
	 * Update the list.
	 */
	private void update(final int selectedIndex)
	{
		list.setListData(entries.toArray());
		this.selectedIndex = selectedIndex;
		list.setSelectedIndex(selectedIndex);
		list.revalidate();
		list.repaint();
	}
	/**
	 * Clear the entries in the collection.
	 */
	public final void clear()
	{
		entries.clear();
	}
	/**
	 * Return the JList.
	 */
	public final JList getList()
	{
		return list;
	}
	/**
	 * TODO Make this return the list of entries in a string format.
	 * Convert the list to a string.
	 */
	@Override
	public String toString()
	{
		return getTitle();
	}
	/**
	 * Add an entry to the list. The added entry will become the currently selected item.
	 * @param entry the entry to add to the list.
	 */
	public final void addEntry(final EntryType entry)
	{
		parent.setCurrentEntry(entry);
		update(0);
	}
	/**
	 * Remove the entry with the given index from the list.
	 */
	public EntryType removeEntry(final int index)
	{
		if (index >= 0 && index < entries.size())
		{
			// We decrement the current index by 1 and set the returned value as
			// our current index. This value must be greater than or equal to 0.
			final int newIndex = Math.max(0, index - 1);

			// Remove the object with the given index and update the list view.
			entries.remove(index);
			update(newIndex);

			// If the entry collection is not empty, return the currently selected object.
			if (!entries.isEmpty())
				return entries.get(newIndex);
		}
		return null;
	}

	/**
	 * FIXME DO NOT DELETE ITEMS HERE BUT RETURN THE NEXT ITEM!
	 * Remove the currently selected element.
	 */
	@Override
	public final EntryType selectNextItem()
	{
		EntryType currentlySelectedEntry = null;
		final int index = getSelectedIndex();
		if (index >= 0)
			currentlySelectedEntry = removeEntry(index);
		else
		{
			final String header = "Warning!";
			final String message = "No item has been selected";

			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this, message, header, JOptionPane.WARNING_MESSAGE);
		}
		return currentlySelectedEntry;
	}
}
