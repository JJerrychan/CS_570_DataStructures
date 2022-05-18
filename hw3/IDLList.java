package hw3;

import java.util.ArrayList;

/**
 * course: CS 570 ws,
 * Homework: Assignment 2,
 * CWID: 10476718.
 *
 * @param <E>
 * @author Junjie Chen
 */
public class IDLList<E> {
    private final ArrayList<Node<E>> indices;
    private Node<E> head;
    private Node<E> tail;
    private int size;

    /**
     * creates an empty double-linked list.
     */
    public IDLList() {
        indices = new ArrayList<>();
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * adds elem at position index (counting from wherever
     * head is). It uses the index for fast access.
     *
     * @param index
     * @param elem
     * @return
     */
    public boolean add(int index, E elem) {
        if (index < 0 || index > indices.size()) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        Node curNode = new Node(elem);
        if (size != 0) {
            Node temp = indices.get(index);
            if (index == 0) {
                temp.prev = curNode;
                head = curNode;
                curNode.next = temp;
            } else if (index == indices.size() - 1) {
                temp.next = curNode;
                tail = curNode;
                curNode.prev = temp;
            } else {
                temp.next.prev = curNode;
                curNode.next = temp.next;
            }
        } else {
            head = curNode;
            tail = curNode;
        }
        indices.add(index, curNode);
        size++;
        return true;
    }

    /**
     * dds elem at position index (counting from wherever
     * head is). It uses the index for fast access.
     *
     * @param elem
     * @return
     */
    public boolean add(E elem) {
        Node curNode = new Node(elem);
        if (head == null) {
            head = curNode;
            tail = curNode;

        } else {
            curNode.next = head;
            head.prev = curNode;
            head = curNode;
        }
        indices.add(0, curNode);
        size++;
        return true;
    }

    /**
     * adds elem as the new last element of the list (i.e. at the tail).
     *
     * @param elem
     * @return
     */
    public boolean append(E elem) {
        Node curNode = new Node(elem);
        if (tail == null) {
            head = curNode;
            tail = curNode;
        } else {
            curNode.prev = tail;
            tail.next = curNode;
            tail = curNode;
        }
        indices.add(size, curNode);
        size++;
        return true;
    }

    /**
     * uses the index for fast access. Indexing starts from 0, thus get(0) returns the head element of the list.
     *
     * @param index
     * @return object at position index from the head.
     */
    public E get(int index) {
        if (index < 0 || index > indices.size()) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        } else {
            return indices.get(index).data;
        }
    }

    /**
     * @return the object at the head.
     */
    public E getHead() {
        if (size == 0) {
            return null;
        }
        return head.data;
    }

    /**
     * @return the object at the tail.
     */
    public E getLast() {
        if (size == 0) {
            return null;
        }
        return tail.data;
    }

    /**
     * @return the list size.
     */
    public int size() {
        return this.size;
    }

    /**
     * removes and returns the element at the head.
     *
     * @return the element at the head.
     */
    public E remove() {
        if (size == 0) {
            return null;
        } else {
            E res = get(0);
            indices.remove(0);
            head = indices.get(0);
            head.prev = null;
            size--;
            return res;
        }
    }

    /**
     * removes and returns the element at the tail.
     *
     * @return the element at the tail.
     */
    public E removeLast() {
        if (size == 0) {
            return null;
        } else {
            E res = get(size - 1);
            indices.remove(size - 1);
            tail = indices.get(size - 2);
            tail.next = null;
            size--;
            return res;
        }
    }

    /**
     * removes and returns the element at the index. Use the index for fast access.
     *
     * @param index
     * @return the element at the index.
     */
    public E removeAt(int index) {
        if (index < 0 || index > indices.size()) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        } else {
            if (index == 0) {
                E res = get(0);
                indices.remove(0);
                head = indices.get(0);
                head.prev = null;
                size--;
                return res;
            }
            if (index == size - 1) {
                E res = get(size - 1);
                indices.remove(size - 1);
                tail = indices.get(size - 2);
                tail.next = null;
                size--;
                return res;
            } else {
                E res = get(index);
                Node pre = indices.get(index - 1);
                Node next = indices.get(index + 1);
                pre.next = next;
                next.prev = pre;
                indices.remove(index);
                size--;
                return res;
            }
        }
    }

    /**
     * removes the first occurrence of elem in the list and returns
     * true. Return false if elem was not in the list.
     *
     * @param elem
     * @return
     */
    public boolean remove(E elem) {
        if (this.size == 0) {
            throw new NullPointerException("error");
        }
        for (int i = 0; i < size; i++) {
            if (elem.equals(indices.get(i).data)) {
                this.removeAt(i);
                size--;
                return true;
            }
        }
        return false;
    }

    /**
     * presents a string representation of the list.
     *
     * @return
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < size - 1; i++) {
            sb.append(indices.get(i).data);
            sb.append(", ");
        }
        sb.append(indices.get(size - 1).data);
        return sb.toString();
    }

    private class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev;

        public Node(E elem) {
            this.data = elem;
        }

        public Node(E elem, Node<E> prev, Node<E> next) {
            this.data = elem;
            this.next = next;
            this.prev = prev;
        }
    }


}
