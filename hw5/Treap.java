package hw5;

import java.util.Random;
import java.util.Stack;

/**
 * A treap is a binary search tree (BST) which additionally maintains heap priorities.
 *
 * @param <E> E must be Comparable
 * @author Junjie Chen 10476718
 */
public class Treap<E extends Comparable<E>> {
    private final Random priorityGenerator;
    private Node<E> root;

    /**
     * Generate an empty treap
     */
    public Treap() {
        priorityGenerator = new Random();
        root = null;
    }

    /**
     * Generate an empty treap by random seed
     *
     * @param seed random seed
     */
    public Treap(long seed) {
        priorityGenerator = new Random(seed);
        root = null;
    }

    public static void main(String[] args) {
        Treap<Integer> testTree = new Treap<Integer>();
        testTree.add(4, 19);
        testTree.add(2, 31);
        testTree.add(6, 70);
        testTree.add(1, 84);
        testTree.add(3, 12);
        testTree.add(5, 83);
        testTree.add(7, 26);
        System.out.println(testTree);
        System.out.println("--------");
        System.out.println("Delete: " + testTree.delete(1));
        System.out.println("Delete: " + testTree.delete(10));
        System.out.println("find: " + testTree.find(1));
        System.out.println("find: " + testTree.find(10));

    }

    /**
     * add a data with a random priority from 0 to 100 into Treap
     *
     * @param key key of the node
     * @return true if a node with the key was successfully added to the treap;
     * false if there is already a node containing the given key
     */
    boolean add(E key) {
        int priority = priorityGenerator.nextInt(100);
        return add(key, priority);
    }

    /**
     * add a data with priority into Treap
     *
     * @param key      key of the node
     * @param priority priority of the node
     * @return true if a node with the key was successfully added to the treap;
     * false if there is already a node containing the given key
     */
    boolean add(E key, int priority) {
        Stack<Node<E>> stack = new Stack<Node<E>>();
        Node<E> node = new Node<E>(key, priority);
        Node<E> current = root;
        int compare;
        if (root == null) {
            root = node;
            return true;
        }
        if (find(node.data)) return false;
        while (current.left != null || current.right != null) {
            compare = node.data.compareTo(current.data);
            if (compare < 0) {
                if (current.left == null) break;
                stack.push(current);
                current = current.left;
            } else {
                if (current.right == null) break;
                stack.push(current);
                current = current.right;
            }
        }
        stack.push(current);
        compare = node.data.compareTo(current.data);
        if (compare < 0) current.left = node;
        else current.right = node;
        while (stack.peek().priority < node.priority) {
            if (stack.peek() == root) {
                current = stack.pop();
                if (current.left == node) current = current.rotateRight();
                else current = current.rotateLeft();
                root = current;
                break;
            } else {
                current = stack.pop();
                if (current.left == node) current = current.rotateRight();
                else current = current.rotateLeft();
                compare = node.data.compareTo(stack.peek().data);
                if (compare < 0) stack.peek().left = current;
                else stack.peek().right = current;
            }
        }
        return true;
    }

    /**
     * deletes the node with the given key from the treap
     *
     * @param key key of the node
     * @return true if delete successfully;
     * if the key was not found, the method does not modify the treap and returns false
     */
    boolean delete(E key) {
        if (!find(key)) return false;
        Node<E> r = root;
        Node<E> target = null;
        Node<E> parent = root;
        Node<E> new_head = null;
        Node<E> new_parent = null;
        if (root == null) return false;
        else if (key == r.data && r.right == null && r.left == null) {
            root = null;
            return true;
        }
        while (true) {
            if (key.compareTo(r.data) < 0) {
                parent = r;
                r = r.left;
            } else if (key.compareTo(r.data) > 0) {
                parent = r;
                r = r.right;
            } else if (key.compareTo(r.data) == 0) {
                target = r;
                break;
            }
        }
        if (target.right == null && target.left == null) {
            if (parent.left == target) parent.left = null;
            else parent.right = null;
            return true;
        }
        while (target.right != null || target.left != null) {
            if (target.right != null && target.left != null) {
                if (target.right.priority > target.left.priority) {
                    new_head = target.rotateLeft();
                    target = new_head.left;
                } else {
                    new_head = target.rotateRight();
                    target = new_head.right;
                }
            } else if (target.right == null) {
                new_head = target.rotateRight();
                target = new_head.right;
            } else {
                new_head = target.rotateLeft();
                target = new_head.left;
            }
            if (target.data == parent.data) root = new_head;
            else if (parent.left == target) parent.left = new_head;
            else parent.right = new_head;
            parent = new_head;
            new_parent = new_head;
        }
        if (new_parent.left == target) new_parent.left = null;
        else new_parent.right = null;
        return true;
    }

    /**
     * Finds a node with the given key in the treap rooted at root
     *
     * @param root root node
     * @param key  key of the node
     * @return true if it finds it and false otherwise
     */
    private boolean find(Node<E> root, E key) {
        while (true) {
            if (root != null) {
                if (root.data == key) return true;
                else if (root.data.compareTo(key) > 0) root = root.left;
                else if (root.data.compareTo(key) < 0) root = root.right;
            } else return false;
        }
    }

    /**
     * Finds a node with the given key in the treap
     *
     * @param key key of the node
     * @return true if it finds it and false otherwise
     */
    public boolean find(E key) {
        return find(root, key);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        preOrderTraversal(sb, root, 1);
        return sb.toString();
    }

    /**
     * preorder traversal of the tree and returns
     * a represen-tation of the nodes as a string
     */
    private void preOrderTraversal(StringBuilder sb, Node<E> node, int depth) {
        for (int i = 1; i < depth; i++) sb.append("  ");
        if (node == null) sb.append("null\n");
        else {
            sb.append(node);
            sb.append("\n");
            preOrderTraversal(sb, node.left, depth + 1);
            preOrderTraversal(sb, node.right, depth + 1);
        }
    }

    /**
     * Inner node of the Treap.
     *
     * @param <E>
     */
    private static class Node<E> {
        public E data;
        public int priority;
        public Node<E> left;
        public Node<E> right;

        /**
         * construct a new node with the given data and priority.
         * The pointers to child nodes are null.
         * Throw exceptions if data is null.
         *
         * @param data     key for the search
         * @param priority heap priority
         */
        public Node(E data, int priority) {
            try {
                if (data == null) throw new Exception("data is null!");
                this.data = data;
                this.priority = priority;
                left = null;
                right = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * performs a right rotation
         *
         * @return a reference to the root of the result
         */
        Node<E> rotateRight() {
            Node<E> temp = this.left;
            this.left = temp.right;
            temp.right = this;
            return temp;
        }

        /**
         * performs a left rotation
         *
         * @return a reference to the root of the result
         */
        Node<E> rotateLeft() {
            Node<E> temp = this.right;
            this.right = temp.left;
            temp.left = this;
            return temp;
        }

        public String toString() {
            return "( key = " + this.data.toString() + ", priority = " + this.priority + " )";
        }
    }
}
