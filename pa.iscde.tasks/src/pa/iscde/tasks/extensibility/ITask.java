package pa.iscde.tasks.extensibility;

/**
 * Represents a Task
 * 
 */
public interface ITask {
	
	
	/**
	 * Get the Priority for one task.
	 * 
	 * @return String (non-null)
	 */
	public String getPriority();

	/**
	 * Define the type to one task.
	 * 
	 * @return ITaskType (non-null)
	 */
	public ITaskType getType();
	

	/**
	 * Get message that will be part of the task
	 * 
	 * @return String (non-null)
	 */
	public String getMsg();

	/**
	 * Get the number line where is located the task.
	 * If the task doesn't is text based, return 0.
	 * @return Integer (not-null)
	 */
	public Integer getLineNo();

	/**
	 * Get the file where is located the task
	 * If the task doesn't is text based, return 0.
	 * 
	 * @return String (non-null)
	 */
	public String getFileName();

	/**
	 * Get the absolute path to file where is located the task
	 * 
	 * @return String (non-null)
	 */
	public String getAbsolutePath();

}
