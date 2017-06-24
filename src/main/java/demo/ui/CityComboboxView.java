package demo.ui;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.VerticalLayout;

import demo.data.City;
import demo.data.CityRepository;

@SpringView(name = CityComboboxView.VIEW_NAME)
public class CityComboboxView extends VerticalLayout implements View {

	private static final long serialVersionUID = -8693685007618443849L;
	public static final String VIEW_NAME = "CityComboboxView";

	private static final Logger LOG = Logger.getLogger(CityComboboxView.class);
	
	@Autowired
	private CityRepository cityRepository;

	@PostConstruct
	void init() {
		// get cities inside germany
		List<City> cities = cityRepository.findByCountryOrderByNameAsc("DE");

		// create combobox
		ComboBox<City> combobox = new ComboBox<City>("Select a City");
		combobox.setItems(cities);
		combobox.setWidth("200px");
		combobox.setItemCaptionGenerator(new ItemCaptionGenerator<City>() {
			
			private static final long serialVersionUID = -5328245622581583103L;

			@Override
			public String apply(City item) {
				return item.getName();
			}
		}); 

		// select listener
		combobox.addSelectionListener(new SingleSelectionListener<City>() {
			
			private static final long serialVersionUID = -5456043484862675591L;

			@Override
			public void selectionChange(SingleSelectionEvent<City> event) {
				LOG.info("City <"+event.getSelectedItem().toString()+"> selected.");
				
			}
		});
		
		addComponent(combobox);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// the view is constructed in the init() method()
	}
}
