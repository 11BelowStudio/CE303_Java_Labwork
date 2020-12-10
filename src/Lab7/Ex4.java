package Lab7;



import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Ex4 {

    private static final int PARALLEL_WIN = 1;
    private static final int TIE = 0;
    private static final int SEQUENTIAL_WIN = -1;

    /**
     * Creates a stream containing numberOfElems random BigIntegers of specified bitlength
     * @param bitLength bitLength for each BigInteger in the stream
     * @param numberOfElems Number of BigIntegers needed in the stream
     * @return aforementioned stream of BigInteger objects
     */
    private static Stream<BigInteger> randomBigInts(int bitLength, int numberOfElems) {
        Random random = new Random();
        return IntStream.range(0, numberOfElems).mapToObj(n -> new BigInteger(bitLength, random));
    }

    /**
     * Tests how long it takes to construct a map of nextProbablePrimes for the stream of bigIntegers sequentially
     * @param bigInts the bigInts to be mapped
     * @return time taken (in ms) for this operation to be carried out
     */
    private static long testSequentialProbablePrimes(Stream<BigInteger> bigInts){//(int bitLength, int numberOfElems){


        long current = System.currentTimeMillis();
        Map<BigInteger, BigInteger> result = bigInts.collect( //randomBigInts(bitLength, numberOfElems).collect(
                Collectors.toMap(
                        k -> k,
                        BigInteger::nextProbablePrime
                )
        );
        long end = System.currentTimeMillis();
        long time = end-current;
        System.out.println(result);
        return time;
    }

    /**
     * Tests how long it takes to construct a map of nextProbablePrimes for the stream of bigIntegers but done in parallel
     * @param bigInts the bigInts to be mapped
     * @return time taken (in ms) for this operation to be carried out
     */
    private static long testParallelProbablePrimes(Stream<BigInteger> bigInts){//(int bitLength, int numberOfElems){


        long current = System.currentTimeMillis();
        Map<BigInteger, BigInteger> result = bigInts.parallel().collect( //randomBigInts(bitLength, numberOfElems).parallel().collect(
                Collectors.toMap(
                        k -> k,
                        BigInteger::nextProbablePrime
                )
        );
        long end = System.currentTimeMillis();
        long time = end-current;
        System.out.println(result);
        return time;
    }

    /**
     * Tests out the testSequentialProbablePrimes and testParallelProbablePrimes methods, and indicates which one was faster
     * @param bitLength the bitlength for the random BigInteger stream those methods will be given
     * @param numberOfElems the number of elements in the BigInteger stream those methods will be given
     * @return an int indicating which one was faster
     */
    private static int testIt(int bitLength, int numberOfElems){
        System.out.println("Bit length " + bitLength + ", elements: " + numberOfElems);
        List<BigInteger> bigInts = randomBigInts(bitLength,numberOfElems).collect(Collectors.toList());
        //System.out.println(bigInts);
        Stream<BigInteger> bigIntsSeq = bigInts.stream();
        Stream<BigInteger> bigIntsPar = bigInts.stream();
        long seq;
        long par;

        //going to test both in a random order, so there's no overarching advantage to one operation due to caching the results from last time or something
        if (Math.random() >= 0.5){
            System.out.println("Sequential:");
            seq = testSequentialProbablePrimes(bigIntsSeq);
            System.out.println(seq + " ms for sequential");
            System.out.println("Parallel:");
            par = testParallelProbablePrimes(bigIntsPar);
            System.out.println(par + " ms for parallel");
        } else {
            System.out.println("Parallel:");
            par = testParallelProbablePrimes(bigIntsPar);
            System.out.println(par + " ms for parallel");
            System.out.println("Sequential:");
            seq = testSequentialProbablePrimes(bigIntsSeq);
            System.out.println(seq + " ms for sequential");
        }

        //return which one was faster this time
        if (seq < par){
            System.out.println("Sequential was faster");
            return SEQUENTIAL_WIN;
        } else if (seq > par){
            System.out.println("Parallel was faster");
            return PARALLEL_WIN;
        } else {
            System.out.println("Tied this time");
            return TIE;
        }
    }

    public static void main(String[] args) {

        int overallResults = 0;
        for (int in = 10; in <= 30; in+= 10) {
            for (int bit = 1000; bit <= 1500; bit+= 100) {
                overallResults += testIt(bit, in);
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
}
