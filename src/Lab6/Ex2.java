package Lab6;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Ex2 {

    /**
     * Method which produces a frequency map for the items in the given collection
     * @param collection the collection that is being looked through
     * @param <A> the type held by that collection
     * @return a map with the values of the collection as the key, and the frequency that each item appeared in it as a value.
     */
    public static <A> Map<A, Integer> frequencyMap(Collection<A> collection){
        Map<A,Integer> freqs = new HashMap<>();

        for (A item: collection) {
            int current = 1;
            if (freqs.containsKey(item)){
                current += freqs.get(item);
                //current++;
            }
            freqs.put(item,current);
        }
        return freqs;
    }

    public static void testFrequencyMap(){

        System.out.println("Test 1");

        Map<Integer, Integer> m1 = frequencyMap(
                Arrays.asList(1,2,3,4,5,6,7,8,9,10,1,1,1,3,3,42,67,35)
        );
        System.out.println("Expected output: {1,4},{2,1},{3,3},{4,1},{5,1},{6,1},{7,1},{8,1},{9,1},{10,1},{42,1},{67,1},{35,1}");

        for (Map.Entry<Integer,Integer> e: m1.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
        System.out.println();

        System.out.println("Test 2");

        Map<String,Integer> m2 = frequencyMap(
                Arrays.asList("one","one","oatmeal","kirbyisapinkguy","kirby very cute","deez nutz","deez nutz","deez nutz")
        );
        System.out.println("Expected output: {one,2},{oatmeal,1},{kirbyisapinkguy,1},{kirby very cute,1},{deez nutz,3}");

        for(Map.Entry<String,Integer> e: m2.entrySet()){
            System.out.println(e.getKey() + " : " + e.getValue());
        }
        System.out.println();
    }

    public static void main(String[] args){
        testFrequencyMap();
    }


}
