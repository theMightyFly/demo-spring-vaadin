package demo;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import demo.data.Customer;
import demo.data.CustomerRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class MongoDBApplicationTests {
	
	@Autowired
	CustomerRepository repository;
	
	private final static Log LOG = LogFactory.getLog(MongoDBApplicationTests.class);

	@Before
	public void loadData(){
		// save a couple of customers
		repository.save(new Customer("Alice", "Smith"));
		repository.save(new Customer("Bob", "Smith"));
	}
	
	@After
	public void deleteData() {
		repository.deleteAll();
	}
	
	@Test
	public void foundAlice(){
		String firstName = "Alice";
		Customer alice = repository.findByFirstName(firstName);
		Assert.assertNotNull(alice);
		Assert.assertEquals(firstName, alice.getFirstName());
		LOG.info(alice);
	}
	
	@Test
	public void foundAll() {
		List<Customer> list = repository.findAll();
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() == 2);
	}
}
