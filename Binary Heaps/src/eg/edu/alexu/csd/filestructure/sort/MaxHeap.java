package eg.edu.alexu.csd.filestructure.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class MaxHeap<T extends Comparable<T>> implements IHeap<T> {

	private int size;
	private ArrayList<INode<T>> maxHeap;

	public MaxHeap() {
		this.maxHeap = new ArrayList<>();
		this.size = 0;
	}

	@Override
	public INode<T> getRoot() {
		if (this.size == 0)
			return null;
		return maxHeap.get(0);
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public void heapify(INode<T> node) {

		if (node != null) {
			TreeNode<T> node1 = (TreeNode<T>) node;
			int index = node1.getIndex();
			int Ileft = (index * 2) + 1;
			int Iright = (index * 2) + 2;
			int largest;

			if (Ileft < this.size && maxHeap.get(Ileft).getValue().compareTo(maxHeap.get(index).getValue()) > 0) {
				largest = Ileft;
			} else
				largest = index;

			if (Iright < this.size
					&& this.maxHeap.get(Iright).getValue().compareTo(maxHeap.get(largest).getValue()) > 0) {
				largest = Iright;

			}
			if (largest != index) {
				swap(maxHeap.get(largest), maxHeap.get(index));
				heapify(maxHeap.get(largest));
			}
		}

	}

	public void swap(INode<T> i, INode<T> j) {
		T temp = i.getValue();
		i.setValue(j.getValue());
		j.setValue(temp);
	}

	public ArrayList<INode<T>> getMaxHeap() {
		return maxHeap;
	}

	@Override
	public T extract() {
		T value = null;
		if (this.size != 0) {
			value = maxHeap.get(0).getValue();
			maxHeap.get(0).setValue(maxHeap.get(size - 1).getValue());
			maxHeap.get(size - 1).setValue(value);
			// decrease heap size without removing max element ,just move it to the last
			this.size--;
			heapify(maxHeap.get(0));
		}
		return value;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			INode<T> newNode = new TreeNode<>(this, this.size);
			newNode.setValue(element);
			int n = maxHeap.size();
			// for loop to remove previos elements that extracted but not removed
			for (int i = this.size; i < n; i++) {
				maxHeap.remove(maxHeap.size() - 1);
			}
			maxHeap.add(newNode);
			this.size++;
			INode<T> parent = newNode.getParent();

			while (parent != null && element.compareTo(parent.getValue()) > 0) {
				newNode.setValue(parent.getValue());
				parent.setValue(element);
				newNode = parent;
				parent = newNode.getParent();
			}
		}

	}

	@Override
	public void build(Collection<T> unordered) {
		if (unordered != null) {
			ArrayList<T> arr = (ArrayList<T>) unordered;
			for (int i = 0; i < arr.size(); i++) {
				INode<T> newNode = new TreeNode<>(this, i);
				newNode.setValue(arr.get(i));
				maxHeap.add(newNode);
			}
			this.size = maxHeap.size();
			for (int i = (this.size / 2) - 1; i >= 0; i--) {
				heapify(maxHeap.get(i));
			}
		}
	}
}
