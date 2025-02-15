package com.itwillbs.domain;

public class PageVO {
	private int endPage;
	private int startPage;
	private int totalCount;
	private boolean next;
	private boolean prev;
	
	private int pageBlock = 10;
	
//	private int page; // 페이지 번호
//	private int pageSize; // 페이지당 출력 개수
	private Criteria cri;
	
	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		pageCalcData();
	}
	
	
	private void pageCalcData() {
		// 하단 페이징처리에 필요한 계산을 수행하는 메서득
		
		// endPage
		endPage = (int)(Math.ceil(cri.getPage() / (double)pageBlock) * pageBlock);
		
		// startPage
		startPage = (endPage - pageBlock) + 1;
		
		// endPage 재설정
		int tmpEndPage = (int)(Math.ceil((double)totalCount / cri.getPageSize()));
		
		if(endPage > tmpEndPage) {
			endPage = tmpEndPage;
		}
		
		// next
		next = endPage * cri.getPageSize() < totalCount;
		// prev
		prev = startPage != 1;
		
		System.out.println("--------------- 페이징처리 정보 계산 완료! ---------------");
		
	}
	
	
	public Criteria getCri() {
		return cri;
	}

	public int getEndPage() {
		return endPage;
	}
	
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	@Override
	public String toString() {
		return "PageVO [endPage=" + endPage + ", startPage=" + startPage + ", totalCount=" + totalCount + ", next="
				+ next + ", prev=" + prev + ", pageBlock=" + pageBlock + ", cri=" + cri + "]";
	}
	
	

	
	
}
