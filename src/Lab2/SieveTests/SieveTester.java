package Lab2.SieveTests;

import Lab2.SievePackage.Sieve;
import Lab2.SievePackage.SieveException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SieveTester {

    //Sieve s;

    @Test
    public void sampleTest(){
        Sieve s = new Sieve(100);
        try {
            assertTrue(s.isPrime(19));
            assertFalse(s.isPrime(91));
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testPrimes(){
        Sieve s = new Sieve(100);
        int[] primes = {2, 3, 5, 7, 11, 13, 17};
        for (int i : primes) {
            try {
                assertTrue(s.isPrime(i));
            } catch (Exception e){
                fail(i + " was incorrectly identified as not prime!");
            }
        }
    }

    @Test
    public void testNotPrimes(){
        Sieve s = new Sieve(100);
        int[] notPrimes = {1, 4, 6, 8, 9,10,12};
        for (int i : notPrimes) {
            try {
                assertFalse(s.isPrime(i));
            } catch (Exception e){
                fail(i + " was incorrectly identified as prime!");
            }
        }
    }

    @Test
    public void testOutOfBounds(){
        Sieve s = new Sieve(100);
        int[] invalidInputs = {-1, -3, 101, 1000};
        for (int i : invalidInputs) {
            assertThrows(
                    SieveException.class,
                    () -> s.isPrime(i),
                    "s.isPrime("+i+") didn't throw exception!"
            );
        }
    }

    @Test
    public void testInBounds(){
        Sieve s = new Sieve(100);
        int[] validInputs = {0, 1, 50, 100};
        for (int i : validInputs) {
            try{
                s.isPrime(i);
            } catch (SieveException e){
                fail("s.isPrime("+i+") incorrectly threw an exception!");
            }
        }
    }

    @Test
    public void testInvalidUpperBound(){
        Sieve s = new Sieve(1);
        int[] invalidInputs = {0, -1, -100};
        for (int i : invalidInputs) {
            assertThrows(
                    SieveException.class,
                    () -> s.setUpperBound(i),
                    "s.setUpperBound("+i+") didn't throw exception!"
            );
        }
    }

    @Test
    public void testValidUpperBound(){
        Sieve s = new Sieve(1);
        int[] validInputs = {2,3,100,5,20,1};
        for (int i : validInputs) {
            try{
                s.setUpperBound(i);
            } catch (SieveException e){
                fail("s.setUpperBound("+i+") incorrectly threw an exception!");
            }
        }
    }

    @Test
    public void testGettingUpperBound(){
        Sieve s = new Sieve(1);
        int[] validInputs = {2,3,100,5,20,1};
        for (int i : validInputs) {
            try{
                s.setUpperBound(i);
                assertEquals(
                        s.getUpperBound(),
                        i,
                        "Expected upper bound of "+ i +", got "+ s.getUpperBound() + "!"
                );
            } catch (SieveException e){
                fail("s.setUpperBound("+i+") incorrectly threw an exception!");
            }
        }
    }

}
