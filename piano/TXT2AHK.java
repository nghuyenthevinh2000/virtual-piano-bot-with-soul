import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class TXT2AHK{

    private TXT2AHK(String original_dir, String target_dir, int time_pause_short, int time_pause){
        this.original_dir = original_dir;
        this.target_dir = target_dir;
        this.time_pause_short = time_pause_short;
        this.time_pause = time_pause;
    }

    public static void convert(String original_dir, String target_dir, int time_pause_short, int time_pause){
        TXT2AHK obj = new TXT2AHK(original_dir, target_dir,time_pause_short, time_pause);
        Scanner in = obj.read_txt_file();
        obj.write_to_ahk(in);
        in.close();
    }
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.print("original file name:");
        String original_dir = String.format("./Online piano sheet/%s.txt",in.nextLine());
        System.out.print("target file name:");
        String target_dir = String.format("./key bindings/%s.ahk",in.nextLine());
        System.out.print("time pause_short:");
        int time_pause_short = Integer.parseInt(in.nextLine());
        System.out.print("time pause_normal:");
        int time_pause_normal = Integer.parseInt(in.nextLine());
        in.close();

        TXT2AHK.convert(original_dir, target_dir,time_pause_short,time_pause_normal);
    }

    private String original_dir;
    private String target_dir;

    private int time_pause_short;
    private int time_pause;
    private int range = 50;
    private static final String TIME_SHORT = "s";
    private static final String TIME_NORMAL = "n";
    private static final String TIME_LONG = "l";

    private static final String DO_NOT_ORDER = "false";

    private Map<Integer,Integer> na_char = new HashMap<Integer,Integer>();
    {
        na_char.put(33,1);
        na_char.put(64,2);
        na_char.put(36,4);
        na_char.put(37,5);
        na_char.put(94,6);
        na_char.put(42,8);
        na_char.put(40,9);
    }
    
    //module read txt file
    private Scanner read_txt_file(){
        Scanner in = null;
        try{
            File txt = new File(original_dir);
            in = new Scanner(txt);
        }catch(FileNotFoundException e){
            System.out.println("Dude, cannot find such original dir");
        }

        return in;
    }

    //module conversion and write directly to target_dir
    private void write_to_ahk(Scanner in){
        int counter = -1;
        try(FileWriter writer = new FileWriter(target_dir)){
            writer.write("$F1::\n");

            while(in.hasNext()){
                counter++;
                String node = in.next();

                boolean not_order = false;
                List<String> orders = handlerManager(node);
                for(String order : orders){
                    //two time skips for an unwanted send and an unwanted sleep
                    if(order.equals(TXT2AHK.DO_NOT_ORDER)){
                        not_order = true;
                        continue;
                    }

                    if(not_order){
                        not_order = false;
                        continue;
                    }

                    writer.write(order);
                    writer.write("\n");
                }
            }
        }catch(IOException e){
            System.out.println(String.format("Cannot write at character number %d",counter));
        }
    }

    //manage different types of input
    private List<String> handlerManager(String note){
        List<String> result = new LinkedList<String>();

        if(note.equals("|")){
            System.out.println("find long note");
            result.add(time_handler(TXT2AHK.TIME_LONG));
            return result;
        }

        int len = note.length();
        if(len == 1){
            result.add(order_handler(one_note_handler(note.charAt(0))));
        }else{
            result = consecutive_notes_handler(note);
        }

        result.add(time_handler(TXT2AHK.TIME_NORMAL));
        return result;
    }

    //handler for pressing one note
    private String one_note_handler(char note){
        System.out.println("note = " + note);
        String order = "";
        
        int code = (int)note;
        //NON ALPHABET CHARACTER
        if(na_char.containsKey(code)){
            order = String.format("{Shift Down}{%d}{Shift Up}",na_char.get(code));
            return order;
        }

        //ALPHABET CHARACTER HANDLER ONLY
        if(65 <= code && code <= 90){
            order = String.format("{Shift Down}{%c}{Shift Up}",Character.toLowerCase(note));
        }else{
            order = String.format("{%c}",note);
        }

        System.out.println("order = " + order);
        return order;
    }

    //handler for consecutive notes
    private List<String> consecutive_notes_handler(String note){
        List<String> result = new LinkedList<String>();
        
        char[] arr = note.toCharArray();
        boolean is_mso_detected = false;
        String mso_chain = "";
        for(char i : arr){
            //MSO detection
            if(i == '['){
                is_mso_detected = true;
                continue;
            }
            if(i == ']'){
                is_mso_detected = false;
                result.add(order_handler(mso_handler(mso_chain)));
                result.add(time_handler(TXT2AHK.TIME_SHORT));
                mso_chain="";
                continue;
            }
            if(is_mso_detected){
                mso_chain += i;
                continue;
            }
            //end MSO detection

            result.add(order_handler(one_note_handler(i)));
            result.add(time_handler(TXT2AHK.TIME_SHORT));
        }
        result.remove(result.size()-1);

        return result;
    }

    //handler for pressing multiple notes simultaneously
    private String mso_handler(String note){
        System.out.println("mso_note =" + note);
        StringBuilder order = new StringBuilder();
        
        char[] arr = note.toCharArray();
        for(int i = 0;i<arr.length;i++){
            order.append(one_note_handler(arr[i]));
        }

        return order.toString();
    }

    private String order_handler(String order){
        if(order.equals(" ") || order.equals("")){
            System.out.println("NULL ORDER PROBLEM");
            return TXT2AHK.DO_NOT_ORDER;
        }

        return String.format("send %s",order);
    }

    //handler for time
    //there is only three types
    //fast,normal,slow
    private String time_handler(String type){
        int time_range = time_pause;
        switch(type){
            case TXT2AHK.TIME_SHORT:
                time_range = time_pause_short;
                break;
            case TXT2AHK.TIME_LONG:
                time_range = time_pause + 150;
                break;
        }

        int time = (int)Math.round((time_range-range) + Math.random()*range*2);
        return String.format("sleep, %d",time);
    }

}