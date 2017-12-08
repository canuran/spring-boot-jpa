package ewing.entity.consts;

import ewing.application.common.AliasName;

/**
 * 权限类型。
 */
public enum PermissionType implements AliasName {
    MENU("菜单"),
    NAVIGATION("导航"),
    PAGE("页面"),
    OPERATION("操作"),
    INTERFACE("接口"),
    TARGET("资源");

    private String alias;

    PermissionType(String alias) {
        this.alias = alias;
    }

    @Override
    public String alias() {
        return alias;
    }
}
