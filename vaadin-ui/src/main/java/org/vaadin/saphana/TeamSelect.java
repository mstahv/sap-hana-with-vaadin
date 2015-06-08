package org.vaadin.saphana;

import org.vaadin.saphana.backend.Team;
import org.vaadin.viritin.fields.TypedSelect;

import com.vaadin.data.Property;
import com.vaadin.ui.AbstractSelect.NewItemHandler;
import com.vaadin.ui.ComboBox;

/**
 * This is an example of an application specific UI class. In this case we just
 * extend the basic select to automatically list options when it is bound to 
 * a property. We also support dynamically adding new Team instances if one 
 * can't find a selection with given filter.
 * 
 */
public class TeamSelect extends TypedSelect<Team> implements NewItemHandler {

	public TeamSelect() {
		super(Team.class);
		withSelectType(ComboBox.class);
		getSelect().setNewItemsAllowed(true);
		getSelect().setNewItemHandler(this);
		setCaption("Group:");
		setDescription("Choose existing group or create "
				+ "new by typing its name and hitting enter.");
	}

	@Override
	public void setPropertyDataSource(Property newDataSource) {
		setOptions(HanaUI.getPersonBean().getGroups());
		super.setPropertyDataSource(newDataSource);
	}

	/**
	 * Called when no item is found by filtering in ComboBox and user hits
	 * enter.
	 */
	@Override
	public void addNewItem(String newItemCaption) {
		Team userGroup = new Team();
		userGroup.setTeamName(newItemCaption);
		userGroup = HanaUI.getPersonBean().save(userGroup);
		setOptions(HanaUI.getPersonBean().getGroups());
		setValue(userGroup);
	}

}
