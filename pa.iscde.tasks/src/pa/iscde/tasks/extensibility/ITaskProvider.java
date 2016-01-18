package pa.iscde.tasks.extensibility;

import java.util.List;

public interface ITaskProvider {
	
	/**
	 * Return the Provider Name
	 * 
	 * @return String (non-null)
	 */
	public String getProviderName();
	
	/**
	 * Get a list of task from the Provider
	 * 
	 * @return List<ITask> (non-null)
	 */
	public List<ITask> getTasks(); 
	

}
