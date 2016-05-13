package com.application.restfulclient;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public interface MongoLabInterface {
	
	JsonArray listDatabases();
	
	JsonArray listCollections(String databaseName);
	
	JsonArray listDocuments(String databaseName, String collectionName);
	
	JsonObject insertDocument(String databaseName, String collectionName, Object document);
	
	JsonObject insertDocuments(String databaseName, String collectionName, List<Object> documents);
		
	JsonObject updateDocument(String databaseName, String collectionName, String mongoId, JsonObject update);
	
	JsonArray updateDocuments(String databaseName, String collectionName, JsonObject query, JsonObject update);

	JsonObject deleteDocument(String databaseName, String collectionName, String mongoId);	
	
	JsonObject deleteDocuments(String databaseName, String collectionName, JsonObject query);
	

	<T> JsonArray queryDocuments(String databaseName, String collectionName, JsonObject query, Class<T> classType);
	
//	Delete/replace multiple documents
//	View, update or delete a single document
	
	

}
