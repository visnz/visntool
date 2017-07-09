package data;

/**
 * 实现了此接口的对象，必须可以被随机创建
 * 返回一个泛型对象
 * {@code class Equipment implements data.RandomCreatable<Equipment>}
 * 对自身进行随机创建声明
 *
 * 调用的时候直接创建一个母体，由此可以创建一定类型的母体来区分，
 * 比如在构造函数中传递某一参数指定改创造器为某某创造器
 * 在该类的具体实现中进行区分构造
 * 调用母体的{@link #randomCreate(Object, Object)}来创建
 *
 * Created by zyvis on 2017/5/24.
 */
public interface RandomCreatable<T> {
    /**
     * 该接口用于返回实现的类可以完成的新的对象
     * 随机创建对象，传入种子与限制
     * 由实现类中进行具体实现
     *
     * @param seed          种子
     * @param limination    限制
     * @return              指定泛型
     */
    T randomCreate(Object seed, Object limination);
}
