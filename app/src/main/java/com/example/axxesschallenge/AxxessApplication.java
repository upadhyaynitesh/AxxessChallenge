package com.example.axxesschallenge;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AxxessApplication extends Application {
   public final ExecutorService executorService = Executors.newFixedThreadPool(2);

}
