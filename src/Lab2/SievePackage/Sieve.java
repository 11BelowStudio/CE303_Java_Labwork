package Lab2.SievePackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;

public class Sieve implements Iterable<Integer> {

    private boolean[] isPrime; //array with a boolean representation for the prime numbers
    private int upperBound; //upper bound for the array

    /**
     * Creates a sieve using the specified starting upper bound
     * @param startingBound starting upper bound
     */
    public Sieve(int startingBound){
        makeSieve(startingBound);
    }

    /**
     * Makes a sieve with a starting upper bound of 10
     */
    public Sieve(){
        makeSieve(10);
    }

    /**
     * Constructs a sieve, using the 'Sieve of Eratosthenes' method
     * @param upperBound the upper bound for the sieve
     */
    private void makeSieve(int upperBound){
        if (upperBound < 1){
            upperBound = 1; //makes the upper bound 1 if it's below 1
        }
        //space for all postive ints beteween 0 and upperBound
        isPrime = new boolean[upperBound+1];
        this.upperBound = upperBound;
        isPrime[0] = false;
        isPrime[1] = false;

        List<Integer> sievedInts = new ArrayList<>();
        for (int i = 2; i < upperBound; i++) {
            boolean currentPrime = true;
            for (Integer j: sievedInts) {
                //works out if this is prime or not by seeing if it divides evenly by any already-found primes
                if (i%j == 0){
                    //it ain't prime if it divides evenly
                    currentPrime = false;
                    break;
                }
            }
            //if it's still considered prime, add it to the sieved list
            if (currentPrime){
                sievedInts.add(i);
            }
            //sets the current index of isPrime to whether or not the current number is prime
            isPrime[i] = currentPrime;
        }
    }

    /**
     * Returns whether or not a given int is prime
     * @param isThisPrime the int that is being checked
     * @return true if it's a prime, false otherwise
     * @throws SieveException if the argument is below 0, or is larger than the upperBound.
     */
    public boolean isPrime(int isThisPrime) throws SieveException{
        if ((isThisPrime < 0) || (isThisPrime > upperBound)){
            throw new SieveException(isThisPrime);
        }
        return isPrime[isThisPrime];
    }

    /**
     * Gets the upper bound
     * @return the upper bound
     */
    public int getUpperBound() {
        return upperBound;
    }

    /**
     * Sets a new upper bound
     * @param newBound the new upper bound to use
     * @throws SieveException if the upper bound is below 1
     */
    public void setUpperBound(int newBound) throws SieveException{
        if (newBound < 1){
            throw new SieveException(newBound + "is too small! Please use a number greater than or equal to 1!");
        }
        makeSieve(newBound);
    }

    /**
     * Obtains a SieveIterator from this sieve
     * @return a SieveIterator (implements Iterator<Integer>
     */
    @Override
    public Iterator<Integer> iterator() {
        return new SieveIterator(isPrime);
    }

    /**
     * Inner class that can iterate through the sieve
     */
    class SieveIterator implements Iterator<Integer>{

        List<Integer> allPrimes; //literally just holds all the prime numbers from the sieve
        int cursor;
        Integer currentPrime;
        boolean canMoveToNext;

        /**
         * Constructs the SieveIterator, using the isPrime array of the Sieve
         * @param isPrimes the boolean array from the Sieve
         */
        SieveIterator(boolean[] isPrimes){
            allPrimes = new ArrayList<>();
            for(int i = 0; i < isPrimes.length; i++){
                if (isPrimes[i]){
                    allPrimes.add(i);
                }
            }
            cursor = -1;
            canMoveToNext = false;
        }

        /**
         * Checks to see whether or not this iterator has a next element
         * @return true if there is a next element, false otherwise
         */
        @Override
        public boolean hasNext() {
            if ((cursor + 1) == allPrimes.size()) {
                return false;
            } else{
                canMoveToNext = true;
                return true;
            }
        }

        /**
         * Obtains the Integer at the next element of the iterator
         * @return the next Integer in the sieve iterator
         */
        @Override
        public Integer next() {
            if (canMoveToNext){
                cursor++;
                currentPrime = allPrimes.get(cursor);
                canMoveToNext = false;
            }
            return currentPrime;
        }
    }


}
