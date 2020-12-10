package Lab7.ex3;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Ex3 {

    public static boolean isPrime(int n){
        return IntStream.rangeClosed(2,(int)Math.sqrt(n)).noneMatch(i -> n % i == 0);
    }

    public static List<Integer> primes(int n){
        IntStream allPrimes = IntStream.iterate(1, i -> i + 2).filter(Ex3::isPrime).limit(n);
        IntStream literallyJustTwo = IntStream.of(2);
        return IntStream.concat(literallyJustTwo,allPrimes).boxed().collect(Collectors.toList());
    }


    public static void main(String[] args) {
        System.out.println(primes(50));
        System.out.println(primes(1000).get(1000));
    }
}
