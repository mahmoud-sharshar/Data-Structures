package eg.edu.alexu.csd.filestructure.sort;

import java.util.ArrayList;

public class Sorting<T extends Comparable<T>> implements ISort<T> {

	@Override
	public IHeap<T> heapSort(ArrayList<T> unordered) {
		MaxHeap<T> Heap = new MaxHeap<>();
		if (unordered != null) {
			Heap.build(unordered);
			for (int i = 0; i < unordered.size() - 1; i++) {
				Heap.extract();
			}
		}
		return Heap;
	}

	@Override
	public void sortSlow(ArrayList<T> unordered) {
		if (unordered != null) {
			for (int i = 0; i < unordered.size() - 1; i++) {
				for (int j = i + 1; j < unordered.size(); j++) {
					if (unordered.get(i).compareTo(unordered.get(j)) > 0) {
						T value = unordered.get(i);
						unordered.set(i, unordered.get(j));
						unordered.set(j, value);
					}
				}
			}

		}
	}

	@Override
	public void sortFast(ArrayList<T> unordered) {
		if (unordered != null) {
			if (unordered.size() < 2)
				return;
			int mid = unordered.size() / 2;
			ArrayList<T> left = new ArrayList<>();
			ArrayList<T> right = new ArrayList<>();
			for (int i = 0; i < mid; i++) {
				left.add(unordered.get(i));
			}
			for (int i = mid; i < unordered.size(); i++) {
				right.add(unordered.get(i));
			}
			sortFast(left);
			sortFast(right);
			merge(unordered, left, right);
		}
	}
	private void merge(ArrayList<T> A, ArrayList<T> left, ArrayList<T> right) {
		int i = 0;
		int j = 0;
		int k = 0;
		while (i < left.size() && j < right.size()) {
			if (left.get(i).compareTo(right.get(j)) > 0) {
				A.set(k, right.get(j));
				j++;
				k++;
			} else {
				A.set(k, left.get(i));
				i++;
				k++;
			}
		}
		while (i < left.size()) {
			A.set(k, left.get(i));
			i++;
			k++;
		}
		while (j < right.size()) {
			A.set(k, right.get(j));
			j++;
			k++;
		}
	}

}
