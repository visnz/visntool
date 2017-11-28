package base.math;

import java.math.BigInteger;

/**
 * 此类主要为静态函数调用，在Math的基础上进行自我工具包需要的制作
 * 里面包含练习过程所写、制作需要所写等，基于基础数学计算的方法
 * <p>
 * Created by zyvis on 2017/5/25.
 */
public final class MathMe {


    /**
     * 小范围整型快速幂
     * 方法用于快速计算整型的多次方
     * 基数只限于int，pow用于次方指示
     * 如果需要大基数运算可以转用{@link #power(BigInteger, int)}
     *
     * @param base 基数
     * @param pow  次数
     * @return 答案
     */
    public static int power(int base, int pow) {
        int result = 1;
        while (pow > 0) {
            if (pow % 2 == 1) {
                result = result * base;
            }
            pow /= 2;
            base = base * base;
        }
        return result;
    }

    /**
     * 整型快速幂
     * 方法用于快速计算整型的多次方，提供大整型
     * 基数上支持大整型的输入，pow用于次方指示
     * 如果是小基数请使用{@link #power(int, int)}
     *
     * @param base 基数
     * @param pow  次数
     * @return 答案
     */
    public static BigInteger power(BigInteger base, int pow) {
        BigInteger result = BigInteger.ONE;
        while (pow > 0) {
            if (pow % 2 == 1) {
                result = result.multiply(base);
            }
            pow /= 2;
            base = base.multiply(base);
        }
        return result;
    }

    /**
     * 此方法为希尔排序的步长计算方法，
     * <p>
     * 步长为斐波那契數列除去0和1將剩餘的數以黃金分割比的兩倍的冪進行運算得到的數列
     * ( 1, 9, 34, 182, 836, 4025, 19001, 90358)
     * 由输入的index获取数据，0获得0，1获得1，2获得9。
     * 该步长公式适合用于大规模的阵列运算
     * 小阵列运算可使用
     * {@link #shellSorting_SedgewickStep(int)}
     *
     * @param index 索引，要求index大于等于0
     * @return 步长
     * @throws IndexOutOfBoundsException
     */
    public static int shellSorting_FibonacciStep(int index) throws IndexOutOfBoundsException {
        if (index < 0) throw new IndexOutOfBoundsException();
        if (index == 0) return 0;
        return (int) Math.pow(MathMe.fibonacci(++index).doubleValue(), 2 / MathMe.getGoldenSection());
    }

    /**
     * 此方法为希尔排序的步长计算方法，
     * 步长序列为Sedgewick提出的(1, 5, 19, 41, 109,...)
     * 由输入的index获取数据，0获得0，1获得1，2获得5。
     * 适用于小阵列排序，大阵列排列的步长可使用
     * {@link #shellSorting_FibonacciStep(int)}
     *
     * @param index 索引，要求index大于等于0
     * @return 步长
     * @throws IndexOutOfBoundsException
     */
    public static int shellSorting_SedgewickStep(int index) throws IndexOutOfBoundsException {
        if (index < 0) throw new IndexOutOfBoundsException();
        if (index == 1 || index == 0) return index;
        if (index % 2 != 0) {
            index = index / 2;
            return (int) (9 * (java.lang.Math.pow(4.0, index) - java.lang.Math.pow(2.0, index)) + 1);
        } else {
            index = index / 2 - 1;
            return (int) (java.lang.Math.pow(2.0, index + 2) * (java.lang.Math.pow(2.0, index + 2) - 3) + 1);
        }
    }

    /**
     * 此方法用于计算斐波那契数列（矩阵快速幂求法）
     * 其中index应大于等于0
     * 由于数字过大，由BigInteger承载并返回
     * 调用{@link #matrixMul(BigInteger[][], BigInteger[][])}矩阵乘法
     * 其中包含了对{@link FormatErrorException}的抓取
     * 代码源自 http://blog.csdn.net/zhufenghao/article/details/70059671
     *
     * @param index 第index个斐波那契数，index应大于等于0
     * @return 该数的值
     * @throws IndexOutOfBoundsException
     */

    public static BigInteger fibonacci(int index) throws IndexOutOfBoundsException {
        if (index < 0) throw new IndexOutOfBoundsException();
        if (index-- == 0) return BigInteger.ZERO;
        BigInteger one = BigInteger.ONE;
        BigInteger zero = BigInteger.ZERO;
        BigInteger[][] result = new BigInteger[][]{{one, zero},{ zero, one}};
        BigInteger[][] A = new BigInteger[][]{{one, one},{one, zero}};
        while (index > 0) {
            if (index % 2 == 1) {
                try {
                    result = matrixMul(result, A);
                } catch (FormatErrorException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            index /= 2;
            try {
                A = matrixMul(A, A);
            } catch (FormatErrorException e) {
                e.printStackTrace();
                return null;
            }
        }
        return result[0][0];
    }


    /**
     * 此方法返回黄金分割比的数值，
     * (sqrt(5)-1)/2 = 0.618...
     * 可由倒数获得1.618
     *
     * @return 黄金分割比
     */
    public static double getGoldenSection() {
        return (Math.pow(5, 0.5) - 1) / 2;
    }

    /**
     * 封闭构造器，只允许静态调用
     */
    private MathMe() {
    }

    /**
     * 此方法用于求两个大数矩阵之间的对应乘积
     * 不适用于大型阵列运算，主要针对大数生产（斐波那契数列）的运算
     * 代码源自 http://blog.csdn.net/zhufenghao/article/details/70059671
     *
     * @param A A矩阵
     * @param B B矩阵
     * @return 相乘矩阵结果
     * @throws FormatErrorException
     */
    public static BigInteger[][] matrixMul(BigInteger[][] A, BigInteger[][] B) throws FormatErrorException {
        int Arows = A.length;
        int Acols = A[0].length;
        int Brows = B.length;
        int Bcols = B[0].length;
        if (Acols != Brows) throw new FormatErrorException();
        BigInteger[][] R = new BigInteger[Arows][Bcols];
        for (int i = 0; i < Arows; i++) {
            for (int j = 0; j < Bcols; j++) {
                BigInteger tmp = BigInteger.ZERO;
                for (int k = 0; k < Acols; k++) {
                    tmp = tmp.add(A[i][k].multiply(B[k][j]));
                }
                R[i][j] = tmp;
            }
        }
        return R;
    }

}
