package cl.ucn.disc.hpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.*;

public class Main {

    // The limit number to check.
    private static final long LIMIT = 1000000;

    // The max number of threads.
    private static final Integer maxThreads = 16;

    // The minimum number of threads.
    private static final Integer minThreads = 1;

    // MAIN

    public static void main(String[] args) {

        //
        for (int i = minThreads; i <= maxThreads ; i++) {

            FindPrimeParallel(i);

        }

    }


    /*
       This function search for all the prime numbers given a number of threads.
     */
    public static void FindPrimeParallel(int numThreads) {

        // Create and start a stopwatch to measure the time of execution.
        long start_time = System.currentTimeMillis();

        // Create a logger for the main class
        final Logger logger = LoggerFactory.getLogger(Main.class);

        // Limit time for the execution
        long time_out = 2;

        // Create a pool of threads
        final ExecutorService pool = Executors.newFixedThreadPool(numThreads);

        // Execute a task for every number between 1 and the limit.
        for (long i = 1; i < LIMIT; i++) {
            pool.submit(new FindPrime(i));
        }

        // The Executor Service will no receive more task.
        pool.shutdown();

        // The calculation has a time limit of 2 hrs
        try {
            // If all the task has been completed, return a log with info
            if (pool.awaitTermination(time_out, TimeUnit.HOURS)) {

                long end_time = System.currentTimeMillis();
                long execution_time = end_time - start_time;
                // Log with info (number of threads used and the execution time.)
                logger.debug("Threads: {}, Primes founded in {} ms.", numThreads, execution_time);

                // Calculate speedup and efficiency

                // Return the execution time.
                //return execution_time;

            } else {
                logger.info("The lapse of execution time was exceeded.");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //return start_time;

    }

/*    public static void calculateSpeedup(long newTime, long oldTime) {



    }*/


}

