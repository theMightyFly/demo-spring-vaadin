package demo.ui;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

import demo.data.Customer;
import demo.data.CustomerRepository;
import demo.ui.event.EventSystem;
import demo.ui.event.ReloadEntriesEvent;

@SpringView(name = MongoDBUIView.VIEW_NAME)
public class MongoDBUIView extends VerticalLayout implements View,ReloadEntriesEvent.ReloadEntriesListener{
	public static final String VIEW_NAME = "mongodbui";
	private static final Log LOG = LogFactory.getLog(MongoDBUIView.class);
	
	private Table entityTable;
	private String selectedId;
	private Customer selectedCustomer;
	
	private Button deleteButton;
	private Button editButton;
	
    @Autowired
    private MongoDBContainer mongodbContainer;
    
    @Autowired
    private CustomerForm editForm;
    
    @Autowired
    private CustomerRepository service;
    
    @Autowired
    private EventSystem eventSystem;
	
	@PostConstruct
	void init() {
		registerEvents();
		initData();
		initLayout();
	}

	private void registerEvents() {
		eventSystem.registerEvent(ReloadEntriesEvent.ReloadEntriesListener.class, ReloadEntriesEvent.class);
		eventSystem.addListener(this);
    }
	
	@SuppressWarnings("serial")
	private void initLayout(){
		setMargin(true);
		setSpacing(true);
		// vaadin table 
        entityTable = new Table();
        entityTable.setContainerDataSource(mongodbContainer);
        entityTable.setVisibleColumns(MongoDBContainer.PROPERTIES);
        entityTable.setColumnHeaders(MongoDBContainer.HEADERS);
        entityTable.setSelectable(true);
        entityTable.setWidth("100%");
        entityTable.setHeight("300px");

        // table select listener
        entityTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                selectedId = (String) event.getItemId();
                selectedCustomer =  mongodbContainer.getItem(selectedId).getBean();

               LOG.info("Selected item id {"+ selectedId+"}");
            }
        });
        // button bar
        final AbstractLayout buttonBar = initButtonBar();
        buttonBar.setWidth("100%");
        
        // edit Form
        editForm.setVisible(false);
        
        addComponent(entityTable);
        addComponent(buttonBar);
        addComponent(editForm);
	}
	
    @SuppressWarnings("serial")
	private AbstractLayout initButtonBar() {
        final HorizontalLayout buttonBar = new HorizontalLayout();

        buttonBar.setSpacing(true);

        final Button addButton = new Button("Add entry");
        editButton = new Button("Edit Entry");
        deleteButton = new Button("Delete entry");

        buttonBar.addComponent(addButton);
        buttonBar.addComponent(editButton);
        buttonBar.addComponent(deleteButton);

        buttonBar.setComponentAlignment(addButton, Alignment.MIDDLE_LEFT);
        buttonBar.setComponentAlignment(editButton, Alignment.MIDDLE_CENTER);
        buttonBar.setComponentAlignment(deleteButton, Alignment.MIDDLE_RIGHT);

        addButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                editForm.setVisible(true);
            }
        });
        editButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                editSelectedEntry();
            }
        });
        deleteButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                removeSelectedEntry();
            }
        });

        return buttonBar;
    }
    
    private void editSelectedEntry() {
        if (selectedId != null) {
        	LOG.info("Edit Customer with id "+selectedId);
            editForm.setData(selectedCustomer);
            editForm.setVisible(true);
        }
    }
    
    private void removeSelectedEntry() {
        if (selectedId != null) {
        	LOG.info("Delete Customer with id "+selectedId);
            service.delete(selectedId);
            eventSystem.fire(new ReloadEntriesEvent());
        }
    }
	
	private void initData() {
		// load data
        List<Customer> all = service.findAll();
        LOG.info(all);
        // clear table
        mongodbContainer.removeAllItems();
        // set table data
        mongodbContainer.addAll(all);
    }
	
	@Override
	public void enter(ViewChangeEvent event) {
		// the view is constructed in the init() method()
	}

	@Override
	public void reloadEntries(ReloadEntriesEvent event) {
        LOG.info("Received reload event. Refreshing entry table!");
        initData();
        entityTable.markAsDirty();
	}

}
