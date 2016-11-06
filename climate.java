import java.io.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class climate {


    private static BlockingQueue <Float> queueTemp = new ArrayBlockingQueue<>(510);
    static float minValue = 10000;
    static float maxValue = -10000;
    static String result = Readfile("src/temperature.txt");
    static String value[] = result.split("\n");


    public static void main(String[] args)throws InterruptedException {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                producerTemp();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                consumerMin();
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                consumerMax();
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();



    }



    public  static void producerTemp ()
    {

        try {

            Thread.sleep(1);
            for (int i = 0 ; i <504 ; i++)
            {
                try
                {
                    queueTemp.put(Float.parseFloat(value[i]));
                }

                catch (NumberFormatException e)
                {

                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }




    }

    public static void consumerMin ()
    {
        while (true)
        {
            try {

                Thread.sleep(100);
                float value = queueTemp.take();

                if (value <= minValue)
                {
                    minValue = value;
                }
                System.out.println("Min value: " + minValue);
                System.out.println("Queue size (min): " + queueTemp.size());

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    public static void consumerMax ()
    {
        while (true)
        {
            try {

                Thread.sleep(100);
                float value = queueTemp.take();



                if (value >= maxValue)
                {
                    maxValue = value;
                }
                System.out.println("Max value: " + maxValue);
                System.out.println("Queue size (max): " + queueTemp.size());


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

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


