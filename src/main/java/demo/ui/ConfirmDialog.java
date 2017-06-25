package demo.ui;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Window;

public class ConfirmDialog extends Window {

	private static final long serialVersionUID = 4788396182027373809L;

	public ConfirmDialog(String pCaption,boolean pModal,ConfirmDialogListener pListener) {
		// set title
		super(pCaption);
		
		// center dialog
		center();
		
		// width
		setWidth("200px");
		
		// modal?
		setModal(pModal);
		
		// first user answer then close
		setClosable(false);
		
		setResizable(false);
		
		// define buttons
		Button yesButton = new Button("yes",event -> {
			pListener.yes();
			close();
		});
		Button noButton = new Button("no", event -> {
			pListener.no();
			close();
		});
		
		// set content
		HorizontalLayout layout = new HorizontalLayout();
		layout.addComponent(yesButton);
		layout.addComponent(noButton);
		setContent(layout);
	}
	
}
