package version_2_0.entities;

/**
 * Created by Proxima on 22.02.2016.
 */

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Proxima on 19.02.2016.
 */
@Entity
@Table(name = "user", schema = "test")
@NamedQueries({
        @NamedQuery(name = "UserEntity.findAll", query = "select c from UserEntity c"),
        @NamedQuery(name = "UserEntity.findById",
                query = "select c  FROM UserEntity c where c.id=:id"),
        @NamedQuery(name = "UserEntity.findByName", query = "select c from UserEntity c where c.name=:name"),


})
public class UserEntity implements Serializable, java.lang.Cloneable {
    private Integer id;
    private String name;
    private int age;
    private Boolean isAdmin;
    private Timestamp createdDate;

    public UserEntity() {
    }

    public UserEntity(String name, int age, Boolean isAdmin, Timestamp createdDate) {

        this.name = name;
        this.age = age;
        this.isAdmin = isAdmin;
        this.createdDate = createdDate;
    }

    @Transient
    Integer check = 0;

    @Id
    @Column(name = "id", nullable = false, updatable = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Basic
    @Column(name = "name", nullable = true, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "age", nullable = true)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Basic
    @Column(name = "isAdmin")
    public Boolean getAdmin() {
        return isAdmin;

    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
//    @Type(type = "numeric_boolean")

//    public boolean getAdmin() {
//        return check != 0;
//    }
//
//    public void setAdmin(boolean admin) {
//        if (admin) {
//            check = 1;
//        } else check = 0;
//
//    }


    @Basic
    @Column(name = "createdDate")
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", isAdmin=" + isAdmin +
                ", createdDate=" + createdDate +
                '}';
    }
}