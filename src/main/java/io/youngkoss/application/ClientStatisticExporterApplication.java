package io.youngkoss.application;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import io.prometheus.client.exporter.MetricsServlet;
import io.youngkoss.prometheus.exporter.UsageDataCollector;

/**
 * @author ykoss
 *
 */
@SpringBootApplication(scanBasePackages = { "io.youngkoss" })
public class ClientStatisticExporterApplication {

	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientStatisticExporterApplication.class.getName());

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ClientStatisticExporterApplication.class, args);
	}

	/**
	 * 
	 */
	@Autowired
	UsageDataCollector usageCollector;

	/**
	 * @return
	 */
	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		return new ServletRegistrationBean(new MetricsServlet() {
			private static final long serialVersionUID = -5710220107721515884L;

			@Override
			protected void doGet(HttpServletRequest req, HttpServletResponse resp)
					throws ServletException, IOException {
				try {
					final long timeMillis = System.currentTimeMillis();
					usageCollector.call();
					LOGGER.warn(
							usageCollector.getClass().getName() + " tooks " + (System.currentTimeMillis() - timeMillis));
				} catch (final Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
				super.doGet(req, resp);

			}
		}, "/topstatistics/*");
	}
}
