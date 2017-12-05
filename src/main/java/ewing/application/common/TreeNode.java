package ewing.application.common;

import java.util.Collection;

/**
 * 树节点接口。
 */
public interface TreeNode<E extends TreeNode, ID> {

    ID getId();

    default void setId(ID id) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    ID getParentId();

    default void setParentId(ID parentId) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    Collection<E> getChildren();

    void setChildren(Collection<E> nodes);

}
