package com.ysd.util;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PageUtil {

	private List rows = new ArrayList();

	private Long total;

	public PageUtil(Page page) {
		this.rows=page.getContent();//得到的集合
		this.total=page.getTotalElements();//得到的总数
	}
	
	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
}
