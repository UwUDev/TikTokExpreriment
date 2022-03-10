package me.uwu.tiktok.experiment;

import com.google.gson.Gson;
import me.uwu.tiktok.experiment.struct.Config;

import java.io.FileReader;
import java.io.IOException;

public class Tester {
    private static int good, fail, gcMomento;

    public static void main(String[] args) throws IOException {
        Config config = new Gson().fromJson(new FileReader("config.json"), Config.class);

        System.out.println("\u001B[36m" + config.getWorkers() + " workers with " + config.getIterations() + " iterations for total of " + (config.getIterations() * +config.getWorkers()) + " packets.");
        System.out.print("\u001B[33mPress enter to start...");
        System.in.read();

        for (int i = 0; i < config.getWorkers(); i++) {
            new Thread(() -> {
                Worker worker = new Worker(config);
                for (int j = 0; j < config.getIterations(); j++) {
                    try {
                        if (worker.exec())
                            good++;
                        else fail++;
                    } catch (Exception e) {
                        fail++;
                        //e.printStackTrace();
                    }
                    System.out.print("\r\u001B[35mAdded " + good + " views and failed " + fail + " request(s).");
                }
            }).start();
        }

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if ((good + fail) - gcMomento > 1000) {
                    gcMomento = good + fail;
                    System.gc(); //techinque de shlag
                }
            }
        }).start();
    }
}
