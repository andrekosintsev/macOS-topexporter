package io.youngkoss.prometheus.exporter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.youngkoss.prometheus.CollectorCallable;

/**
 * @author ykoss
 *
 */
@Component
public class UsageDataCollector implements CollectorCallable {

	/**
	 * collectionStatsExporter
	 */
	private final ComputerClientMetricsExporter collectionStatsExporter;
	/**
	 * topExec
	 */
	private TopExecutorColl topExec;

	/**
	 * @param collectionStatsExporter
	 * @param topExec
	 */
	@Autowired
	public UsageDataCollector(ComputerClientMetricsExporter collectionStatsExporter, TopExecutorColl topExec) {
		this.collectionStatsExporter = collectionStatsExporter;
		this.topExec = topExec;
	}

	@Override
	public Void call() throws Exception {
		collectionStatsExporter.collect(topExec.executeTop());
		return null;
	}

}
