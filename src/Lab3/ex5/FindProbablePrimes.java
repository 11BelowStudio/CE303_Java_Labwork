package Lab3.ex5;

import java.math.BigInteger;
import java.util.*;

import static java.lang.System.currentTimeMillis;

public class FindProbablePrimes {
    static Random rnJesus = new Random();

    static final int PARALLEL_WIN = 1; static final int TIE = 0; static final int SEQUENTIAL_WIN = -1;

    public static void main(String[] args) throws InterruptedException{

        int overallResults;


        long startTime;
        long endTime;
        long sequentialTimeTaken;
        long parallelTimeTaken;
        Map<BigInteger,BigInteger> sResults;
        Map<BigInteger,BigInteger> pResults;


        List<BigInteger> inputsToUse = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            inputsToUse.add(new BigInteger(20, rnJesus));
        }
        startTime = currentTimeMillis();
        sResults = sequential(inputsToUse);
        endTime = currentTimeMillis();

        sequentialTimeTaken = endTime-startTime;
        System.out.println("Sequential: " + sequentialTimeTaken + "ms");

        startTime = currentTimeMillis();
        pResults = parallel(inputsToUse);
        endTime = currentTimeMillis();

        parallelTimeTaken = endTime - startTime;
        System.out.println("Parallel: " + parallelTimeTaken + "ms");

        if (parallelTimeTaken < sequentialTimeTaken){
            System.out.println("Parallel wins!");
            overallResults = PARALLEL_WIN;
        } else if (parallelTimeTaken > sequentialTimeTaken){
            System.out.println("Sequential wins!");
            overallResults = SEQUENTIAL_WIN;
        } else{
            System.out.println("It's a tie!");
            overallResults = TIE;
        }

        for (int in = 5; in <= 25; in++) {
            for (int bit = 16; bit <= 32; bit++) {
                overallResults += testIt(in,bit);
            }
        }

        System.out.println("\n\n");
        System.out.println("Final score: " + overallResults);
        if (overallResults > 0){
            System.out.println("Parallel wins overall!");
        } else if (overallResults < 0){
            System.out.println("Sequential wins overall!");
        } else {
            System.out.println("It's a tie!");
        }
    }


    public static int testIt(int countOfInputs, int bits) throws InterruptedException {
        long startTime;
        long endTime;
        long sequentialTimeTaken;
        long parallelTimeTaken;
        Map<BigInteger,BigInteger> sResults;
        Map<BigInteger,BigInteger> pResults;

        List<BigInteger> inputsToUse = new ArrayList<>();
        for (int i = 0; i < countOfInputs; i++) {
            inputsToUse.add(new BigInteger(bits, rnJesus));
        }

        System.out.println("\nInputs: "+ countOfInputs + "\tBits: "+ bits);
        startTime = currentTimeMillis();
        sResults = sequential(inputsToUse);
        endTime = currentTimeMillis();

        sequentialTimeTaken = endTime-startTime;
        System.out.println("Sequential: " + sequentialTimeTaken + "ms");

        startTime = currentTimeMillis();
        pResults = parallel(inputsToUse);
        endTime = currentTimeMillis();

        parallelTimeTaken = endTime - startTime;
        System.out.println("Parallel: " + parallelTimeTaken + "ms");

        if (parallelTimeTaken < sequentialTimeTaken){
            System.out.println("Parallel wins!");
            return PARALLEL_WIN;
        } else if (parallelTimeTaken > sequentialTimeTaken){
            System.out.println("Sequential wins!");
            return SEQUENTIAL_WIN;
        } else{
            System.out.println("It's a tie!");
            return TIE;
        }
    }


    
    
    public static Map<BigInteger, BigInteger> sequential(List<BigInteger> arguments) throws InterruptedException {
        //empty map
        Map <BigInteger, BigInteger> nextPrimesMap = new HashMap<BigInteger,BigInteger>();

        //sequentially puts each argument and it's nextProbablePrime into the nextPrimesMap
        for (BigInteger i: arguments) {
            nextPrimesMap.put(i,i.nextProbablePrime());
        }
        //returns the nextPrimesMap
        return nextPrimesMap;
        
    }

    public static Map<BigInteger, BigInteger> parallel(List<BigInteger> arguments) throws InterruptedException{
        //empty map
        Map <BigInteger, BigInteger> nextPrimesMap = new HashMap<BigInteger,BigInteger>();
        //how many inputs
        int inputs = arguments.size();
        //array of the NextProbablePrimes threads
        NextProbablePrimeThread[] threads = new NextProbablePrimeThread[inputs];

        //making and starting each thread
        for (int i = 0; i < inputs; i++) {
            threads[i] = new NextProbablePrimeThread(arguments.get(i));
            threads[i].start();
        }

        //joining each thread, putting the results into the map
        for (int i = 0; i < inputs; i++) {
            threads[i].join();
            nextPrimesMap.put(threads[i].original,threads[i].result);
        }

        return nextPrimesMap;
    }
    
    
    
    
}


class NextProbablePrimeThread extends Thread{
    BigInteger original;
    
    BigInteger result;

    //initialises the original input value
    NextProbablePrimeThread(BigInteger input){
        original = input;
    }
    //works out the next probable prime for the original
    public void run(){
        result = original.nextProbablePrime();
    }
}
