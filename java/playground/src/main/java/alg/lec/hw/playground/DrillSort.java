package alg.lec.hw.playground;

/**
 * Created by woojin on 2015. 11. 26..
 */
public class DrillSort {
	public static void main(String... args) {
		final short MAX_CNT = 10;
//		int[] unsortedNumbers = new int[MAX_CNT];
//		IntStream.range(0, MAX_CNT).forEach(i -> unsortedNumbers[i] = new Random().nextInt(1000));
//
//		System.out.println("\n\nBefore Insertion Sort...");
//		Arrays.stream(unsortedNumbers).forEach(System.out::println);
//
//		System.out.println("\n\nAfter system default sort...");
//		Arrays.stream(unsortedNumbers).sorted().forEach(System.out::println);
		String test = "123:345|asfsfaf|sf1r2r3:safdff";
		String[] strings = test.split("\\|");
		for(String str : strings) {
			System.out.println(str);
		}
	}
}
