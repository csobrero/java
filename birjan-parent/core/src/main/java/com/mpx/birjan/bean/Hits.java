package com.mpx.birjan.bean;

public class Hits {
	
	private float betAmount;
	private int position;
	private int hits;
	
	public Hits(float betAmount, int position, int hits) {
		this.betAmount = betAmount;
		this.position = position;
		this.hits = hits;
	}

	public final float getBetAmount() {
		return betAmount;
	}

	public final int getPosition() {
		return position;
	}

	public final int getHits() {
		return hits;
	}
	
	

}
