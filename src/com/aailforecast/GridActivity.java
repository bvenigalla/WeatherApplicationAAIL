package com.aailforecast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class GridActivity extends Activity{
	RecyclerView recycler;
	EditText edit;
	Button enter;
	GridLayoutManager grid;
	MyRecyclerAdapter adapter;
	List<Data> lists;
	String city;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_recycler);
		lists = new ArrayList<Data>();
		recycler = (RecyclerView) findViewById(R.id.recycler);
		recycler.setHasFixedSize(true);
		edit = (EditText) findViewById(R.id.editText);
		enter = (Button) findViewById(R.id.button);
		grid = new GridLayoutManager(this, 2);	
		recycler.setLayoutManager(grid);
		//adapter = new MyRecyclerAdapter(this, lists);
		
		enter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				city = edit.getText().toString();
				edit.setText("");
				
				String url = "202.53.87.242/forcast/weather-forecast.php?location=hyderabad&latitude=17.3753&longitude=78.4744&appid=2de143494c0b295cca9337e1e96b00e0";
				new AsyncTaskHttp().execute(url);
			}
		});
	}
	
	public class AsyncTaskHttp extends AsyncTask<String, String, Integer>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			
			Integer result = 0;
	        HttpURLConnection urlConnection;
	        try {
	            URL url = new URL(params[0]);
	            urlConnection = (HttpURLConnection) url.openConnection();
	            int statusCode = urlConnection.getResponseCode();

	            // 200 represents HTTP OK
	            if (statusCode == 200) {
	                BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	                StringBuilder response = new StringBuilder();
	                String line;
	                while ((line = r.readLine()) != null) {
	                    response.append(line);
	                }
	            //    reponse1 = response.toString();
	                parseResult(response.toString());
	                System.out.println("printing response" +response);
	                result = 1; // Successful
	            } else {
	                result = 0; //"Failed to fetch data!";
	            }
	        } catch (Exception e) {
	           // Log.d(TAG, e.getLocalizedMessage());
	        }
	        return result; //"Failed to fetch data!";
	    
			
		}
		
		
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
		
	}
	
	private void parseResult(String result) {
		// TODO Auto-generated method stub
		
		try {
			
			JSONObject jsonobject = new JSONObject(result);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
