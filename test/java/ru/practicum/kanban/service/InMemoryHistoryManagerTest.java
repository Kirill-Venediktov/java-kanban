package ru.practicum.kanban.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {

    private InMemoryHistoryManager manager;

    @BeforeEach
    private void managerInit() {
        manager = new InMemoryHistoryManager();
    }

    @Test
    void getHistory_shouldGetEmptyList() {
        assertEquals(new LinkedList<>(), manager.getHistory());
    }

}
