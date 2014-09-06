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
	private boolean hasPre;
	//是否尾页
	private boolean hasNext;
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
	
	public boolean isHasPre() {
		return hasPre;
	}
	public void setHasPre(boolean hasPre) {
		this.hasPre = hasPre;
	}
	public boolean isHasNext() {
		return hasNext;
	}
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
	public IPageContext<E> getPageContext() {
		return pageContext;
	}
	public void setPageContext(IPageContext<E> pageContext) {
		this.pageContext = pageContext;
	}

	
	
}
