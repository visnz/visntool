package data.sorter;
import data.Indexable;

/**
 * 备注：通常随机生成的数组，混乱程度=数目*波动范围/3
 * 本来设计思路是将此作为静态的单例，以此所有使用过sorter的对象组
 * 都会转换成数学模型进行自适应演算，然后发现自己对于具体的
 * 最优排序方法还缺少实践等，就先保留了一些接口
 *
 * 默认使用归并排序、堆排序和希尔排序三种
 *
 * Created by zyvis on 2017/5/26.
 */
public class AutomaticSorter<T extends Indexable>{
    /**
     * 包含一个私有的内部类，用于藏匿宿主类对象。
     */
    private static class ASI {
        private static final AutomaticSorter<Indexable> AS=new AutomaticSorter<>();
    }

    /**
     * 宿主类对象的最佳筛选方案
     */
    private int best=Sorter.NullSort;
    /**
     * 用于寄放单一的筛选器
     */
    private Sorter<T> sorter=new Sorter<T>();
    /**
     * 筛选的评测器
     */
    private SortReporter sortReporter=new SortReporter(sorter);

    /**
     * 静态调用，线程同步，汇报输入的对象
     * 作用与评测器的相同接口的方法一样
     *
     * @param indexables    需要排序的对象
     * @return              现有排序方法的耗时
     */
    public synchronized static double[] report(Indexable[] indexables) {
        ASI.AS.searchBset(indexables);
        return ASI.AS.sortReporter.report(indexables);
    }

    /**
     * 静态调用，线程同步，汇报输入的对象
     * @param indexables    需要排序的对象
     * @return              返回排序结果
     */
    public static Indexable[] getSort(Indexable[] indexables){
        ASI.AS.searchBset(indexables);
        return ASI.AS.sorter.sort(indexables,ASI.AS.best);
    }

    public static boolean isIncrease() {
        return ASI.AS.sorter.isDecrease();
    }

    public static void setIncrease(boolean increase) {
        ASI.AS.sorter.setDecrease(increase);
    }

    /**
     * 此方法等待改写
     *
     * 这段代码是我自己写过的最烂的代码没有之一！！
     *
     * @return
     */
    private void searchBset(Indexable[] sample){
        if(ASI.AS.best!=Sorter.NullSort)return;
        sorter.sort(sample,Sorter.MergeSort);
        double tmptime=sorter.time;
        best=sorter.MergeSort;
        sorter.sort(sample,Sorter.HeapSort);
        if(sorter.time<tmptime)best=sorter.HeapSort;
        tmptime=sorter.time;
        sorter.sort(sample,Sorter.ShellSort);
        if(sorter.time<tmptime)best=sorter.SelectSort;
        System.out.println("best : "+best);
    }

}
