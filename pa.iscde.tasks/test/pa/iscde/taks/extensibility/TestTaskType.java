package pa.iscde.taks.extensibility;

import org.junit.Test;

import pa.iscde.tasks.extensibility.TaskType;

public class TestTaskType {

	private class DummyType implements TaskType {
		@Override
		public String getType() {
			return "Dummy";
		}
	}

	@Test
	public void instanciateAnTaskType() {
		new DummyType();
	}

}
