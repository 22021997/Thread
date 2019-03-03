import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Temperature extends Consumer {


     String result = Readfile("src/temperature.txt");
     String value[] = result.split("\n");


    Thread tProdtemp = new Thread(new Runnable() {
        @Override

        public void run() {
            producerTemp();
        }
    });

    public void producerTemp ()
    {

        for (int i = 0 ; i <504 ; i++)
        {
            synchronized (lock)
            {
                while (queue1.size() == 100)
                {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                queue1.put(Float.parseFloat(value[i]));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                queue2.put(Float.parseFloat(value[i]));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                queue3.put(Float.parseFloat(value[i]));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.notify();
            System.out.println("Min value: " + consumerMin());
        }

    }

}
