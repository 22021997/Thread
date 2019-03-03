import java.io.*;

public class Climate {

    public static void main(String[] args) {

        Temperature temperature = new Temperature();
        Consumer consumer = new Consumer();

        temperature.tProdtemp.start();
        consumer.tConmin.start();
        try {
            temperature.tProdtemp.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            consumer.tConmin.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }



}
