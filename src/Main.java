import base.IO.log.Log;
import base.IO.log.PrintLogcat;
import core.VisntoolCore;
public class Main{
    public static void main(String[] args) throws Exception {
        VisntoolCore.install();//此行用于启动初始化
        VisntoolCore.setPrintLog(PrintLogcat.defaultFilepath);
        Log.d("helloworld");

    }
}



