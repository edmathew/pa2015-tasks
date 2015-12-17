package pa.iscde.tasks.model;

import pa.iscde.tasks.extensibility.TaskType;

public class TaskOccurence implements pa.iscde.tasks.extensibility.Task {

	private final Task task;
	private final String filename;
	private final String absPath;
	private final Integer linePos;

	public TaskOccurence(Task task, String filename, String absPath, Integer linePos) {
		this.task = task;
		this.filename = filename;
		this.absPath = absPath;
		this.linePos = linePos;
	}

	@Override
	public String toString() {
		return "Filename:" + filename + ",AbsPath:" + absPath + ",LinePos:" + linePos + "Task: " + task.toString();
	}

	@Override
	public String getPriority() {
		return task.getPriority().toString();
	}

	@Override
	public TaskType getType() {
		return task.getType();
	}

	@Override
	public String getMsg() {
		return task.getMsg();
	}

	@Override
	public Integer getLineNo() {
		return linePos;
	}

	@Override
	public String getFileName() {
		return filename;
	}

	@Override
	public String getAbsolutePath() {
		return absPath;
	}

}
