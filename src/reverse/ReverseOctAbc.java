package reverse;

import scanner.MyScanner;

import java.util.Arrays;
public class ReverseOctAbc {

    public static int[] a, len;

    public static int pos_a, pos_row;

    private static void inc(int plus) {
        if (plus == 0) {
            pos_row++;
        }
        if (pos_row >= len.length) {
            len = Arrays.copyOf(len, Math.max(2 * pos_row, 1));
        }
        len[pos_row] += plus;
    }

    private static void add(int val) {
        if (pos_a >= a.length) {
            a = Arrays.copyOf(a, Math.max(1, 2 * pos_a));
        }
        a[pos_a] = val;
        pos_a++;
    }

    public static void read() {
        MyScanner sc = MyScanner.newScanner();
        while (sc.hasNext()) {
            String x = sc.nextLineAbc();
            if (!x.isEmpty()) {
                for (int i = 0; i < x.length();) {
                    int number = 0, l = i, radix = 10, shift = 0;
                    while (i < x.length() && Character.isWhitespace(x.charAt(i))) {
                        i++;
                        l++;
                    }
                    while (i < x.length() && !Character.isWhitespace(x.charAt(i))) {
                        i++;
                    }
                    i--;
                    if (x.charAt(i) == 'o') {
                        radix = 8;
                        shift = 1;
                    }
                    if (i - l - shift + 1 > 0) {
                        if (x.charAt(l) == '-') {
                            number = Integer.parseInt(x.substring(l, i - shift + 1), radix);
                        } else {
                            number = Integer.parseUnsignedInt(x.substring(l, i - shift + 1), radix);
                        }
                        add(number);
                        inc(1);
                    }
                    i++;
                }
            }
            inc(0);
        }
    }

    public static void write() {
        pos_a--;
        for (int i = pos_row - 1; i >= 0; i--) {
            if (pos_a != -1) {
                for (int j = 0; j < len[i]; j++) {
                    System.out.print(a[pos_a] + " ");
                    pos_a--;
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        a = new int[0];
        len = new int[0];
        pos_a = 0;
        pos_row = 0;
        read();
        write();
    }
}