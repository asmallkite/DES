import com.sun.deploy.util.ArrayUtil;

import java.util.Arrays;

/**
 * Created by 10648 on 2017/5/13 0013.
 * 子秘钥生成
 * 1. 将64位秘钥经过PC-1 置换生成56秘钥
 * 2.将56位的密钥分成左右两部分，分别进行移位操作（一共进行16轮），产生16个56位长度的子密钥。 
 * 3.将16个56位的子密钥分别进行PC-2置换生成16个48位的子密钥。
 */
public class KeyGenerate {

    private static final int[] PC_1 = {
            57,49,41,33,25,17,9,
            1,58,50,42,34,26,18,
            10,2,59,51,43,35,27,
            19,11,3,60,52,44,36,
            63,55,47,39,31,23,15,
            7,62,54,46,38,30,22,
            14,6,61,53,45,37,29,
            21,13,5,28,20,12,4,
    };
    private static final int[] LeftMove = { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2,
            2, 2, 2, 1 };

    private static final int[] PC_2 = {
            14,17,11,24,1,5,
            3,28,15,6,21,10,
            23,19,12,4,26,8,
            16,7,27,20,13,2,
            41,52,31,37,47,55,
            30,40,51,45,33,48,
            44,49,39,56,34,53,
            46,42,50,36,29,32,
    };


    public static int[][] keysGenerate(int[] key64) {
        KeyGenerate generate = new KeyGenerate();
        int[] pc_1 = generate.PC_1(key64);
        int[][] leftMoved = generate.LeftMove(pc_1);
        return generate.PC_2(leftMoved);
    }


    /**
     * 第一步
     * PC_1置换
     * @param key64 64 位密钥
     * @return 56 位密钥
     */
    private int[] PC_1(int[] key64) {
        int[] key56 = new int[56];
        for (int i = 0; i < 56; i++) {
            key56[i] = key64[PC_1[i] - 1];
        }
        return key56;
    }

    /**
     * 第二步
     * 循环左移
     * @param key56 PC_1 置换的 56 位密钥
     * @return 16个56位长度的子密钥
     */
    private int[][] LeftMove(int[] key56) {
        int[][] keys = new int[16][56];
        for (int i = 0; i < 56; i++) {
            keys[i] = LeftMoveCore(key56, LeftMove[i]);
        }
        return keys;
    }

    /**
     * 第三步
     * PC_2置换
     * @param keys 16个56位长度的子密钥
     * @return 16个 置换后的 48 位长度的子密钥
     */
    private int[][] PC_2(int[][] keys) {
        int[][] pc2Res = new int[16][48];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 48; j++) {
                pc2Res[i][j] = keys[i][PC_2[j] - 1];
            }
        }
        return pc2Res;
    }


    /**
     * 第二步 核心左移方法
     * 功能：对给定的数组按照offset指定的位数进行循环左移，输出经过左移后的数组 
     * 作用：将经过pc-1置换处理后的56位密钥先分成左右各28位，然后根据左移表要求对密钥进行左移
     * @param key 经过pc-1处理后的56位密钥
     * @param offset 左移表的位移
     * @return 单个56位的子密钥（初步，未经过pc2置换）
     */
    private int[] LeftMoveCore(int[] key, int offset) {
        int[] subKey = new int[key.length]; // 子密钥 结果返回
        //拆分key数组成两部分
        int[] C0 = new int[28];
        int[] D0 = new int[28];
        System.arraycopy(key, 0, C0, 0, C0.length);
        System.arraycopy(key, 28, D0, 0, D0.length);
        //循环左移C0，D0
        reverse(C0, 0, offset - 1);
        reverse(C0, offset, C0.length - 1);
        reverse(C0, 0, C0.length - 1);
        reverse(D0, 0, offset - 1);
        reverse(D0, offset, D0.length - 1);
        reverse(D0, 0, D0.length - 1);
        //合并两个数组
        //方法一：
        System.arraycopy(C0, 0, subKey, 0, C0.length);
        System.arraycopy(D0, 0, subKey, C0.length, D0.length);
        //方法二： Arrays.copyOf()
        return subKey;
    }

    private void reverse(int[] array, int lo, int hi) {
        int length = hi - lo + 1;
        for (int i = 0; i < length / 2; i++) {
            int temp = array[lo];
            array[lo] = array[hi];
            array[hi] = temp;
            lo++;
            hi--;
        }
    }
}
