package com.example.shdemo.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Car;
import com.example.shdemo.domain.Person;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@Rollback
//@Commit
@Transactional(transactionManager = "txManager")
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
public class SellingManagerDBUnitTest {


	@Autowired
	SellingManager sellingManager;

	@Test
	@DatabaseSetup("/fullData.xml")
	@ExpectedDatabase(value = "/addPersonData.xml", 
	assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void getClientCheck() {
	    assertEquals(2, sellingManager.getAllClients().size());
        
        Person p = new Person();
        p.setFirstName("Kaziu");
        p.setPin("8754");
        p.setRegistrationDate(new Date());

        sellingManager.addClient(p);
        assertEquals(3, sellingManager.getAllClients().size());

    }
}
