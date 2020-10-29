package Lab2.SievePackage;

public class SieveMain {
    public static void main(String[] args){
        final int n = 100;
        final int i = 51;
        Sieve s = new Sieve(n);
        try{
            StringBuilder sb = new StringBuilder();
            sb.append(i);
            sb.append(" is ");
            if (s.isPrime(i)){
                sb.append("prime.");
            } else{
                sb.append("not prime.");
            }
            System.out.println(sb.toString());
        } catch (SieveException e){
            System.out.println(e.getMessage());
        }

        Sieve sieve = new Sieve(20);
        for(Integer k: sieve){
            System.out.println(k);
        }
    }
}
