/**
 * 
 */
package eg.edu.alexu.csd.filestructure.sort;

/**
 * @author mahmo
 *
 */
public interface INode<T extends Comparable<T>> {
	/**
	 * * Returns the left child of the current element/node in the heap tree
	 * * @return
	 */
	INode<T> getLeftChild();

	/**
	 * * Returns the right child of the current element/node in the heap tree
	 * * @return
	 */
	INode<T> getRightChild();

	/**
	 * * Returns the parent node of the current element/node in the heap tree
	 * * @return
	 */
	INode<T> getParent();

	/**
	 * Set/Get the value of the current node
	 * @return
	 */
	T getValue();

	void setValue(T value);

}
