import java.util.Arrays;

/**
 * Created by 10648 on 2017/5/13 0013.
 */
public class Main {

    public static void main(String[] args) {


        Crypt crypt= new Crypt();
        int[] a = parseBytes("5469875321456045");
        int[] b = parseBytes("5987423651456987");
        int[] res = crypt.enCrypt(a, b);
        System.out.println("Main: 密文");
        print(res);
    }

    public static void print(int[] res) {
        int count = 0;
        StringBuilder sb = new StringBuilder(4);
        for (int val : res) {
            if (count % 4 == 0 && count != 0) {
                System.out.printf("%X", Integer.parseInt(sb.toString(), 2));
                sb = new StringBuilder(4);

            }
            count ++;
            sb.append(val);
        }
        System.out.printf("%X\n", Integer.parseInt(sb.toString(), 2));
    }

    private static int[] parseBytes(String s) {
        s = s.replace(" ", "");
        int[] ba = new int[64];
        for (int i = 0; i < 16; i++) {
//            System.out.println(s.charAt(i));
//            System.out.println(charToNibble(s.charAt(i)));
            String oneInteger = Integer.toBinaryString(charToNibble(s.charAt(i)));
            switch (oneInteger.length()) {
                case 0:
                    oneInteger = "0000";
                    break;
                case 1:
                    oneInteger = "000" + oneInteger;
                    break;
                case 2:
                    oneInteger = "00" + oneInteger;
                    break;
                case 3:
                    oneInteger = "0" + oneInteger;
                    break;
                case 4:
                    break;
                    default:
                        break;

            }
//            System.out.println(oneInteger);
            for (int j = 0; j <4; j++) {
                ba[i * 4 + j] = charToNibble(oneInteger.charAt(j));
            }
        }
        return ba;
    }

    private static int charToNibble(char c) {
        if (c >= '0' && c <= '9') {
            return (c - '0');
        } else if (c >= 'a' && c <= 'f') {
            return (10 + c - 'a');
        } else if (c >= 'A' && c <= 'F') {
            return (10 + c - 'A');
        } else {
            return 0;
        }
    }
}
