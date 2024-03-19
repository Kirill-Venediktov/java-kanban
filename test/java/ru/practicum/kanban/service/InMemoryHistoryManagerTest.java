package ru.practicum.kanban.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.kanban.models.Epic;
import ru.practicum.kanban.models.Subtask;
import ru.practicum.kanban.models.Task;
import ru.practicum.kanban.models.enums.TaskStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {

    private static final String TASK_NAME = "name";
    private static final String TASK_DESCRIPTION = "description";
    private static final String EPIC_NAME = "Epic name";
    private static final String EPIC_DESCRIPTION = "Epic description";
    private static final String SUBTASK_NAME = "Subtask name";
    private static final String SUBTASK_DESCRIPTION = "Subtask description";
    private static final int TASK_ID = 1;
    private static final int SUBTASK_ID = 2;
    private static final int EPIC_ID = 3;

    private InMemoryHistoryManager manager;

    @BeforeEach
    private void managerInit() {
        manager = new InMemoryHistoryManager();
    }

    @Test
    void getHistoryShouldGetEmptyList() {
        assertEquals(new LinkedList<>(), manager.getHistory());
    }

    @Test
    void getHistoryShouldGetListOfTasks() {
        Task task = new Task(TASK_NAME, TASK_DESCRIPTION);
        task.setTaskStatus(TaskStatus.IN_PROGRESS);
        task.setId(TASK_ID);
        Epic epic = new Epic(EPIC_NAME, EPIC_DESCRIPTION, new ArrayList<>());
        epic.setId(EPIC_ID);
        Subtask subtask = new Subtask(SUBTASK_NAME, SUBTASK_DESCRIPTION, epic);
        subtask.setId(SUBTASK_ID);
        manager.add(task);
        manager.add(subtask);
        manager.add(epic);
        List<Task> expectedTaskList = new ArrayList<>();
        expectedTaskList.add(task);
        expectedTaskList.add(subtask);
        expectedTaskList.add(epic);
        assertEquals(expectedTaskList, manager.getHistory());
    }

    @Test
    void addShouldAddTaskToHistory() {
        Task task = new Task(TASK_NAME, TASK_DESCRIPTION);
        task.setTaskStatus(TaskStatus.IN_PROGRESS);
        task.setId(TASK_ID);
        manager.add(task);
        List<Task> expectedTaskList = new ArrayList<>(Collections.singletonList(task));
        assertEquals(expectedTaskList, manager.getHistory());
    }

    @Test
    void removeShouldRemoveTaskFromHistory() {
        Task task = new Task(TASK_NAME, TASK_DESCRIPTION);
        task.setTaskStatus(TaskStatus.IN_PROGRESS);
        task.setId(TASK_ID);
        manager.add(task);
        manager.remove(task.getId());
        assertEquals(new ArrayList<>(), manager.getHistory());
    }

}
