package ch.theowinter.toxictodo.client.ui.model;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class IconComboBoxModel extends AbstractListModel<FontString> implements ComboBoxModel<FontString>{
	private static final long serialVersionUID = 1689150508606190545L;
	private ArrayList<FontString> iconArray;
	private Object selectedItem;
	
	/**
	 * Creates a new instance of this class.
	 *
	 * @param aIconArray
	 */
	public IconComboBoxModel() {
		super();
		iconArray = generateList();
	}

	@Override
	public int getSize() {
		return iconArray.size();
	}

	@Override
	public FontString getElementAt(int aIndex) {
		return iconArray.get(aIndex);
	}

	@Override
	public void setSelectedItem(Object anItem) {
		 selectedItem = anItem;
	}

	@Override
	public Object getSelectedItem() {
		return selectedItem;
	}
	
	public ArrayList<FontString> generateList(){
		ArrayList<FontString> newIcons = new ArrayList<FontString>();
		newIcons.add(new FontString("Wine Glass",'\uf000'));
		newIcons.add(new FontString("Heart",'\uf004'));
		newIcons.add(new FontString("Film",'\uf008'));
		newIcons.add(new FontString("House",'\uf015'));
		newIcons.add(new FontString("Tag",'\uf02b'));
		newIcons.add(new FontString("Printer",'\uf02f'));
		newIcons.add(new FontString("Leaf",'\uf06c'));
		newIcons.add(new FontString("Eye",'\uf06e'));
		newIcons.add(new FontString("Globe",'\uf0ac'));
		newIcons.add(new FontString("Book",'\uf02d'));
		newIcons.add(new FontString("Calendar",'\uf073'));
		newIcons.add(new FontString("Chart",'\uf080'));
		newIcons.add(new FontString("Telephone",'\uf095'));
		newIcons.add(new FontString("Creditcard",'\uf09d'));
		newIcons.add(new FontString("Cloud",'\uf0c2'));
		newIcons.add(new FontString("Creditcard",'\uf09d'));
		newIcons.add(new FontString("Envelope",'\uf0e0'));
		newIcons.add(new FontString("Box",'\uf187'));
		newIcons.add(new FontString("Camera",'\uf030'));
		newIcons.add(new FontString("Gift",'\uf06b'));
		newIcons.add(new FontString("Shopping Cart",'\uf07a'));
		newIcons.add(new FontString("Suitcase",'\uf0f4'));
		return newIcons;
	}	
}
