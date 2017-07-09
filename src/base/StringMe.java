package base;

import java.io.Serializable;

/**
 * Created by zyvis on 2017/6/1.
 */
public class StringMe {
    private StringMe(){}
    public static String combine(Serializable[] serializables){
        String tmp="";
        for(Serializable  s:serializables)
            tmp+=s;
        return tmp;
    }
    public static String combineln(Serializable[] serializables){
        String tmp="";
        for(Serializable s:serializables)
            tmp+=s+"\n";
        return tmp;
    }
}
