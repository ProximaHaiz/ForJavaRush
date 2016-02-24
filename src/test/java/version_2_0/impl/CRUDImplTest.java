package version_2_0.impl;

/**
 * Created by Proxima on 24.02.2016.
 */
/*
* Not working :((
* */

//public class CRUDImplTest {
//    CRUDImpl crud = new CRUDImpl();
//
//
//    @Before
//    public void setUp() throws Exception {
//        crud.refreshCTX();
//        ContactService service = new ContactService();
//        service.createDemoService();
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        crud.removeAllFromDB();
//    }
//
//
//    @Test
//    public void testSave() throws Exception {
//        crud.refreshCTX();
//        UserEntity petr = new UserEntity("Petr", 55, false,
//                new Timestamp(System.currentTimeMillis()));
//        petr.setId(1);
//        crud.save(petr);
//    }
//
//    @Test
//    public void testFindAll() throws Exception {
//        List<UserEntity> all = crud.findAll();
//        Objects.nonNull(all);
//        System.out.println(all);
//    }
//
//    @Test
//    public void testRemove() throws Exception {
//
//    }
//}