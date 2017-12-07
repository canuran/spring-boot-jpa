package ewing.application.common;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;

/**
 * 树节点接口。
 */
public interface TreeNode<E extends TreeNode, ID> {

    ID getId();

    default void setId(ID id) {
        throw new NotImplementedException();
    }

    ID getParentId();

    default void setParentId(ID parentId) {
        throw new NotImplementedException();
    }

    Collection<E> getChildren();

    void setChildren(Collection<E> nodes);

}
