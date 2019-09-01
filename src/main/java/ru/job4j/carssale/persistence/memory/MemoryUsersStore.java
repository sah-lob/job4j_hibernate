package ru.job4j.carssale.persistence.memory;

import ru.job4j.carssale.models.Person;
import ru.job4j.carssale.persistence.interfaces.UsersStore;

import java.util.HashMap;

public class MemoryUsersStore implements UsersStore {

    private final static MemoryUsersStore MEMORY_USERS_STORE = new MemoryUsersStore();
    private HashMap<String, Person> hashMap = new HashMap<>();

    public static MemoryUsersStore getInstance() {
        return MEMORY_USERS_STORE;
    }

    public MemoryUsersStore() {
    }

    @Override
    public void add(Person person) {
        hashMap.put(person.getLogin(), person);
    }

    @Override
    public boolean isExists(String login) {
        return false;
    }

    @Override
    public boolean validatePerson(Person person) {
        var result = false;
        if (hashMap.size() > 0) {
            Person p2 = hashMap.get(person.getLogin());
            if (p2.equals(person)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean editPerson(String login, String fio, String number) {
        Person p = getPerson(login);
        p.setFio(fio);
        p.setPhone(number);
        return true;
    }

    @Override
    public Person getPerson(String login) {
        return hashMap.get(login);
    }
}
