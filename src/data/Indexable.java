package data;

/**
 * 此接口用于将某一类对象进行线性数字管理
 * 实现了该接口的类，在方法中返回该对象的等级，
 * 将用于与同类对象做比较、做排序、做混乱程度计算等。
 *
 * 如果单纯实用Comparable接口的话，方法实现return 0即可
 *
 * Created by zyvis on 2017/5/26.
 */
public interface Indexable<T> extends Comparable<T>{
    /**
     * 此方法返回一个能代表这个对象的数值，
     * 此后该数值将用于对同类对象进行比较
     *
     * @return  对象代表数值
     */
    int getLevel();
}
