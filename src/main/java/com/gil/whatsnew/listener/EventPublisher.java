package com.gil.whatsnew.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;


import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
public class EventPublisher implements ApplicationEventPublisherAware {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}

	public void articleEvent() {
		System.out.println("Publishing event...");
		publisher.publishEvent(new AppEvent(this));
	}
}
