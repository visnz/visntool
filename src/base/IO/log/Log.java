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

    private static class LogInside {
        private final static Log log = new Log();
    }

    /**
     * 隐藏构造器，不允许创建对象
     */
    private Log() {
        logcats = new LinkedList<>();
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
     * 此标识符指定为最基本的日志输出
     */
    public final static Level verbose = new Level("Verbose", 100);
    public final static Color verbose_color = Color.gray;
    /**
     * 此标识符指定为调试信息的输出
     */
    public final static Level debug = new Level("Debug", 200);
    public final static Color debug_color = Color.blue;
    /**
     * 此标识符指定为对需要搜集的用户信息的区分
     */
    public final static Level info = new Level("Info;", 300);
    public final static Color info_color = Color.green;
    /**
     * 此标识符指定为可能存在潜在风险的地方
     */
    public final static Level warn = new Level("Warn", 400);
    public final static Color warn_color = Color.yellow;
    /**
     * 此标识符指定为对错误信息的指示
     * 通常包含在错误catch异常等语句中
     */
    public final static Level error = new Level("Error", 500);
    public final static Color error_color = Color.red;

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
        LogInside.log.boardcast(new SingleLog(Log.verbose, getTag(), getTag()+" run ."));
    }

    public static void v(String msg) {
        LogInside.log.boardcast(new SingleLog(Log.verbose, getTag(), msg));
    }
    public static void d(String msg) {
        LogInside.log.boardcast(new SingleLog(Log.debug, getTag(), msg));
    }

    public static void i(String msg) {
        LogInside.log.boardcast(new SingleLog(Log.info, getTag(), msg));
    }

    public static void w(String msg) {
        LogInside.log.boardcast(new SingleLog(Log.warn, getTag(), msg));
    }

    public static void e(String msg) {
        LogInside.log.boardcast(new SingleLog(Log.error, getTag(), msg));
    }

    public static void d(String msg, Throwable tr) {
        LogInside.log.boardcast(new SingleLog(Log.debug, getTag(), msg, tr));
    }

    public static void w(String msg, Throwable tr) {
        LogInside.log.boardcast(new SingleLog(Log.warn, getTag(), msg, tr));
    }

    public static void e(String msg, Throwable tr) {
        LogInside.log.boardcast(new SingleLog(Log.error, getTag(), msg, tr));
    }

    public static void v(String tag, String msg) {
        LogInside.log.boardcast(new SingleLog(Log.verbose, tag, msg));
    }

    public static void d(String tag, String msg) {
        LogInside.log.boardcast(new SingleLog(Log.debug, tag, msg));
    }

    public static void i(String tag, String msg) {
        LogInside.log.boardcast(new SingleLog(Log.info, tag, msg));
    }

    public static void w(String tag, String msg) {
        LogInside.log.boardcast(new SingleLog(Log.warn, tag, msg));
    }

    public static void e(String tag, String msg) {
        LogInside.log.boardcast(new SingleLog(Log.error, tag, msg));
    }

    public static void d(String tag, String msg, Throwable tr) {
        LogInside.log.boardcast(new SingleLog(Log.debug, tag, msg, tr));
    }

    public static void w(String tag, String msg, Throwable tr) {
        LogInside.log.boardcast(new SingleLog(Log.warn, tag, msg, tr));
    }

    public static void e(String tag, String msg, Throwable tr) {
        LogInside.log.boardcast(new SingleLog(Log.error, tag, msg, tr));
    }

    /**
     * 此方法将所有传入的Log扩散，广播到Log在记列表中的所有监听器
     * 可以使用{@link #addLogcat(Logcat)}添加监听器
     * 通过{@link #getLogcats()}获取这个列表
     *
     * @param singleLog
     */
    private synchronized void boardcast(SingleLog singleLog) {
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
                Log.verbose,
                Log.debug,
                Log.info,
                Log.warn,
                Log.error
        };
    }

}
