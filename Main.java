
import java.io.*;
import java.util.ArrayList;


public class Main {


    private ArrayList<String> file;


    public static void main (String[] args) throws IOException {
        Main m = new Main();
        MessageProducer messageProducer;


        try {
        if (args.length != 0) {m.file =  m.reader(args[0]);
        messageProducer = new MessageProducer(m.file);
        m.thread(messageProducer);


            InputStreamReader br = new  InputStreamReader(System.in);
            int s = br.read();
            if(s>0){
                messageProducer.finish();
            }
            br.close();

        }
        else
            System.out.println("Имя файла не верно или файл отсутствует");


        } catch (IOException e) {
            System.out.println("Имя файла не верно или файл отсутствует");
        }




    }



    public  ArrayList<String> reader (String filename) throws IOException {
        String s;
        ArrayList<String> list = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(filename));
        while (br.ready()){
            s = br.readLine();
            list.add(s);
        }
        br.close();

        return list;

    }

    public void thread (Runnable runnable) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.start();
    }
}
