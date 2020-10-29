package Lab2.SievePackage;

public class SieveException extends Exception{
    public SieveException(int n){
        super(n + " is out of bounds!");
    }

    public SieveException(String s){
        super(s);
    }
}
