package ru.practicum.kanban.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void getDefault_shouldReturnInMemoryTaskManager() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        assertEquals(taskManager, Managers.getDefault());
    }

    @Test
    void getDefaultHistory_shouldReturnInMemoryHistoryManager() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        assertEquals(historyManager, Managers.getDefaultHistory());
    }

}
