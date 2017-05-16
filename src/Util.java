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
}
