package com.mpx.birjan.common;

import javax.xml.bind.annotation.XmlElement;

class MapElements {
	
	@XmlElement
	public String key;
	@XmlElement
	public Float value;

	@SuppressWarnings("unused")
	private MapElements() {
	} // Required by JAXB

	public MapElements(String key, Float value) {
		this.key = key;
		this.value = value;
	}
}
