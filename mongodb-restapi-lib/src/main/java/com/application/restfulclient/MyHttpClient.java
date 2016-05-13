package com.application.restfulclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;

public class MyHttpClient  extends AsyncTask<String, String, String> {
	
	protected enum InputFields {
		URL {
			@Override
			public String getField(String... params) {
				return params[0];
			}
		},
		REQUEST_METHOD {
			@Override
			public String getField(String... params) {
				return params[1];				
			}
		},
		BODY_REQUEST {
			@Override
			public String getField(String... params) {
				return params[2];
			}
		};
		
		protected abstract String getField(String... params);
	}
	
	protected String url;
	protected String bodyRequest;
	protected String requestMethod;

	/**
	 * in params put in order : url (mandatory), requestMethod (mandatory), bodyRequest (optional)
	 */
	@Override
	protected String doInBackground(String... params){
		String result = new String();
		
		try {
			url = InputFields.URL.getField(params);
			requestMethod = InputFields.REQUEST_METHOD.getField(params);
			bodyRequest = InputFields.BODY_REQUEST.getField(params);
		} catch (IndexOutOfBoundsException iobe) {
			throw new UnsupportedOperationException();
		}

		URL url;
		try {
			url = new URL(this.url);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("Content-Type", "application/json");

			if (bodyRequest != null) {
				conn.setUseCaches(false);
				conn.setDoInput(true);
				conn.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
				wr.writeBytes(bodyRequest);
				wr.flush();
				wr.close();
			}
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				result += output;
			}
			conn.disconnect();	
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}