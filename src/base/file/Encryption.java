package base.file;

import base.IO.log.Log;

import java.math.BigInteger;

/**
 * Created by zyvis on 2017/11/21.
 */
public class Encryption {
    /**
     * 計算十六位校驗和
     * 更換了大數據類型支持較長長度的步長
     * 不過關於效率和步長關係暫未清楚
     * 推薦在8左右
     *
     * @param origin    原始數據
     * @param step      按十六進制切割的基本單位
     * @return          校驗和
     */
    public static String calCheckSum_Hex(String origin, int step) {
        for (; origin.length() % step != 0; ) origin += "0";
        BigInteger[] tmp = new BigInteger[origin.length() / step];
        for (int i = 0; i < origin.length() / step; i++) {
            String hex=origin.substring(0 + i * step, step + i * step );
            tmp[i] = new BigInteger(hex,16);
        }
        BigInteger sum=BigInteger.ZERO;
        for (BigInteger i : tmp) {
            sum=sum.add(new BigInteger(String.valueOf(i)));
        }
        String sumString=sum.toString(16);
        Log.d(sumString);
        int sumsize=sumString.length()-step+1;
        String[] base=new String[sumsize];
        base[0]=sumString.substring(sumString.length() -step ,sumString.length());
        for(int i=1;i<base.length;i++){
            base[i]=sumString.substring(i-1,i);
        }
        BigInteger[] tmpsum = new BigInteger[sumsize];
        BigInteger sumall=BigInteger.ZERO;
        for(int i=0;i<sumsize;i++) {
            tmpsum[i] = new BigInteger(base[i], 16);
            sumall=sumall.add(tmpsum[i]);
        }
        return sumall.toString(16);
    }
}
