package com.gogo.page;

import java.util.List;

public class QueryPageContext<E> implements IPageContext<E> {
	
	private List<E> items;
	//总记录数
	private int totalCount;
	//每页记录数
	private int pageSize;
	
	
	public QueryPageContext(int totalCount, int pageSize, List<E> items) {
        this.totalCount = totalCount;
        this.pageSize = pageSize == 0 ? DEFAULT_PAGE_SIZE : pageSize;
        if (items != null) {
            this.items = items;
        }
    }

	public Page<E> getPage(int index) {
		Page<E> page = new Page<E>();
        page.setPageContext(this);
        int index2 = index > getPageCount() ? 1 : index;
        page.setEnd(index2 < getPageCount());
        page.setFirst(index2 > 1);
        page.setCurrentIndex(index2);
        page.setItems(items);
        return page;
	}

	/**
	 * 总页数
	 */
	public int getPageSize() {
		return this.pageSize;
	}

	/**
	 * 总记录数
	 */
	public int getTotal() {
		return this.totalCount;
	}
	
	/**
	 * 计算总页数
	 */
	public int getPageCount() {
		int div = totalCount / pageSize;
        int result = (totalCount % pageSize == 0) ? div : div + 1;
        return result;
	}

}
