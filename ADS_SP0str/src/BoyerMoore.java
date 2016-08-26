
/**
 * 
 * @author Rahul aravind mehalingam, ramesh suthan palani, anandan sundar, sanjana ramakrishnan
 *
 */
public class BoyerMoore {
	
	private static final int CHARACTER_SET = 256;
	
	public int stringMatcher(String text, String pattern) {
		int textLen = text.length();
		int patternLen = pattern.length();
		
		// Bad character heuristic table
		int badCharHeuristic[] = new int[CHARACTER_SET];
		
		for(int i = 0; i < CHARACTER_SET; i++) {
			badCharHeuristic[i] = -1;
		}
		
		// set the index for the characters that occurs in the pattern
		
		for(int i = 0; i < patternLen; i++) {
			badCharHeuristic[pattern.charAt(i)] = i;
		}
		
		int tIdx = 0;
		
		while(tIdx <= textLen - patternLen) {
			
			int pIdx = patternLen - 1;
			
			//scan the pattern from right to left
			while(pIdx >= 0 && pattern.charAt(pIdx) == text.charAt(pIdx + tIdx)) {
				pIdx--;
			}
			
			// if the pattern is found, return the valid shift
			if(pIdx < 0) {
				return tIdx;
			} else {
				
				/**
				 * If there is a mismatch, then the mismatched character in the text is 
				 * located in the pattern by shifting the characters till that. The max
				 * function is used to identify that if we get a positive shift.
				 */
				int shift = Math.max(1, pIdx - badCharHeuristic[text.charAt(tIdx + pIdx)]);
				tIdx += shift;
			}
			
		}
		
		return -1; // pattern not found.
	}
	
	public static void main(String[] args) {
		
		String text = "ABABDABACDABABCABAB";
		String pattern = "DABAB";
		
		Timer timer = new Timer();
		
		timer.start();
		
		BoyerMoore boyerMoore = new BoyerMoore();
		int validShift = boyerMoore.stringMatcher(text, pattern);
		
		timer.end();
		
		System.out.println("Valid shift: " + validShift);
		System.out.println("Time and memory stats");
		System.out.println(timer);
	}

}
