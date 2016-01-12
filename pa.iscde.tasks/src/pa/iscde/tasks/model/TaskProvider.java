package pa.iscde.tasks.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;

import pa.iscde.tasks.control.TasksActivator;
import pa.iscde.tasks.extensibility.ITask;
import pa.iscde.tasks.extensibility.ITaskProvider;
import pa.iscde.tasks.model.parser.CommentExtractor;
import pa.iscde.tasks.model.parser.FileToString;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public class TaskProvider implements ITaskProvider {

	@Override
	public void performAction(ITask task) {
		TasksActivator.getJavaEditorServices().openFile(new File(task.getAbsolutePath()));
	}	
	
	@Override
	public List<ITask> getTasks() {
		try {
			return populateInternalTask(TasksActivator.getBrowserServices().getRootPackage().getChildren());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Collections.emptyList();
	}
	
	public String getProviderName(){
		return "pa.iscde.tasks";
	}
	
	private List<ITask> populateInternalTask(SortedSet<SourceElement> sources) throws IOException {
		final List<ITask> lst = new ArrayList<ITask>();
		handleSources(sources, lst);
		return lst;
	}
	

	private void handleSources(SortedSet<SourceElement> sources, List<ITask> lst) throws IOException {
		// TODO - Convert to Visitor?
		for (SourceElement e : sources) {
			if (e.isPackage())
				handleSources(((PackageElement) e).getChildren(), lst);
			else {
				lst.addAll(new CommentExtractor(new FileToString(e.getFile()).parse(), e.getName(),
						e.getFile().getAbsolutePath()).getCommentDetails());
			}
		}
	}
}
