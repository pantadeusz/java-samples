package pl.tau.dbunitdemo.service;
// przyklad na podstawie przykladow J. Neumanna
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.tau.dbunitdemo.domain.Person;

public class PersonManagerTest {

	PersonManager personManager = new PersonManagerImpl();

	public PersonManagerTest() throws SQLException {
	}

	@After
    public void cleanup() throws SQLException {
        //personManager.clearPersons();
    }

	@Before
	public void filldata() throws SQLException {
		//personManager.clearPersons();
	}

	@Test
	public void checkConnection() {
	    assertNotNull(personManager.getConnection());
	}
	
	@Test
	public void checkAdding() throws SQLException{
		String NAME_1 = "Janek";
		int YOB_1 = 1939;

		Person person = new Person();
		person.setName(NAME_1);
		person.setYob(YOB_1);
		
		personManager.clearPersons();
		assertEquals(1,personManager.addPerson(person));
		
		List<Person> persons = personManager.getAllPersons();
		Person personRetrieved = persons.get(0);
		
		assertEquals(NAME_1, personRetrieved.getName());
		assertEquals(YOB_1, personRetrieved.getYob());
	}

}
