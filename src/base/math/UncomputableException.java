package base.math;

/**
 * 当数据出现不可计算的异常时候抛出
 * 溢出计算、负数索引都可以
 * 负数索引通常使用{@link IndexOutOfBoundsException}
 *
 * Created by zyvis on 2017/5/25.
 */
public class UncomputableException extends Exception {

    public UncomputableException(String message) {
        super(message);
    }
}
