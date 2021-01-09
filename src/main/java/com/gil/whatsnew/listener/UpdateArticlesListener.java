package com.gil.whatsnew.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.gil.whatsnew.logic.Timer;

@Component
public class UpdateArticlesListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private TaskExecutor taskExecutor;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			taskExecutor.execute(ctx.getBean(Timer.class));
		}

	}

}
