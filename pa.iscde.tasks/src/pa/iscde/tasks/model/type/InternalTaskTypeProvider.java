package pa.iscde.tasks.model.type;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import pa.iscde.tasks.control.TasksActivator;
import pa.iscde.tasks.extensibility.ITask;
import pa.iscde.tasks.extensibility.ITaskType;

public class InternalTaskTypeProvider {

	private static class TextType implements ITaskType {

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

		@Override
		public InputStream getIconStream() {
			//Get path to image
			String path = "/images/" + getType() + ".png";
			InputStream istream = getClass().getResourceAsStream(path);
			return istream;
		}

		@Override
		public void performAction(ITask task) {
			TasksActivator.getJavaEditorServices().openFile(new File(task.getAbsolutePath()));
		}
	}

	private enum TYPE {
		BUG, REFACTOR, ANALISYS;
	}

	private static final List<ITaskType> instances = new LinkedList<>();

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
					ITaskType t = (ITaskType) c.createExecutableExtension("class");
					if (t != null) {
						instances.add(t);
					}
				} catch (CoreException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static List<ITaskType> getTypes() {
		return instances;
	}
	
	public static ITaskType getTaskType(final String type) {
		for (ITaskType t : instances) {
			if (type.equals(t.getType())) {
				return t;
			}
		}

		return null;
	}

}
