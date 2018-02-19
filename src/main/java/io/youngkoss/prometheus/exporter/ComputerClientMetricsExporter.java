package io.youngkoss.prometheus.exporter;

import static io.youngkoss.prometheus.exporter.TopMacOSConstants.COMMA_CONSTANT;
import static io.youngkoss.prometheus.exporter.TopMacOSConstants.CPU_USAGE_CONSTANT;
import static io.youngkoss.prometheus.exporter.TopMacOSConstants.EMPTY_STRING;
import static io.youngkoss.prometheus.exporter.TopMacOSConstants.LOAD_AVERAGE_CONSTANT;
import static io.youngkoss.prometheus.exporter.TopMacOSConstants.NETWORKS_CONSTANT;
import static io.youngkoss.prometheus.exporter.TopMacOSConstants.PERCENT_CONSTANT;
import static io.youngkoss.prometheus.exporter.TopMacOSConstants.PROCESSES_CONSTANT;
import static io.youngkoss.prometheus.exporter.TopMacOSConstants.SPACE_CONSTANT;
import static io.youngkoss.prometheus.exporter.TopMacOSConstants.PACKETS_CONSTANT;

import org.springframework.stereotype.Component;

import io.prometheus.client.Gauge;

/**
 * @author ykoss
 *
 */
@Component
public class ComputerClientMetricsExporter {

	private static final Gauge PROCESSES = Gauge.build().name("process_common").help("Processes Explanation Details")
			.labelNames("name").register();

	private static final Gauge LOAD_AVG = Gauge.build().name("load_average").help("Load Average Explanation Details")
			.labelNames("name").register();

	private static final Gauge CPU_USAGE = Gauge.build().name("cpu_usage_statistics")
			.help("CPU Usage Explanation Details").labelNames("name").register();

	private static final Gauge NETWORKS = Gauge.build().name("network_usage").help("Job Explanation Details")
			.labelNames("name").register();

	public Void collect(String[] results) throws Exception {
		for (String job : results) {

			if (job.contains(PROCESSES_CONSTANT)) {
				job = job.replace(PROCESSES_CONSTANT, EMPTY_STRING);
				String[] jobs = job.split(COMMA_CONSTANT);
				PROCESSES.labels("total").set(Double.valueOf(jobs[0].trim().split(SPACE_CONSTANT)[0]));
				PROCESSES.labels("running").set(Double.valueOf(jobs[1].trim().split(SPACE_CONSTANT)[0]));
				PROCESSES.labels("sleeping").set(Double.valueOf(jobs[2].trim().split(SPACE_CONSTANT)[0]));
				PROCESSES.labels("threads").set(Double.valueOf(jobs[3].trim().split(SPACE_CONSTANT)[0]));
			}
			if (job.contains(LOAD_AVERAGE_CONSTANT)) {
				job = job.replace(LOAD_AVERAGE_CONSTANT, EMPTY_STRING);
				String[] jobs = job.split(COMMA_CONSTANT);
				LOAD_AVG.labels("first_param").set(Double.valueOf(jobs[0].trim()));
				LOAD_AVG.labels("second_param").set(Double.valueOf(jobs[1].trim()));
				LOAD_AVG.labels("third_param").set(Double.valueOf(jobs[2].trim()));
			}
			if (job.contains(CPU_USAGE_CONSTANT)) {
				job = job.replace(CPU_USAGE_CONSTANT, EMPTY_STRING);
				String[] jobs = job.split(COMMA_CONSTANT);
				CPU_USAGE.labels("user").set(Double
						.valueOf(jobs[0].trim().split(SPACE_CONSTANT)[0].replace(PERCENT_CONSTANT, EMPTY_STRING)));
				CPU_USAGE.labels("sys").set(Double
						.valueOf(jobs[1].trim().split(SPACE_CONSTANT)[0].replace(PERCENT_CONSTANT, EMPTY_STRING)));
				CPU_USAGE.labels("idle").set(Double
						.valueOf(jobs[2].trim().split(SPACE_CONSTANT)[0].replace(PERCENT_CONSTANT, EMPTY_STRING)));
			}
			if (job.contains(NETWORKS_CONSTANT)) {
				job = job.replace(NETWORKS_CONSTANT, EMPTY_STRING);
				job = job.replace(PACKETS_CONSTANT, EMPTY_STRING);
				String[] jobs = job.split(COMMA_CONSTANT);
				NETWORKS.labels("packets_in_first_m")
						.set(Double.valueOf(jobs[0].trim().split(SPACE_CONSTANT)[0].replace("M", "").split("/")[0]));
				NETWORKS.labels("packets_in_second_m")
						.set(Double.valueOf(jobs[0].trim().split(SPACE_CONSTANT)[0].replace("M", "").split("/")[1]));

				NETWORKS.labels("packets_out_first_m")
						.set(Double.valueOf(jobs[1].trim().split(SPACE_CONSTANT)[0].replace("M", "").split("/")[0]));
				NETWORKS.labels("packets_out_second_m")
						.set(Double.valueOf(jobs[1].trim().split(SPACE_CONSTANT)[0].replace("M", "").split("/")[1]));
			}

		}
		return null;
	}

}
