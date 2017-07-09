import bank.FatherWindow;

/**
 * 此类用于测试
 * Created by zyvis on 2017/5/28.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        VisntoolCore.install();//此行用于启动初始化

        FatherWindow.setup();
//        FatherWindow.singleSetup(FatherWindow.DTCtag);

    }
}
