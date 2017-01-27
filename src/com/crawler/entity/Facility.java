package com.crawler.entity;

import java.util.HashMap;
import java.util.Map;

public class Facility {
	
	private String name;
	private Map<String, Court> courts;
	
	public Facility() {
		super();
		courts = new HashMap<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Court> getCourts() {
		return courts;
	}

	public void setCourts(Map<String, Court> courts) {
		this.courts = courts;
	}

	@Override
	public String toString() {
		return "Facility [name=" + name + ", courts=" + courts + "]";
	}

	public String toJson() {
		return "";
	}
}
