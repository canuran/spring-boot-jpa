package ewing.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * 实体类。
 */
@Entity
public class Permission {
    private Long parentId;
    private String name;
    private String code;
    private String type;
    private String target;
    private Timestamp createTime;
    private Long id;
    private Permission permissionByParentId;
    private Collection<Permission> permissionsById;
    private Collection<RolePermission> rolePermissionsById;
    private Collection<UserPermission> userPermissionsById;

    @Basic
    @Column(name = "parent_id")
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "target")
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permission that = (Permission) o;

        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (target != null ? !target.equals(that.target) : that.target != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = parentId != null ? parentId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (target != null ? target.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false)
    public Permission getPermissionByParentId() {
        return permissionByParentId;
    }

    public void setPermissionByParentId(Permission permissionByParentId) {
        this.permissionByParentId = permissionByParentId;
    }

    @OneToMany(mappedBy = "permissionByParentId")
    public Collection<Permission> getPermissionsById() {
        return permissionsById;
    }

    public void setPermissionsById(Collection<Permission> permissionsById) {
        this.permissionsById = permissionsById;
    }

    @OneToMany(mappedBy = "permissionByPermissionId")
    public Collection<RolePermission> getRolePermissionsById() {
        return rolePermissionsById;
    }

    public void setRolePermissionsById(Collection<RolePermission> rolePermissionsById) {
        this.rolePermissionsById = rolePermissionsById;
    }

    @OneToMany(mappedBy = "permissionByPermissionId")
    public Collection<UserPermission> getUserPermissionsById() {
        return userPermissionsById;
    }

    public void setUserPermissionsById(Collection<UserPermission> userPermissionsById) {
        this.userPermissionsById = userPermissionsById;
    }
}
