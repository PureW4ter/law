package com.jfzy.mweb.batch;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jfzy.service.IdService;
import com.jfzy.service.bo.IdTypeEnum;

@EnableScheduling
@Component
public class IdInitBatch {

	@Autowired
	private IdService idService;

	@PostConstruct
	public void init() {
		initOrder();
	}

	@Scheduled(cron = "0 0 0 * * ?")
	public void initOrder() {
		DateTime today = new DateTime();
		idService.init(IdTypeEnum.ORDER.getId(), getOrderPage(today));
		idService.init(IdTypeEnum.ORDER.getId(), getOrderPage(today.plusDays(1)));
		idService.init(IdTypeEnum.ORDER.getId(), getOrderPage(today.plusDays(2)));
	}

	private int getOrderPage(DateTime today) {
		return today.getYear() * 10000 + today.getMonthOfYear() * 100 + today.getDayOfMonth();
	}

}
