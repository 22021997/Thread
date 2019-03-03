import java.io.*;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Consumer {

    protected float min = 10000;

    protected BlockingQueue<Float> queue1 = new ArrayBlockingQueue<>(510);
    protected BlockingQueue <Float> queue2 = new ArrayBlockingQueue<>(510);
    protected BlockingQueue <Float> queue3 = new ArrayBlockingQueue<>(510);
    protected Object lock = new Object();

    Thread tConmin = new Thread(new Runnable() {
        @Override
        public void run() {
            consumerMin();
        }
    });


    public  float consumerMin ()
    {
        while (true)
        {
            synchronized (lock)
            {
                while (queue1.size()==0)
                {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            float value = 0;
            try {
                value = queue1.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (value < min)
            {
                min = value;
            }
            lock.notify();
            return min;
        }
    }

    static String Readfile (String fname)
    {

        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream(fname);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        String resultString = "";
        try {
            while ((strLine = br.readLine()) != null) {
                resultString+= strLine + "\r\n";
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultString;
    }

}
