package ru.job4j.todo.persistent;

import ru.job4j.todo.models.Item;
import java.util.List;

public interface Store {
    void add(Item item);
    List<Item> findAll();
    void resetStatus(int id, boolean done);
}
