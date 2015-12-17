package pa.iscde.tasks.gui.view;

import java.io.File;
import java.util.Map;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import pa.iscde.tasks.extensibility.Task;

import pa.iscde.tasks.control.TasksActivator;
import pa.iscde.tasks.model.ModelProvider;
import pt.iscte.pidesco.extensibility.PidescoView;

public class TableView implements PidescoView {

	private static final String[] HEADER_ARRAY = { "Type", "File", "Priority", "Line No", "Msg" };
	private static final int[] BOUNDS_ARRAY = { 100, 100, 100, 100, 300 };

	private static TableView instance;

	private TableViewer taskViewer;

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		instance = this;
		taskViewer = buildTaskTable(viewArea);
		taskViewer.setContentProvider(ArrayContentProvider.getInstance());
		taskViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				final IStructuredSelection selection = taskViewer.getStructuredSelection();
				final Task taskOcc = (Task) selection.getFirstElement();
				if (taskOcc != null)
					TasksActivator.getJavaEditorServices().openFile(new File(taskOcc.getAbsolutePath()));
			}
		});

		final Table table = taskViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		final Menu contextMenu = generateMenu(table);
		taskViewer.getTable().setMenu(contextMenu);

		refresh();
	}

	/**
	 * Refreshes the view with the information from the model
	 */
	public void refresh() {
		taskViewer.setInput(ModelProvider.INSTANCE.getTasksList());
	}

	public static TableView getInstance() {
		return instance;
	}

	private TableViewer buildTaskTable(Composite viewArea) {
		final TableViewer taskView = new TableViewer(viewArea,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		// Task Type
		createColumn(taskView, HEADER_ARRAY[0], BOUNDS_ARRAY[0], 0).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Task) element).getType().getType();
			}
		});

		// File
		createColumn(taskView, HEADER_ARRAY[1], BOUNDS_ARRAY[1], 1).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Task) element).getFileName();
			}
		});

		// Priority
		createColumn(taskView, HEADER_ARRAY[2], BOUNDS_ARRAY[2], 2).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Task) element).getPriority().toString();
			}
		});

		// Line
		createColumn(taskView, HEADER_ARRAY[3], BOUNDS_ARRAY[3], 3).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Task) element).getLineNo().toString();
			}
		});

		// msg
		createColumn(taskView, HEADER_ARRAY[4], BOUNDS_ARRAY[4], 4).setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Task) element).getMsg();
			}
		});

		return taskView;
	}

	private TableViewerColumn createColumn(TableViewer view, String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(view, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	private Menu generateMenu(Table table) {
		final Menu menu = new Menu(table);
		final MenuItem refreshMenu = new MenuItem(menu, SWT.CASCADE);
		refreshMenu.setText("Refresh");
		refreshMenu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				refresh();
			}
		});

		final MenuItem priorityMenu = new MenuItem(menu, SWT.CASCADE);
		priorityMenu.setText("Priority");

		final Menu prioritySubMenu = new Menu(menu);
		priorityMenu.setMenu(prioritySubMenu);

		 

		return menu;
	}

	// taskViewer.addFilter(new PriorityFilter(PRIORITY.HIGH));
}
