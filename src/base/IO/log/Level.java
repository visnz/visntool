package base.IO.log;

/**
 * Created by zyvis on 2017/6/1.
 */
public final class Level {
    public final String name;
    public final int level;

    public Level(String name, int level) {
        this.name = name;
        this.level = Math.max(level,0);
    }

    @Override
    public String toString() {
        return name;
    }
}
