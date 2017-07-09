import base.IO.log.DefaultLogcat;
import base.IO.log.Log;
import base.IO.log.Logcat;

/**
 * 此类用于在最开始的时候完成所有部件的初始化。
 * Created by zyvis on 2017/6/1.
 */
public class VisntoolCore {
    /**
     * 已初始化的标志
     */
    private static boolean installed = false;

    /**
     * 私有构造函数 禁止创建
     */
    private VisntoolCore() {
    }

    /**
     * Core包含了一个defaultLogcat对系统输出做最基础的输出
     */
    private static Logcat defaultLogcat = new DefaultLogcat();

    /**
     * Core包含了一个defaultLogcat对系统输出做最基础的输出
     *
     * @return 返回defaultLogcat
     */
    public static Logcat getDefaultLogcat() {
        return defaultLogcat;
    }

    /**
     * 执行此方法进行初始化
     */
    public static synchronized void install() {
        if (installed) return;
        Log.addLogcat(defaultLogcat);
        Log.d("default logcat has been installed");

        //IOrouter =new IORouter(defaultLogcat.outputStream);

        installed = true;
        Log.d("Core has been installed");
    }

}
