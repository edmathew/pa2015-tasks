package pa.iscde.tasks.integration;

import pa.iscde.metrix.extensibility.ExportMetrix;
import pa.iscde.metrix.extensibility.typeExport;

public class MetricExport implements ExportMetrix {

	@Override
	public typeExport exportMetrix() {
		return typeExport.CSV;
	}

}
