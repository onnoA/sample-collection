package com.onnoa.shop.common.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FixedThreadExecutor {
    private static ExecutorService instance = null;

    private FixedThreadExecutor() {
    }

    public static void execute(Runnable runnable) {
        instance.execute(runnable);
    }

    public static <V> Future<V> submit(Callable<V> callable) {
        return instance.submit(callable);
    }

    static {
        int processors = Runtime.getRuntime().availableProcessors();
        int size = processors <= 1 ? 4 : processors * 2;
        instance = Executors.newFixedThreadPool(size);
    }
}
