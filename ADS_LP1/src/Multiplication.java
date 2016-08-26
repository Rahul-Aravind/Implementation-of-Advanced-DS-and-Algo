import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Multiplication {
	BigNumber obj = new BigNumber();
	
	void padZeroes(BigNumber bigNumber, int len, boolean front) {
		ArrayList<Long> list = bigNumber.digits;
		for(int i = 0; i < len; i++) {
			if(!front) list.add(0L);
			else list.add(0, 0L);
		}
	}
	
	public BigNumber multiplySingleBit(BigNumber x, BigNumber y) {
		ArrayList<Long> xList = x.digits;
		ArrayList<Long> yList = y.digits;
		
		return new BigNumber(xList.get(0) * yList.get(0));
	}
	
	public BigNumber multiply(BigNumber num1, BigNumber num2) {
		int len1 = num1.digits.size();
		int len2 = num2.digits.size();
		int base = num1.base;
		
		int maxLen = Math.max(len1,  len2);
		
		//base conditions
		if(maxLen == 0) {
			return new BigNumber(0L);
		}
		
		if(maxLen == 1) {
			return multiplySingleBit(num1, num2);
		}
		
		if(len1 < len2) {
			padZeroes(num1, len2 - len1, false);
		} else if(len1 > len2) {
			padZeroes(num2, len1 - len2, false);
		}
		
		int left = (maxLen + 1) / 2;
		
		/***
		 * num1 = a1a2
		 * num2 = b1b2
		 * 
		 * sum1 = (a1 + a2)
		 * sum2 = (b1 + b2)
		 * 
		 * sum = sum1 * sum2
		 * 
		 * subtract = sum - (a2b2 + a1b1)
		 * 
		 * product = a2b2 * (base ^ (2 * left)) + subtract * (base ^ left) + a1b1
		 * 
		 * 
		 */
		
		// split num1 into two halves 
		BigNumber a1 = num1.splitBigNumber(0, left);
		BigNumber a2 = num1.splitBigNumber(left, maxLen);
		
//		System.out.println("# DEBUG a1 " + a1.digits);
//		System.out.println("# DEBUG a2 " + a2.digits);
		
		// split num2 into two halves
		BigNumber b1 = num2.splitBigNumber(0, left);
		BigNumber b2 = num2.splitBigNumber(left, maxLen);
		
//		System.out.println("# DEBUG b1 " + b1.digits);
//		System.out.println("# DEBUG b2 " + b2.digits);
		
		BigNumber a1b1 = multiply(a1, b1);
		BigNumber a2b2 = multiply(a2, b2);
		
		BigNumber sum1 = obj.add(a1, a2);
		BigNumber sum2 = obj.add(b1, b2);
		BigNumber sum = multiply(sum1, sum2);
		
		BigNumber subtract = obj.subtract(sum, obj.add(a2b2, a1b1));
		
		//padding zeroes at the front
		padZeroes(a2b2, 2 * left, true);
		padZeroes(subtract, left, true);
		
//		System.out.println("# DEBUG a2b2 " + a2b2.digits);
//		System.out.println("# DEBUG subtract " + subtract.digits);
//		System.out.println("# DEBUG a1b1 " + a1b1.digits);
		
		BigNumber res1 = obj.add(a2b2, subtract);
		BigNumber res2 = obj.add(res1, a1b1);
		
//		System.out.println("#DEBUG res2 " + res2.digits);
		
		return res2; // product
	}
	
	public BigNumber power(BigNumber a, long n) {
		if(n == 0)
			return new BigNumber(1L);
		
		if(n == 1)
			return a;
		
		
		
		if(n % 2 == 0){
			BigNumber res = power(a, n / 2);
			return multiply(res, res);
		}
		else {
			return power(a,n-1);
		}
	}
	
	BigNumber product(BigNumber num1, BigNumber num2) {
		int len1 = num1.digits.size();
		int len2 = num2.digits.size();
		
		int base = num1.base;
		
		BigNumber res = new BigNumber(0L);
		for(int i = 0; i < len1 + len2 - 1; i++) {
			res.digits.add(0L);
		}
				
		for(int i = 0; i < len1; i++) {
			long carry = 0;
			for(int j = 0; j < len2; j++){
				long sum = res.digits.get(i + j) + num1.digits.get(i) * num2.digits.get(j) + carry;
				res.digits.set(i + j, sum % base);
				carry = sum / base;
			}
			if(carry > 0) 
				res.digits.set(i + len2, carry);
		}
		
		obj.trim(res);
		
		return res;
	}
	
	public static void main(String args[]) {
		BigNumber num1 = new BigNumber("999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");
		BigNumber num2 = new BigNumber("999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");
		
		Multiplication m = new Multiplication();
		
//		System.out.println(num1.digits);
//		System.out.println(num2.digits);
		
		/*BigNumber res = m.multiply(num1, num2);
		System.out.println(res.toString());*/
		
		//BigNumber res = m.power(num1, 4000000);
		//System.out.println(res);
		BigNumber res1 = m.product(num1, num2);
		System.out.println(res1);
	}

}
