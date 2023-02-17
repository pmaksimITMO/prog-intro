package sum;

public class SumLongOctal {

	public static void main(String[] args) {
		long sum = 0;
		for (int i = 0; i < args.length; i++) {
			int radix = 10, begin = 0, end = 0, cut = 0, need = 0;
			while (end < args[i].length()) {
				if (Character.isWhitespace(args[i].charAt(end))) {
					cut = 1;
					need = 1;
				} else {
					if (args[i].charAt(end) == 'o' || args[i].charAt(end) == 'O') {
						radix = 8;
						cut = 1;
						need = 1;
					}
				}
				if (need == 1 || end + 1 == args[i].length()) {
					if (end - begin + 1 - cut > 0) {         
						sum += Long.parseLong(args[i].substring(begin, end - cut + 1), radix);
					}          
					begin = end + 1;
				}
				end += 1;
				cut = 0;
				need = 0;
				radix = 10;
			}
		}
		System.out.println(sum);
	}
}