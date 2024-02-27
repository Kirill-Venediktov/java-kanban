package ru.practicum.kanban.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void getDefaultShouldReturnInMemoryTaskManager() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        assertEquals(taskManager, Managers.getDefault());
    }

    @Test
    void getDefaultHistoryShouldReturnInMemoryHistoryManager() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        assertEquals(historyManager, Managers.getDefaultHistory());
    }

}
