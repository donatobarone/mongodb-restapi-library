package com.application.restfulclient;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MongoLabImpl implements MongoLabInterface{

	public static String TAG = "mymongolab.donato.barone";
	private static String baseUrl = "https://api.mongolab.com/api/1/databases";
	private static String collectionUrl = "collections";
	private static String apiKey = "BaqHYkFSYkoL0HqQSrkDbXfvAo26NhXp";  
	
	protected static Gson gson;
	protected JsonParser jsonParser;
	
	private static MongoLabImpl mongoLabImpl;
	
	public static MongoLabImpl createInstance() {
		if (mongoLabImpl == null) {
			mongoLabImpl = new MongoLabImpl();
		}
		return mongoLabImpl;
	}	

	private MongoLabImpl(){
    	gson = new Gson();
		jsonParser = new JsonParser();
	}
	
	private static String getApiKeyParameter(){
		return "apiKey=" + apiKey;
	}
	
	private String getMultipleDocumentsParameter(){
		return "m=true";
	}	
	
	private String getQueryParameter(String query){
		return "q=" + query;
	}
	
	@Override
	public JsonArray listDatabases(){
		MyHttpClient connector = new MyHttpClient();
		
		AsyncTask<String, String, String> connectTask = connector.execute(baseUrl + "?" + getApiKeyParameter(), "GET", null);
		JsonArray jsonResult = new JsonArray();
		try {
			String result = connectTask.get();	
			jsonResult = gson.fromJson(result, JsonArray.class);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // retrieve the result of the query.

		return jsonResult;
	}
	
	@Override
	public JsonArray listCollections(String databaseName){
		MyHttpClient connector = new MyHttpClient();
		
		AsyncTask<String, String, String> connectTask = connector.execute(baseUrl + "/" + databaseName + "/" + collectionUrl + "?" + getApiKeyParameter(), "GET", null);
		JsonArray jsonResult = new JsonArray();
		try {
			String result = connectTask.get();	
			jsonResult = gson.fromJson(result, JsonArray.class);
			Log.d(TAG, "Database  " + databaseName);
				
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // retrieve the result of the query.
	
		return jsonResult;
	}
	
	@Override
	public JsonArray listDocuments(String databaseName, String collectionName){
		MyHttpClient connector = new MyHttpClient();
		
		AsyncTask<String, String, String> connectTask = connector.execute(baseUrl + "/" + databaseName + "/" + collectionUrl + "/" + collectionName + "?" + getApiKeyParameter(), "GET", null);
		JsonArray jsonResult = new JsonArray();
		try {
			String result = connectTask.get();	
			jsonResult = gson.fromJson(result, JsonArray.class);
			Log.d(TAG, "Database  " + databaseName);
			Log.d(TAG, "Collection  " + collectionName);
					
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // retrieve the result of the query.
		
		return jsonResult;
	
	}
	
	
	@Override
	public JsonObject insertDocument(String databaseName, String collectionName, Object document) {
		String documentJson = gson.toJson(document);
		MyHttpClient connector = new MyHttpClient();
		JsonObject jsonResult = new JsonObject();
		
		AsyncTask<String, String, String> connectTask = connector.execute(baseUrl + "/" + databaseName + "/" + collectionUrl + "/" + collectionName + "?" + getApiKeyParameter(), "POST", documentJson);
		
		try {
			String result = connectTask.get();	
			jsonResult = gson.fromJson(result, JsonObject.class);
			Log.d(TAG, "Database  " + documentJson);
			Log.d(TAG, result);				
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // retrieve the result of the query.
		return jsonResult;
	}


	@Override
	public JsonObject insertDocuments(String databaseName, String collectionName, List<Object> documents) {
		// Serializing the document object into a Json String
		String documentsJson = gson.toJson(documents);		
		MyHttpClient connector = new MyHttpClient();
		JsonObject jsonResult = new JsonObject();
		
		AsyncTask<String, String, String> connectTask = connector.execute(baseUrl + "/" + databaseName + "/" + collectionUrl + "/" + collectionName + "?" + getApiKeyParameter(), "POST", documentsJson);
		
		try {
			String result = connectTask.get();	
			jsonResult = gson.fromJson(result, JsonObject.class);
			Log.d(TAG, "Database  " + documentsJson);
			Log.d(TAG, result);				
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // retrieve the result of the query.
		return jsonResult;
	}

	
	@Override
	public JsonObject updateDocument(String databaseName, String collectionName, String mongoId, JsonObject update) {
		JsonObject updateAttribute = new JsonObject();
		updateAttribute.add("$set", update);
		String updateAttributeString = gson.toJson(updateAttribute);
		String url = baseUrl + "/"
				+ databaseName + "/" + collectionUrl + "/" + collectionName + "/" + mongoId + "?" + getApiKeyParameter();
		
		MyHttpClient connector = new MyHttpClient();
		JsonObject jsonResult = new JsonObject();
		AsyncTask<String, String, String> connectTask = connector.execute(baseUrl + "/"
				+ databaseName + "/" + collectionUrl + "/" + collectionName + "/" + mongoId + "?" + getApiKeyParameter(), "PUT", updateAttributeString);

		try {
			String result = connectTask.get();
			jsonResult = gson.fromJson(result, JsonObject.class);
			Log.d(TAG, result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // retrieve the result of the query.
		return jsonResult;
		
		
	}


	@Override
	public JsonArray updateDocuments(String databaseName, String collectionName, JsonObject query, JsonObject update) {
		JsonObject updateAttribute = new JsonObject();
		updateAttribute.add("$set", update);
		String updateAttributeString = gson.toJson(updateAttribute);
//		String url = baseUrl + "/"
//				+ databaseName + "/" + collectionUrl + "/" + collectionName + "?" + getApiKeyParameter() + "&" + getQueryParameter(gson.toJson(query)) + "&" + getMultipleDocumentsParameter();
		MyHttpClient connector = new MyHttpClient();
		JsonArray jsonResult = new JsonArray();
		AsyncTask<String, String, String> connectTask = connector.execute(baseUrl + "/"
				+ databaseName + "/" + collectionUrl + "/" + collectionName + "?" + getApiKeyParameter() + "&" + getQueryParameter(gson.toJson(query)) + "&" + getMultipleDocumentsParameter(), "PUT", updateAttributeString);

		try {
			String result = connectTask.get();
			jsonResult = gson.fromJson(result, JsonArray.class);
			Log.d(TAG, result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // retrieve the result of the query.
		return jsonResult;
	}


	@Override
	public JsonObject deleteDocument(String databaseName, String collectionName, String mongoId) {
//			String url = baseUrl + "/"
//				+ databaseName + "/" + collectionUrl + "/" + collectionName + "/" + mongoId + "?" + getApiKeyParameter();
	
		MyHttpClient connector = new MyHttpClient();
		JsonObject jsonResult = new JsonObject();
		AsyncTask<String, String, String> connectTask = connector.execute(baseUrl + "/"
				+ databaseName + "/" + collectionUrl + "/" + collectionName + "/" + mongoId + "?" + getApiKeyParameter(), "DELETE", "");

		try {
			String result = connectTask.get();
			jsonResult = gson.fromJson(result, JsonObject.class);
			Log.d(TAG, result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // retrieve the result of the query.

		return jsonResult;
	}
	
	@Override
	public JsonObject deleteDocuments(String databaseName, String collectionName, JsonObject query) {
//		String url = baseUrl + "/"
//				+ databaseName + "/" + collectionUrl + "/" + collectionName + "?" + getApiKeyParameter() + "&" + getQueryParameter(gson.toJson(query)) + "&" + getMultipleDocumentsParameter();
		MyHttpClient connector = new MyHttpClient();
		JsonObject jsonResult = new JsonObject();
		AsyncTask<String, String, String> connectTask = connector.execute(baseUrl + "/"
				+ databaseName + "/" + collectionUrl + "/" + collectionName + "?" + getApiKeyParameter() + "&" + getQueryParameter(gson.toJson(query)) + "&" + getMultipleDocumentsParameter(), "PUT", "[]");

		try {
			String result = connectTask.get();
			jsonResult = gson.fromJson(result, JsonObject.class);
			Log.d(TAG, result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // retrieve the result of the query.	
		return jsonResult;
	}
	

	@Override
	public <T> JsonArray queryDocuments(String databaseName, String collectionName, JsonObject query, Class<T> classType) {
		
		MyHttpClient connector = new MyHttpClient();
		JsonArray jsonResult = new JsonArray();
		AsyncTask<String, String, String> connectTask = connector.execute(baseUrl + "/" + databaseName + "/" + collectionUrl + "/" + collectionName + "?" + getApiKeyParameter() + "&" + getQueryParameter(gson.toJson(query)), "GET", null);
		
		try {
			String result = connectTask.get();	
			jsonResult = gson.fromJson(result, JsonArray.class);
			Log.d(TAG, "Database  " + databaseName);
			Log.d(TAG, "Collection  " + collectionName);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // retrieve the result of the query.
		return jsonResult;
		
	}
}
