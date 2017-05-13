/**
 * Created by 10648 on 2017/5/8 0008.
 *   轮函数f的过程
 * 1.f 函数的一个环节， 扩展 32 位变48位
 * 2. 将48位输出与子秘钥异或，返回结果
 * 3. 运算结果经过S盒代换运算，得到一个32位输出
 * 4. 32位比特输出再经过P置换表进行P置换，生成32位输出
 */
public class F {

    private static final int[] E = {32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9,
            10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20,
            21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1 };

    //S盒
    private static final int[][] S_Box = {
            //S_Box[1]
            {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7,
                    0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8,
                    4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0,
                    15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13},
            //S_Box[2]
            {15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10,
                    3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5,
                    0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15,
                    13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9},
            //S_Box[3]
            {10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8,
                    13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1,
                    13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7,
                    1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12},
            //S_Box[4]
            {7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15,
                    13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9,
                    10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4,
                    3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14},
            //S_Box[5]
            {2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9,
                    14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6,
                    4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14,
                    11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3},
            //S_Box[6]
            {12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11,
                    10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8,
                    9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6,
                    4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13},
            //S_Box[7]
            {4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1,
                    13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6,
                    1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2,
                    6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12},
            //S_Box[8]
            {13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7,
                    1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2,
                    7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8,
                    2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11}

    };

    private static final int[] P = {
            16,7,20,21,
            29,12,28,17,
            1,15,23,26,
            5,18,31,10,
            2,8,24,14,
            32,27,3,9,
            19,13,30,6,
            22,11,4,25
    };

    /**
     * f函数的第一步
     *对64位明文的右半部分进行扩展，然后与子密钥进行异或
     * @param data 32位右半部分
     * @return 扩展后的48位数据
     */
    public int[] EExpend(int[] data) {
        int[]  dataExpend = new int[48];
        for (int i = 0; i < dataExpend.length; i++) {
            dataExpend[i] = data[E[i] - 1];
        }
        return dataExpend;
    }

    /**
     * 异或操作
     * 主要是f函数的第二步
     * 扩展后的48位数据和子密钥异或
     * @param data1 data1
     * @param data2 data2
     * @return 异或后的结果， 长度和data1 2一致
     */

    public int[] XOR(int[] data1, int[] data2) {
        int[] XORResult = new int[data1.length];
        for (int i = 0; i < data1.length; i++) {
            XORResult[i] = data1[i] ^ data2[i];
        }
        return XORResult;
    }

    /**
     *  进行S盒代换
     *  轮换数f的第三步
     * @param temp 48 位异或的结果
     * @return 返回S盒代换后的结果
     */

    public int[] SBox(int[] temp) {
        int[] sBox32 = new int[32]; // S盒代换后的32位结果
        int[] sBox8 = new int[8];   //S盒代换的8个10进制数

        //将temp 48位数据分层8 组，每组6位，存储在二维数组tempArray
        int[][] tempArray = new int[8][6];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 6; j++) {
                tempArray[i][j] = temp[i * 6 + j];
            }
            //根据S盒代换规则，对每6个数进行代换
            sBox8[i] = S_Box[i][
                    ((tempArray[i][0] << 1) + tempArray[i][5]) *  16  //定位行 每行16个
                    + (tempArray[i][1] << 3) + (tempArray[i][2] << 2) + (tempArray[i][3] << 1) + tempArray[i][4]];

            //转换为相应的二进制存储在SBox32
            for (int k = 0; k < 4; k++) {
                sBox32[i * 4 + 3 - k] = sBox8[i] % 2;
                sBox8[i] /= 2;
            }
        }
        return sBox32;
    }

    /**
     * P置换
     * 轮换数f的第四步
     * @param sBox32 S盒置换后的结果
     * @return 返回P置换后的结果
     */
    public int[] PReplace(int[] sBox32) {
        int[] res = new int[32];
        for (int i = 0; i < 32; i++) {
            res[i] = sBox32[P[i] - 1];
        }
        return res;
    }
}
