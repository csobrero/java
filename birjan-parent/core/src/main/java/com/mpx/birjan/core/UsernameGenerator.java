package com.mpx.birjan.core;

import java.util.Random;

public class UsernameGenerator {

	public static String hash(final String name, final String surname, final int agencyId) {
		char n = name.toUpperCase().charAt(0);
		char s = surname.toUpperCase().charAt(0);
		int a = Integer.parseInt(((agencyId > 10 ? "" : "0") + agencyId).charAt(0) + "");
		int g = Integer.parseInt(((agencyId > 10 ? "" : "0") + agencyId).charAt(1) + "");

		int r = (int)(10 * new Random().nextDouble());

		n = (char) (n + r > 90 ?-90 + 64 + n + r : n + r);
		s = (char) (s - r < 65 ? 91 - 65 + s - r : s - r);
		a = a + r > 9 ?-10 + a + r : a + r;
		g = g - r < 0 ? 10 + g - r : g - r;

		return (n + "" + a + "" + s + "" + g + "" + r).toLowerCase();
	}

	public static String decode(String hash) {
		char n = hash.toUpperCase().charAt(0);
		char s = hash.toUpperCase().charAt(2);
		int a = Integer.parseInt(hash.toUpperCase().charAt(1) + "");
		int g = Integer.parseInt(hash.toUpperCase().charAt(3) + "");

		int r = Integer.parseInt(hash.charAt(4) + "");

		n = (char) (n - r < 65 ? 91 - 65 + n - r : n - r);
		s = (char) (s + r > 90 ?-90 + 64 + s + r : s + r);
		a = a - r < 0 ? 10 + a - r : a - r;
		g = g + r > 9 ?-10 + g + r : g + r;

		return n + "" + s + "" + a + "" + g;
	}

}
