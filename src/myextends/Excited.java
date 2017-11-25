package myextends;

import base.file.Encryption;
import base.file.EndofFile;
import base.file.FileProxy;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by zyvis on 2017/11/25.
 */
public class Excited {
    private static FileProxy filestart=null;
    private static ArrayList<Event> arrayList=new ArrayList<>();

    public static Event getEvent()  {
        if(filestart==null)return null;
        return arrayList.get(new Random().nextInt(arrayList.size()));
    }
    public static void setupFile(File file){
        filestart=new FileProxy(file,false);
        while(true){
            try {
                loadEvent(filestart.readByLine());
            } catch (EndofFile endofFile) {
                break;
            }
        }
    }

    private static void loadEvent(String s) {
        arrayList.add(Event.toEvent(s));
    }

    public static void createEvent(Event event){
        for (Event e : arrayList) {
            if(e.getId()==event.getId()){
                event.setId(Encryption.randomHexKeyCreate(10));
            }
        }
        filestart.writeByLine(Event.toJson(event));
    }

}
