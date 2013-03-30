package com.mpx.birjan.bean;

import com.mpx.birjan.core.Rule;

public enum Lottery {

	NACIONAL_PRIMERA(Rule.NAP), 
	NACIONAL_MATUTINA(Rule.NAM), 
	NACIONAL_VESPERTINA(Rule.NAV), 
	NACIONAL_NOCTURNA(Rule.NAN), 
	PROVINCIA_PRIMERA(Rule.NAP), 
	PROVINCIA_MATUTINA(Rule.NAM), 
	PROVINCIA_VESPERTINA(Rule.NAV), 
	PROVINCIA_NOCTURNA(Rule.NAN);

	private Rule rule;

	Lottery(Rule rule) {
		this.rule = rule;
	}

	private static final float DEFAULT_WIN_RATIO = 70;

	public float getWinRatio() {
		return DEFAULT_WIN_RATIO;
	}

	public final Rule getRule() {
		return rule;
	}
}
