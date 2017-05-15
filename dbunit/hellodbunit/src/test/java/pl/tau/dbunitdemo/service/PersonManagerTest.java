package pl.tau.dbunitdemo.service;

import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pl.tau.dbunitdemo.domain.Person;

import java.net.URL;
import java.sql.SQLException;

@RunWith(JUnit4.class)
public class PersonManagerTest extends DBTestCase {
	PersonManager personManager;

    public PersonManagerTest() throws SQLException {
        super("PersonManagerImpl test");
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.hsqldb.jdbcDriver" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:hsqldb:hsql://localhost/workdb" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "sa" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "" );
    }

    /**
     * Gets the default dataset. This dataset will be the initial state of database
     * @return the default dataset
     * @throws Exception when there are errors getting resources
     */
    @Override
    protected IDataSet getDataSet() throws Exception {
        return this.getDataSet("dataset-pm.xml");
    }

    /**
     * Returns dataset for selected resource
     * @param datasetName filename in resources
     * @return flat xml data set
     * @throws Exception when there is a problem with opening dataset
     */
    protected IDataSet getDataSet(String datasetName) throws Exception {
        URL url = getClass().getClassLoader().getResource(datasetName);
        FlatXmlDataSet ret = new FlatXmlDataSetBuilder().build(url.openStream());
        return ret;
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        personManager = new PersonManagerImpl(this.getConnection().getConnection());
    }
	
	@Test
	public void checkAdding() throws Exception {
		Person person = new Person();
		person.setName("Janek");
		person.setYob(1939);

		assertEquals(1, personManager.addPerson(person));

        // Data verification

        IDataSet dbDataSet = this.getConnection().createDataSet();
        ITable actualTable = dbDataSet.getTable("PERSON");
        ITable filteredTable = DefaultColumnFilter.excludedColumnsTable
                (actualTable, new String[]{"ID"});
        IDataSet expectedDataSet = getDataSet("dataset-pm-add.xml");
        ITable expectedTable = expectedDataSet.getTable("PERSON");

        Assertion.assertEquals(expectedTable, filteredTable);
    }

}
