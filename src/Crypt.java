/**
 * Created by 10648 on 2017/5/13 0013.
 * 1. 对64明文 加密  IP操作
 * 2. 将IP置换输出的明文一分为二，L0,R0,各32位
 * 3. 16 轮迭代加密
 * 4. 逆置换 IP^-1
 */
public class Crypt {

    private static final int[] IP = {
            58,50,42,34,26,18,10,2,
            60,52,44,36,28,20,12,4,
            62,54,46,38,30,22,14,6,
            64,56,48,40,32,24,16,8,
            57,49,41,33,25,17,9,1,
            59,51,43,35,27,19,11,3,
            61,53,45,37,29,21,13,5,
            63,55,47,39,31,23,15,7,
    };

    private static final int[] IP_1 = {
            40,8,48,16,56,24,64,32,
            39,7,47,15,55,23,63,31,
            38,6,46,14,54,22,62,30,
            37,5,45,13,53,21,61,29,
            36,4,44,12,52,20,60,28,
            35,3,43,11,51,19,59,27,
            34,2,42,10,50,18,58,26,
            33,1,41,9,49,17,57,25,
    };

    /**
     * 加密过程
     * @param data 64位明文数据
     * @return DES加密结果
     */
    public int[] enCrypt(int[] data) {
        int[] dataIPed = IPReplace(data); // IP置换
        int[] itera = iteration16Times(dataIPed);
        return IP_1(itera);
    }


    /**
     * 1. 对64明文 加密  IP操作
     * @param data 64 为明文
     * @return IP置换后的 64 位输出
     */
    private int[] IPReplace(int[] data) {
        int[] IPed = new int[64];
        for (int i = 0; i < 64; i++) {
            IPed[i] = data[IP[i] - 1];
        }
        return IPed;
    }



    /**
     * 将IP置换输出的明文一分为二，L0,R0,各32位
     * 16 轮迭代加密
     * @param dataIPed IP置换后的 64 位输出
     * @return
     */
    private int[] iteration16Times(int[] dataIPed) {
        int[][] keys = KeyGenerate.keysGenerate(dataIPed); // 生成密钥数组

        int[] L0 = new int[32];
        int[] R0 = new int[32];
        //一分为二
        System.arraycopy(dataIPed, 0, L0, 0, 32);
        System.arraycopy(dataIPed, 32, R0, 0, 32);

        //16轮迭代
        int[] L1 = new int[32];
        int[] R1 = new int[32];
        int[] f_ed = new int[32]; // f操作的结果
        for (int i = 0; i < 16; i++) {
            // L1 = R0
            System.arraycopy(R0, 0, L1, 0, 32);
            // f 函数
            f_ed = F.f(R0, keys[i]);
            // R1 = L0 XOR f (R0, ki)
            R1 = Util.XOR(L0, f_ed);
            // 重置 L0, R0, 初始下次迭代
            System.arraycopy(L1, 0, L0, 0, 32);
            System.arraycopy(R1, 0, R0, 0, 32);
        }
        // 把弟16次反置回来, 并合并数组
        int[] temp = new int[64];
        System.arraycopy(R1, 0, temp, 0, 32);
        System.arraycopy(L1, 0, temp, 32, 32);
        return temp;
    }

    /**
     * IP-1逆置换
     * @param data data
     * @return 逆置换 IP^-1的结果
     */
    private int[] IP_1(int[] data) {
        int [] IP_1ed = new int[64];
        for (int i = 0; i < 64; i++) {
            IP_1ed[i] = data[IP_1[i] - 1];
        }
        return IP_1ed;
    }
}
