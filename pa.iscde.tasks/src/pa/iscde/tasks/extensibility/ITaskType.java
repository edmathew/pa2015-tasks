package pa.iscde.tasks.extensibility;

import java.io.InputStream;

/**
 * Represents a Task Type that can be used by Tasks.
 *
 */
public interface ITaskType {

	/**
	 * A display representation of the TaskType. This is the name that will be
	 * showed in the Type Cell.
	 * 
	 * @return String (non-null)
	 */
	public String getType();
	
	/**
     * A Stream that contains a icon image for the Type.
     * 
     * @return InputStream (if null is return then the default image is used)
     */
	public InputStream getIconStream();
	
	/**
	 * Define action to be perform in ITask task
	 * 
	 * @param task ITask where action will be perform
	 */
	public void performAction(ITask task);
	

}
