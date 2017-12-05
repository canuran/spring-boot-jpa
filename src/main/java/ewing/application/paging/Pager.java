package ewing.application.paging;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * 分页参数。
 *
 * @author Ewing
 **/
public class Pager {
    private int page = 1;
    private int limit = 100;

    public Pager() {
    }

    public Pager(int page, int limit) {
        this.setPage(page);
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page > 0 ? page : 1;
    }

    public int getOffset() {
        return page * limit - limit;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Pageable pageable() {
        return new PageRequest(page > 0 ? page - 1 : 0, limit);
    }
}
