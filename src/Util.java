/**
 * Created by 10648 on 2017/5/13 0013.
 * 工具类
 */
public class Util {
    /**
     * 异或操作,两个用途
     * 1. 主要是f函数的第二步, 扩展后的48位数据和子密钥异或
     * 2. 加密主过程中 L0 ^ f(R0, k)
     *
     * @param data1 data1
     * @param data2 data2
     * @return 异或后的结果， 长度和data1 2一致
     */

    public static int[] XOR(int[] data1, int[] data2) {
        int[] XORResult = new int[data1.length];
        for (int i = 0; i < data1.length; i++) {
            XORResult[i] = data1[i] ^ data2[i];
        }
        return XORResult;
    }

    /**
     * 主要用于最终的结果显示
     * 将64 bit 数组 转化为 16 进制字符串
     * @param data bit 数组
     * @return 16 进制 字符串
     */

    public static String bit2String(int[] data) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        int sum = 0;
        for (int val : data) {
            sum = (sum << 1) + val;
            count++;
            if (count % 4 == 0) {
                sb.append(Integer.toHexString(sum).toUpperCase());
                sum = 0;
            }
        }
        return sb.toString();
    }

    /**
     * 打印16 进制 字符串
     * @param res 同上
     */

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

    public static int[] parseBytes(String s) {
        s = s.replace(" ", "");
        int[] ba = new int[64];
        for (int i = 0; i < 16; i++) {
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
