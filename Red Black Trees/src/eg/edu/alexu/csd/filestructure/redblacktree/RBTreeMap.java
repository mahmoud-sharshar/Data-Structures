package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;

import javax.management.RuntimeErrorException;

import java.util.Set;

public class RBTreeMap<T extends Comparable<T>, V> implements ITreeMap<T, V> {

	private IRedBlackTree<T, V> RBT;
	private int size;

	public RBTreeMap() {
		RBT = new RBTree<>();
		size = 0;
	}

	@Override
	public Entry<T, V> ceilingEntry(T key) {
		if (key == null)
			throw new RuntimeErrorException(new Error());
		if (this.size == 0)
			return null;
		INode<T, V> x = RBT.getRoot();
		while (true) {
			if (x.getLeftChild().isNull() && x.getRightChild().isNull() && x.getKey().compareTo(key) < 0)
				return null;
			else if (x.getKey().compareTo(key) == 0)
				break;
			else if (x.getKey().compareTo(key) > 0
					&& (x.getLeftChild().isNull() || maxKey(x.getLeftChild()).compareTo(key) < 0)) {
				break;
			} else if (key.compareTo(x.getKey()) > 0)
				x = x.getRightChild();
			else if (key.compareTo(x.getKey()) < 0)
				x = x.getLeftChild();
		}
		Entry<T, V> e = makeEntry(x.getKey(), x.getValue());
		return e;
	}

	private T maxKey(INode<T, V> root) {
		INode<T, V> x = root;
		while (!x.getRightChild().isNull()) {
			x = x.getRightChild();
		}
		return x.getKey();
	}

	@Override
	public T ceilingKey(T key) {
		if (key == null)
			throw new RuntimeErrorException(new Error());
		Entry<T, V> e = ceilingEntry(key);
		if (e == null)
			return null;
		return e.getKey();
	}

	@Override
	public void clear() {
		RBT.clear();
		this.size = 0;
	}

	@Override
	public boolean containsKey(T key) {
		if (key == null)
			throw new RuntimeErrorException(new Error());
		INode<T, V> x = RBT.getRoot();

		while (!x.isNull()) {
			if (x.getKey().equals(key)) {
				return true;
			} else if (x.getKey().compareTo(key) > 0)
				x = x.getLeftChild();
			else
				x = x.getRightChild();
		}
		return false;
	}

	@Override
	public boolean containsValue(V value) {
		if (value == null)
			throw new RuntimeErrorException(new Error());

		return this.values().contains(value);
	}

	@Override
	public Set<Map.Entry<T, V>> entrySet() {
		if (this.size == 0)
			return null;
		Set<Map.Entry<T, V>> set = new LinkedHashSet<>();
		inorderTraverseEntry(set, RBT.getRoot());
		return set;
	}

	@Override
	public Entry<T, V> firstEntry() {
		if (this.size() == 0)
			return null;

		INode<T, V> x = RBT.getRoot();
		while (!x.getLeftChild().isNull()) {
			x = x.getLeftChild();
		}
		Entry<T, V> e = makeEntry(x.getKey(), x.getValue());
		return e;
	}

	@Override
	public T firstKey() {
		if (this.size == 0)
			return null;
		return firstEntry().getKey();
	}

	@Override
	public Entry<T, V> floorEntry(T key) {
		if (key == null)
			throw new RuntimeErrorException(new Error());
		if (this.size == 0)
			return null;
		INode<T, V> x = RBT.getRoot();
		while (true) {
			if (x.getLeftChild().isNull() && x.getRightChild().isNull() && x.getKey().compareTo(key) > 0)
				return null;
			else if (x.getKey().compareTo(key) == 0)
				break;
			else if (x.getKey().compareTo(key) < 0
					&& (x.getRightChild().isNull() || minKey(x.getRightChild()).compareTo(key) > 0)) {
				break;
			} else if (key.compareTo(x.getKey()) > 0)
				x = x.getRightChild();
			else if (key.compareTo(x.getKey()) < 0)
				x = x.getLeftChild();
		}
		Entry<T, V> e = makeEntry(x.getKey(), x.getValue());
		return e;
	}

	private T minKey(INode<T, V> root) {
		INode<T, V> x = root;
		while (x.getLeftChild().getKey() != null) {
			x = x.getLeftChild();
		}
		return x.getKey();
	}

	@Override
	public T floorKey(T key) {
		if (key == null)
			throw new RuntimeErrorException(new Error());
		if (this.size == 0)
			return null;
		Entry<T, V> e = floorEntry(key);

		return e.getKey();
	}

	@Override
	public V get(T key) {
		if (key == null)
			throw new RuntimeErrorException(new Error());
		INode<T, V> x = RBT.getRoot();
		while (!x.isNull()) {
			if (x.getKey().equals(key))
				return x.getValue();
			else if (x.getKey().compareTo(key) > 0)
				x = x.getLeftChild();
			else
				x = x.getRightChild();
		}
		return null;
	}

	@Override
	public ArrayList<Entry<T, V>> headMap(T toKey) {
		if (toKey == null)
			throw new RuntimeErrorException(new Error());

		return headMap(toKey, false);
	}

	@Override
	public ArrayList<Entry<T, V>> headMap(T toKey, boolean inclusive) {
		if (toKey == null)
			throw new RuntimeErrorException(new Error());

		if (this.size == 0)
			return null;
		ArrayList<Entry<T, V>> entries = new ArrayList<>();
		inorderToKey(entries, toKey, RBT.getRoot(), inclusive);
		return entries;
	}

	@Override
	public Set<T> keySet() {
		if (this.size == 0)
			return null;
		Set<T> hashSet = new LinkedHashSet<>();
		inorderTraverseKey(hashSet, RBT.getRoot());
		return hashSet;
	}

	@Override
	public Entry<T, V> lastEntry() {
		if (this.size() == 0)
			return null;

		INode<T, V> x = RBT.getRoot();
		while (!x.getRightChild().isNull()) {
			x = x.getRightChild();
		}
		Entry<T, V> e = makeEntry(x.getKey(), x.getValue());
		return e;
	}

	@Override
	public T lastKey() {
		if (this.size == 0)
			return null;
		return lastEntry().getKey();
	}

	@Override
	public Entry<T, V> pollFirstEntry() {
		if (this.size == 0)
			return null;
		Entry<T, V> e = firstEntry();
		RBT.delete(e.getKey());
		this.size--;
		return e;
	}

	@Override
	public Entry<T, V> pollLastEntry() {
		if (this.size == 0)
			return null;
		Entry<T, V> e = lastEntry();
		RBT.delete(e.getKey());
		this.size--;
		return e;
	}

	@Override
	public void put(T key, V value) {
		if (key == null || value == null)
			throw new RuntimeErrorException(new Error());
		if (!RBT.contains(key))
			this.size++;
		RBT.insert(key, value);
	}

	@Override
	public void putAll(Map<T, V> map) {
		if (map == null)
			throw new RuntimeErrorException(new Error());

		map.forEach((k, m) -> {
			if (!RBT.contains(k))
				this.size++;
			RBT.insert(k, m);

		});
	}

	@Override
	public boolean remove(T key) {
		if (key == null)
			throw new RuntimeErrorException(new Error());
		if (RBT.delete(key)) {
			this.size--;
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public Collection<V> values() {
		if (this.size == 0)
			return null;
		ArrayList<V> values = new ArrayList<>();
		inorderTraverseValues(values, RBT.getRoot());
		return values;
	}

	private void inorderTraverseValues(ArrayList<V> val, INode<T, V> root) {
		if (!root.isNull()) {
			inorderTraverseValues(val, root.getLeftChild());
			val.add(root.getValue());
			inorderTraverseValues(val, root.getRightChild());
		}
	}

	private void inorderTraverseKey(Set<T> set, INode<T, V> root) {
		if (!root.isNull()) {
			inorderTraverseKey(set, root.getLeftChild());
			set.add(root.getKey());
			inorderTraverseKey(set, root.getRightChild());
		}
	}

	private void inorderTraverseEntry(Set<Entry<T, V>> set, INode<T, V> root) {
		if (!root.isNull()) {
			inorderTraverseEntry(set, root.getLeftChild());
			set.add(makeEntry(root.getKey(), root.getValue()));
			inorderTraverseEntry(set, root.getRightChild());
		}
	}

	private void inorderToKey(ArrayList<Entry<T, V>> entries, T toKey, INode<T, V> root, Boolean in) {
		if (!root.isNull()) {
			inorderToKey(entries, toKey, root.getLeftChild(), in);
			if (in && root.getKey().compareTo(toKey) == 0) {
				entries.add(makeEntry(root.getKey(), root.getValue()));
				return;
			} else if (root.getKey().compareTo(toKey) < 0) {
				entries.add(makeEntry(root.getKey(), root.getValue()));
			} else
				return;

			inorderToKey(entries, toKey, root.getRightChild(), in);
		}
	}

	private AbstractMap.SimpleEntry<T, V> makeEntry(T key, V Value) {
		return new SimpleEntry<T, V>(key, Value);
	}
}
