package ewing.entity.consts;

import ewing.application.common.AliasName;

/**
 * 用户姓别。
 */
public enum UserGender implements AliasName {
    SECRET("保密"),
    MALE("男"),
    FEMALE("女");

    private String alias;

    UserGender(String alias) {
        this.alias = alias;
    }

    @Override
    public String alias() {
        return alias;
    }
}
