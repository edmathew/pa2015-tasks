package pa.iscde.tasks.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import pa.iscde.tasks.control.TasksActivator;
import pa.iscde.tasks.extensibility.TaskProvider;
import pa.iscde.tasks.extensibility.Task;
import pa.iscde.tasks.model.parser.CommentExtractor;
import pa.iscde.tasks.model.parser.FileToString;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public enum ModelProvider {

	INSTANCE;

	private final List<Task> taskList;

	private ModelProvider() {
		taskList = new ArrayList<>();
	}

	public List<Task> getTasksList() {
		taskList.clear();

		getTasksFromProviders();

		try {
			handleSources(TasksActivator.getBrowserServices().getRootPackage().getChildren(), taskList);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return taskList;
	}

	public void getTasksFromProviders() {
		final IExtensionRegistry extRegistry = Platform.getExtensionRegistry();
		final IExtensionPoint extensionPoint = extRegistry.getExtensionPoint("pa.iscde.tasks.provider");

		for (IExtension e : extensionPoint.getExtensions()) {
			IConfigurationElement[] confElements = e.getConfigurationElements();
			for (IConfigurationElement c : confElements) {
				try {
					TaskProvider tp = (TaskProvider) c.createExecutableExtension("class");
					if (tp != null) {
						taskList.addAll(tp.getTasks());
					}
				} catch (CoreException e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	private void handleSources(SortedSet<SourceElement> sources, List<Task> tasks) throws IOException {
		// TODO - Convert to Visitor?
		for (SourceElement e : sources) {
			if (e.isPackage())
				handleSources(((PackageElement) e).getChildren(), tasks);
			else
				taskList.addAll(new CommentExtractor(new FileToString(e.getFile()).parse(), e.getName(),
						e.getFile().getAbsolutePath()).getCommentDetails());

		}
	}

	/*
	 * // taskViewer.setInput(TEST_ARRAY);
	 * 
	 * // Tasks #OLA "I don't like this"
	 * 
	 * ProjectBrowserServices serv = TasksActivator.getBrowserServices();
	 * 
	 * serv.getRootPackage().traverse(new Visitor() {
	 * 
	 * @Override public boolean visitPackage(PackageElement packageElement) {
	 * System.out.println("pkg: " + packageElement.getName());
	 * 
	 * return true; }
	 * 
	 * @Override public void visitClass(ClassElement classElement) {
	 * 
	 * System.out.println(classElement.getFile().getPath());
	 * 
	 * /* if (classElement.getName().equals("PidescoActivator.java")) { try {
	 * final Scanner s = new Scanner(classElement.getFile()); while
	 * (s.hasNextLine()) { System.out.println(s.nextLine());
	 * 
	 * } } catch (FileNotFoundException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 *
	 * } });
	 * 
	 * System.out.println("In TableView: ");
	 * 
	 * 
	 */
}
