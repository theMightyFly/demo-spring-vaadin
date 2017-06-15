package demo.ui;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.ListDataProvider;

import demo.data.Customer;

@Component
public class MongoDBContainer extends ListDataProvider<Customer> {

	private static final long serialVersionUID = 3090067422968423191L;

	public static final String BEAN_ID = "id";

    public static final String[] PROPERTIES = {BEAN_ID, "firstName", "lastName"};
    public static final String[] HEADERS = {"ID", "First Name", "Last Name"};

    public MongoDBContainer() {
    	this(new ArrayList<Customer>());
    }
    
    public MongoDBContainer(List<Customer> pList) {
    	super(pList);
    }
}
