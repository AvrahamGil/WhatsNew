package com.gil.whatsnew.listener;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import com.gil.whatsnew.bean.Business;
import com.gil.whatsnew.bean.News;
import com.gil.whatsnew.bean.Sport;
import com.gil.whatsnew.bean.Technology;
import com.gil.whatsnew.bean.Travel;
import com.gil.whatsnew.enums.EventType;

@Service
public class EventPublisher implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher publisher;

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}

	public void newsEvenet(EventType eventType) {
		publisher.publishEvent(new NewsEvent(this, eventType));
	}
	
	public void businessEvent(EventType eventType) {
		publisher.publishEvent(new BusinessEvent(this, eventType));
	}
	
	public void sportEvent( EventType eventType) {
		publisher.publishEvent(new SportEvent(this, eventType));
	}
	
	public void technologyEvent(EventType eventType) {
		publisher.publishEvent(new TechnologyEvent(this,  eventType));
	}
	
	public void travelEvent(EventType eventType) {
		publisher.publishEvent(new TravelEvent(this, eventType));
	}
}
