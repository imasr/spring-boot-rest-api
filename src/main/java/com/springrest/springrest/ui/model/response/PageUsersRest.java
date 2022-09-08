package com.springrest.springrest.ui.model.response;

import java.util.List;

import org.springframework.data.domain.Sort;

public class PageUsersRest {
    List<UserRest> content;
    long totalCount;
    long page;
    long size;
    long totalPage;
    String sortby;
    Sort.Direction SortOrder;

    public String getSortby() {
        return sortby;
    }

    public void setSortby(String sortby) {
        this.sortby = sortby;
    }

    public Sort.Direction getSortOrder() {
        return SortOrder;
    }

    public void setSortOrder(Sort.Direction sortOrder) {
        SortOrder = sortOrder;
    }

    public List<UserRest> getContent() {
        return content;
    }

    public void setContent(List<UserRest> content) {
        this.content = content;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }
}
