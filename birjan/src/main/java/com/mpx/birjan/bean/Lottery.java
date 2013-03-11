package com.mpx.birjan.bean;

public enum Lottery {

	NACIONAL, PROVINCIAL;

	private static final float DEFAULT_WIN_RATIO = 70;

	public float getWinRatio() {
		return DEFAULT_WIN_RATIO;
	}

}
