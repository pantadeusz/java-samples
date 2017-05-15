package pl.tau.restdemo.service;

import org.springframework.stereotype.Component;
import pl.tau.restdemo.domain.Person;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tp on 24.04.17.
 */
@Component
public class PersonManagerImpl implements  PersonManager {

    private Connection connection;

    private String url = "jdbc:hsqldb:hsql://localhost/workdb";


    private PreparedStatement addPersonStmt;
    private PreparedStatement deletePersonStmt;
    private PreparedStatement getAllPersonsStmt;

    private Statement statement;

    public PersonManagerImpl() throws SQLException {
        connection = DriverManager.getConnection(url);
        statement = connection.createStatement();

        ResultSet rs = connection.getMetaData().getTables(null, null, null,
                null);
        boolean tableExists = false;
        while (rs.next()) {
            if ("Person".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
                tableExists = true;
                break;
            }
        }

        if (!tableExists)
            statement.executeUpdate("CREATE TABLE Person(id bigint GENERATED BY DEFAULT AS IDENTITY, " +
                    "name varchar(20), yob integer)");

        addPersonStmt = connection
                .prepareStatement("INSERT INTO Person (name, yob) VALUES (?, ?)");
        deletePersonStmt = connection
                .prepareStatement("DELETE FROM Person where id = ?");
        getAllPersonsStmt = connection
                .prepareStatement("SELECT id, name, yob FROM Person");

    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void deletePerson(Person person) throws SQLException {
        addPersonStmt.setLong(1, person.getId());
        deletePersonStmt.executeUpdate();
    }

    @Override
    public void clearPersons() throws SQLException {
        connection.prepareStatement("delete from Person").executeUpdate();
    }

    @Override
    public int addPerson(Person person) {
        int count = 0;
        try {
            addPersonStmt.setString(1, person.getName());
            addPersonStmt.setInt(2, person.getYob());

            count = addPersonStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public Person getPerson(Person person) {
        throw new NotImplementedException(); // TODO: Implemet it!
    }

    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<Person>();

        try {
            ResultSet rs = getAllPersonsStmt.executeQuery();

            while (rs.next()) {
                Person p = new Person();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setYob(rs.getInt("yob"));
                persons.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

}
