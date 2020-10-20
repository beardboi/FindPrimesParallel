package cl.ucn.disc.hpc;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


public class FindPrime implements Runnable {

    // The number to check
    private final long number;

    // The arraylist that contains the prime numbers
    public static ArrayList<Long> primeList = new ArrayList<>();

    // The atomic counter (is being used by multiple threads concurrently)
    private final static AtomicInteger counter = new AtomicInteger(0);

    /**
     * Constructor
     * @param number to check primality
     */
    public FindPrime(final long number) {
        this.number = number;
    }

    public static int getPrimes() {
        return counter.get();
    }

    public static ArrayList<Long> getPrimeList() {
        return primeList;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        // for prime numbers, increment the counter (i++)
        if(isPrime(this.number)) {
            counter.getAndIncrement();
            primeList.add(this.number);
        }
    }


    /**
     * Function to check primality given a number
     * @param number to check
     * @return TRUE for a prime number, FALSE if not.
     */
    public static boolean isPrime(final long number) {

        // Check if the number is negative (border validation)
        if (number < 0) {
            throw new IllegalArgumentException("Negative numbers can't be prime");
        }

        // 1 is not a prime number
        if (number == 1) {
            return false;
        }

        // Check from 2 to n/2
        for (int i = 2; i <= number / 2; i++) {
            if ((number % i) == 0) {
                return false;
            }
        }

        return true;

    }

}