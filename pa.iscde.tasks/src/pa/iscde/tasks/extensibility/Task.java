package pa.iscde.tasks.extensibility;

/**
 * Abstracts the Task concept
 * 
 */
public interface Task {

	public String getPriority();

	public TaskType getType();

	public String getMsg();

	public Integer getLineNo();

	public String getFileName();

	public String getAbsolutePath();

}
