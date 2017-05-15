package pl.tau.dbunitdemo.service;

import pl.tau.dbunitdemo.domain.Person;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PersonManagerImpl implements  PersonManager {

    private Connection connection;

    private String url = "jdbc:hsqldb:hsql://localhost/workdb";


    private PreparedStatement addPersonStmt;
    private PreparedStatement getAllPersonsStmt;

    public PersonManagerImpl() throws SQLException {
        connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();

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
        getAllPersonsStmt = connection
                .prepareStatement("SELECT id, name, yob FROM Person");

    }

    @Override
    public Connection getConnection() {
        return connection;
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

    public List<Person> getAllPersons() {
        List<Person> persons = new LinkedList<>();

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
            throw new IllegalStateException(e.getMessage() + "\n" + e.getStackTrace().toString());
        }
        return persons;
    }

}
