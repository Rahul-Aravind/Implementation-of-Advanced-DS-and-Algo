import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

public class HeapSort {
	
	@SuppressWarnings("rawtypes")
	void sort (List<Item> arr, List<Item> sortArr) {
		heapsort(arr, sortArr);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void heapsort(List<Item> arr, List<Item> sortArr) {
		PriorityQueue heap = new PriorityQueue(arr);
		
		while(heap.isEmpty() == false) {
			sortArr.add((Item) heap.poll());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "resource" })
	public static void main(String args[]) {
		
		Random random = new Random();
		Scanner s = new Scanner(System.in);
		HeapSort hs = new HeapSort();
		
		System.out.println("Enter the size");
		int size = s.nextInt();
		List<Item> list = new ArrayList<Item>();
		List<Item> sortlist = new ArrayList<Item>();
		
		for(int i = 0; i < size; i++) {
			list.add(new Item<>(random.nextInt(100)));
		}
		
		long start_time = System.nanoTime();
		hs.sort(list, sortlist);
		long end_time = System.nanoTime();
		
		for(int i = 0; i < 10; i++) {
			System.out.print(sortlist.get(i).toString() + " ");
		}
		
		System.out.println("\n" + "Time Taken to sort " + size + " Integers using Heap sort technique: " + (end_time - start_time) / 1000000000.0 + " s");
	}

}
