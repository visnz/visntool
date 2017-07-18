package net;

import base.IO.log.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 此类废
 * socket部分要重新设计一下……
 * Created by zyvis on 2017/7/18.
 */
public class PortListener extends Thread{
    protected ServerSocket receiver;
    protected Socket socket =null;
    boolean mention=true;
    public PortListener(int port) throws IOException {
        receiver=new ServerSocket(port);
        Log.d("PortListener start, port : "+port);
        this.start();
    }

    @Override
    public synchronized void run() {
        super.run();


        for(;;) {
            //System.out.println("run main");
            if (!isLink()) {
                if(mention) {
                    Log.d("no socket is linking, wait for connection");
                    mention=false;
                }
                try {
                    socket = receiver.accept();
                    if(isLink())Log.d("connect ! socket link in : "+socket.getInetAddress());
                    mention=true;
                } catch (IOException e) {
                    Log.e("could not accept from socket receiver");
                }
            }else{
                if(mention) {
                    Log.d("socket is link in");
                    mention=false;
                }
            }
        }
    }
    public void disconnect() throws IOException {
        if(isLink()) {
            Log.d("port listener disconnect the socket");
            receiver.close();
            socket.close();
            socket = null;
            mention=true;
            //System.out.println(isLink()+"disconnect");
        }
    }
    public boolean isLink(){
        return socket!=null;
    }
}
