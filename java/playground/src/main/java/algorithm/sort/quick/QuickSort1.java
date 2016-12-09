package algorithm.sort.quick;

import java.util.Arrays;
import java.util.Scanner;

public class QuickSort1 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int size = Integer.parseInt(sc.nextLine());
		final String[] numStrs = sc.nextLine().split(" ");
		int[] nums = new int[size];
		for (int i = 0; i < size; i++) {
			nums[i] = Integer.parseInt(numStrs[i]);
		}

		quickSort(nums, 0, nums.length - 1);

		Arrays.stream(nums).forEach(n -> System.out.print(n + " "));
		System.out.println("");

	}

	private static void quickSort(int[] nums, int minIdx, int maxIdx) {
		if (maxIdx < minIdx || minIdx == maxIdx) return;

		int pivot = nums[maxIdx];
		int lowerIdx = minIdx;
		int idx = minIdx;

		for(; idx < maxIdx; idx++) {
			if (nums[idx] < pivot) {
				swap(nums, idx, lowerIdx);
				lowerIdx++;
			}
		}

		swap(nums, idx, lowerIdx);
		quickSort(nums, minIdx, lowerIdx-1);
		quickSort(nums, lowerIdx+1, maxIdx);
	}

	private static void swap(int[] nums, int idx1, int idx2) {
		int temp = nums[idx1];
		nums[idx1] = nums[idx2];
		nums[idx2] =  temp;
	}
}
