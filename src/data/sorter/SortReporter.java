package data.sorter;

import data.Indexable;

import java.util.LinkedList;
import java.util.List;

/**
 * 此类包含了一个Sorter，用于检测排序方法性能，择优。
 * 开发者可以将数据组透过{@link #report(Indexable[])}进行数据测试
 * 同时提供了面对大样本的抽样方法{@link #getSampleGroup(Indexable[], int)}
 * 提供了检测样本混乱程度的{@link #disorderLevel()}
 * <p>
 * Created by zyvis on 2017/5/26.
 */
public class SortReporter {
    /**
     * 持有指定需要评测的筛选器
     */
    private Sorter sorter = null;

    /**
     * 此队列用来装载Sorter类可支持的排序对象
     */
    protected static List sortTagList = new LinkedList<>();

    /**
     * 此数值用于指示一个界限
     * 超过界限的数据样本在做分析的时候会生成不大于此界限的样本
     */
    private int limitationGetSampleGroup = 10000;

    /**
     * 获取生成样本的界限
     * 超过界限的数据样本在做分析的时候会生成不大于此界限的样本
     *
     * @return 获取生成样本的界限
     */
    public int getLimitationGetSampleGroup() {
        return limitationGetSampleGroup;
    }

    /**
     * 设置该样本的界限
     * 超过界限的数据样本在做分析的时候会生成不大于此界限的样本
     * 最少为2
     *
     * @param limitationGetSampleGroup 样本界限
     */
    public void setLimitationGetSampleGroup(int limitationGetSampleGroup) {
        this.limitationGetSampleGroup = (limitationGetSampleGroup < 2) ? 2 : limitationGetSampleGroup;
    }

    /**
     * 构造器同时传入一个筛选器
     * @param sorter    筛选器
     */
    public SortReporter(Sorter sorter) {
        this.sorter = sorter;
    }

    /**
     * 此方法返回十种排序方法的时间数据。
     *
     * @param indexables 可排序对象
     * @return 结果数组
     */
    public double[] report(Indexable[] indexables) {
        if (sortTagList.isEmpty()) {
            int[] tmp=Sorter.getSortTag();
            for (int i : tmp) {
                sortTagList.add(i);
                System.out.println(i);
            }

        }
        if (sorter == null) throw new NullPointerException();
        if (indexables.length > limitationGetSampleGroup) indexables = getSampleGroup(indexables, limitationGetSampleGroup);
        double[] report = new double[Sorter.getSortTag().length];
        for (int index = 0; index < report.length; index++) {
            sorter.sort(indexables, (int)sortTagList.get(index));
            report[index] = sorter.time;
        }
        return report;
    }


    /**
     * 此方法用于返回整个对象组的混乱程度
     * 对象的数字比较来自实现了Indexable接口的类中对于此的实现
     * 返回的紊乱程度越大说明越紊乱，0代表空数组
     *
     * @return 紊乱程度
     */
    public int disorderLevel() {
        if (sorter == null || sorter.tmp == null) throw new NullPointerException();
        int disorder = 0;
        Indexable min = sorter.tmp[0], max = sorter.tmp[0];
        for (int i = 0; i < sorter.tmp.length - 1; i++) {
            if (sorter.tmp[i].compareTo(min) == 1) min = sorter.tmp[i];
            if (max.compareTo(sorter.tmp[i]) == 1) max = sorter.tmp[i];
            disorder += Math.abs(sorter.tmp[i].getLevel() - sorter.tmp[i + 1].getLevel());
        }
        disorder /= (max.getLevel() - min.getLevel());
        return disorder;
    }

    /**
     * 由于样本量过大，有些时候会导致样本检测延误太长
     * 此方法用于对样本进行等步长抽样
     *
     * @param indexables 原本样本
     * @param maxAmount  理想的最大数
     * @return 限制样本
     */
    public Indexable[] getSampleGroup(Indexable[] indexables, int maxAmount) {
        int step = indexables.length / maxAmount + 1;
        // System.out.println("step:"+step);
        Indexable[] tmp = new Indexable[(int) Math.floor(indexables.length / step)];
        // System.out.println("tmp:"+tmp.length);
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = indexables[i * step];
        }
        return tmp;
    }
}
