import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import jdk.jfr.events.FileWriteEvent;
public class file{
    public static void main(String[] args) {
        String fn="./ex00.txt";
        //write
        try{
            FileWriter writer= new FileWriter(fn);
            FileWriter append= new FileWriter(fn,true);
            writer.write("hello foo!\n");
            writer.close();
            append.write("hello bar!\n");
            append.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}