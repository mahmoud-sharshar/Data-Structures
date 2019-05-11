package eg.edu.alexu.csd.filestructure.sort;

import java.util.ArrayList;

public class TreeNode<T extends Comparable<T>> implements INode<T> {

	private T value;
	private int Iparent;
	private int Ileft;
	private int Iright;
	private int index;
	private MaxHeap<T> Heap;

	public TreeNode(IHeap<T> Heap, int index) {
		this.index = index;
		this.Heap = (MaxHeap<T>) Heap;
	}

	@Override
	public INode<T> getLeftChild() {
		Ileft = (index * 2) + 1;
		if (Ileft >= Heap.getMaxHeap().size())
			return null;

		return Heap.getMaxHeap().get(Ileft);
	}

	@Override
	public INode<T> getRightChild() {
		Iright = (index * 2) + 2;
		if (Iright >= Heap.getMaxHeap().size())
			return null;
		return Heap.getMaxHeap().get(Iright);
	}

	@Override
	public INode<T> getParent() {
		if (index == 0)
			return null;
		Iparent = (index - 1) / 2;

		return Heap.getMaxHeap().get(Iparent);
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public void setValue(T value) {
		this.value = value;
	}
	
	public int getIndex() {
		return this.index;
	}
	
//	public int getRightIndex() {
//		return (index * 2) + 2;
//	}

}
