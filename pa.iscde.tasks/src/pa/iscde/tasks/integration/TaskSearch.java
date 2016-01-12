package pa.iscde.tasks.integration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ViewerFilter;

import extensionpoints.ISearchEvent;
import extensionpoints.ISearchEventListener;
import pa.iscde.tasks.control.TasksActivator;
import pa.iscde.tasks.extensibility.ITaskType;
import pa.iscde.tasks.model.TaskFilter;
import pa.iscde.tasks.model.type.InternalTaskTypeProvider;
import pa.iscde.tasks.gui.view.TableView;

//Implementação do Servico de DeepSearch
public class TaskSearch {
	
	private ISearchEvent deepsearch = TasksActivator.getDeepSearchServices();
	private boolean listening;
	private ISearchEventListener listener = new ISearchEventListener() {
		@Override
		public void widgetSelected(final String text_Search, String text_SearchInCombo, String specificText_SearchInCombo,
				String text_SearchForCombo, ArrayList<String> buttonsSelected_SearchForCombo) {
			
			if(text_Search.length() > 0 && text_Search.trim().charAt(0) == '#')  {
				
				TaskFilter filter = new TaskFilter();
				filter.setFilter(text_Search);
				TableView.getInstance().setFilter(filter);
			} else 
			{
				TableView.getInstance().removeAllFilters();
			}
		}
	};
	
	public void start() {
		if(!listening) {
			deepsearch.addListener(listener);
			listening = true;
		}
		
	}
	
	public void stop() {
		if(listening) {
			deepsearch.removeListener(listener);
			listening = false;
		}
	}
	
	public boolean isRunning() {
		return listening;
	}
}
