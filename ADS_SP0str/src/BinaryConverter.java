
public class BinaryConverter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String[] hexcodes = { "11111111" };
		for (int i = 0; i < hexcodes.length; i++) {
			String s1 = hexcodes[i].substring(0, 8);
			String s2 = hexcodes[i].substring(8, 16);
			String s3 = hexcodes[i].substring(17, hexcodes[i].length());

			Integer i1 = convertBintoDec(hexcodes[i]);
			Integer i2 = convertBintoDec(hexcodes[i]);
			Integer i3 = convertBintoDec(hexcodes[i]);

		}

	}

	private static Integer convertBintoDec(String hexcodes) {

		System.out.println(" 8888888");
		/*
		 * int decimal=0; int power=0; while(binary.length()>0) { int temp =
		 * Integer.parseInt(binary.charAt((binary.length())-1)+"");
		 * decimal+=temp*Math.pow(2, power++);
		 * binary=binary.substring(0,binary.length()-1); }
		 * System.out.println(decimal);
		 */

		// TODO Auto-generated method stub
	return 88;
	}

}
