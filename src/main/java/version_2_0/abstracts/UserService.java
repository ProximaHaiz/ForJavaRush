package version_2_0.abstracts;

import version_2_0.entities.UserEntity;

import java.util.List;

/**
 * Created by Proxima on 22.02.2016.
 */
public interface UserService {

    List<UserEntity> findAll();

    List<UserEntity> findByName(String name);

    UserEntity findById(Integer id);

    UserEntity save(UserEntity user);

    void delete(UserEntity userEntity);

    void deleteAll();

    void customQuery(String s);
}
