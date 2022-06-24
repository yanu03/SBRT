package kr.tracom.cm.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class Scheduler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Scheduled(fixedDelay = 10000)
	public void schedule_10sec() {
		//logger.info("schedule_10sec");
		try {

		} catch (Exception e) {
			logger.error("schedule_10sec Exception!!! {}", e);
		}
	}
	
	@Scheduled(fixedDelay = 1000)
	public void schedule_1sec() {
		//logger.info("schedule_1sec");
		try {

		} catch (Exception e) {
			logger.error("schedule_1sec Exception!!! {}", e);
		}
	}
}
