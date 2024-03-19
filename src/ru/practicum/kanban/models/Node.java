package ru.practicum.kanban.models;

import java.util.Objects;

public class Node<T> {

    private Node<T> previous;

    private T item;

    private Node<T> next;

    public Node(Node<T> previous, T item, Node<T> next) {
        this.previous = previous;
        this.item = item;
        this.next = next;
    }

    public Node<T> getPrevious() {
        return previous;
    }

    public void setPrevious(Node<T> previous) {
        this.previous = previous;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(previous, node.previous) && Objects.equals(item, node.item) && Objects.equals(next, node.next);
    }

    @Override
    public int hashCode() {
        return Objects.hash(previous, item, next);
    }

    @Override
    public String toString() {
        return "Node{" +
            "previous=" + previous +
            ", item=" + item +
            ", next=" + next +
            '}';
    }

}
