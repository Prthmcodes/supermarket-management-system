package datastructures;

public class MyLinkedList<T> {

    private Node head;
    private int size = 0;

    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
        }
    }

    // add element to end
    public void add(T data) {
        Node newNode = new Node(data);

        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    // get element by index
    public T get(int index) {
        if (index < 0 || index >= size) return null;

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    // remove element by index
    public void remove(int index) {
        if (index < 0 || index >= size) return;

        if (index == 0) {
            head = head.next;
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next = current.next.next;
        }
        size--;
    }

    public int size() {
        return size;
    }
}