/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.petah.common.util.profiler.Profiler;

/**
 *
 * @author Petah
 */
public class ThreadManager {

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static Future run(final String name, final Runnable runnable) {
        return executorService.submit(new Runnable() {

            public void run() {
                Profiler.start(ThreadManager.class, name + ".run()");
                runnable.run();
                Profiler.stop(ThreadManager.class, name + ".run()");
            }
        });
    }

//    public static Future run(Runnable runnable, final Future waitFor) {
//        if (waitFor != null && !waitFor.isDone()) {
//            run(new Runnable() {
//
//                public void run() {
//                    try {
//                        waitFor.get();
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(ThreadManager.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (ExecutionException ex) {
//                        Logger.getLogger(ThreadManager.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            });
//        }
//        return run(runnable);
//    }
    public static void shutDown() {
        executorService.shutdownNow();
    }
}
