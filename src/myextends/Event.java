package myextends;

import java.util.Random;

/**
 * Created by zyvis on 2017/11/25.
 */
public class Event {
    public void setId(String id) {
        this.id=id;
    }

    public enum  LuckyLevel{
        SOBAD("SOBAD"),BAD("BAD"),SOSO("SOSO"),GOOD("GOOD"),SOGOOD("SOGOOD");

        String string;
        LuckyLevel(String s) {
            this.string=s;
        }

        @Override
        public String toString() {
            return string;
        }
        public static LuckyLevel getLevel(String key){
            switch (key){
                case "SOBAD":return SOBAD;
                case "BAD":return BAD;
                case "SOSO":return SOSO;
                case "GOOD":return GOOD;
                case "SOGOOD":return SOGOOD;
                default:return SOSO;
            }
        }
        public  static LuckyLevel getRandom(){
            int t=new Random().nextInt(5);
            switch (t){
                case 1:return SOBAD;
                case 2:return BAD;
                case 3:return SOSO;
                case 4:return GOOD;
                case 0:return SOGOOD;
                default:return SOSO;
            }
        }
    }
    private String id;
    private String title;
    private LuckyLevel luckyLevel;
    private String description;

    public Event(String id, String title, LuckyLevel luckyLevel, String description) {
        this.id = id;
        this.title = title;
        this.luckyLevel = luckyLevel;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LuckyLevel getLuckyLevel() {
        return luckyLevel;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", luckyLevel=" + luckyLevel +
                ", description='" + description + '\'' +
                '}';
    }

    public static String toJson(Event e){
        return "{"+e.id+","+e.title+","+e.luckyLevel.toString()+","+e.description+"}";
    }
    public static Event toEvent(String s){
        s=s.substring(1,s.length()-1);
        String[] stemp=s.split(",");
        if(stemp.length>4){
            String tmp="";
            for (int i = 4; i < stemp.length; i++) {
                tmp+=stemp[i];
                tmp+=",";
            }
            return new Event(stemp[0],stemp[1],LuckyLevel.getLevel(stemp[2]),tmp);
        }else{
            return new Event(stemp[0],stemp[1],LuckyLevel.getLevel(stemp[2]),stemp[3]);
        }


    }

}
