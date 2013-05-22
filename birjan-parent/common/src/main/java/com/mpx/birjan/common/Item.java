package com.mpx.birjan.common;

import java.io.Serializable;

public class Item implements Serializable{

	private static final long serialVersionUID = 5227610248022771026L;

	private String itemName;
	private int quantity = 0;
	private Float[] amounts = {0f,0f,0f};
	
	@SuppressWarnings("unused")
	private Item() {}
	
	public Item(String itemName) {
		super();
		this.itemName = itemName;
	}
	
	public void add(Float... amount){
		add(true, amount);
	}
	
	public void add(boolean incrementItemCounter, Float... amounts){
		if(incrementItemCounter)
			quantity++;
		for (int i = 0; i < amounts.length; i++) {
			if(amounts[i]!=null)
				this.amounts[i]+=amounts[i];
		}
	}

	public String getItemName() {
		return itemName;
	}

	public int getQuantity() {
		return quantity;
	}

	public Float[] getAmounts() {
		return amounts;
	}
	
	
	
}
