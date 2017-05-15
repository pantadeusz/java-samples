package pl.tau.dbunitdemo.service;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pl.tau.dbunitdemo.domain.Person;
import java.sql.*;

import java.sql.SQLException;

@RunWith(JUnit4.class)
public class PersonManagerTest {
    PersonManager personManager;

    public PersonManagerTest() throws SQLException {
        String url = "jdbc:hsqldb:hsql://localhost/workdb";
        personManager = new PersonManagerImpl(DriverManager.getConnection(url));
    }

    @Test
    public void checkAdding() throws Exception {
        Person person = new Person();
        person.setName("Janek");
        person.setYob(1939);

        assertEquals(1, personManager.addPerson(person));
    }

}
