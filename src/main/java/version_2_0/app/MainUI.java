package version_2_0.app;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import version_2_0.entities.UserEntity;
import version_2_0.impl.ContactService;

import javax.servlet.annotation.WebServlet;

/**
 * Created by Proxima on 22.02.2016.
 */

public class MainUI extends UI {

    TextField filter = new TextField();
    Grid contactList = new Grid();
    Button newUser = new Button("New contact");
    Button addDataExample = new Button("Add Data");


    // ContactForm is an example of a custom component class
    ContactForm contactForm = new ContactForm();

    // ContactService is a in-memory mock DAO that mimics
    // a real-world datasource. Typically implemented for
    // example as EJB or Spring Data based service.
    ContactService service;


    @Override
    protected void init(VaadinRequest request) {
        service = new ContactService();

//        service.createDemoService();
        service.updateMap();
        configureComponents();
        buildLayout();
    }


    private void configureComponents() {
        /**
         * Save example data to the database
         */
        addDataExample.addClickListener((e) -> {
            service.createDemoService();
            refreshContacts();
        });
        newUser.addClickListener((event) -> contactForm.edit(new UserEntity()));


        filter.setInputPrompt("Filter contacts...");
        filter.addTextChangeListener(e -> refreshContacts(e.getText()));

        contactList.setContainerDataSource(new BeanItemContainer<>(UserEntity.class));
        contactList.setColumnOrder("name", "age");
        contactList.setSelectionMode(Grid.SelectionMode.SINGLE);
        contactList.addSelectionListener(e -> contactForm.edit((UserEntity) contactList.getSelectedRow()));
        refreshContacts();
    }

    //    Robust layouts
    private void buildLayout() {
        HorizontalLayout actions = new HorizontalLayout(filter, addDataExample, newUser);
        actions.setWidth("100%");
        filter.setWidth("100%");
        actions.setExpandRatio(filter, 1);

        VerticalLayout left = new VerticalLayout(actions, contactList);
        left.setSizeFull();
        contactList.setSizeFull();
        left.setExpandRatio(contactList, 1);

        HorizontalLayout mainLayout = new HorizontalLayout(left, contactForm);
        mainLayout.setSizeFull();
        mainLayout.setExpandRatio(left, 1);

        // Split and allow resizing
        Window window = new Window();
        window.center();
        window.setHeight(600, Unit.PIXELS);
        window.setWidth(700, Unit.PIXELS);
        window.setContent(mainLayout);
//        setContent(mainLayout);
        addWindow(window);

    }


    void refreshContacts() {
        refreshContacts(filter.getValue());
    }

    private void refreshContacts(String stringFilter) {

        contactList.setContainerDataSource(new BeanItemContainer<>(
                UserEntity.class, service.findAll(stringFilter)));
        contactForm.setVisible(false);
    }


    @WebServlet(urlPatterns = "/*", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }


}