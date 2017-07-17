package base.IO.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zyvis on 2017/7/17.
 */
public class PrintLogcat extends DefaultLogcat{
    public boolean isPrintLog() {
        return printLog;
    }

    public void setPrintLog(boolean printLog) {
        this.printLog = printLog;
    }

    /**
     * 此开关用于指定是否讲Log打印到本地文件
     */
    protected boolean printLog;


    public PrintLogcat(File path) {
        path.mkdirs();
        File file;
        try {
            file=new File(path+"\\Main_"+String.valueOf(System.currentTimeMillis()).substring(5)+".log");
            file.createNewFile();
            outputStream=new FileOutputStream(file);
        } catch (IOException e) {
            Log.e("could not create the file output stream to the logfile",e);
        }

    }

    @Override
    public void print(SingleLog singleLog) {
        try {
            outputStream.write((singleLog.toString()+"\n").getBytes());
        } catch (IOException e) {
            Log.e("the log could not be printed to file",e);
        }
    }
}
