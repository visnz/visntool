package bank;

import base.IO.log.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by zyvis on 2017/6/8.
 */
public class FatherWindow{
    public static final String DTCtag="dtc_setup";
    private JPanel panel1;
    private JButton Button1;
    private JPanel base;
    private JPanel left;
    public static void setup() throws Exception {
        JFrame frame = new JFrame("FatherWindow");
        FatherWindow fatherWindow=new FatherWindow();
        frame.setContentPane(fatherWindow.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(300,400);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        setupWindows();
        fatherWindow.buttonEvent();
    }

    private static void setupWindows() throws Exception {
        Log.v();

    }

    public static void singleSetup(String arg) throws Exception {
        switch (arg){
            case DTCtag:DTC.startup();break;
        }

    }

    private FatherWindow() {

    }
    private void buttonEvent()throws Exception{
        Button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DTC.startup();
                } catch (AWTException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
