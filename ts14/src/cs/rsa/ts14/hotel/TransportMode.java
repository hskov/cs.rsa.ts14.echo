package cs.rsa.ts14.hotel;

import java.util.HashMap;
import java.util.Map;


public enum TransportMode {
	CAR("Ca"), 
	BICYCLE("Bi"), 
	PUBLIC_TRANSPORTATION("Pu"),
	TRAVELLING("Tr"),
	HOME("Ho"),
	NON_WORKING_DAYS("No"),
	UNKNOWN(null);
	
	private static Map<String, TransportMode> modeMap;
	
	private String code;
	
	private TransportMode(String code) {
		this.code = code;
	}

	public static TransportMode getByCode(String code) {
		prepareMap();
		if (modeMap.containsKey(code)) {
			return (TransportMode) modeMap.get(code);
		}
		return UNKNOWN;
	}
	
	private static void prepareMap() {
		if (modeMap == null) {
			modeMap = new HashMap<String, TransportMode>();
			for (TransportMode t : values()) {
				modeMap.put(t.code, t);
	        }
		}
	}
}
