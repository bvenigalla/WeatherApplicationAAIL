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






import com.aailforecast.HourlyRecyclerAdapter;
import com.aailforecast.RequiredHourly;

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
import android.widget.Toast;
 
/**
 * Created by hp1 on 21-01-2015.
 */
public class HourlyActivity extends Fragment {
	
	
	Button button, button1;
	String city;
	double latitude, longitude;
	double lng;
	double lat;
	List<RequiredHourly> weatherlists;
	RecyclerView recyclerview;
	LinearLayoutManager linear;
	HourlyRecyclerAdapter adapter;
	ArrayList<String> weather;
 
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.activity_main,container,false);
        
        
        
        
        weatherlists = new ArrayList<RequiredHourly>();
    	
		recyclerview = (RecyclerView) v.findViewById(R.id.recycler);
		recyclerview.setHasFixedSize(true);
		linear = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
		recyclerview.setLayoutManager(linear);
		adapter = new HourlyRecyclerAdapter(getContext(), weatherlists);
		recyclerview.setAdapter(adapter);

		
		
		
		
			lat = 16.5083;
			lng = 80.6417;
			new WeatherAsyncTask().execute();
			
		
        
        
        
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

				new WeatherAsyncTask().execute();

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

	public class WeatherAsyncTask extends AsyncTask<String, Void, Integer> {

		int i = 0;
		int j = 0;
		String result1;

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
					result1 = response.toString();
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

			adapter = new HourlyRecyclerAdapter(getContext(),
					weatherlists);
			recyclerview.setAdapter(adapter);

		}

		private String parseResult(String result) {

			try {
				JSONObject response = new JSONObject(result);

				JSONObject hourly = response.optJSONObject("hourly");
				System.out.println("printinghourly" + hourly);

				JSONArray data = hourly.optJSONArray("data");

				for (i = 0; i < data.length(); i++) {
					System.out.println("print" + i);
					JSONObject hourlydata = data.optJSONObject(i);
					String timeparse = hourlydata.optString("time");
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
					// Required Class object
					RequiredHourly object = new RequiredHourly();

					object.setDay(day);

					// Conversion from Time Stamp to Time
					String time = new SimpleDateFormat("hh" + "aa",
							Locale.ENGLISH).format(timeStamp);

					System.out.println("printtime" + time);
					object.setTime(time);
					object.setDescription(hourlydata.optString("summary"));
					object.setTemperature(hourlydata.optInt("temperature"));
					object.setHumidity(hourlydata.optDouble("humidity"));
					System.out.println("hourlydata"+hourlydata.optDouble("humidity"));
					object.setWindspeed(hourlydata.optDouble("windSpeed"));
					object.setPressure(hourlydata.optDouble("pressure"));
					object.setWindbearing(hourlydata.optDouble("windBearing"));

					double bearing = hourlydata.optDouble("windBearing");

					// if (bearing < 0 && bearing > -180) {
					// // Normalize to [0,360]
					// bearing = 360.0 + bearing;
					// }
					// if (bearing > 360 || bearing < -180) {
					// return "Unknown";
					// }
					String directions[] = { "N", "NNE", "NE", "ENE", "E",
							"ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W",
							"WNW", "NW", "NNW", "N" };
					String cardinal = directions[(int) Math
							.floor(((bearing + 11.25) % 360) / 22.5)];
					// return cardinal + " (" + formatBearing.format(bearing) +
					// " deg)";
					System.out.println("print cardinal" + cardinal);

					object.setDirection(cardinal);
					if (weatherlists == null) {
						weatherlists = new ArrayList<RequiredHourly>();
					}
					weatherlists.add(object);

				}

				System.out.println("doinbackgroundweatherlists" + weatherlists);

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return result;

		}

	}
	

}
