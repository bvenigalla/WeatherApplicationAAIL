package com.aailforecast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;




import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.aailforecast.HourlyActivity.WeatherAsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WeeklyActivity extends Activity {
	
	EditText edittext;
	Button button1;
	String city;
	double latitude, longitude;
	double lng;
	double lat;
	List<RequiredWeekly> weeklylists;
	RecyclerView recyclerview;
	LinearLayoutManager linear;
	WeeklyRecyclerAdapter adapter;
	ArrayList<String> weather;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_weekly);
		
		
		//edittext = (EditText) findViewById(R.id.editText);
		//button = (Button) findViewById(R.id.button);
	//	button1 = (Button)findViewById(R.id.button1);
		weeklylists = new ArrayList<RequiredWeekly>();

		recyclerview = (RecyclerView) findViewById(R.id.recyclerweekly);
		recyclerview.setHasFixedSize(true);
		linear = new LinearLayoutManager(this);
		recyclerview.setLayoutManager(linear);
		adapter = new WeeklyRecyclerAdapter(WeeklyActivity.this, weeklylists);
		recyclerview.setAdapter(adapter);
		Bundle intentget = getIntent().getExtras();
		if(intentget != null){
			city = intentget.getString("citypassing");
		}
		new LatLongAsynchTask().execute();
//		button1.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//				city = edittext.getText().toString();
//				System.out.println("printingcity" + city);
//
//				
//
//				System.out.println("printlatlong" + lat + lng);
//			}
//		});
	}
	
	
	private class LatLongAsynchTask extends AsyncTask<String, Void, String[]> {
		ProgressDialog dialog = new ProgressDialog(WeeklyActivity.this);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage("Please wait...");
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
		}

		@Override
		protected String[] doInBackground(String... params) {
			String response;
			try {
				response = getLatLongByURL("http://maps.google.com/maps/api/geocode/json?address="
						+ city + "&sensor=false");
				Log.d("response", "" + response);
				return new String[] { response };
			} catch (Exception e) {
				return new String[] { "error" };
			}
		}

		@Override
		protected void onPostExecute(String... result) {
			try {
				JSONObject jsonObject = new JSONObject(result[0]);

				lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
						.getJSONObject("geometry").getJSONObject("location")
						.getDouble("lng");

				lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
						.getJSONObject("geometry").getJSONObject("location")
						.getDouble("lat");

				Log.d("latitude", "" + lat);
				Log.d("longitude", "" + lng);

				latitude = lat;

				longitude = lng;

				new WeeklyAsyncTask().execute();

				System.out.println("LatLong" + latitude + longitude);

				Toast.makeText(getApplicationContext(), "latlong" + lat + lng,
						Toast.LENGTH_SHORT).show();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}
	}

	public String getLatLongByURL(String requestURL) {
		URL url;
		String response = "";
		try {
			url = new URL(requestURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(15000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setDoOutput(true);
			int responseCode = conn.getResponseCode();

			if (responseCode == HttpsURLConnection.HTTP_OK) {
				String line;
				BufferedReader br = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				while ((line = br.readLine()) != null) {
					response += line;
				}
			} else {
				response = "";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	
	
	public class WeeklyAsyncTask extends AsyncTask<String, Void, Integer> {

		int i = 0;
		

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected Integer doInBackground(String... params) {
			InputStream inputStream = null;
			Integer result = 0;
			HttpURLConnection urlConnection = null;

			// Connecting URL1(Open Weather API)

			try {

				URL url1 = new URL(
						"https://api.forecast.io/forecast/02222b8bbbe1767c27b84cf6103c9ae4/"
								+ lat + "," + lng);

				System.out.println("new url is " + url1);
				urlConnection = (HttpURLConnection) url1.openConnection();

				urlConnection.setRequestMethod("GET");

				int statusCode = urlConnection.getResponseCode();

				// 200 represents HTTP OK
				if (statusCode == 200) {
					BufferedReader r = new BufferedReader(
							new InputStreamReader(
									urlConnection.getInputStream()));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = r.readLine()) != null) {
						response.append(line);
						// System.out.println("response"+response);
					}
					parseResult(response.toString());
				//	result1 = response.toString();
					result = 1; // Successful

				} else {
					result = 0; // "Failed to fetch data!";
				}
			} catch (Exception e) {
				// Log.d(TAG, e.getLocalizedMessage());
			}
			return result;

		}

		@Override
		protected void onPostExecute(Integer result) {
			// Download complete. Let us update UI
			// progressBar.setVisibility(View.GONE);
Toast.makeText(getApplicationContext(), "sending to adapter", Toast.LENGTH_SHORT).show();
System.out.println("postexecuteeeeeeeeeeeeeeeeeeeeee"+weeklylists);
			adapter = new WeeklyRecyclerAdapter(WeeklyActivity.this, weeklylists);
			recyclerview.setAdapter(adapter);

		}

		private String parseResult(String result) {

				try {
					JSONObject response = new JSONObject(result);

					JSONObject daily = response.optJSONObject("daily");
					System.out.println("printinghourly" + daily);

					JSONArray data = daily.optJSONArray("data");

					for (i = 1; i < data.length(); i++) {
					System.out.println("print" + i);
					JSONObject dailydata = data.optJSONObject(i);
					String timeparse = dailydata.optString("time");
					System.out.println("time is " + timeparse);
					// Converstion from Time Stamp to DAY of the WEEK
					long timeStamp = Long.parseLong(timeparse) * 1000;
					Calendar c = Calendar.getInstance();
					c.setTimeInMillis(timeStamp);
					// SimpleDateFormat s = new
					// SimpleDateFormat("ddMMyyyyhhmmss");
					// String format = s.format(new Date());
					String day = new SimpleDateFormat("EEEE", Locale.ENGLISH)
							.format(timeStamp);
					//Required Class object
					RequiredWeekly object = new RequiredWeekly();
					System.out.println("dailycloudcoverprintingggggggggggg"+dailydata.getDouble("cloudCover"));
					
					object.setDay(day);
					
					// Conversion from Time Stamp to Time
					String time = new SimpleDateFormat("HH:mm:ss",
							Locale.ENGLISH).format(timeStamp);
					
					object.setTime(time);
					
					System.out.println("prntngggggggggggggggggg"+day+time);
					
					long sunrisetime = Long.parseLong(dailydata.optString("sunriseTime"))*1000;
					
					String sunrise = new SimpleDateFormat("hh:mm"+"aa", Locale.ENGLISH)
					.format(sunrisetime);
					
					
					long sunsettime = Long.parseLong(dailydata.optString("sunsetTime"))*1000;
					
					String sunset =  new SimpleDateFormat("hh:mm"+"aa", Locale.ENGLISH)
					.format(sunsettime);
					
					System.out.println("gggggggggggggggggggggggggggggggggggggggggggggggggg"+sunrise+sunset);
					
					object.setIcon(dailydata.getString("icon"));
					object.setSummary(dailydata.optString("summary"));
					object.setSunrisetime(sunrise);
					object.setSunsettime(sunset);
					object.setMoonPhase(dailydata.optDouble("moonPhase"));
					object.setPrecipIntensity(dailydata.optDouble("precipIntensity"));
					object.setPrecipIntensityMax(dailydata.optDouble("precipIntensityMax"));
					object.setPrecipIntensityMaxTime(dailydata.optString("precipIntensityMaxTime"));
					object.setPrecipProbability(dailydata.optDouble("precipProbability"));
					object.setPrecipType(dailydata.optString("precipType"));
					object.setTemperatureMin(dailydata.optInt("temperatureMin"));
					object.setTemperatureMinTime(dailydata.optString("temperatureMinTime"));
					object.setTempeatureMax(dailydata.optInt("temperatureMax"));
					//System.out.println("temperaturemaxprinting"+dailydata.optInt("temperatureMax"));
					object.setTempeaturemaxTime(dailydata.optString("temperatureMaxTime"));
					object.setApparentTemperatureMin(dailydata.optInt("apparentTemperatureMin"));
					object.setApparentTemperatureMinTime(dailydata.optString("apparenttemperatureMinTime"));
					object.setApparentTemperatureMax(dailydata.optInt("apparentTemperatureMax"));
					object.setApparentTemperaturemaxTime(dailydata.optString("apparentTemperaturemaxTime"));
					object.setDewpoint(dailydata.optInt("dewPoint"));
					object.setHumidity(dailydata.optDouble("humidity"));
					object.setWindspeed(dailydata.optDouble("windSpeed"));
					object.setWindbearing(dailydata.optDouble("windBearing"));
					object.setCloudcover(dailydata.optDouble("cloudCover"));
					object.setPressure(dailydata.optDouble("pressure"));
					object.setOzone(dailydata.optDouble("ozone"));
//					object.setDescription(hourlydata.optString("summary"));
//					object.setTemperature(hourlydata.optInt("temperature"));
//					object.setHumidity(hourlydata.optDouble("humidity"));
//					object.setWindspeed(hourlydata.optDouble("windSpeed"));
//					object.setPressure(hourlydata.optDouble("pressure"));
//					object.setWindbearing(hourlydata.optDouble("windBearing"));

				
					if(weeklylists == null){
						weeklylists = new ArrayList<RequiredWeekly>();
					}
					weeklylists.add(object);				
					//System.out.println("afteradddddinnnnnnnngggggggggggg"+weeklylists);
				}
				
				System.out.println("doinbackgroundweatherlists" + weeklylists);

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return result;

		}

	}

}
*/

 
/**
 * Created by hp1 on 21-01-2015.
 */
public class WeeklyActivity extends Fragment {
	
	
	EditText edittext;
	Button button1;
	String city;
	double latitude, longitude;
	double lng;
	double lat;
	List<RequiredWeekly> weeklylists;
	RecyclerView recyclerview;
	LinearLayoutManager linear;
	WeeklyRecyclerAdapter adapter;
	ArrayList<String> weather;
 
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.activity_main_weekly,container,false);
        
        
        
        
        weeklylists = new ArrayList<RequiredWeekly>();

		recyclerview = (RecyclerView) v.findViewById(R.id.recyclerweekly);
		recyclerview.setHasFixedSize(false);
		linear = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
		recyclerview.setLayoutManager(linear);
		adapter = new WeeklyRecyclerAdapter(getContext(), weeklylists);
		recyclerview.setAdapter(adapter);

		
		
		
		
			lat = 16.5083;
			lng = 80.6417;
			new WeeklyAsyncTask().execute();
			
		
        
        
        
        return v;
    }
    
    
    
    
    
    
    private class LatLongAsynchTask extends AsyncTask<String, Void, String[]> {
		ProgressDialog dialog = new ProgressDialog(getContext());

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage("Please wait...");
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
		}

		@Override
		protected String[] doInBackground(String... params) {
			String response;
			try {
				response = getLatLongByURL("http://maps.google.com/maps/api/geocode/json?address="
						+ city + "&sensor=false");
				Log.d("response", "" + response);
				return new String[] { response };
			} catch (Exception e) {
				return new String[] { "error" };
			}
		}

		@Override
		protected void onPostExecute(String... result) {
			try {
				JSONObject jsonObject = new JSONObject(result[0]);

				lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
						.getJSONObject("geometry").getJSONObject("location")
						.getDouble("lng");

				lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
						.getJSONObject("geometry").getJSONObject("location")
						.getDouble("lat");

				Log.d("latitude", "" + lat);
				Log.d("longitude", "" + lng);

				latitude = lat;

				longitude = lng;

				new WeeklyAsyncTask().execute();

				System.out.println("LatLong" + latitude + longitude);

				Toast.makeText(getContext(), "latlong" + lat + lng,
						Toast.LENGTH_SHORT).show();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}
	}

	public String getLatLongByURL(String requestURL) {
		URL url;
		String response = "";
		try {
			url = new URL(requestURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(15000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setDoOutput(true);
			int responseCode = conn.getResponseCode();

			if (responseCode == HttpsURLConnection.HTTP_OK) {
				String line;
				BufferedReader br = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				while ((line = br.readLine()) != null) {
					response += line;
				}
			} else {
				response = "";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public class WeeklyAsyncTask extends AsyncTask<String, Void, Integer> {

		int i = 0;
		

		@Override
		protected void onPreExecute() {

		}

		@Override
		protected Integer doInBackground(String... params) {
			InputStream inputStream = null;
			Integer result = 0;
			HttpURLConnection urlConnection = null;

			// Connecting URL1(Open Weather API)

			try {

				URL url1 = new URL(
						"https://api.forecast.io/forecast/02222b8bbbe1767c27b84cf6103c9ae4/"
								+ lat + "," + lng);

				System.out.println("new url is " + url1);
				urlConnection = (HttpURLConnection) url1.openConnection();

				urlConnection.setRequestMethod("GET");

				int statusCode = urlConnection.getResponseCode();

				// 200 represents HTTP OK
				if (statusCode == 200) {
					BufferedReader r = new BufferedReader(
							new InputStreamReader(
									urlConnection.getInputStream()));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = r.readLine()) != null) {
						response.append(line);
						// System.out.println("response"+response);
					}
					parseResult(response.toString());
				//	result1 = response.toString();
					result = 1; // Successful

				} else {
					result = 0; // "Failed to fetch data!";
				}
			} catch (Exception e) {
				// Log.d(TAG, e.getLocalizedMessage());
			}
			return result;

		}

		@Override
		protected void onPostExecute(Integer result) {
			// Download complete. Let us update UI
			// progressBar.setVisibility(View.GONE);
Toast.makeText(getContext(), "sending to adapter", Toast.LENGTH_SHORT).show();
System.out.println("postexecuteeeeeeeeeeeeeeeeeeeeee"+weeklylists);
			adapter = new WeeklyRecyclerAdapter(getContext(), weeklylists);
			recyclerview.setAdapter(adapter);

		}

		private String parseResult(String result) {

				try {
					JSONObject response = new JSONObject(result);

					JSONObject daily = response.optJSONObject("daily");
					System.out.println("printinghourly" + daily);

					JSONArray data = daily.optJSONArray("data");

					for (i = 1; i < data.length(); i++) {
					System.out.println("print" + i);
					JSONObject dailydata = data.optJSONObject(i);
					String timeparse = dailydata.optString("time");
					System.out.println("time is " + timeparse);
					// Converstion from Time Stamp to DAY of the WEEK
					long timeStamp = Long.parseLong(timeparse) * 1000;
					Calendar c = Calendar.getInstance();
					c.setTimeInMillis(timeStamp);
					// SimpleDateFormat s = new
					// SimpleDateFormat("ddMMyyyyhhmmss");
					// String format = s.format(new Date());
					String day = new SimpleDateFormat("EEEE", Locale.ENGLISH)
							.format(timeStamp);
					//Required Class object
					RequiredWeekly object = new RequiredWeekly();
					System.out.println("dailycloudcoverprintingggggggggggg"+dailydata.getDouble("cloudCover"));
					
					object.setDay(day);
					
					// Conversion from Time Stamp to Time
					String time = new SimpleDateFormat("HH:mm:ss",
							Locale.ENGLISH).format(timeStamp);
					
					object.setTime(time);
					
					System.out.println("prntngggggggggggggggggg"+day+time);
					
					long sunrisetime = Long.parseLong(dailydata.optString("sunriseTime"))*1000;
					
					String sunrise = new SimpleDateFormat("hh:mm"+"aa", Locale.ENGLISH)
					.format(sunrisetime);
					
					
					long sunsettime = Long.parseLong(dailydata.optString("sunsetTime"))*1000;
					
					String sunset =  new SimpleDateFormat("hh:mm"+"aa", Locale.ENGLISH)
					.format(sunsettime);
					
					System.out.println("gggggggggggggggggggggggggggggggggggggggggggggggggg"+sunrise+sunset);
					
					object.setIcon(dailydata.getString("icon"));
					object.setSummary(dailydata.optString("summary"));
					object.setSunrisetime(sunrise);
					object.setSunsettime(sunset);
					object.setMoonPhase(dailydata.optDouble("moonPhase"));
					object.setPrecipIntensity(dailydata.optDouble("precipIntensity"));
					object.setPrecipIntensityMax(dailydata.optDouble("precipIntensityMax"));
					object.setPrecipIntensityMaxTime(dailydata.optString("precipIntensityMaxTime"));
					object.setPrecipProbability(dailydata.optDouble("precipProbability"));
					object.setPrecipType(dailydata.optString("precipType"));
					object.setTemperatureMin(dailydata.optInt("temperatureMin"));
					object.setTemperatureMinTime(dailydata.optString("temperatureMinTime"));
					object.setTempeatureMax(dailydata.optInt("temperatureMax"));
					//System.out.println("temperaturemaxprinting"+dailydata.optInt("temperatureMax"));
					object.setTempeaturemaxTime(dailydata.optString("temperatureMaxTime"));
					object.setApparentTemperatureMin(dailydata.optInt("apparentTemperatureMin"));
					object.setApparentTemperatureMinTime(dailydata.optString("apparenttemperatureMinTime"));
					object.setApparentTemperatureMax(dailydata.optInt("apparentTemperatureMax"));
					object.setApparentTemperaturemaxTime(dailydata.optString("apparentTemperaturemaxTime"));
					object.setDewpoint(dailydata.optInt("dewPoint"));
					object.setHumidity(dailydata.optDouble("humidity"));
					object.setWindspeed(dailydata.optDouble("windSpeed"));
					object.setWindbearing(dailydata.optDouble("windBearing"));
					object.setCloudcover(dailydata.optDouble("cloudCover"));
					object.setPressure(dailydata.optDouble("pressure"));
					object.setOzone(dailydata.optDouble("ozone"));
//					object.setDescription(hourlydata.optString("summary"));
//					object.setTemperature(hourlydata.optInt("temperature"));
//					object.setHumidity(hourlydata.optDouble("humidity"));
//					object.setWindspeed(hourlydata.optDouble("windSpeed"));
//					object.setPressure(hourlydata.optDouble("pressure"));
//					object.setWindbearing(hourlydata.optDouble("windBearing"));

				
					if(weeklylists == null){
						weeklylists = new ArrayList<RequiredWeekly>();
					}
					weeklylists.add(object);				
					//System.out.println("afteradddddinnnnnnnngggggggggggg"+weeklylists);
				}
				
				System.out.println("doinbackgroundweatherlists" + weeklylists);

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return result;

		}

	}
	

}
