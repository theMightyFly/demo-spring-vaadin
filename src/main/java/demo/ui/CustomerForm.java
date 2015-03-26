package demo.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

import demo.data.Customer;
import demo.data.CustomerRepository;
import demo.ui.event.EventSystem;
import demo.ui.event.ReloadEntriesEvent;


@Component
@Scope("prototype")
public class CustomerForm extends FormLayout {

    private Logger log = LoggerFactory.getLogger(CustomerForm.class);

    @Autowired
    private CustomerRepository customerService;

    @Autowired
    private EventSystem eventSystem;

    private String id = null;
    private TextField firstName = new TextField("First Name:");
    private TextField lastName = new TextField("Last Name:");

    public CustomerForm() {
        initForm();
    }
    
    public void setData(Customer customer){
    	id = customer.getId();
    	firstName.setValue(customer.getFirstName());
    	lastName.setValue(customer.getLastName());
    }

    private void initForm() {
        addComponent(firstName);
        addComponent(lastName);

        final Button commit = new Button("Commit");
        final Button cancel = new Button("Cancel");

        cancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                clearAndHide();
            }
        });
        commit.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                commitForm();
                fireCommitEvent();
                clearAndHide();
            }
        });

        final HorizontalLayout buttonBar = new HorizontalLayout();

        buttonBar.addComponent(commit);
        buttonBar.addComponent(cancel);

        addComponent(buttonBar);
    }

    private void commitForm() {
        if(id!=null){
        	log.info("Update user with id {}, name {} and address {}", id, firstName.getValue(), lastName.getValue());
        	Customer customer = customerService.findOne(id);
        	customer.setFirstName(firstName.getValue());
        	customer.setLastName(lastName.getValue());
        	customerService.save(customer);
        }
        else {        	
        	log.info("Creating user with name {} and address {}", firstName.getValue(), lastName.getValue());
        	customerService.save(new Customer(firstName.getValue(), lastName.getValue()));
        }
    }

    private void clearAndHide() {
        firstName.setValue("");
        lastName.setValue("");
        id = null;
        setVisible(false);
    }

    private void fireCommitEvent() {
        log.info("Fire commit event!");
        eventSystem.fire(new ReloadEntriesEvent());
    }
} 