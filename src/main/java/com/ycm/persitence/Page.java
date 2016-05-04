package com.ycm.persitence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Page<E> implements Serializable {
	
	private static final long serialVersionUID = -4861421206255705029L;

	private static int DEFAULT_PAGE_SIZE = 5; // 默认分页记录数
	private int totalCount; // 总记录数
	private int pageSize; // 页面记录条数
	private int currentPage; // 当前页码
	private int prevPage;	 // 上一页
	private int nextPage;    // 下一页
	private int firstPage;   // 首页
	private int lastPage ;  // 尾页
	
	private int startIndex;
	private int endIndex;
	
	private List<E> list = new ArrayList<E>(); // 数据记录List
	
	private int pagecode = 3;  // 当前页前后显示页码数
	
	public Page() {
		totalCount = -1;
		pageSize = DEFAULT_PAGE_SIZE;
		currentPage = -1;
	}
	
	public Page(int pageSize, int currentPage) {
		super();
		this.pageSize = pageSize;
		this.currentPage = currentPage;
	}

	public Page(int pageSize, int currentPage, int prevPage,
			int nextPage) {
		super();
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.prevPage = prevPage;
		this.nextPage = nextPage;
	}

	public Page(int totalCount, int pageSize, int currentPage, int prevPage,
			int nextPage, int pagecode) {
		super();
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.prevPage = prevPage;
		this.nextPage = nextPage;
		this.pagecode = pagecode;
	}



	@Override
	public String toString() {
		
		return "Page [totalCount=" + totalCount + ", pageSize=" + pageSize
				+ ", currentPage=" + currentPage
				+ ", prevPage=" + prevPage + ", nextPage=" + nextPage
				+ ", firstPage=" + firstPage + ", lastPage=" + lastPage
				+ ", list=" + list + ", pagecode=" + pagecode + "]";
	}


	/**
	 * 初始化参数
	 */
	public void initialize(){
				
		//1
		this.firstPage = 1;
		
		this.lastPage = (int)(this.totalCount / (this.pageSize < 1 ? DEFAULT_PAGE_SIZE : this.pageSize) + this.firstPage - 1);
		
		if (this.totalCount % this.pageSize != 0 || this.totalCount == 0) {
			this.lastPage++;
		}

		if (this.lastPage < this.firstPage) {
			this.lastPage = this.firstPage;
		}
		
		if (this.currentPage <= 1) {
			this.currentPage = this.firstPage;
		}

		if (this.currentPage >= this.lastPage) {
			this.currentPage = this.lastPage;
		}

		if (this.currentPage < this.lastPage - 1) {
			this.nextPage = this.currentPage + 1;
		} else {
			this.nextPage = this.lastPage;
		}

		if (this.currentPage > 1) {
			this.prevPage = this.currentPage - 1;
		} else {
			this.prevPage = this.firstPage;
		}
		
		//2
		if (this.currentPage < this.firstPage) {// 如果当前页小于首页
			this.currentPage = this.firstPage;
		}

		if (this.currentPage > this.lastPage) {// 如果当前页大于尾页
			this.currentPage = this.lastPage;
		}
		//3 页面页码显示的条数，当前页前pagecode页码，当前页后pagecode个页码
		this.startIndex = this.currentPage - this.pagecode;
		if(this.startIndex < 0){
			this.startIndex = 1;
		}
		this.endIndex = (this.currentPage + this.pagecode);
		if(this.endIndex > this.lastPage || this.endIndex < 0){
			this.endIndex = this.lastPage;
		}
		
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	
	public int getPrevPage() {
		return prevPage;
	}


	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}


	public int getNextPage() {
		return nextPage;
	}


	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}


	public int getTotalCount() {
		return totalCount;
	}


	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		initialize();
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public List<E> getList() {
		return list;
	}


	public void setList(List<E> list) {
		this.list = list;
	}


	public int getPagecode() {
		return pagecode;
	}


	public void setPagecode(int pagecode) {
		this.pagecode = pagecode;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	
}
