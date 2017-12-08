package ewing.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ewing.application.common.GlobalIdWorker;
import ewing.entity.consts.UserGender;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

/**
 * 用户实体类。
 */
@Entity
public class User {
    private BigInteger id;
    private String name;
    private String password;
    private UserGender gender;
    private java.sql.Date birthday;
    private Date createTime;

    private Collection<UserPermission> userPermissions;
    private Collection<UserRole> userRoles;

    @Id
    @GeneratedValue(generator = "GlobalIdGen")
    @GenericGenerator(name = "GlobalIdGen",strategy = GlobalIdWorker.NAME)
    @Column(name = "id")
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", length = 64, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "password", length = 64, nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 16, nullable = false)
    public UserGender getGender() {
        return gender;
    }

    public void setGender(UserGender gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "birthday")
    @JsonFormat(pattern = "yyyy-MM-dd")
    public java.sql.Date getBirthday() {
        return birthday;
    }

    public void setBirthday(java.sql.Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "create_time", nullable = false)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    @org.hibernate.annotations.ForeignKey(name = "none")
    public Collection<UserPermission> getUserPermissions() {
        return userPermissions;
    }

    public void setUserPermissions(Collection<UserPermission> userPermissions) {
        this.userPermissions = userPermissions;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    @org.hibernate.annotations.ForeignKey(name = "none")
    public Collection<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Collection<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id != null ? id.equals(user.id) : user.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
