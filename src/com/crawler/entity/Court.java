package com.crawler.entity;

import java.util.ArrayList;
import java.util.List;


import com.crawler.CourtType;

public class Court {
	
	private String name;
	private CourtType courtType;
	private List<Frame> frames;
	
	public Court() {
		super();
		
		frames = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CourtType getCourtType() {
		return courtType;
	}

	public void setCourtType(CourtType courtType) {
		this.courtType = courtType;
	}

	public List<Frame> getFrames() {
		return frames;
	}

	public void setFrames(List<Frame> frames) {
		this.frames = frames;
	}

	@Override
	public String toString() {
		return "Court [name=" + name + ", courtType=" + courtType + ", frames=" + frames + "]";
	}
	
	
	public String toJson() {
		return "Court [name=" + name + ", courtType=" + courtType + ", frames=" + frames + "]";
	}
	
}
