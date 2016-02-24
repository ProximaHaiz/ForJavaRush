package version_2_0.app;

import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

import version_2_0.entities.UserEntity;

import java.sql.Timestamp;

/* Create custom UI Components.
 *
 * Create your own Vaadin components by inheritance and composition.
 * This is a form component inherited from VerticalLayout. Use
 * Use BeanFieldGroup to bind data fields from DTO to UI fields.
 * Similarly named field by naming convention or customized
 * with @PropertyId annotation.
 */
public class ContactForm extends FormLayout implements ClickListener {

    Button save = new Button("Save", this);
    Button cancel = new Button("Cancel", this);
    Button deleteUser = new Button("Delete row", this);

    TextField name = new TextField(" Name");
    TextField age = new TextField("Age");
    CheckBox admin = new CheckBox("isAdmin?");
    DateField dateField = new DateField("Created_Date");

    PopupDateField birthDate = new PopupDateField("Birth date");


    UserEntity testEntity2;

    // Easily bind forms to beans and manage validation and buffering
    BeanFieldGroup<UserEntity> formFieldBindings;

    public ContactForm() {

        addValidators(name, age);

        dateField.setDateFormat("yyyy.MM.dd");
        dateField.setWidth(200, Unit.PIXELS);
        birthDate.setVisible(false);
//        birthDate.setDateFormat("yyyy.MM.dd. HH:mm:ss");
        birthDate.addValueChangeListener((e) -> {
            Notification.show(e.getProperty().getValue().toString(), Type.WARNING_MESSAGE);
        });

        configureComponents();
        buildLayout();
    }

    private void buildLayout() {
        setSizeUndefined();
        setMargin(true);

        HorizontalLayout actions = new HorizontalLayout(save, cancel, deleteUser);
        actions.setSpacing(true);

        addComponents(actions, name, age, admin, birthDate, dateField);
    }

    public void edit(UserEntity testEntity2) {
        this.testEntity2 = testEntity2;
        if (testEntity2 != null) {
            // Bind the properties of the contact POJO to fiels in this form
            formFieldBindings = BeanFieldGroup
                    .bindFieldsBuffered(testEntity2, this);

            /**
             * помещает дату с выбраной строки в поле для редактирования даты
             */
            Notification.show(testEntity2.toString());
            dateField.setValue(testEntity2.getCreatedDate());
            name.focus();

        } else {
            name.setValue("");
            age.setValue("");
        }

        setVisible(testEntity2 != null);
    }

    @Override
    public MainUI getUI() {
        return (MainUI) super.getUI();
    }

    @Override
    public void buttonClick(ClickEvent event) {
        if (event.getButton() == save) {
            try {
                Notification.show(dateField.getValue().toString(), Type.WARNING_MESSAGE);

                // Commit the fields from UI to DAO

                /**
                 * Кастыль, как сохранить в поле бина TimeStamp, дату формата java.util.date,
                 * т.к. в Vaadine такая конвертация какого-то хера не предусмотрета.
                 *
                 */
                UserEntity bean = formFieldBindings.getItemDataSource().getBean();
                bean.setCreatedDate(new Timestamp(dateField.getValue().getTime()));

                formFieldBindings.isValid();
                formFieldBindings.commit();

                // Save DAO to backend with direct synchronous service API
                if (this.testEntity2.getAdmin() == null) {
                    this.testEntity2.setAdmin(false);
                }
                getUI().service.save(testEntity2);
                String msg = String.format("Saved '%s %s'.",
                        testEntity2.getName(), testEntity2.getAdmin());
                Notification.show(msg, Type.TRAY_NOTIFICATION);
                getUI().refreshContacts();
            } catch (FieldGroup.CommitException e) {
                e.printStackTrace();
            }


        } else if (event.getButton() == cancel) {
            // Place to call business logic.
            Notification.show("Cancelled", Type.TRAY_NOTIFICATION);
            getUI().contactList.select(null);


        } else if (event.getButton() == deleteUser) {

            UserEntity selectedRow = (UserEntity) getUI().contactList.getSelectedRow();
            Notification.show(selectedRow.toString());
            getUI().service.delete(selectedRow);
            getUI().refreshContacts();

            Notification.show("User with" + selectedRow.getId() + " successfully deleted"
                    , Type.WARNING_MESSAGE);
        }

    }

    private static void addValidators(TextField name, TextField age) {
        name.addValidator((Validator) value -> {
            String nameValue = value == null ? "" : value.toString();

            if (nameValue.length() < 1 && nameValue.length() > 40) {
                throw new Validator.InvalidValueException("incorrect lenth of name ");
            }
            if (!nameValue.matches(".*[a-zA-Z].*")) {
                throw new Validator.InvalidValueException("name must be containt no digit");
            }
        });
        age.addValidator(new IntegerRangeValidator("incorrect range of age", 1, 110));
    }

    private void configureComponents() {

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        setVisible(false);
    }

}