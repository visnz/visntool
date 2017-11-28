import base.IO.log.Log;
import base.IO.log.PrintLogcat;
import base.file.Encryption;
import base.file.FileProxy;
import base.tester.DataCreator;
import core.VisntoolCore;
import myextends.Event;
import myextends.Excited;

import java.io.File;
import java.util.Random;

/**
 * 此类用于测试
 * Created by zyvis on 2017/5/28.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        VisntoolCore.install();//此行用于启动初始化
        VisntoolCore.setPrintLog(PrintLogcat.defaultFilepath);

        Excited.setupFile(new File("./excited"));

        Log.d(Excited.getEvent().toString());
        Log.d(Excited.getEvent().toString());
        Log.d(Excited.getEvent().toString());
        Log.d(Excited.getEvent().toString());
        Log.d(Excited.getEvent().toString());
        for (int i = 0; i < 20; i++) {

            Excited.createEvent(new Event(Encryption.randomHexKeyCreate(10), "guaguagua", Event.LuckyLevel.getRandom(), "HUFIAHUFIA"));
        }


        //PortListener portListener=new PortListener(10086);
//        Thread.sleep(5000);
//        portListener.disconnect();

//FatherWindow.setup();
//        FatherWindow.singleSetup(FatherWindow.DTCtag);

    }
}
