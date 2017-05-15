package pl.tau.restdemo.service;

// w oparciu o przyklad J Neumanna, przerobiony przez T Puzniakowskiego

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pl.tau.restdemo.domain.Person;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public interface PersonManager {
	public Connection getConnection();
	public void deletePerson(Person person) throws SQLException;
	public void clearPersons() throws SQLException;
	public int addPerson(Person person);
	public Person getPerson(Person person);
	public List<Person> getAllPersons();

}
