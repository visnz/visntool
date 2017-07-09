package bank;

import base.IO.log.Log;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 这代码写得我都看不懂也是服了
 * 继承Thread是为了可以进行多线程
 * Created by zyvis on 2017/5/23.
 */
public final class DTC extends Thread{
    private static Date date=new Date();
    private int timeDuration,index;
    private boolean run=false;
    private boolean metionRestart=true;

    private boolean hadBeenInstalled=false;
    Robot robot = new Robot();
    Rectangle r;


    public void setFolderAddress(String folderAddress) {
        this.folderAddress = folderAddress;
    }

    private String folderAddress=null;
    private JComboBox comboBox1;
    private JButton applyButton;
    private JPanel result;
    private JTextArea textArea1;
    private JButton startButton;
    private JButton pauseButton;
    private JButton fileButton;
    private JPanel controlPanel;
    private JPanel timePanel;
    private JPanel controller;
    private JPanel totally;
    private JButton seleButton;
    private JCheckBox CheckBox;
    private JPanel checkPanel;
    private static JFrame frame;
    private static DTC mainobj;
    private int originIndex=100000;

    protected static void startup() throws AWTException {
        Log.v();
        if(mainobj==null)mainobj=new DTC();
        if(mainobj.hadBeenInstalled){frame.setVisible(true);return;}
        frame= new JFrame("Desktop Time-lapse Cameraman");
        frame.setContentPane(mainobj.totally);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLocation(1920/2,1080/2);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        mainobj.hadBeenInstalled=true;
        mainobj.start();
    }
    protected DTC() throws AWTException {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        r= new Rectangle(d.width, d.height);
        index=originIndex;
        init(10);
        log("UPDATE", update());
    }

    protected String update() {
        return new String("" +
                "ver 1.1" + "\n"+
                "新增了可以在原有终点继续延时的功能" +"\n"+
                "添加了更新日志的功能" +"\n"+
                "" +
                "");
    }

    protected void init(int defaultTime){
        int[] time=new int[]{1,2,4,10,20,30,60,120};
        for(int t:time)
        comboBox1.addItem(t);
        comboBox1.setSelectedItem(defaultTime);
        setTimeDuration(defaultTime);


        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tmp=(int)comboBox1.getSelectedItem();
                setTimeDuration(tmp);
                log(" LOG ","Apply the new timeDuration : "+tmp+" s , "+(0.4*tmp)+" Hours to 1 min");
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!run) {
                    if (folderAddress == null) {
                        log("ERROR", "Folder Address is null, Please Select the Folder Address");
                        stopprint();
                        return;
                    }
                    if(CheckBox.isSelected()){
                        index=getEndIndex();
                    }else index=originIndex;
                    run = true;
                    log(" LOG ", "Start Screen Print");
                }
            }
        });
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(run) {
                    stopprint();
                    log(" LOG ", "Pause Screen Print");
                }
            }
        });
        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Runtime.getRuntime().exec("cmd /c start explorer " + folderAddress);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        seleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc=new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//????????
                try{
                    fc.showOpenDialog(null);
                    String tmp=fc.getSelectedFile().getPath();
                    setFolderAddress(tmp+"\\");
                    log(" LOG ","select new folder : "+tmp+"\\");
                }
                catch(HeadlessException head){
                    log("ERROR","select new folder failed");
                }
            }
        });
    }

    private int getEndIndex() {
        if(folderAddress==null) {
            log("ERROR", "Folder Address is null, Please Select the Folder Address");
            return index;
        }
        int tmpindex=index;
        File tmp;
        do {
            tmp = new File(folderAddress + tmpindex++ + ".jpg");
          //  log(folderAddress+tmpindex+".jpg");
        }while(tmp.exists());
        return --tmpindex;
    }

    public void log(String type,String... ss){
        if(ss.length!=1) {
            String tmp="";
            for (String s:ss)
                tmp=tmp+s;
            textArea1.setText(textArea1.getText()+"\n[ "+type+" ] : "+tmp);
            return;
        }
        textArea1.setText(textArea1.getText()+"\n[ "+type+" ] : "+ss[0]);

    }
    protected void setTimeDuration(int timeDuration){
        this.timeDuration = timeDuration*1000;
    }
    protected void stopprint(){
        run=false;
        metionRestart=true;
    }
    @Override
    public void run() {
        Log.v();
        for(;;) {
            if (run) {
                BufferedImage bufferedImage = robot.createScreenCapture(r);
                try {
                    ImageIO.write(bufferedImage, "jpg", new File(folderAddress + (index++) + ".jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                date=new Date();
                Log.d(" LOG ","Output Screen Print : "+ date+" , File Name : "+(index-1)+".jpg");
                log(" LOG ","Output Screen Print : "+ date+" , File Name : "+(index-1)+".jpg");
            }
            try {
                if (!run&&metionRestart) {
                    log(" LOG ","Waiting for Waking Up");
                    metionRestart=false;
                }
                Thread.sleep(timeDuration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (index % 500 == 0) System.gc();
        }
    }
}
