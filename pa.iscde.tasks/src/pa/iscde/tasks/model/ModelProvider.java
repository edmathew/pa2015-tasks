package pa.iscde.tasks.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import pa.iscde.tasks.extensibility.ITask;
import pa.iscde.tasks.extensibility.ITaskProvider;

public enum ModelProvider {

	INSTANCE;

	private final HashMap<ITaskProvider, List<ITask>> taskProviderRelation = new HashMap<>();

	private ModelProvider() {
	}

	public List<ITask> getTasksList() {
		taskProviderRelation.clear();
		
		getInternalTasks();
		getTasksFromClientProviders();

		List<ITask> returnListTasks = new ArrayList<>();
		for (List<ITask> list : taskProviderRelation.values()) {
			returnListTasks.addAll(list);
		}

		return returnListTasks;
	}

	private void getInternalTasks(){
		final ITaskProvider internalProvider = new TaskProvider();
		final List<ITask> internalTasks = internalProvider.getTasks();
		taskProviderRelation.put(internalProvider, internalTasks);
	}
	
	// Get tasks from plugin clients.
	public void getTasksFromClientProviders() {
		final IExtensionRegistry extRegistry = Platform.getExtensionRegistry();
		final IExtensionPoint extensionPoint = extRegistry.getExtensionPoint("pa.iscde.tasks.provider");

		for (IExtension e : extensionPoint.getExtensions()) {
			IConfigurationElement[] confElements = e.getConfigurationElements();
			for (IConfigurationElement c : confElements) {
				try {
					ITaskProvider tp = (ITaskProvider) c.createExecutableExtension("class");
					if (tp != null)
						taskProviderRelation.put(tp, tp.getTasks());

				} catch (CoreException e1) {
					e1.printStackTrace();
				}
			}
		}
	}


	public ITaskProvider findProvider(ITask task) {
		for(ITaskProvider tp: taskProviderRelation.keySet()){
			if(taskProviderRelation.get(tp).contains(task))
				return tp;
		}
		
		return null;
	}

	public void performActionFromTaskProvider(ITask task) {
		ITaskProvider tp = findProvider(task);
		if (tp != null) {
			tp.performAction(task);
		}
	}
}
