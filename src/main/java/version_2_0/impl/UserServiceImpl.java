package version_2_0.impl;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import version_2_0.abstracts.UserService;
import version_2_0.entities.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Service("jpaContactService")
@Transactional
public class UserServiceImpl implements UserService {

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<UserEntity> findAll() {
        return em.createNamedQuery("UserEntity.findAll", UserEntity.class).getResultList();
    }


    @Override
    public List<UserEntity> findByName(String name) {
        TypedQuery<UserEntity> query = em.createNamedQuery("UserEntity.findByName", UserEntity.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public UserEntity findById(Integer id) {
        TypedQuery<UserEntity> query = em.createNamedQuery("UserEntity.findById", UserEntity.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public UserEntity save(UserEntity user) {
        if (user.getId() == null) {
            em.persist(user);
            System.out.println("Contact saved with id:" + user.getId());
        } else {
            em.merge(user);
            System.out.println("Contact merge with id:" + user.getId());
        }

        return user;
    }

    @Override
    public void delete(UserEntity userEntity) {
        em.remove(em.contains(userEntity) ? userEntity : em.merge(userEntity));
        System.out.println("Contact with id: " + userEntity.getId() + "deleted successfully");
    }

    @Override
    public void deleteAll() {
        em.createQuery("delete from UserEntity ");
    }

    @Override
    public void customQuery(String s) {
        em.createQuery(s);

    }
}
