package com.crawler.entity;

import java.util.Date;

public class Frame {

	private Date start;
	private Date end;
	private Integer size;
	
	public Frame() {
		super();
	}
	
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	
	@Override
	public String toString() {
		return "Frame [start=" + start + ", end=" + end + ", size=" + size + "]";
	}
	
	public String toJson() {
		return "Frame [start=" + start + ", end=" + end + ", size=" + size + "]";
	}
	
}
