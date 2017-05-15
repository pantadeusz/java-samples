package pl.tau.dbunitdemo.service;

// w oparciu o przyklad J Neumanna, przerobiony przez T Puzniakowskiego

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import pl.tau.dbunitdemo.domain.Person;

public interface PersonManager {
	public Connection getConnection();
	public int addPerson(Person person);
	public List<Person> getAllPersons();
}
