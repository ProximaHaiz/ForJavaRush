package version_2_0.impl;

import org.apache.commons.beanutils.BeanUtils;
import version_2_0.entities.UserEntity;

import java.sql.Timestamp;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ContactService {

    CRUDImpl CRUDImpl = new CRUDImpl();

    public ContactService() {
        CRUDImpl.refreshCTX();
    }

    /**
     * update
     *
     * @see contacts from bd
     */
    public void updateMap() {
        List<UserEntity> all = CRUDImpl.findAll();

        for (UserEntity user : all) {
            contacts.put(user.getId(), user);
        }

        System.out.println(contacts);
    }
    
    static String[] fnames = {"Peter", "Alice", "John", "Mike", "Olivia",
            "Nina", "Alex", "Rita", "Dan", "Umberto", "Henrik", "Rene", "Lisa",
            "Linda", "Timothy", "Daniel", "Brian", "George", "Scott",
            "Jennifer"};

    public void createDemoService() {

        Random r = new Random(0);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < 10; i++) {
            UserEntity testEntity2 = new UserEntity();
            testEntity2.setName(fnames[r.nextInt(fnames.length)]);
            testEntity2.setAdmin(true);
            testEntity2.setAge(5+(r.nextInt(50-5)));
            Timestamp createdDate = new Timestamp(System.currentTimeMillis());
            testEntity2.setCreatedDate(createdDate);
            CRUDImpl.save(testEntity2);
            contacts.put(testEntity2.getId(), testEntity2);

        }
    }


    public HashMap<Integer, UserEntity> contacts = new HashMap<>();


    public synchronized List<UserEntity> findAll(String stringFilter) {


        List<UserEntity> arrayList = new ArrayList<UserEntity>();
        for (UserEntity contact : contacts.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase()
                        .contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact);
                }
            } catch (Exception ex) {
                Logger.getLogger(ContactService.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, (o1, o2) -> (int) (o2.getId() - o1.getId()));

        return arrayList;
    }


    public synchronized long count() {
        return contacts.size();
    }

    public synchronized void delete(UserEntity value) {

        //remove from map and from db
        contacts.remove(value.getId());
        CRUDImpl.remove(value);

    }

    public synchronized void save(UserEntity entry) {
        try {
            entry = (UserEntity) BeanUtils.cloneBean(entry);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        //save to bd
        CRUDImpl.save(entry);
        contacts.put(entry.getId(), entry);
    }

    public void print() {
        for (Map.Entry<Integer, UserEntity> map : contacts.entrySet()) {
            System.out.println("User: " + map.getKey() + " - " + map.getValue());
        }
    }

}
