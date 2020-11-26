package Lab6;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.IntBinaryOperator;

public class Ex3And4<L> {

    /**
     * Given a function f of type
     * IntBinaryOperator and lists list1 and list2 of type List<\Integer\>, the method will
     * return a new list of integers constructed by applying f consecutively to successive elements of list1
     * and list2
     * @param f function to apply to objects of the list
     * @param list1 left hand side operands
     * @param list2 right hand side operands
     * @return list containing f(list1[n],list2[n])
     */
    public static List<Integer> zipWith(IntBinaryOperator f, List<Integer> list1, List<Integer> list2){

        int smallestSize = Integer.min(list1.size(),list2.size());

        List<Integer> outputList = new ArrayList<>();

        for (int i = 0; i < smallestSize; i++) {
            int currentResult = f.applyAsInt(list1.get(i),list2.get(i));
            outputList.add(currentResult);
        }

        return outputList;

    }

    public static void testZipWith(){
        System.out.println("Test 1");

        List<Integer> l1 = zipWith(
                (a,b) -> a + b,
                Arrays.asList(1,2,3,4,5),
                Arrays.asList(2,3,4,5,6)
        );
        System.out.println("Expected output: 3,5,7,9,11");

        for (Integer i: l1) {
            System.out.println(i);
        }
        System.out.println();

        System.out.println("Test 2");

        List<Integer> l2 = zipWith(
                (a,b) -> (int)(((double)(a + b)/(double)b)*a),
                Arrays.asList(5,6,7,8,9,10),
                Arrays.asList(2,4,6,8,3,15)
        );
        System.out.println("Expected output: 17, 15, 15, 16, 36, 16");

        for (Integer i: l2) {
            System.out.println(i);
        }
        System.out.println();

        List<Integer> l3 = zipWith(
                (a,b) -> a * (b + a),
                Arrays.asList(1,2,3,4,67242624),
                Arrays.asList(4,5,6,7)
        );
        System.out.println("Expected output: 5,14,27,44");

        for (Integer i: l3) {
            System.out.println(i);
        }
        System.out.println();
    }


    /**
     * A generic version of zipWith
     * @param f BiFunction, taking arguments of type L and R, returning value of type A
     * @param list1 list of values of type L
     * @param list2 list of values of type R
     * @param <A> the result list type
     * @param <L> type held by list1, type of 1st BiFunction argument
     * @param <R> type held by list2, type of 2nd BiFunction argument
     * @return a list containing the results of f for each element of list1 and list2
     */
    public static <A, L, R> List<A> genericZipWith(BiFunction<L, R, A> f, List<L> list1, List<R> list2){

        int smallestSize = Integer.min(list1.size(),list2.size());

        List<A> outputs = new ArrayList<>();

        for (int i = 0; i < smallestSize; i++) {
            A currentResult = f.apply(list1.get(i),list2.get(i));
            outputs.add(currentResult);
        }
        return outputs;
    }


    public static void testGenericZipWith(){
        System.out.println("Test 1");
        List<Integer> l1 = genericZipWith(
                (a,b) -> a + b,
                Arrays.asList(1,2,3,4,5),
                Arrays.asList(2,3,4,5,6)
        );
        System.out.println("Expected output: 3,5,7,9,11");

        for (Integer i: l1) {
            System.out.println(i);
        }
        System.out.println();

        System.out.println("Test 2");

        List<String> l2 = genericZipWith(
                (String a, Integer b) -> (a.concat("~").concat(String.valueOf(b))),
                Arrays.asList("ok","so","basically","DEEZ NUTZ LMAO GOTTEM"),
                Arrays.asList(1,2,64,15)
        );
        System.out.println("Expected output: ok~1,so~2,basically~64,DEEZ NUTZ LMAO GOTTEM~15");

        for (String s: l2){
            System.out.println(s);
        }
        System.out.println();

        System.out.println("Test 3");
        List<Map.Entry<String, Integer>> l3 = genericZipWith(
                AbstractMap.SimpleEntry::new, //(String a, Integer b) -> new AbstractMap.SimpleEntry<String, Integer>(a,b),
                Arrays.asList("a","b","c","d","banana"),
                Arrays.asList(35,15,63,12)
        );
        System.out.println("Expected output: {a,35},{b,15},{c,63},{d,12}");

        for(Map.Entry<String, Integer> e: l3){
            System.out.println(e.getKey()+","+e.getValue());
        }
        System.out.println();
    }


    /**
     * An even more generic version of GenericZipWith
     * @param f BiFunction, taking arguments of type L and R, returning value of type A
     * @param coll1 collection of values of type L
     * @param coll2 collection of values of type R
     * @param <A> the result list type
     * @param <L> type held by coll1, type of 1st BiFunction argument
     * @param <R> type held by coll2, type of 2nd BiFunction argument
     * @return a list containing the results of f for each element of coll1 and coll2
     */
    public static <A, L, R> List<A> genericerZipWith(BiFunction<L, R, A> f, Collection<L> coll1, Collection<R> coll2){

        int smallestSize = Integer.min(coll1.size(),coll2.size());

        List<A> outputs = new ArrayList<>();

        Iterator<L> it1 = coll1.iterator();
        Iterator<R> it2 = coll2.iterator();

        for (int i = 0; i < smallestSize; i++) {
            A currentResult = f.apply(it1.next(), it2.next());
            outputs.add(currentResult);
        }
        return outputs;
    }


    public static void testGenericerZipWith(){
        System.out.println("Test 1");

        List<Integer> l1 = genericerZipWith(
                Integer::sum, //(Integer a, Integer b) -> a + b
                Arrays.asList(1,2,3,4,5),
                new HashSet<>(Arrays.asList(2,3,4,5,6))
        );

        System.out.println("Expected output: 3,5,7,9,11");

        for (Integer i: l1) {
            System.out.println(i);
        }
        System.out.println();


        System.out.println("Test 2");
        List<Map.Entry<String, Integer>> l3 = genericerZipWith(
                AbstractMap.SimpleEntry::new, //(String a, Integer b) -> new AbstractMap.SimpleEntry<String, Integer>(a,b),
                new HashSet<String>(Arrays.asList("a","b","c","d","banana")),
                new TreeSet<Integer>(Arrays.asList(35,15,63,12))
        );
        System.out.println("Expected output: {banana,12},{a,15},{b,35},{c,63}");

        for(Map.Entry<String, Integer> e: l3){
            System.out.println(e.getKey()+","+e.getValue());
        }
        System.out.println();



    }

    public static void main(String[] args){
        System.out.println("Exercise 3:");
        testZipWith();

        System.out.println("\n");
        System.out.println("Exercise 4:");
        testGenericZipWith();

        System.out.println("\n");
        System.out.println("Extra bit of work:");
        testGenericerZipWith();
    }

}
