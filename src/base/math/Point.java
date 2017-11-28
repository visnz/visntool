package base.math;

/**
 * 此类包含xy两个变量，用于数学计算
 * 安全角度使用protected，同时便于子类扩展
 *
 * Created by zyvis on 2017/5/26.
 */
public class Point {
    protected int x,y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Point(int x, int y) {

        this.x = x;
        this.y = y;
    }
}
