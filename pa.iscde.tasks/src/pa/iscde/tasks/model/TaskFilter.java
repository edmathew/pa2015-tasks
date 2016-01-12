package pa.iscde.tasks.model;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import pa.iscde.tasks.extensibility.ITask;

public class TaskFilter extends ViewerFilter {

	private String filter;
	
	public void setFilter(String filter)  {
		this.filter = filter;
	}
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if(filter == null || filter.length() == 0) {
			return true;
		}
		ITask task = (ITask) element;
		String type = "#" + task.getType().getType();
		if(type.equals(filter))  {
			return true;
		}
		
		return false;
	}

}
