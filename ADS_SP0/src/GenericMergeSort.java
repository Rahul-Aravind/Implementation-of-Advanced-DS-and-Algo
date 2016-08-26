import java.util.Random;
import java.util.Scanner;

public class GenericMergeSort {
	
	
	@SuppressWarnings({ "rawtypes" })
	void sort (Item[] arr) {
		mergesort(arr, 0, arr.length - 1);
	}
	
	@SuppressWarnings({ "rawtypes" })
	void mergesort(Item[] arr, int low, int high) {
		if(high - low < 1) return;
		
		int mid = (int) (low + (high - low) * 0.5);
		mergesort(arr, low, mid);
		mergesort(arr, mid + 1, high);
		merge(arr, low, mid, high);
	}
	
	@SuppressWarnings({ "rawtypes" })
	void merge(Item[] arr, int low, int mid, int high) {
		Object[] temp_arr = new Object[high - low + 1];
		
		int i = low;
		int j = mid + 1;
		int k = 0;
		
		while(i <= mid && j <= high) {
			if(arr[i].compareTo(arr[j]) <= 0)
				temp_arr[k++] = arr[i++];
			else
				temp_arr[k++] = arr[j++];
		}
		
		if(i <= mid && j > high) {
			while(i <= mid)
				temp_arr[k++] = arr[i++];
		}
		else {
			while(j <= high)
				temp_arr[k++] = arr[j++];
		}
		
		for(k = 0; k < temp_arr.length; k++) {
			arr[low + k] = (Item) (temp_arr[k]);
		}
	}
	
	@SuppressWarnings({ "unchecked", "resource" })
	public static void main(String[] args) {
		Random random = new Random();
		Scanner s = new Scanner(System.in);
		
		System.out.println("Enter the size");
		int size = s.nextInt();
		
		Item<Integer>[] a = new Item[size];
		
		for(int i = 0; i < size; i++) {
			a[i] = new Item<>(random.nextInt(100));
		}

		long start_time = System.nanoTime();
		GenericMergeSort ms = new GenericMergeSort();
		ms.sort(a);
		long end_time = System.nanoTime();
		
		for(int i = 0; i < 10; i++) {
			System.out.print(a[i].toString() + " ");
		}
		
		System.out.println("\n" + "Time Taken to sort "+ size + " Integers using Merge sort technique: " + (end_time - start_time) / 1000000000.0 + " s");

		
		
	}

}
