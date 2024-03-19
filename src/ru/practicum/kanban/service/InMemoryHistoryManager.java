package ru.practicum.kanban.service;

import ru.practicum.kanban.models.Node;
import ru.practicum.kanban.models.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InMemoryHistoryManager implements HistoryManager {

    private static final int HISTORY_SIZE = 10;

    private final Map<Integer, Node<Task>> historyMap = new HashMap<>();

    @Override
    public void add(Task task) {
        if (task != null) {
            remove(task.getId());
            Task snapshotTask = new Task(task.getName(), task.getDescription());
            snapshotTask.setId(task.getId());
            snapshotTask.setTaskStatus(task.getTaskStatus());
            linkLast(task);
            checkHistory();
        }
    }

    @Override
    public void remove(int id) {
        if (historyMap.containsKey(id)) {
            removeNode(historyMap.get(id));
            historyMap.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InMemoryHistoryManager that = (InMemoryHistoryManager) o;
        return Objects.equals(historyMap, that.historyMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(historyMap);
    }

    private void removeNode(Node<Task> node) {
        if (node.getPrevious() != null) {
            node.getPrevious().setNext(node.getNext());
        }
        if (node.getNext() != null) {
            node.getNext().setPrevious(node.getPrevious());
        }
    }

    private Node<Task> getFirst() {
        Node<Task> node = null;
        if (!historyMap.isEmpty()) {
            for (Map.Entry<Integer, Node<Task>> entry : historyMap.entrySet()) {
                node = entry.getValue();
                if (node.getPrevious() == null) {
                    break;
                }
            }
        }
        return node;
    }

    private Node<Task> getLast() {
        Node<Task> node = null;
        if (!historyMap.isEmpty()) {
            for (Map.Entry<Integer, Node<Task>> entry : historyMap.entrySet()) {
                node = entry.getValue();
                if (node.getNext() == null) {
                    break;
                }
            }
        }
        return node;
    }

    private void linkLast(Task task) {
        Node<Task> lastNode = getLast();
        Node<Task> newNode = new Node<>(lastNode, task, null);
        if (lastNode != null) {
            lastNode.setNext(newNode);
        }
        historyMap.put(task.getId(), newNode);
    }

    private List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        if (!historyMap.isEmpty()) {
            Node<Task> node = getFirst();
            if (node != null) {
                tasks.add(node.getItem());
                while (node.getNext() != null) {
                    node = node.getNext();
                    tasks.add(node.getItem());
                }
            }
        }
        return tasks;
    }

    private void checkHistory() {
        if (historyMap.size() > HISTORY_SIZE) {
            Node<Task> node = getFirst();
            if (node != null) {
                remove(node.getItem().getId());
            }
        }
    }

}
