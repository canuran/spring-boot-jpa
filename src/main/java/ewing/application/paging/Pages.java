package ewing.application.paging;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 分页数据。
 *
 * @author Ewing
 **/
public class Pages<T> {
    private long total;

    private List<T> content;

    public Pages() {
    }

    public Pages(List<T> content) {
        if (content == null) {
            return;
        }
        this.content = content;
        this.total = content.size();
    }

    public Pages(Page<T> page) {
        this.content = page.getContent();
        this.total = page.getTotalElements();
    }

    public Pages(long total, List<T> content) {
        this.total = total;
        this.content = content;
    }

    public long getTotal() {
        return total;
    }

    public Pages<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    public List<T> getContent() {
        return content;
    }

    public Pages<T> setContent(List<T> content) {
        this.content = content;
        return this;
    }
}
