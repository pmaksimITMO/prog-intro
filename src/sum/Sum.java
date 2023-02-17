package sum;

public class Sum {

	public static void main(String[] args) {
		int sum = 0;
		for (int i = 0; i < args.length; i += 1) {
			String current = args[i], number = "";
			for (int j = 0; j < current.length(); j += 1) {
				char symbol = current.charAt(j);
				if (symbol == '-') {
					if (number.isEmpty()) {
						number += symbol;
					} else if (!number.equals("-")) {
						sum += Integer.parseInt(number);
						number = "-";
					}
				} else if (Character.isDigit(symbol)) {
					number += symbol;
				} else {
					if (!number.isEmpty()) {
						sum += Integer.parseInt(number);
						number = "";
					}
				}
			}
			if (!number.isEmpty()) {
				sum += Integer.parseInt(number);
			}
		}
		System.out.println(sum);
	}
}