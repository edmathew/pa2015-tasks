package pa.iscde.tasks.model.type;

import java.util.LinkedList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import pa.iscde.tasks.extensibility.TaskType;

public class InternalTaskTypeProvider {

	private static class TextType implements TaskType {

		private final String typeDescription;

		public TextType(final String typeDescription) {
			this.typeDescription = typeDescription;
		}

		@Override
		public String getType() {
			return typeDescription;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;

			if (!(obj instanceof TextType))
				return false;

			final TextType tx = (TextType) obj;

			return typeDescription.equals(tx.typeDescription);
		}
	}

	private enum TYPE {
		BUG, REFACTOR, ANALISYS;
	}

	private static final LinkedList<TaskType> instances = new LinkedList<>();

	static {
		for (TYPE t : TYPE.values())
			InternalTaskTypeProvider.instances.add(new TextType(t.toString()));

		loadExtensionTypes();
	}

	private static void loadExtensionTypes() {
		final IExtensionRegistry extRegistry = Platform.getExtensionRegistry();
		final IExtensionPoint extensionPoint = extRegistry.getExtensionPoint("pa.iscde.tasks.type");

		for (IExtension e : extensionPoint.getExtensions()) {
			IConfigurationElement[] confElements = e.getConfigurationElements();
			for (IConfigurationElement c : confElements) {
				try {
					TaskType t = (TaskType) c.createExecutableExtension("class");
					if (t != null) {
						instances.add(t);
					}
				} catch (CoreException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static TaskType getTaskType(final String type) {
		for (TaskType t : instances) {
			if (type.equals(t.getType())) {
				return t;
			}
		}

		return null;
	}

}
