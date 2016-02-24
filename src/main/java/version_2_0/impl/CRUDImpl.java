package version_2_0.impl;

import org.springframework.context.support.GenericXmlApplicationContext;
import version_2_0.abstracts.UserService;
import version_2_0.entities.UserEntity;

import java.util.List;

/**
 * Created by Proxima on 22.02.2016.
 */

public class CRUDImpl {
    private GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();

    static int coint = 0;

    public void refreshCTX() {
        ctx.load("classpath:spring-config.xml");
        if (coint == 0) {
            ctx.refresh();
            coint++;
        }
    }


    public void save(UserEntity user) {
        UserService service = ctx.getBean("jpaContactService", UserService.class);
        service.save(user);
    }


    /*public void save1() {
        UserService service = ctx.getBean("jpaContactService", UserService.class);
        ContactService contactService = new ContactService();
        contactService.createDemoService();

        Collection<UserEntity> values = contactService.contacts.values();

        for (UserEntity user : values) {
            UserEntity userToSave = new UserEntity(user.getName(), user.getAge(),
                    user.getAdmin(), user.getCreatedDate());
            service.save(userToSave);
        }
        System.out.println(contactService.contacts);
    }*/


   /* public void findAllByName() {
        UserService userService = ctx.getBean("jpaContactService", UserService.class);
        printAll(userService.findByName("Alice"));
    }*/

    public List<UserEntity> findAll() {
        UserService userService = ctx.getBean("jpaContactService", UserService.class);
        return userService.findAll();
    }

    public void remove(UserEntity deleteUser) {
        UserService service = ctx.getBean("jpaContactService", UserService.class);
        service.delete(deleteUser);
    }

    public void removeAllFromDB() {
        UserService service = ctx.getBean("jpaContactService", UserService.class);
        service.deleteAll();
    }

    private static void printAll(List<UserEntity> users) {
        System.out.println("JPA entities");
        for (UserEntity u : users) {
            System.out.println(u);
        }
    }
}
