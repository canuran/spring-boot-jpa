package ewing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ewing.common.GlobalIdWorker;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * 角色实体。
 */
@Entity
public class Role {
    @Id
    @GenericGenerator(name = "generator", strategy = GlobalIdWorker.REFRENCE)
    @GeneratedValue(generator = "generator")
    private String roleId;

    private String name;

    private String description;

    private Date createTime;

    @ManyToMany
    @JoinTable(name = "admin_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "admin_id"))
    @JsonIgnore
    private Set<Admin> admins;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Set<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(Set<Admin> admins) {
        this.admins = admins;
    }
}
