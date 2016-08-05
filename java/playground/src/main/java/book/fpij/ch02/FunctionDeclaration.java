package book.fpij.ch02;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FunctionDeclaration {
    public static void main(String[] args) {
        FunctionDeclaration fd = new FunctionDeclaration();


//        fd.runExample();
        fd.runStream();
    }

    public interface BinaryOperator extends Function<Integer, Function<Integer, Integer>> {
    }

    private void runExample() {
//        Function<Integer, Function<Integer, Integer>> add = x -> y -> x + y;
        BinaryOperator add = x -> y -> x + y;
        BinaryOperator mult = x -> y -> x * y;

        System.out.println(add.apply(2).apply(4));
//        add(2)(4);

        Function<Function<Integer, Integer>,
                Function<Function<Integer, Integer>, Function<Integer, Integer>>> compose =
                x -> y -> z -> x.apply(y.apply(z));

        System.out.println((compose.apply(x -> x * 3).apply(x -> x + 1)).apply(6));

        // Polymorphic version
    }

    private void runStream() {
        List<String> data = Arrays.asList("Test", "Value", "AC", "Apple", "Pear");

        data.stream().forEach(System.out::println);
        data.stream().forEach(System.out::println);


        List<Long> testLongs = Arrays.asList( 1000L, 2000L, 30000L, 4000L);
        Long longNumber = Long.valueOf((String) testLongs.stream().map(String::valueOf).collect(Collectors.joining()));
    }
}
