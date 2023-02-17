package reverse;

import java.util.Scanner;
import java.util.Arrays;
public class ReverseAvg {

    public static long[] sum_row;

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
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String x = sc.nextLine();
            if (x.length() != 0) {
                int number, l = 0;
                for (int j = 0; j < x.length() + 1; j++) {
                    if (j == x.length() || Character.isWhitespace(x.charAt(j))) {
                        if (j - l > 0) {
                            number = Integer.parseInt(x.substring(l, j));
                            add(number);
                            inc(1);
                        }
                        l = j + 1;
                    }
                }
            }
            inc(0);
        }
    }

    public static void write() {
        if (a.length == 0) {
            for (int i = 0; i < pos_row; i++) {
                System.out.println();
            }
            return;
        }
        int mx = 0;
        for (int i : len) {
            mx = Math.max(mx, i);
        }
        sum_row = new long[pos_row];
        int pref_cnt = 0;
        for (int i = 0; i < pos_row; i++) {
            sum_row[i] = 0;
            for (int j = 0; j < len[i]; j++) {
                sum_row[i] += a[pref_cnt + j];
            }
            pref_cnt += len[i];
        }
        pref_cnt = 0;
        for (int i = 0; i < pos_row; i++) {
            for (int j = 0; j < len[i]; j++) {
                long ans = sum_row[i] - a[pref_cnt + j];
                int count = len[i] - 1, skip = 0;
                for (int k = 0; k < pos_row; k++) {
                    if (j < len[k]) {
                        ans += a[skip + j];
                        count++;
                    }
                    skip += len[k];
                }
                System.out.print(ans / count + " ");
            }
            pref_cnt += len[i];
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