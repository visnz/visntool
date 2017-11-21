package base.tester;

import java.util.Random;

/**
 * 一個專門產生測試數據的類
 * 請自由擴展
 * Created by zyvis on 2017/11/21.
 */
public class DataCreator {
    /**
     * 隨機數生成對象
     */
    private static Random random=new Random();

    /**
     * 如題所示 生產整型隊列，調用隨機數
     * @param length    整型組長度
     * @param range     整型的範圍，0～range-1
     * @return          整型組
     */
    public static int[] randomIntArray(int length,int range){
        int[] tmp=new int[length];
        for(int i=0;i<length;i++)
            tmp[i]=random.nextInt(range);
        return tmp;
    }

    /**
     * 將整型組轉換為String組
     * @param ints      整型組
     * @return          String組
     */
    public static String[] intArrayToStringArray(int[] ints){
        String[] tmp=new String[ints.length];
        for (int i = 0; i < ints.length; i++) {
            tmp[i]=String.valueOf(ints[i]);
        }
        return tmp;
    }

    /**
     * 生產等差數組
     * @param length    數組長度
     * @param start     開始值
     * @param step      步長
     * @return          數組
     */
    public static int[] equalDiffIntArray(int length, int start,int step){
        int[] tmp=new int[length];
        tmp[0]=start;
        for(int i=1;i<length;i++)
            tmp[i]=tmp[i-1]+step;
        return tmp;
    }
}
