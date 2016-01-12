package pa.iscde.tasks.extensibility;

/**
 * Abstracts the Task concept
 * 
 */
public interface ITask {

	public String getPriority();

	public ITaskType getType();

	public String getMsg();

	public Integer getLineNo();

	public String getFileName();

	public String getAbsolutePath();

}
