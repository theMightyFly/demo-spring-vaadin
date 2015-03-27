package demo.ui.event;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.wolfie.blackboard.Blackboard;
import com.github.wolfie.blackboard.Event;
import com.github.wolfie.blackboard.Listener;

@Component
@Scope("session")
public class EventSystem implements Serializable {
	private static final long serialVersionUID = 7829012291289167478L;
	private static final Blackboard blackboard = new Blackboard();

    public EventSystem() {
        init();
    }

    private void init() {
        blackboard.enableLogging();
    }

    public void registerEvent(Class<? extends Listener> listenerClass, Class<? extends Event> eventClass) {
        blackboard.register(listenerClass, eventClass);
    }

    public <T extends Listener> void addListener(T listener) {
        blackboard.addListener(listener);
    }

    public <T extends Event> void fire(T event) {
        blackboard.fire(event);
    }
}