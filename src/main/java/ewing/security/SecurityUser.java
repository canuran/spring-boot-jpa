package ewing.security;

import ewing.entity.Permission;
import ewing.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * Security 用户。
 *
 * @author Ewing
 */
public class SecurityUser extends User implements UserDetails {

    private List<RoleAsAuthority> authorities;

    private List<Permission> permissions;

    /**
     * Authority相当于角色。
     */
    public void setAuthorities(List<RoleAsAuthority> authorities) {
        this.authorities = authorities;
    }

    /**
     * 判断用户是否有对应的权限编码。
     */
    public boolean hasPermission(String code) {
        return getPermissionByCode(code) != null;
    }

    /**
     * 根据权限编码获取用户权限。
     */
    public Permission getPermissionByCode(String code) {
        for (Permission permission : permissions) {
            if (permission.getCode().equals(code)) {
                return permission;
            }
        }
        return null;
    }

    /**
     * 注解中hasRole表达式会调用该方法。
     */
    @Override
    public List<RoleAsAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return String.valueOf(getName());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
