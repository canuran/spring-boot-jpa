package ewing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ewing.application.common.TreeNode;
import ewing.entity.consts.PermissionType;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * 权限实体类。
 */
@Entity
public class Permission implements TreeNode<Permission, Long> {
    private Long id;
    private String name;
    private String code;
    private PermissionType type;
    private String target;
    private Long parentId;
    private Date createTime;

    private Permission parent;
    private Collection<Permission> children;
    private Collection<RolePermission> rolePermissions;
    private Collection<UserPermission> userPermissions;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", length = 128, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @GeneratedValue
    @Column(name = "code", length = 64, nullable = false)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 16, nullable = false)
    public PermissionType getType() {
        return type;
    }

    public void setType(PermissionType type) {
        this.type = type;
    }

    @Basic
    @Column(name = "target", length = 512)
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Basic
    @Column(name = "parent_id")
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "create_time", nullable = false)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false)
    public Permission getParent() {
        return parent;
    }

    public void setParent(Permission parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy = "parent")
    public Collection<Permission> getChildren() {
        return children;
    }

    public void setChildren(Collection<Permission> children) {
        this.children = children;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "permission")
    public Collection<RolePermission> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(Collection<RolePermission> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "permission")
    public Collection<UserPermission> getUserPermissions() {
        return userPermissions;
    }

    public void setUserPermissions(Collection<UserPermission> userPermissions) {
        this.userPermissions = userPermissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permission that = (Permission) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
