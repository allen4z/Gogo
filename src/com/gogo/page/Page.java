package com.gogo.page;

import java.util.List;

/**
 * 按页查询实体
 * @author Administrator
 *
 * @param <E>
 */
public class Page<E>{
	//当前页数
	private int currentIndex;
	//是否首页
	private boolean isFirst;
	//是否尾页
	private boolean isEnd;
	//当前页内容
	private List<E> items;
	
	public List<E> getItems() {
		return items;
	}
	public void setItems(List<E> items) {
		this.items = items;
	}
	private IPageContext<E> pageContext;

	public int getCurrentIndex() {
		return currentIndex;
	}
	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
	
	public boolean isFirst() {
		return isFirst;
	}
	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}
	public boolean isEnd() {
		return isEnd;
	}
	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}
	public IPageContext<E> getPageContext() {
		return pageContext;
	}
	public void setPageContext(IPageContext<E> pageContext) {
		this.pageContext = pageContext;
	}

	
	
}
