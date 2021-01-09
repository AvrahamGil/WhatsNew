package com.gil.whatsnew.listener;

import org.springframework.context.ApplicationEvent;
import com.gil.whatsnew.enums.EventType;

public class BusinessEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;

	
	 private EventType eventType;
	 
	 public BusinessEvent(Object source, EventType type) {
		super(source);
		this.eventType = type;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	 
	 

}
