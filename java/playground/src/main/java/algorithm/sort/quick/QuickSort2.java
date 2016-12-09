package algorithm.sort.quick;

import java.util.Random;

public class QuickSort2 {

	public static final int SORT_CNT = 10;

	public static void main(String[] args) {
		int[] nums = new int[SORT_CNT];
		for (int i = 0; i < SORT_CNT; i++) {
			nums[i] = (new Random()).nextInt(30);
		}

		System.out.println("before sorting...");
		printArr(nums);

		quickSort(nums, 0, nums.length-1);

		System.out.println("after sorting...");
		printArr(nums);
	}

	private static void quickSort(final int[] nums, final int minIdx, final int maxIdx) {
		if (minIdx >= maxIdx) return;

		int pivot = nums[maxIdx];
		int lowerIdx = minIdx;
		int higherIdx = maxIdx;

		while (lowerIdx < higherIdx) {
			while (nums[lowerIdx] < pivot)
				lowerIdx++;
			while (lowerIdx < higherIdx && nums[higherIdx] >= pivot)
				higherIdx--;
			// lowerIdx는 pivot 보다 크고 higerIdx는 pivot 보다 작음
			swap(nums, lowerIdx, higherIdx);
		}

		swap(nums, lowerIdx, maxIdx);
		quickSort(nums, minIdx, lowerIdx-1);
		quickSort(nums, lowerIdx+1, maxIdx);

	}

	private static void swap(int[] nums, int idx1, int idx2) {
		int temp = nums[idx1];
		nums[idx1] = nums[idx2];
		nums[idx2] = temp;
	}

	private static void printArr(int[] nums) {
		for (int num : nums) {
			System.out.print(num + " ");
		}
		System.out.println("");
	}
}
