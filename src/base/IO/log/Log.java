package base.IO.log;


import java.awt.*;
import java.util.Collection;
import java.util.LinkedList;
/**
 * https://developer.android.com/reference/android/util/Log.html
 * 此类包含了五种类型的日志输出方法，源自android.util.log类的仿生
 * Created by zyvis on 2017/5/28.
 */
public final class Log {

    /**
     * 枚举了五个对象
     */
    public enum Level{
        None,Verbose,Debug,Info,Warn,Error;
        public  String name;
        public int level;
        public Color color;
        private static void init(){
            None.name="none";
            None.color=Color.gray;
            None.level=600;
            Verbose.name="verbose";
            Verbose.level=100;
            Verbose.color=Color.white;
            Debug.name="debug";
            Debug.level=200;
            Debug.color=Color.blue;
            Info.name="info";
            Info.level=300;
            Info.color=Color.green;
            Warn.name="warn";
            Warn.level=400;
            Warn.color=Color.yellow;
            Error.name="error";
            Error.level=500;
            Error.color=Color.red;
        }
    }

    private static class LogInside {
        private final static Log log = new Log();
    }

    /**
     * 隐藏构造器，不允许创建对象
     */
    private Log() {
        logcats = new LinkedList<Logcat>();
        Level.init();
    }

    ;

    /**
     * 用一个容器存放logcat的列表
     */
    private Collection<Logcat> logcats;

    public static Collection<Logcat> getLogcats() {
        return LogInside.log.logcats;
    }


    /**
     * 此方法调用线程的堆栈追踪，可以查询到调用这个方法的类和方法
     *
     * @return 返回当前调用这个方法的类和方法
     */
    public static String getNowTag() {
        StackTraceElement tmp = Thread.currentThread().getStackTrace()[2];
        return tmp.getClassName() + ":" + tmp.getMethodName();
    }

    /**
     * 此方法调用线程的堆栈追踪，可以查询到调用这个方法的类和方法
     * 与公开方法不同，此私有方法用于创建SingleLog时候调用
     * 在堆栈追踪上多增了一层嵌套
     *
     * @return 返回当前调用这个方法的类和方法
     */
    private static String getTag() {
        StackTraceElement tmp = Thread.currentThread().getStackTrace()[3];
        return tmp.getClassName() + ":" + tmp.getMethodName();
    }


    public static void v() {
        LogInside.log.boardcast(new SingleLog(Level.Verbose, getTag(), getTag()+" run ."));
    }

    public static void v(String msg) {
        LogInside.log.boardcast(new SingleLog(Level.Verbose, getTag(), msg));
    }
    public static void d(String msg) {
        LogInside.log.boardcast(new SingleLog(Level.Debug, getTag(), msg));
    }

    public static void i(String msg) {
        LogInside.log.boardcast(new SingleLog(Level.Info, getTag(), msg));
    }

    public static void w(String msg) {
        LogInside.log.boardcast(new SingleLog(Level.Warn, getTag(), msg));
    }

    public static void e(String msg) {
        LogInside.log.boardcast(new SingleLog(Level.Error, getTag(), msg));
    }

    public static void d(String msg, Throwable tr) {
        LogInside.log.boardcast(new SingleLog(Level.Debug, getTag(), msg, tr));
    }

    public static void w(String msg, Throwable tr) {
        LogInside.log.boardcast(new SingleLog(Level.Warn, getTag(), msg, tr));
    }

    public static void e(String msg, Throwable tr) {
        LogInside.log.boardcast(new SingleLog(Level.Error, getTag(), msg, tr));
    }

    public static void v(String tag, String msg) {
        LogInside.log.boardcast(new SingleLog(Level.Verbose, tag, msg));
    }

    public static void d(String tag, String msg) {
        LogInside.log.boardcast(new SingleLog(Level.Debug, tag, msg));
    }

    public static void i(String tag, String msg) {
        LogInside.log.boardcast(new SingleLog(Level.Info, tag, msg));
    }

    public static void w(String tag, String msg) {
        LogInside.log.boardcast(new SingleLog(Level.Warn, tag, msg));
    }

    public static void e(String tag, String msg) {
        LogInside.log.boardcast(new SingleLog(Level.Error, tag, msg));
    }

    public static void d(String tag, String msg, Throwable tr) {
        LogInside.log.boardcast(new SingleLog(Level.Debug, tag, msg, tr));
    }

    public static void w(String tag, String msg, Throwable tr) {
        LogInside.log.boardcast(new SingleLog(Level.Warn, tag, msg, tr));
    }

    public static void e(String tag, String msg, Throwable tr) {
        LogInside.log.boardcast(new SingleLog(Level.Error, tag, msg, tr));
    }

    /**
     * 此方法将所有传入的Log扩散，广播到Log在记列表中的所有监听器
     * 可以使用{@link #addLogcat(Logcat)}添加监听器
     * 通过{@link #getLogcats()}获取这个列表
     *
     * @param singleLog
     */
    private synchronized void boardcast(final SingleLog singleLog) {
        LogInside.log.logcats.forEach((i) -> i.filter(singleLog));

    }

    /**
     * 此方法用于往Log中添加Logcat监听器，可扩展。
     *
     * @param logcat Logcat的实体类
     */
    public static void addLogcat(Logcat logcat) {
        LogInside.log.logcats.add(logcat);
    }


    /**
     * 此方法归档Log支持的原始五个等级
     * 返回为对象组
     *
     * @return Loglevel的对象组
     */
    public static Level[] getAllLevel() {
        return new Level[]{
                Level.Verbose,
                Level.Debug,
                Level.Info,
                Level.Warn,
                Level.Error
        };
    }

}
