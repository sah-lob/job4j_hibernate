package ru.job4j.carssale.persistence.db;

import org.junit.Test;
import ru.job4j.carssale.models.Person;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DBUsersStoreTest {

    private final DBUsersStore dbUsersStore = DBUsersStore.getInstance();

    @Test
    public void testAdd() {
        var fio = "Alexander";
        var login = "login";
        var person = new Person(login, fio, "324", "none", "8-495-7777777");
        dbUsersStore.add(person);
        assertThat(fio, is(dbUsersStore.getPerson(login).getFio()));
    }

    @Test
    public void testIsExists() {
        var fio = "Alexander";
        var login = "login2";
        var person = new Person(login, fio, "324", "none", "8-495-7777777");
        dbUsersStore.add(person);
        assertThat(true, is(dbUsersStore.isExists(login)));
        assertThat(false, is(dbUsersStore.isExists(login + "sdf")));
    }

    @Test
    public void testValidatePerson() {
        var fio = "Alexander";
        var login = "login3";
        var psssword = "q323";
        var person = new Person(login, fio, psssword, "none", "8-495-7777777");
        var person2 = new Person(login, fio, psssword + "d", "none", "8-495-7777777");
        dbUsersStore.add(person);
        assertThat(true, is(dbUsersStore.validatePerson(person)));
        assertThat(false, is(dbUsersStore.validatePerson(person2)));
    }

    @Test
    public void testEditPerson() {
        var fio = "Alexander";
        var login = "login4";
        var person = new Person(login, fio, "324", "none", "8-495-7777777");
        dbUsersStore.add(person);
        var newNumber = "666";
        dbUsersStore.editPerson(login, "newFIO", newNumber);
        assertThat(newNumber, is(dbUsersStore.getPerson(login).getPhone()));
    }

    @Test
    public void testGetPerson() {
        var fio = "Alexander";
        var login = "login5";
        var person = new Person(login, fio, "324", "none", "8-495-7777777");
        dbUsersStore.add(person);
        assertThat(fio, is(dbUsersStore.getPerson(login).getFio()));
    }
}