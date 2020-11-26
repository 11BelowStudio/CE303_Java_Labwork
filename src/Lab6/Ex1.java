package Lab6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ex1 {

    /**
     * Concatenates the vararg argument lists into one big list
     * @param lists the lists to be concatenated into a big list
     * @param <A> the type held by the list
     * @return concatenated version of all lists
     */
    @SafeVarargs
    public static <A> List<A> concat(List<A>... lists){
        List<A> bigList = new ArrayList<>();
        for (List<A> l: lists) {
            bigList.addAll(l);
        }
        return bigList;
    }

    public static void testConcat(){
        System.out.println("Test 1");
        List<Integer> l1 = concat(
                Arrays.asList(new Integer[]{1,2,3,4,5}),
                Arrays.asList(new Integer[]{5,6,7,8,9}),
                Arrays.asList(new Integer[]{34,51,1})
        );
        System.out.println("Expected output: 1,2,3,4,5,6,7,8,9,34,51,1");

        for (Integer i: l1) {
            System.out.println(i);
        }
        System.out.println();

        System.out.println("Test 2");
        List<String> l2 = concat(
                Arrays.asList(new String[]{"deez","nutz"}),
                Arrays.asList(new String[]{"lmao","gottem"})
        );
        System.out.println("Expected output: deez,nutz,lmao,gottem");
        for (String s: l2){
            System.out.println(s);
        }
        System.out.println();
    }

    public static void main(String[] args){
        testConcat();

    }

}
