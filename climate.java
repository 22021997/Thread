import java.io.*;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class climate {


    private static BlockingQueue <Float> queue1 = new ArrayBlockingQueue<>(510);
    private static BlockingQueue <Float> queue2 = new ArrayBlockingQueue<>(510);
    private static BlockingQueue <Float> queue3 = new ArrayBlockingQueue<>(510);


    static float minValue = 10000;
    static float maxValue = -10000;
    static float sum = 0;
    static float avg = 0;
    static float count = 0 ;
    static volatile boolean runVar = true;

    static String result = Readfile("src/temperature.txt");
    static String value[] = result.split("\n");


    public static void main(String[] args)throws InterruptedException {

        Thread tProdtemp = new Thread(new Runnable() {
            @Override
            public void run() {
                producerTemp();
            }
        });


        Thread tConmin = new Thread(new Runnable() {
            @Override
            public void run() {
                consumerMin();
            }
        });

        Thread tConmax = new Thread(new Runnable() {
            @Override
            public void run() {
                consumerMax();
            }
        });

        Thread tConavg = new Thread(new Runnable() {
            @Override
            public void run() {
                consumerAvg();
            }
        });

        tProdtemp.start();
        tConmin.start();
        tConmax.start();
        tConavg.start();

        tProdtemp.join();
        tConmin.join();
        tConmax.join();
        tConavg.join();

        // HUMIDITY

         queue1.clear();
         queue2.clear();
         queue3.clear();
         String result = Readfile("src/humidity.txt");
         String value[] = result.split("\n");
         minValue = 10000;
         maxValue = -10000;
         sum = 0;
         avg = 0;
         count = 0 ;

         tProdtemp.start();
         tConmin.start();
         tConmax.start();
         tConavg.start();

         tProdtemp.join();
         tConmin.join();
         tConmax.join();
         tConavg.join();



    }



    public  static void producerTemp ()
    {
        int counter=0;
        Random random = new Random();
        int num = random.nextInt(6)+1;

            try {

                Thread.sleep(1);
                for (int i = 0 ; i <504 ; i++)
                {
                    try
                    {
                        queue1.put(Float.parseFloat(value[i]));
                        queue2.put(Float.parseFloat(value[i]));
                        queue3.put(Float.parseFloat(value[i]));
                    }

                    catch (NumberFormatException e)
                    {

                    }

                }
                while (runVar)
                {
                    counter++;
                    float minValue = consumerMin();
                    System.out.println("Min Value: " + minValue);

                    float maxValue = consumerMax();
                    System.out.println("Max Value: " + maxValue);

                    float avgValue = consumerAvg();
                    System.out.println("Avg Value: " + avgValue);
                    System.out.println();


                    if (counter == 499)
                        runVar = shutdown();
                }


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }







    }



    public static float consumerMin ()
    {
        Random random = new Random();
        int num = random.nextInt(3);

        while (true)
        {
            try {

                Thread.sleep(1);
                float value = queue1.take();
                System.out.println(value);
                if (value <= minValue)
                {
                    minValue = value;
                }
//                System.out.println("Min value: " + minValue);
//                System.out.println("Queue size (min): " + queue1.size());

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return minValue;
        }
    }

    public static float consumerMax ()
    {
        Random random = new Random();
        int num = random.nextInt(3);

        while (true)
        {
            try {

                Thread.sleep(1);
                float value = queue2.take();
                if (value >= maxValue)
                {
                    maxValue = value;
                }
//                System.out.println("Max value: " + maxValue);
//                System.out.println("Queue size (max): " + queue2.size());


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return maxValue;
        }
    }

    public static float consumerAvg ()
    {
        Random random = new Random();
        int num = random.nextInt(3);

        while (true)
        {
            try {

                Thread.sleep(1);
                count++;
                float value = queue3.take();
                sum+=value;
                avg = sum/count;
//                System.out.println("Sum: " + sum);
//                System.out.println("Average value: " + avg);
//                System.out.println("Queue size (avg): " + queue2.size());


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return avg;
        }
    }

    public static boolean shutdown()
    {
        System.out.println("shut down");
        runVar = false;
        return runVar;
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


