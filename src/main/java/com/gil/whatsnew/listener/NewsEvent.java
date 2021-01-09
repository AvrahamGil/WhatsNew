package com.gil.whatsnew.listener;



import org.springframework.context.ApplicationEvent;
import com.gil.whatsnew.enums.EventType;

public class NewsEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;

	private EventType eventType;
	
	public NewsEvent(Object source, EventType eventType) {
		super(source);
		this.eventType = eventType;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	
}
