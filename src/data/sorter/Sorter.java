package data.sorter;

import base.MathMe;
import data.Indexable;

/**
 * 此类包含了九种比较常见的排序方法，分别用int作为标识符
 * 提供了升序降序、模式选择、打印选择等公开方法
 * 通常创建对象的时候可以把需要排序的对象组一起扔进来
 * {@code Sorter sorter = new Sorter<T>(group); }
 * 可调用sort()方法进行运算
 * <p>
 * 此类可以装载到一个{@link data.sorter.SortReporter}中，
 * 用于记载分析对于同一数据组。不同算法的时间，以此寻求适合该数组的数据。
 * SortReporter和一个Sorter同时被封装在一个
 * {@link data.sorter.AutomaticSorter}，提供最优化的排序方法。
 * <p>
 * 该类扩展性很差
 * 可能需要重写
 *
 * 顺手记一下：翻译方法：
 * 即：在实现Comparable接口的时候，compare方法为：
 * 主体比客体大，返回1，客体比主体大，返回-1，相等返回0
 * Created by zyvis on 2017/5/24.
 */
public class Sorter<T extends Indexable> {
    /**
     * 无排序标志，排序的话直接返回原对象
     */
    public static final int NullSort = 0x00;
    /**
     * 插入排序标志
     */
    public static final int InsertSort = 0x01;
    /**
     * 选择排序标志
     */
    public static final int SelectSort = 0x02;
    /**
     * 冒泡排序标志
     */
    public static final int BubbleSort = 0x03;
    /**
     * 快速排序标志
     */
    public static final int QuickSort = 0x04;
    /**
     * 希尔排序标志
     */
    public static final int ShellSort = 0x11;
    /**
     * 堆排序标志
     */
    public static final int HeapSort = 0x12;
    /**
     * 双向冒泡排序标志（鸡尾酒排序）
     */
    public static final int CocktailSort = 0x13;
    /**
     * 迭代归并排序标志
     */
    public static final int MergeSort = 0x14;
    /**
     * 递归归并排序标志
     */
    public static final int RecursiveMergeSort = 0x15;

    /**
     * 初始的筛选器的 排序方法
     * 默认设置为无排序，需要通过{@link #setSortMode(int)}设置筛选模式
     */
    private int sortMode = NullSort;
    /**
     * 初始的筛选器 顺序方式
     * 默认设置为降序，可以通过{@link #setDecrease(boolean)}修改顺序
     */
    private boolean Decrease = true;

    public int getShellSort_LargeComputeBoundary() {
        return shellSort_LargeComputeBoundary;
    }

    public void setShellSort_LargeComputeBoundary(int shellSort_LargeComputeBoundary) {
        this.shellSort_LargeComputeBoundary = shellSort_LargeComputeBoundary;
    }

    /**
     * 此整型用来指定希尔排序中规定为大规模的阵列
     * 小于此数值的将使用Sedgewick步长序列进行排序
     * 大于等于此将用斐波那契步长进行排序
     */
    private int shellSort_LargeComputeBoundary = 10000;

    /**
     * 此开关指定是否将排序结果进行打印
     * 方法定义到{@link #print()}
     * 可以通过覆写完成输出重定义
     */
    private boolean printAfterSort = false;

    /**
     * 此变量组用来存放临时的数据对象
     * 子类可见，可用于print方法的重新编写
     */
    protected Indexable[] tmp = null;

    /**
     * 此数据在每次执行sort后会更新一次，记录筛选所需要的时间
     * {@link #sort(Indexable[])}
     */
    protected long time;

    public boolean isPrintAfterSort() {
        return printAfterSort;
    }

    public void setPrintAfterSort(boolean printAfterSort) {
        this.printAfterSort = printAfterSort;
    }

    public boolean isDecrease() {
        return Decrease;
    }

    public void setDecrease(boolean decrease) {
        Decrease = decrease;
    }

    public int getSortMode() {
        return sortMode;
    }

    public void setSortMode(int sortMode) {
        this.sortMode = sortMode;
    }

    /**
     * 默认构造器，设置为空排序，升序，无对象组
     */
    public Sorter() {
    }

    /**
     * 对象组构造器，设置为空排序，升序。
     *
     * @param indexables 对象组
     */
    public Sorter(Indexable[] indexables) {
        tmp = indexables;
    }

    /**
     * 此方法为核心的入口方法，按照目前指定的排序模式进行
     *
     * @param indexableObjs 传入一个实现了Indexable接口的对象组
     * @param sortMode      传入指定的排序模式
     * @return 返回已经排序的结果
     */
    public Indexable<T>[] sort(Indexable[] indexableObjs, int sortMode) {
        this.setSortMode(sortMode);
        return sort(indexableObjs);
    }

    /**
     * 此方法为核心的入口方法，按照已经设置好的排序模式进行
     * 其中也提供了计时器计时排序时间{@link #time}
     * 排序完后的{@link #print()}方法由{@link #printAfterSort}开关决定
     *
     * @param indexableObjs 传入一个实现了Indexable接口的对象组
     * @return 返回已经排序的结果
     */
    public Indexable<T>[] sort(Indexable[] indexableObjs) {
        tmp = indexableObjs;
        long startpoint = System.nanoTime();
        switch (sortMode) {
            case NullSort:
                tmp = indexableObjs;
                break;
            case InsertSort:
                insertSort();
                break;
            case SelectSort:
                SelectSorting();
                break;
            case BubbleSort:
                bubbleSort();
                break;
            case QuickSort:
                quickSort();
                break;
            case ShellSort:
                shellSort();
                break;
            case HeapSort:
                heapSort();
                break;
            case CocktailSort:
                cocktailSort();
                break;
            case MergeSort:
                mergeSort();
                break;
            case RecursiveMergeSort:
                recursiveMergeSort();
                break;
        }
        long endpoint = System.nanoTime();
        time = endpoint - startpoint;
        System.out.println((sortMode)+" "+(time));
        if (printAfterSort) print();
        return tmp;
    }

    /**
     * 此方法完成两个Indexable对象的交换
     * @param indexA    待交换对象
     * @param indexB    待交换对象
     */
    private void swap(int indexA, int indexB) {
        Indexable tmpobj = tmp[indexA];
        tmp[indexA] = tmp[indexB];
        tmp[indexB] = tmpobj;
    }

    /**
     * 此方法由开关{@link #printAfterSort}控制
     * 确认打印时候可打印出筛选的结果，并汇报运行时间。
     */
    protected void print() {
        for (Indexable c : tmp)
            System.out.println(c.toString());
        System.out.println("runtime : " + time);
    }


    /**
     * 插入排序法：
     * 从第二个元素开始，遍历后面的元素，
     * 如果比当前的小，则交换并排在该元素前方（相邻元素交换）
     * 以此保证最开始的元素是最小的
     */
    private void insertSort() {
        for (int i = 1; i < tmp.length; i++) {
            for (int j = i; j > 0; j--) {
                if (tmp[j].compareTo(tmp[j - 1]) == (Decrease ? 1 : -1)) {
                    swap(j, j - 1);
                }
            }
        }
    }

    /**
     * 选择排序法：
     * 从第一个元素开始向后遍历，依次与已排序的最大元素做比较
     * 获取后面最小的元素放在最前面
     */
    private void SelectSorting() {
        for (int i = 0; i < tmp.length - 1; i++) {
            for (int j = i + 1; j < tmp.length; j++) {
                if (tmp[j].compareTo(tmp[i]) == (Decrease ? 1 : -1)) {
                    swap(i, j);
                }
            }
        }
    }

    /**
     * 冒泡排序法：
     * 从第一个元素开始，相邻两个元素相比，将小的元素换到前面来
     * 每次递归向后推一个元素，以此保证最前面的元素为最小的
     */
    private void bubbleSort() {
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp.length - i - 1; j++) {
                if (tmp[1 + j].compareTo(tmp[j]) == (Decrease ? 1 : -1)) {
                    swap(j, j + 1);
                }
            }
        }
    }

    /**
     * 快速排序法：
     * 此方法为调度入口，里面包含了一个带参数的可以递归运行的快速排序法。
     */
    private void quickSort() {
        quickSortInside(0, tmp.length - 1);
    }

    /**
     * 此方法用于递归调用执行快速排序法，传入数组的开始和结束，
     * 由此取最后一个数据作为基准点，左右夹逼进行二分排序，
     * 再将两部分分别传入递归方法运行。
     *
     * @param start 开始点
     * @param end   结束点
     */
    private void quickSortInside(int start, int end) {
        if (start > end) return;
        int pivot = end;
        int left = start, right = end - 1;
        while (left < right) {
            while (tmp[left].compareTo(tmp[pivot]) == (Decrease ? 1 : -1) && left < right) left++;
            //未超过区间的情况下，从左向右寻找比pivot大的对象的索引
            while (tmp[right].compareTo(tmp[pivot]) != (Decrease ? 1 : -1) && left < right) right--;
            //未超过区间的情况下，从右向左寻找比pivot小的对象的索引
            swap(left, right);
            //将两者交换，循环继续寻找
        }
        if (tmp[left].compareTo(tmp[pivot]) != (Decrease ? 1 : -1))
            //此时整个区间的left和right正常为同一个值，一个空位留给pivot
            //若大于等于pivot，则移到后面去，再跟随后面继续排序
            swap(left, pivot);
        else
            left++;
        //若不正常就右移left，进行新的排序
        quickSortInside(start, left - 1);
        quickSortInside(left + 1, end);
    }

    /**
     * 希尔排序法
     * 希尔排序的步长计算公式由{@link base.MathMe}
     * 此方法适用于小规模阵列的计算，大规模阵列计算使用
     * 跨越某个步长进行间隔比较排序，由此占用尽量少的计算量
     */
    private void shellSort() {
        int step = 1, i, j, len = tmp.length;
        Indexable temp;
        //设置一个步长索引，寻找最大步长
        int index;
        for (index = 1; step < len; index++) {
            step = (len < shellSort_LargeComputeBoundary ?
                    MathMe.shellSorting_SedgewickStep(index) :
                    MathMe.shellSorting_FibonacciStep(index));
        }
        for (; step > 0;
             step = (len < shellSort_LargeComputeBoundary ?
                     MathMe.shellSorting_SedgewickStep(--index) :
                     MathMe.shellSorting_FibonacciStep(--index))
                )
            //由最大步长开始寻找，排序
            for (i = step; i < len; i++) {
                temp = tmp[i];
                for (j = i - step; j >= 0 && temp.compareTo(tmp[j]) == (Decrease ? 1 : -1); j -= step)
                    tmp[j + step] = tmp[j];
                tmp[j + step] = temp;
            }
    }

    /**
     * 堆排序法
     * 算法参考wiki：https://zh.wikipedia.org/wiki/%E5%A0%86%E6%8E%92%E5%BA%8F#Java
     * 原理解释：http://bubkoo.com/2014/01/14/sort-algorithm/heap-sort/
     */
    private void heapSort() {
        /*
         *  第一步：将数组堆化
         *  beginIndex = 第一个非叶子节点。
         *  从第一个非叶子节点开始即可。无需从最后一个叶子节点开始。
         *  叶子节点可以看作已符合堆要求的节点，根节点就是它自己且自己以下值为最大。
         */
        int len = tmp.length - 1;
        int beginIndex = (len - 1) >> 1;
        for (int i = beginIndex; i >= 0; i--) {
            heapSortMaxHeapify(i, len);
        }
        /*
         * 第二步：对堆化数据排序
         * 每次都是移出最顶层的根节点A[0]，与最尾部节点位置调换，同时遍历长度 - 1。
         * 然后从新整理被换到根节点的末尾元素，使其符合堆的特性。
         * 直至未排序的堆长度为 0。
         */
        for (int i = len; i > 0; i--) {
            swap(0, i);
            heapSortMaxHeapify(0, i - 1);
        }

    }

    /**
     * 调整索引为 index 处的数据，使其符合堆的特性。
     * 此方法为堆排序的内部运行算法部件
     *
     * @param index 需要堆化处理的数据的索引
     * @param len   未排序的堆（数组）的长度
     */
    private void heapSortMaxHeapify(int index, int len) {
        int li = (index << 1) + 1; // 左子节点索引
        int ri = li + 1;           // 右子节点索引
        int cMax = li;             // 子节点值最大索引，默认左子节点。

        if (li > len) return;       // 左子节点索引超出计算范围，直接返回。
        if (ri <= len && tmp[li].compareTo(tmp[ri]) == (Decrease ? 1 : -1)) // 先判断左右子节点，哪个较大。
            cMax = ri;
        if (tmp[index].compareTo(tmp[cMax]) == (Decrease ? 1 : -1)) {
            swap(cMax, index);      // 如果父节点被子节点调换，
            heapSortMaxHeapify(cMax, len);  // 则需要继续判断换下后的父节点是否符合堆的特性。
        }
    }

    /**
     * 鸡尾酒排序法
     * 左右向中逼近，每次对某一指定索引的左/右部分遍历比较
     * 时间复杂度上与冒泡排序相同
     */
    private void cocktailSort() {
        int i, left = 0, right = tmp.length - 1;
        Indexable temp;
        while (left < right) {
            for (i = left; i < right; i++)
                if (tmp[i + 1].compareTo(tmp[i]) == (Decrease ? 1 : -1)) {
                    temp = tmp[i];
                    tmp[i] = tmp[i + 1];
                    tmp[i + 1] = temp;
                }
            right--;
            for (i = right; i > left; i--)
                if (tmp[i].compareTo(tmp[i - 1]) == (Decrease ? 1 : -1)) {
                    temp = tmp[i];
                    tmp[i] = tmp[i - 1];
                    tmp[i - 1] = temp;
                }
            left++;
        }
    }

    /**
     * 归并排序算法（迭代法）
     * 详见wiki：https://zh.wikipedia.org/wiki/%E5%BD%92%E5%B9%B6%E6%8E%92%E5%BA%8F
     * 思路为两个下标元素互相比较选小的放入合并空间，合并子排序
     * 会比较省空间
     */
    private void mergeSort() {
        int len = tmp.length;
        Indexable[] result = new Indexable[len];
        int block, start;

        // 原版代码的迭代次数少了一次，没有考虑到奇数列数组的情况
        for (block = 1; block < len * 2; block *= 2) {
            for (start = 0; start < len; start += 2 * block) {
                int low = start;
                int mid = (start + block) < len ? (start + block) : len;
                int high = (start + 2 * block) < len ? (start + 2 * block) : len;
                //两个块的起始下标及结束下标
                int start1 = low, end1 = mid;
                int start2 = mid, end2 = high;
                //开始对两个block进行归并排序
                while (start1 < end1 && start2 < end2) {
                    result[low++] = tmp[start1].compareTo(tmp[start2]) == (Decrease ? 1 : -1) ? tmp[start1++] : tmp[start2++];
                }
                while (start1 < end1) {
                    result[low++] = tmp[start1++];
                }
                while (start2 < end2) {
                    result[low++] = tmp[start2++];
                }
            }
            Indexable[] temp = tmp;
            tmp = result;
            result = temp;
        }
    }

    /**
     * 归并排序算法（递归法）
     * 将相邻的两个对象进行归并操作，形成子序列，重复递归获得更大的元素
     */
    private void recursiveMergeSort() {
        int len = tmp.length;
        Indexable[] result = new Indexable[len];
        mergeSortRecursive(tmp, result, 0, len - 1);

    }

    /**
     * 归并排序算法（递归法）的子方法
     * 传入原数据组和临时存放的数据组，指定上下标可以进行范围内的排序
     *
     * @param tmp    原数据组
     * @param result 临时数据组
     * @param start  上标
     * @param end    下标
     */
    private void mergeSortRecursive(Indexable[] tmp, Indexable[] result, int start, int end) {
        if (start >= end)
            return;
        int len = end - start, mid = (len >> 1) + start;
        int start1 = start, end1 = mid;
        int start2 = mid + 1, end2 = end;
        mergeSortRecursive(tmp, result, start1, end1);
        mergeSortRecursive(tmp, result, start2, end2);
        int k = start;
        while (start1 <= end1 && start2 <= end2)
            result[k++] = tmp[start1].compareTo(tmp[start2]) == (Decrease ? 1 : -1) ? tmp[start1++] : tmp[start2++];
        while (start1 <= end1)
            result[k++] = tmp[start1++];
        while (start2 <= end2)
            result[k++] = tmp[start2++];
        for (k = start; k <= end; k++)
            tmp[k] = result[k];
    }

    /**
     * 此方法放回整个包含了所有筛选模式的标志组
     * @return  标志组
     */
    public static int[] getSortTag() {
        return new int[]{
                NullSort,
                InsertSort,
                SelectSort,
                BubbleSort,
                QuickSort,
                ShellSort,
                HeapSort,
                CocktailSort,
                MergeSort,
                RecursiveMergeSort
        };
    }

}
