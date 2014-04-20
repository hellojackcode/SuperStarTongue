package com.hellojackcode.superstartongue;

/*
 * 참고자료 : http://quadflask.tistory.com/59 (초성/중성/종성에 대한 설명 글임)
 * 
 */
public class StyleSimilarity {
	
	
	public static String Han_UnicodeNumberToString(int[] s) throws IllegalArgumentException {
		if (s.length != 3) throw new IllegalArgumentException();
		s[0] -= 0x1100;
		s[1] -= 0x1161;
		s[2] -= 0x11a8;
		char c = (char) ((((s[0] * 588) + s[1] * 28) + s[2]) + 44032);
		return String.valueOf(c);
	}

	public static int[] Han_CharacterToIMFUnicode(char s) {
		int[] result = new int[3];
		int a = s - 44032;
		result[0] = 0x1100 + ((a / 28) / 21);
		result[1] = 0x1161 + ((a / 21) % 21);
		result[2] = 0x11a8 + (a % 28);
		return result;
	}

}
