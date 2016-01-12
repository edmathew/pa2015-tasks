package pa.iscde.tasks.integration;

import java.util.ArrayList;

import pa.iscde.metrix.extensibility.DefaultMetrics;
import pa.iscde.metrix.extensibility.NewMetric;
import pa.iscde.metrix.extensibility.TypeNewMetric;

public class Metric implements NewMetric {

	@Override
	public String metricName() {
		return "Number Bugs";
	}

	@Override
	public TypeNewMetric typeMetric() {
		return TypeNewMetric.COUNT;
	}

	@Override
	public ArrayList<DefaultMetrics> targetMetrics(DefaultMetrics[] possibleMetrics) {
		ArrayList<DefaultMetrics> arr = new ArrayList<>();
		arr.add(DefaultMetrics.NUMBER_OF_CHARACTERS);
		arr.add(DefaultMetrics.NUMBER_OF_METHODS);
		return arr;
	}

}
