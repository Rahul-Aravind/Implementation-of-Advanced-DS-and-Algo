
/**
 * 
 * @author Rahul aravind mehalingam, ramesh suthan palani, anandan sundar, sanjana ramakrishnan
 *
 */

public class KMP {
	
	private int[] computePrefix(String pattern) {
		
		int patternLen = pattern.length();
		
		int[] prefix;
		prefix = new int[patternLen];
		
		prefix[0] = 0;
		
		int k = 0;
		
		for(int q = 1; q < patternLen;) {
			if(pattern.charAt(k) == pattern.charAt(q)) {
				prefix[q] = k + 1;
				k++;
				q++;
			} else {
				if(k != 0) {
					k = prefix[k - 1];
				} else {
					prefix[q] = 0;
					q++;
				}
			}
		}
		
		return prefix;
	}
	
	public int stringMatcher(String text, String pattern) {
		
		// computing prefix array
		int prefix[] = computePrefix(pattern);		
		
		int textLen = text.length();
		int patternLen = pattern.length();
		
		int tIdx = 0;
		int pIdx = 0;
		
		while(tIdx < textLen && pIdx < patternLen) {
			if(text.charAt(tIdx) == pattern.charAt(pIdx)) {
				tIdx++;
				pIdx++;
			} else {
				if(pIdx != 0) {
					pIdx = prefix[pIdx - 1];
				} else {
					tIdx++;
				}
			}
			
			if(pIdx == patternLen) {
				return tIdx - pIdx;
			}
		}
		
		return -1;
	}
	
	public static void main(String[] args) {
		KMP kmp = new KMP();
		
		String text = "ABABDABACDABABCABAB";
		String pattern = "ABAC";
		
		Timer timer = new Timer();
		timer.start();
		
		int validShift = kmp.stringMatcher(text, pattern);
		timer.end();
		
		System.out.println("Valid shift: " + validShift);
		System.out.println(timer);
	}

}
