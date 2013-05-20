package com.mpx.birjan.common;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

class MapAdapter extends XmlAdapter<MapElements[], Map<String, Float>> {
	public MapElements[] marshal(Map<String, Float> arg0) throws Exception {
		MapElements[] mapElements = new MapElements[arg0.size()];
		int i = 0;
		for (Map.Entry<String, Float> entry : arg0.entrySet())
			mapElements[i++] = new MapElements(entry.getKey(), entry.getValue());

		return mapElements;
	}

	public Map<String, Float> unmarshal(MapElements[] arg0) throws Exception {
		Map<String, Float> r = new HashMap<String, Float>();
		for (MapElements mapelement : arg0)
			r.put(mapelement.key, mapelement.value);
		return r;
	}
}
