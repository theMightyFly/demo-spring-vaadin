package demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import demo.data.Customer;
import demo.data.CustomerRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MongoDBApplication.class)
public class MongoDBApplicationTests {
	
	@Autowired
	CustomerRepository repo;
	
	private final static Log LOG = LogFactory.getLog(MongoDBApplicationTests.class);

	@Test
	public void foundAlice(){
		Customer alice = repo.findByFirstName("Alice");
		Assert.assertNotNull(alice);
		LOG.info(alice);
	}
}
