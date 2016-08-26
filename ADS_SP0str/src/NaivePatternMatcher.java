
/**
 * 
 * @author Rahul aravind mehalingam, ramesh suthan palani, anandan sundar, sanjana ramakrishnan
 *
 */
public class NaivePatternMatcher {
	
	public int stringMatcher(String text, String pattern) {
		int textLen = text.length();
		int patternLen = pattern.length();
		
		for(int i = 0; i <= textLen - patternLen; i++) {
			
			int idx;
			
			for(idx = 0; idx < patternLen; idx++) {
				if(text.charAt(i + idx) != pattern.charAt(idx)) {
					break;
				}
			}
			
			if(idx == patternLen) {
				return i;
			}
		}
		
		return -1;
	}
	
	public static void main(String[] args) {
		NaivePatternMatcher naivePatternMatcher = new NaivePatternMatcher();
		
		String text = "ABABDABACDABABCABAB";
		String pattern = "BACD";
		
		Timer timer = new Timer();
		
		timer.start();
		
		int validShift = naivePatternMatcher.stringMatcher(text, pattern);
		
		timer.end();
		System.out.println("valid shift: " + validShift);
		System.out.println(timer);
	}

}
