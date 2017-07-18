import base.IO.log.Log;
import net.PortListener;

import java.util.Random;

/**
 * 此类用于测试
 * Created by zyvis on 2017/5/28.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        VisntoolCore.install();//此行用于启动初始化
        Random a=new Random();
        Log.d(a.nextDouble()+" out");
        PortListener portListener=new PortListener(10086);
        Thread.sleep(5000);
        portListener.disconnect();

//FatherWindow.setup();
//        FatherWindow.singleSetup(FatherWindow.DTCtag);

    }
}
