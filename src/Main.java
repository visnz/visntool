import base.IO.log.Log;
import base.IO.log.PrintLogcat;
import base.file.Encryption;
import base.file.FileProxy;
import base.tester.DataCreator;
import core.VisntoolCore;

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

        FileProxy p1=new FileProxy(new File("./test1"),false);
        //設置文件代理

        String hex=p1.readByHex();
        //以二進制讀取

        String after=Encryption.calCheckSum_Hex(hex,20);
        //傳入二進制文本和步長

        Log.d(after);



        //PortListener portListener=new PortListener(10086);
//        Thread.sleep(5000);
//        portListener.disconnect();

//FatherWindow.setup();
//        FatherWindow.singleSetup(FatherWindow.DTCtag);

    }
}
