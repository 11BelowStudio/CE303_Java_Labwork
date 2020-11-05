package Lab3.ex6;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.in;

public class generalizedEx5 {
    static Random rnJesus = new Random();

    static final int PARALLEL_WIN = 1; static final int TIE = 0; static final int SEQUENTIAL_WIN = -1;



    public static void main(String[] args) throws InterruptedException{

        parallelFunctionalTests(new FunctionalParallelization<>());


        int overallResults = 0;
        for (int in = 5; in <= 25; in++) {
            for (int bit = 16; bit <= 32; bit++) {
                overallResults += testIt(in,bit, new FunctionalParallelization<>());
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


    static int testIt(int countOfInputs, int bits, FunctionWithFunction<List<BigInteger>, Map<BigInteger,BigInteger>, Function<BigInteger,BigInteger>> parallelFun) throws InterruptedException {
        long startTime;
        long endTime;
        long sequentialTimeTaken;
        long parallelTimeTaken;

        Function<BigInteger,BigInteger> pInnerFun = new NextProbablePrimeFunction<>();

        List<BigInteger> inputsToUse = new ArrayList<>();
        for (int i = 0; i < countOfInputs; i++) {
            inputsToUse.add(new BigInteger(bits, rnJesus));
        }

        System.out.println("\nInputs: "+ countOfInputs + "\tBits: "+ bits);
        startTime = currentTimeMillis();
        sequential(inputsToUse);
        endTime = currentTimeMillis();

        sequentialTimeTaken = endTime-startTime;
        System.out.println("Sequential: " + sequentialTimeTaken + "ms");

        startTime = currentTimeMillis();
        parallelFun.apply(inputsToUse,pInnerFun);
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


    static void parallelFunctionalTests(FunctionWithFunction<List<BigInteger>, Map<BigInteger,BigInteger>, Function<BigInteger,BigInteger>> fun){
        Map<BigInteger, BigInteger> results = new HashMap<>();
        List<BigInteger> inputs = new ArrayList<>();

        Function<BigInteger, BigInteger> nextProbableFunction = new NextProbablePrimeFunction<>();

        for (int in = 5; in <= 25; in++) {
            for (int bit = 16; bit <= 32; bit++) {
                inputs.clear();
                results.clear();
                for (int i = 0; i < in; i++) {
                    inputs.add(new BigInteger(bit, rnJesus));
                }
                results = fun.apply(inputs, nextProbableFunction);
            }
        }

        for (Map.Entry<BigInteger,BigInteger> e: results.entrySet()) {
            System.out.println("in: " + e.getKey() + "\tout: " + e.getValue());
        }
    }

    static Map<BigInteger, BigInteger> sequential(List<BigInteger> arguments) throws InterruptedException {
        //empty map
        Map <BigInteger, BigInteger> nextPrimesMap = new HashMap<BigInteger,BigInteger>();

        //sequentially puts each argument and it's nextProbablePrime into the nextPrimesMap
        for (BigInteger i: arguments) {
            nextPrimesMap.put(i,i.nextProbablePrime());
        }
        //returns the nextPrimesMap
        return nextPrimesMap;

    }

    static Map<BigInteger, BigInteger> parallel(List<BigInteger> arguments) throws InterruptedException{
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

@FunctionalInterface
interface FunctionWithFunction<T, R, F>{
    //like Function<T,R>, but it applies another function the to input t to get the result
    R apply(T t, F function);
}

class NextProbablePrimeFunction<T> implements Function<BigInteger, BigInteger> {

    @Override
    public BigInteger apply(BigInteger b) {
        return b.nextProbablePrime();
    }
}


class FunctionalParallelization<T,R> implements FunctionWithFunction<List<T>, Map<T,R>, Function<T,R>>{

    @Override
    public Map<T,R> apply(List<T> t, Function<T,R> function){
        Map<T,R> outputs = new HashMap<T,R>(); //making the output map

        try {

            int inputSize = t.size(); //working out size of inputs
            FunctionalThread<T,R>[] threads = new FunctionalThread[inputSize]; //holds each FunctionalThread

            //makes and starts each FunctionalThread, using current value from list t and the given function
            for (int i = 0; i < inputSize; i++) {
                threads[i] = new FunctionalThread<T, R>(t.get(i), function);
                threads[i].start();
            }

            //joining each thread, putting the results into the map
            for (int i = 0; i < inputSize; i++) {
                threads[i].join();
                outputs.put(threads[i].input, threads[i].output);
            }

        } catch (InterruptedException ignored){}

        //returning the map
        return outputs;
    }
}


//generalized NextProbablePrime thread
class FunctionalThread<T, R> extends Thread{

    T input;

    Function<T, R> function;

    R output;

    //initialises the original input value and the function being used
    FunctionalThread(T input, Function<T,R> functionToUse){
        this.input = input;
        function = functionToUse;
    }
    //works out the next probable prime for the original
    public void run(){
        output = function.apply(input);
    }
}




