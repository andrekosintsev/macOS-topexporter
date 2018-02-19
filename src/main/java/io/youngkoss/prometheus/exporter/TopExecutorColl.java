package io.youngkoss.prometheus.exporter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static io.youngkoss.prometheus.exporter.TopMacOSConstants.EMPTY_STRING;

/**
 * @author ykoss
 *
 */
@Component
public class TopExecutorColl {
	private static final Logger LOGGER = LoggerFactory.getLogger(TopExecutorColl.class);

	/**
	 * 
	 */
	@Autowired
	public TopExecutorColl() {

	}

	/**
	 * @return
	 */
	public String[] executeTop() {
		java.lang.Process p = null;
		BufferedReader in = null;
		String defaultInfo = null;
		String loadAvg = null;
		String cpuUsage = null;
		String time = null;
		String networkUsage = null;
		try {
			p = Runtime.getRuntime().exec("top -n 1");
			in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while (defaultInfo == null || defaultInfo.contentEquals(EMPTY_STRING)) {
				defaultInfo = in.readLine();
			}
			while (time == null || time.contentEquals(EMPTY_STRING)) {
				time = in.readLine();
			}
			while (loadAvg == null || loadAvg.contentEquals(EMPTY_STRING)) {
				loadAvg = in.readLine();
			}
			while (cpuUsage == null || cpuUsage.contentEquals(EMPTY_STRING)) {
				cpuUsage = in.readLine();
			}
			while (networkUsage == null || networkUsage.contentEquals(EMPTY_STRING)) {

				networkUsage = in.readLine();
				if (!networkUsage.toLowerCase().contains("networks")) {
					networkUsage = null;
				}

			}
		} catch (IOException e) {
			LOGGER.error("Error during top execution");
		} finally {
			try {
				in.close();
				p.destroy();
			} catch (IOException e) {
				LOGGER.error("Error during top execution");
			}
		}
		return new String[] { defaultInfo, loadAvg, cpuUsage, networkUsage };
	}

}
