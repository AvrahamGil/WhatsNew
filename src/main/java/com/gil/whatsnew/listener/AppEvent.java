package com.gil.whatsnew.listener;

import org.springframework.context.ApplicationEvent;


public class AppEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;

	public AppEvent(Object source) {
		super(source);
	}

}


