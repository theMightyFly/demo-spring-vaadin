package demo.ui.event;

import com.github.wolfie.blackboard.Event;
import com.github.wolfie.blackboard.Listener;
import com.github.wolfie.blackboard.annotation.ListenerMethod;

public class ReloadEntriesEvent implements Event {

	public interface ReloadEntriesListener extends Listener {
		@ListenerMethod
		public void reloadEntries(ReloadEntriesEvent event);
	}

	public ReloadEntriesEvent() {
	}
}
