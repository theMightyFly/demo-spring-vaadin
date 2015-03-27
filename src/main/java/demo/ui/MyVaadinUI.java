package demo.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import demo.ui.event.EventSystem;
import demo.ui.event.ReloadEntriesEvent;

@Theme("valo")
@SpringUI
public class MyVaadinUI extends UI {

	@Autowired
	private SpringViewProvider viewProvider;
	
	@Autowired
	EventSystem eventSystem;

	@Override
	protected void init(VaadinRequest request) {
		initLayout();
		registerEvents();
	}

	private void registerEvents() {
		eventSystem.registerEvent(ReloadEntriesEvent.ReloadEntriesListener.class, ReloadEntriesEvent.class);
	}

	private void initLayout() {
		final VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		root.setMargin(true);
		root.setSpacing(true);
		setContent(root);

		final CssLayout navigationBar = new CssLayout();
		navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
	
		navigationBar.addComponent(createNavigationButton("Default View",
                DefaultView.VIEW_NAME));		
        navigationBar.addComponent(createNavigationButton("MongoDB View",
                MongoDBUIView.VIEW_NAME));
		
		root.addComponent(navigationBar);

		final Panel viewContainer = new Panel();
		viewContainer.setSizeFull();
		root.addComponent(viewContainer);
		root.setExpandRatio(viewContainer, 1.0f);

		Navigator navigator = new Navigator(this, viewContainer);
		navigator.addProvider(viewProvider);
	}

	private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }
}