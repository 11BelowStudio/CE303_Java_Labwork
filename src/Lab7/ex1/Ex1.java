package Lab7.ex1;

import java.util.stream.IntStream;

public class Ex1 {

    /**
     * Calculates the sum of the squares of numbers from 1-n
     * @param n
     * @return
     */
    public static int sumOfSquares(int n){
        int result = 0;
        return IntStream.rangeClosed(1,n).reduce(result, (r, i) -> r += i );
    }

    public static void main(String[] args) {
        System.out.println(sumOfSquares(3));
        System.out.println(sumOfSquares(3+1));
        System.out.println(sumOfSquares((56+1)/6));
    }
}
