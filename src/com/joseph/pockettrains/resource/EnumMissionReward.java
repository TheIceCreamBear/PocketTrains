package com.joseph.pockettrains.resource;

import java.util.HashMap;

public enum EnumMissionReward {
	COINS,
	BUX,
	CRATE;
	
	private static HashMap<Integer, EnumMissionReward> map = new HashMap<Integer, EnumMissionReward>();
	
	static {
		map.put(COINS.ordinal(), COINS);
		map.put(BUX.ordinal(), BUX);
		map.put(CRATE.ordinal(), CRATE);
	}
	
	public static EnumMissionReward getEnumMissionReward(int id) {
		return map.get(id);
	}
}