package org.vaadin.saphana;

import org.vaadin.saphana.backend.Person;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;

/**
 * A re-usable form composition to edit Person entities. The super class
 * provides "databinding" (using BeanFieldGroup behind the scenes) and
 * save/cancel/delete buttons. See HanaUI for usage example.
 */
public class PersonForm extends AbstractForm<Person> {

	private TextField firstName = new MTextField("First name:");
	private TextField lastName = new MTextField("Last name:");
	private DateField birthDay = new DateField("Birth day:");
	private TeamSelect team = new TeamSelect();

	@Override
	protected Component createContent() {
		return new MVerticalLayout(firstName, lastName, birthDay, team, getToolbar());
	}

}
