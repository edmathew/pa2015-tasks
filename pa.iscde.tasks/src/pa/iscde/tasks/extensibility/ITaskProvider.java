package pa.iscde.tasks.extensibility;

import java.util.List;

public interface ITaskProvider {
	
	public String getProviderName();
	
	public List<ITask> getTasks(); 
	
	public void performAction(ITask task);
	
}
