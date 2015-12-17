package pa.iscde.tasks.model;

import pa.iscde.tasks.extensibility.TaskType;

public class Task {

	public enum PRIORITY {
		HIGH, MEDIUM, LOW;
	}

	private final PRIORITY priority;
	
	private final TaskType type;
	private final String msg;

	public PRIORITY getPriority() {
		return priority;
	}

	public TaskType getType() {
		return type;
	}

	public String getMsg() {
		return msg;
	}

	// Constructors...
	public Task(PRIORITY priority, TaskType type, String msg) {
		this.priority = priority;
		this.type = type;
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "prioraty:" + priority + ",Type:" + type + " Msg: " + msg;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof Task))
			return false;

		final Task other = (Task) obj;

		return priority.equals(other.priority) && type.equals(other.type) && msg.equals(other.msg);
	}

}
