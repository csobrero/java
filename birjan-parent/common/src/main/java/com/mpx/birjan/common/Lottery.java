package com.mpx.birjan.common;


public enum Lottery {

	NACIONAL_PRIMERA(Rule.NAP), 
	NACIONAL_MATUTINA(Rule.NAM), 
	NACIONAL_VESPERTINA(Rule.NAV), 
	NACIONAL_NOCTURNA(Rule.NAN), 
	PROVINCIA_PRIMERA(Rule.PRP), 
	PROVINCIA_MATUTINA(Rule.PRM), 
	PROVINCIA_VESPERTINA(Rule.PRV), 
	PROVINCIA_NOCTURNA(Rule.PRN);
	
	public static final Lottery[] NACIONAL = { NACIONAL_PRIMERA, NACIONAL_MATUTINA, NACIONAL_VESPERTINA, NACIONAL_NOCTURNA };
	public static final Lottery[] PROVINCIA = { PROVINCIA_PRIMERA, PROVINCIA_MATUTINA, PROVINCIA_VESPERTINA, PROVINCIA_NOCTURNA };

	public static final Lottery[][] ALL = { NACIONAL, PROVINCIA };
	
	private Rule rule;

	Lottery(Rule rule) {
		this.rule = rule;
	}
	
	public String getLotteryName(){
		return this.name().split("_")[0];
	}
	
	public String getVariantName(){
		return this.name().split("_")[1];
	}
	
	public static String[] getLotteryNames(){
		return new String[]{"NACIONAL", "PROVINCIA"};
	}

	private static final float DEFAULT_WIN_RATIO = 70;

	public float getWinRatio() {
		return DEFAULT_WIN_RATIO;
	}

	public final Rule getRule() {
		return rule;
	}

	public static Lottery identify(String str) {
		for (Lottery lottery : Lottery.values()) {
			if(str.equals(lottery.getRule().getClass().getName()))
				return lottery;
		}
		return null;
	}
}
