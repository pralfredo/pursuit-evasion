
/**
 * File: Heap.java
 * Author: Pramithas Upreti
 * Class: CS231
 * Section: A
 * Project 8 ---> Pursuit Evasion on a Graph
 * Date: May 05, 2023
 * 
 * Purpose: This is a Java class called Heap, which defines the common methods and 
 * properties for it. It implements the PriorityQueue interface and uses a binary heap 
 * to keep track of the elements. The heap can be either a max or min heap.
 */
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Heap<T> implements PriorityQueue<T> {
    /**
     * A node class for a binary tree data structure.
     * 
     * @param <T> the type of data stored in the node.
     */
    private static class Node<T> {

        /** The left child node of this node. */
        Node<T> left;

        /** The right child node of this node. */
        Node<T> right;

        /** The parent node of this node. */
        Node<T> parent;

        /** The data stored in this node. */
        T data;

        /**
         * 
         * Constructs a new node with the given data and child/parent nodes.
         * 
         * @param data   the data to be stored in the node.
         * @param parent the parent node of this node.
         */
        public Node(T data, Node<T> parent) {
            this.data = data;
            this.parent = parent;
        }
    }

    private int size; // the size
    private Node<T> root, last; // the root and the last nodes
    private Comparator<T> comparator; // the comparator

    /**
     * Initializes a default min Heap.
     */
    public Heap() {
        this(null, false);
    }

    /**
     * Initializes a default Heap that is either min or max depending on the
     * specified boolean.
     * 
     * @param maxHeap the specified parameter, true if max, otherwise false
     */
    public Heap(boolean maxHeap) {
        this(null, maxHeap);
    }

    /**
     * Initializes a Heap based on the given comparator and the given boolean
     * indicating max or min.
     * 
     * @param comparator the specified comparator
     * @param maxHeap    the specified boolean, true if max, otherwise false
     */
    @SuppressWarnings("unchecked")
    public Heap(Comparator<T> comparator, boolean maxHeap) {
        if (comparator != null) {
            this.comparator = comparator;
        } else {
            this.comparator = new Comparator<T>() {

                @Override
                public int compare(T o1, T o2) {
                    return ((Comparable<T>) o1).compareTo(o2);
                }

            };
        }
        if (maxHeap) {
            this.comparator = new Comparator<T>() {
                @Override
                public int compare(T o1, T o2) {
                    return Heap.this.comparator.compare(o2, o1);
                }
            };
        }
        size = 0;
        root = null;
        last = null;
    }

    /**
     * Adds the specified item to the Heap
     */
    @Override
    public void offer(T item) {
        // bounded by log
        if (size == 0) {
            root = new Node<>(item, null);
            last = root;
            size++;

            return;
        }

        if (size % 2 == 0) {
            if (last.parent == null) {
                root.left = new Node<>(item, root);
                last = root.left;
            } else {
                last.parent.right = new Node<>(item, last.parent);
                last = last.parent.right;
            }
        } else {
            Node<T> curNode = last;
            while (curNode != root && curNode == curNode.parent.right) {
                curNode = curNode.parent;
            }
            if (curNode != root) {
                curNode = curNode.parent.right;
            }
            try {
                while (curNode.left != null) {
                    curNode = curNode.left;
                }

                curNode.left = new Node<>(item, curNode);
                last = curNode.left;
            } catch (Exception e) {
                System.out.println("Error..");
            }
        }

        bubbleUp(last);
        size++;

    }

    /**
     * A private helper method that swaps the data of the two nodes specified
     * 
     * @param node1 the first specified node
     * @param node2 the second specified node
     */
    private void swap(Node<T> node1, Node<T> node2) {
        T temp = node1.data;
        node1.data = node2.data;
        node2.data = temp;
    }

    /**
     * A private recursive helper method that we use after offering. This is to make
     * sure that the Heap
     * property is still true. The specified node gets sifted up until it satisfies
     * the Heap property.
     * 
     * @param curNode the specified node
     */
    private void bubbleUp(Node<T> curNode) {
        if (curNode == root) {
            return;
        }

        T myself = curNode.data;
        T myParent = curNode.parent.data;

        if (comparator.compare(myself, myParent) < 0) {
            swap(curNode, curNode.parent);
            bubbleUp(curNode.parent);
        }
    }

    /**
     * A private recursive helper method that we use after polling. This is to make
     * sure that the Heap
     * property is still true. The specified node gets sifted down until it
     * satisfies the Heap property.
     * 
     * @param curNode the specified node
     */
    private void bubbleDown(Node<T> curNode) {
        if (curNode.left == null) {
            // then we know curNode has no children, so we can just end
            return;
        } else if (curNode.right == null) {
            // then we know curNode has exactly one child, just its left
            // so we just need to determine if we need to swap to the left
            if (comparator.compare(curNode.data, curNode.left.data) > 0) {
                swap(curNode, curNode.left);
                bubbleDown(curNode.left);
            }
        } else {
            // then we know that curNode has both a left and right child
            // so we first have to determine which child is of greater priority
            // then determine if we have to swap with that child
            if (comparator.compare(curNode.left.data, curNode.right.data) < 0) {
                if (comparator.compare(curNode.data, curNode.left.data) > 0) {
                    swap(curNode, curNode.left);
                    bubbleDown(curNode.left);
                }
            } else {
                if (comparator.compare(curNode.data, curNode.right.data) > 0) {
                    swap(curNode, curNode.right);
                    bubbleDown(curNode.right);
                }
            }
        }
    }

    /**
     * Returns the number of items in the Heap
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the item of greatest priority in the Heap
     */
    @Override
    public T peek() {
        return root.data;
    }

    @Override
    /**
     * Removes and returns the item of the highest priority
     */
    public T poll() {
        // There could be no items in the heap
        if (size == 0) {
            throw new NoSuchElementException();
        }

        T data;

        // If only one item left in the heap, update root and last and return the data
        if (size == 1) {
            data = last.data;
            root = null;
            last = null;
            size--;
        } else {
            data = root.data;
            // If the size is even, then analogous to what we did in offer(), we need to
            // find the next last node in the Heap and bubbleDown the root since it has the
            // last value in it now
            if (size % 2 == 0) {
                root.data = last.data;
                Node<T> curNode = last;
                while (curNode != root && curNode == curNode.parent.left) {
                    curNode = curNode.parent;
                }
                if (curNode != root) {
                    curNode = curNode.parent.left;
                }

                while (curNode.right != null) {
                    curNode = curNode.right;
                }

                if (last.parent.left == last) {
                    last.parent.left = null;
                }

                last = curNode;
                bubbleDown(root);
            }
            // In odd cases, the new last is just the left child of the parent
            else {
                root.data = last.data;

                if (last.parent.right == last) {
                    last.parent.right = null;
                }
                last = last.parent.left;
                bubbleDown(root);
            }
            size--;
        }
        return data;
    }

    @Override
    /**
     * Updates the priority of the given item - that is, ensures that it is 'behind'
     * items with higher priority and
     * 'ahead' of items with lower priority
     */
    public void updatePriority(T item) {
        Node<T> node = findNode(root, item);
        if (node == null) {
            return;
        }
        node.data = item;
        bubbleUp(node);
        bubbleDown(node);
    }

    /**
     * A private helper method that finds an item in the Heap
     * 
     * @param current the current node
     * @param item the item to be found
     * @return the node found
     */
    private Node<T> findNode(Node<T> current, T item) {
        if (current == null) {
            return null;
        }
        if (current.data.equals(item)) {
            return current;
        }
        Node<T> leftResult = findNode(current.left, item);
        if (leftResult != null) {
            return leftResult;
        }
        Node<T> rightResult = findNode(current.right, item);
        if (rightResult != null) {
            return rightResult;
        }
        return null;
    }

    /**
     * The main method for the Heap
     * @param args the arguments
     */
    public static void main(String[] args) {
        // create a new heap
        Heap<Integer> heap = new Heap<>();

        // test offer method
        heap.offer(50);
        heap.offer(40);
        heap.offer(20);
        heap.offer(30);
        heap.offer(10);

        // test peek method
        Integer min1 = heap.peek();
        // expected output: 10
        System.out.println("Min element: " + min1);

        // test poll method
        Integer removed = heap.poll();
        // expected output: 10
        System.out.println("Removed element: " + removed);

        // test peek method
        Integer min2 = heap.peek();
        // expected output: 20
        System.out.println("Min element: " + min2);

    }
}