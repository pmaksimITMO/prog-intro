package reverse;

import java.util.Arrays;
import scanner.MyScanner;

public class Reverse {

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
            String x = sc.readLine();
            if (!x.isEmpty()) {
                int l, r = 0;
                while (r != x.length()) {
                    while (r < x.length() && Character.isWhitespace(x.charAt(r))) {
                        r++;
                    }
                    l = r;
                    while (r < x.length() && !Character.isWhitespace(x.charAt(r))) {
                        r++;
                    }
                    add(Integer.parseInt(x.substring(l, r)));
                    inc(1);
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