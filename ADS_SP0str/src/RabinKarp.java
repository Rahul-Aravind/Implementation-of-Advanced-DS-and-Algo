
/**
 * 
 * @author Rahul aravind mehalingam, ramesh suthan palani, anandan sundar, sanjana ramakrishnan
 *
 */

public class RabinKarp {
	
	private static final int prime = 97;
	
	/**
	 * A polynomial function used for generating hash code
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	
	public long getHashCode(String str, int len) {
		long hash = 0;
		
		for(int i = 0; i < len; i++) {
			hash += str.charAt(i) * Math.pow(prime, i);
		}
		
		return hash;
	}
	
	/**
	 * Equals function to check if the two strings are equal or not.
	 * 
	 * @param text
	 * @param tStart
	 * @param tEnd
	 * @param pattern
	 * @param pStart
	 * @param pEnd
	 * @return
	 */
	
	public boolean isEqual(String text, int tStart, int tEnd, String pattern, int pStart, int pEnd) {
		int tLen = tEnd - tStart;
		int pLen = pEnd - pStart;
		
		if(pLen != tLen) {
			return false;
		}
		
		while(tStart <= tEnd && pStart <= pEnd) {
			if(text.charAt(tStart) != pattern.charAt(pStart)) {
				return false;
			}
			
			tStart++;
			pStart++;
		}
		
		return true;
	}
	
	
	public int stringMatcher(String text, String pattern) {
		
		int textLen = text.length();
		int patternLen = pattern.length();
		
		// calculate the hash for the pattern
		long patternHashCode = getHashCode(pattern, patternLen);
		
		long textHashCode = getHashCode(text, patternLen);
		
		for(int i = 1; i <= textLen - patternLen; i++) {
			
			if(patternHashCode == textHashCode) {
				if(isEqual(text, i - 1, i + patternLen - 2, pattern, 0, patternLen - 1)) {
					return i - 1;
				}
			}
			
			// If not equal, re compute the hash value
			
			if(i <= textLen - patternLen) {
			
				long newStrHashCode = textHashCode - text.charAt(i - 1);
				newStrHashCode /= prime;
				newStrHashCode += text.charAt(i + patternLen - 1) * Math.pow(prime, patternLen - 1);
				textHashCode = newStrHashCode;
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		String text = "ABABDABACDABABCABABABABDABACDABABCABABABABDABACDABABCABABABABDABACDABABCABABABABDABACDABABCABABABABDABACDABABCABAB";
		String pattern = "BACDABABCABABABABDABACDABABCABABABABDABACDABABCABABABABDABACDABABCABABABABDABACDABABCABAB";
		
		RabinKarp rabinKarp = new RabinKarp();
		
		Timer timer = new Timer();
		timer.start();
		int validShift = rabinKarp.stringMatcher(text, pattern);
		
		timer.end();
		
		System.out.println("Valid shift: " + validShift);
		
		System.out.println(timer);
	}

}
