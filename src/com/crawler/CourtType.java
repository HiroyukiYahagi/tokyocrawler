package com.crawler;

public enum CourtType {
	OMNI,
	CLAY,
	HARD,
	OTHER;
	
	public static CourtType fromLabel(String name){
		switch (name) {
		case "人工芝":
			return CourtType.OMNI;
		case "ハード":
			return CourtType.HARD;
		case "クレー":
			return CourtType.CLAY;
		default:
			return CourtType.OTHER;
		}
	}
	
	@Override
	public String toString(){
		switch (this) {
		case OMNI:
			return "人工芝";
		case HARD:
			return "ハード";
		case CLAY:
			return "クレー";
		default:
			return "その他";
		}
	}
}
