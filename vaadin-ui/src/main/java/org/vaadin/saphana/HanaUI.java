package org.vaadin.saphana;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;

import org.apache.commons.lang3.StringUtils;
import org.vaadin.saphana.backend.PersistenceFacade;
import org.vaadin.saphana.backend.Person;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.fields.MValueChangeEvent;
import org.vaadin.viritin.fields.MValueChangeListener;
import org.vaadin.viritin.form.AbstractForm.DeleteHandler;
import org.vaadin.viritin.form.AbstractForm.ResetHandler;
import org.vaadin.viritin.form.AbstractForm.SavedHandler;
import org.vaadin.viritin.label.Header;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

@Theme("valo")
public class HanaUI extends UI {

	PersonForm form = new PersonForm();

	MTable<Person> personList = new MTable<Person>(Person.class).withProperties(
			"firstName", "lastName", "created", "lastModified");

	Button addNewBtn = new Button(FontAwesome.PLUS);
	TextField search = new MTextField().withInputPrompt("Filter");

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		
		listEntities();

		setContent(new MVerticalLayout(
				new Header("SAP Hana with Vaadin UI example"),
				new MHorizontalLayout(search, addNewBtn),
				personList));

		search.addTextChangeListener(new TextChangeListener() {

			@Override
			public void textChange(TextChangeEvent event) {
				String text = event.getText();
				if (StringUtils.isNotEmpty(text)) {
					filterEntities(text);
				} else {
					listEntities();
				}
			}
		});

		personList.addMValueChangeListener(new MValueChangeListener<Person>() {

			@Override
			public void valueChange(MValueChangeEvent<Person> event) {
				if (event.getValue() != null) {
					form.setEntity(event.getValue());
					// Allow deleting existing entities
					form.setDeleteHandler(new DeleteHandler<Person>() {

						@Override
						public void onDelete(Person entity) {
							getPersonBean().delete(entity);
							listEntities();
							form.getPopup().close();
						}
					});
					form.openInModalPopup();
				}
			}
		});

		form.setSavedHandler(new SavedHandler<Person>() {

			@Override
			public void onSave(Person entity) {
				try {
					getPersonBean().save(entity);
				} catch (Exception e) {
					Notification.show("Saving entity failed!",
							Type.ERROR_MESSAGE);
					e.printStackTrace();
				}
				listEntities();
				form.getPopup().close();
			}
		});
		
		form.setResetHandler(new ResetHandler<Person>() {

			@Override
			public void onReset(Person entity) {
				listEntities();
				form.getPopup().close();
			}

		});

		addNewBtn.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// disable deleting (for non saved entity)
				form.setDeleteHandler(null);
				form.setEntity(new Person());
				form.openInModalPopup();
			}
		});
		
	}

	protected void filterEntities(String text) {
		personList.setBeans(getPersonBean().getPersonsMatching(text));
	}

	private void listEntities() {
		personList.setBeans(getPersonBean().getAllPersons());
	}

	/**
	 * SAP Hana Cloud platform don't fully comfort to Java EE Spec and thus
	 * Vaadin CDI don't work out of the box there. That's why not using Vaadin
	 * CDI, but specify a basic servlet. Also injecting the EJB there and
	 * providing a static helper method to get a reference to it as we cannot
	 * directly inject the EJB where needed.
	 */
	@WebServlet(urlPatterns = "/*", name = "HanaUIServlet")
	@VaadinServletConfiguration(ui = HanaUI.class, productionMode = false)
	public static class HanaUIServlet extends VaadinServlet {

		@EJB
		private PersistenceFacade personBean;

		public PersistenceFacade getPersistenceFacade() {
			return personBean;
		}

	}

	/**
	 * A helper method to get access to PersonBean. A temporary workaround to
	 * limited CDI support in SAP Hana Cloud, see servlet class.
	 * 
	 * @return
	 */
	public static PersistenceFacade getPersonBean() {
		return ((HanaUIServlet) VaadinServlet.getCurrent()).getPersistenceFacade();
	}

}
