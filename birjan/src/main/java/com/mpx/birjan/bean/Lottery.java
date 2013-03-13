package com.mpx.birjan.bean;

import java.sql.Time;

import org.joda.time.Hours;
import org.joda.time.TimeOfDay;

public enum Lottery {

	NACIONAL_PRIMERA, NACIONAL_MATUTINA, NACIONAL_VESPERTINA, NACIONAL_NOCTURNA, 
	PROVINCIAL_PRIMERA, PROVINCIAL_MATUTINA, PROVINCIAL_VESPERTINA, PROVINCIAL_NOCTURNA;

	private static final float DEFAULT_WIN_RATIO = 70;

	public float getWinRatio() {
		return DEFAULT_WIN_RATIO;
	}
	
	public Hours[] getValid(){
		this.name().contains("PRIMERA");
		TimeOfDay tod = new TimeOfDay();
		
		
		return null;
	}

}
