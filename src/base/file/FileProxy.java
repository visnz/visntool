package base.file;

import base.IO.log.Log;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by zyvis on 2017/11/21.
 */
public class FileProxy {
    private File file;
    private BufferedReader reader;
    private BufferedWriter writer;

    public boolean isOverride() {
        return override;
    }

    public void setOverride(boolean override) {
        this.override = override;
    }

    private boolean override;
    public FileProxy(File file,boolean override) {
        this.override=override;
        this.file = file;
        if(!file.exists()) {
            try {
                Log.w("file not found");
                file.createNewFile();
            } catch (IOException e) {
                Log.w("cloud not create new file", e);
                this.file=null;
                return;
            }
        }
        try {
            reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,!override)));
        } catch (FileNotFoundException e) {
            Log.w("create reader or writer failed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String readByLine()throws EndofFile{
        if(file==null)Log.d("fileproxy has null file");
        try {
            String ret=reader.readLine();
            if(ret!=null)
                return ret;
        } catch (IOException e) {
            Log.w("cannot read by line cuz IO exception",e);
        }
        throw new EndofFile();
    }
    public String[] readByLineToArray(){
        ArrayList<String> tmp=new ArrayList<>();
        while(true){
            try {
                tmp.add(readByLine());
            } catch (EndofFile endofFile) {
                break;
            }
        }
        String[] ret = new String[tmp.size()];
        tmp.toArray(ret);
        return ret;
    }
    public boolean writeByLine(String[] args) {
        for(String i:args)
            writeByLine(i);
        return true;
    }
    public synchronized boolean writeByLine(String arg) throws NullPointerException{
        if(file==null)Log.d("fileproxy has null file");
        try {
            writer.write(arg);
            writer.newLine();
            writer.flush();
            return true;
        } catch (IOException e) {
            Log.w("cannot write by line ",e);
            return false;
        }
    }
    public static boolean pipTo(FileProxy fileProxy1,FileProxy fileProxy2){
        while(true) {
            try {
                fileProxy2.writeByLine(fileProxy1.readByLine());
            } catch (EndofFile endofFile) {
                break;
            }catch (NullPointerException e){
                break;
            }
        }
        return true;
    }
    public boolean destory(){
        try {
            reader.close();
            writer.close();
        } catch (IOException e) {
            Log.w("can not close the stream",e);return false;
        }
        return true;
    }
    public boolean clearFile(){
        try {
            file.createNewFile();
        } catch (IOException e) {
            Log.w("can not clear file",e);
        }
        return true;
    }
    public String readByHex(){
        String all="";
        String[] allfile=readByLineToArray();
        for (String single : allfile) {
            byte[] tmp = single.getBytes();
            for (byte t : tmp)
                all+= Integer.toHexString(t);
        }
        return all;
    }

}
