package com.gil.whatsnew.enums;

public enum Keys {
	
	Context("context"),
	NewYorkTimes("newyorktimes"),
	Key("keys"),
	Config("config"),
	Mongoconfig("mongoconfig"),
	
	ConfigPackage("package"),
	DriverClass("driverClass"),
	Url("url"),
	UserName("userName"),
	Pass("password"),
	HibernateProperty("hibernateProperty"),
	HibernateValue("hibernateValue"),
	
	MongoPackage("package"),
	MongoConnection("connection"),
	
	RapidHost("RapidHost"),
	ContextRapidHostValue("ContextRapidHostValue"),
	RapidKey("RapidKey"),
	RapidKeyValue("RapidKeyValue"),
	NewYorkApiKey("NewYorkApiKey"),
	
	Create("create"),
	Delete("delete"),
	GetListOfArticles("getListOfArticles"),
	GetNewYorkArticles("getNewYorkArticles"),
	GetRandom("getRandom");
	


	String key;
	
	Keys(String key) {
		this.key= key;
	}
	
	public String getKey() {
		return this.key;
	}
}
