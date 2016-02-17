package com.aailforecast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HourlyRecyclerAdapter extends RecyclerView.Adapter<HourlyRecyclerAdapter.WeatherViewHolder> {

	List<RequiredHourly> weatherlists;
	static Context context;
	Bitmap bitmap1;
	public HourlyRecyclerAdapter(Context context,
			List<RequiredHourly> weatherlists) {
		// TODO Auto-generated constructor stub

		this.weatherlists = weatherlists;
		this.context = context;

		System.out.println("printinginadapter" + weatherlists);
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return (null != weatherlists ? weatherlists.size() : 0);
	}

	@Override
	public WeatherViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		// TODO Auto-generated method stub

		View view = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.hourly_layout, viewGroup, false);
		WeatherViewHolder viewHolder = new WeatherViewHolder(view);

		return viewHolder;
	}

	public class WeatherViewHolder extends RecyclerView.ViewHolder {

		protected TextView textview1;
		protected TextView textview2;
		protected TextView textview3;
		protected TextView textview4;
		protected TextView textview5;
		protected TextView textview6;
		protected TextView textview7;
		protected TextView textview8;
		protected TextView textview9;
		Button button1;
		CardView relative;
		

		public WeatherViewHolder(View view) {
			super(view);
			// TODO Auto-generated constructor stub

			this.textview1 = (TextView) view.findViewById(R.id.day);
			this.textview2 = (TextView) view.findViewById(R.id.time);
			this.textview3 = (TextView) view.findViewById(R.id.description);
			this.textview4 = (TextView) view.findViewById(R.id.temperature);
			// this.textview5 = (TextView) view.findViewById(R.id.humidity);
			this.textview6 = (TextView) view.findViewById(R.id.windspeed);
			// this.textview7 = (TextView) view.findViewById(R.id.pressure);
			// this.textview8 = (TextView) view.findViewById(R.id.windbearing);
			this.textview9 = (TextView) view.findViewById(R.id.direction);
			this.button1 = (Button) view.findViewById(R.id.button1);
			relative = (CardView) view.findViewById(R.id.cardview);
		}

	}

	@Override
	public void onBindViewHolder(final WeatherViewHolder viewholder, int i) {
		// TODO Auto-generated method stub
		 Random rand = new Random();
		
		RequiredHourly element = weatherlists.get(i);

		viewholder.textview1.setText(element.getDay());
		viewholder.textview2.setText(element.getTime());
		viewholder.textview3.setText(element.getDescription());
		viewholder.textview4
				.setText((((element.getTemperature() - 32) * 5) / 9) + "°C");
		// viewholder.textview5.setText(String.valueOf(element.getHumidity()));
		viewholder.textview6.setText(String.valueOf(element.getWindspeed())
				+ "mph");
		// viewholder.textview7.setText(String.valueOf(element.getPressure())+"Pa");
		// viewholder.textview8.setText(String.valueOf(element.getWindbearing()+"°"));
		viewholder.textview9.setText(element.getDirection());
		
		
		
		
		viewholder.button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//take screenshot
	            bitmap1 = captureScreen(viewholder.relative);

	            Toast.makeText(context, "Screenshot captured..!", Toast.LENGTH_LONG).show();

	            try {
	                if(bitmap1!=null){
	                    //save image to SD card
	                    saveImage(bitmap1);
	                }
	                Toast.makeText(context, "Screenshot saved..!", Toast.LENGTH_LONG).show();
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
			
			}
		});
		
		
		
		
		
		                      

		                        //This runs in a background thread.  
		                        //We cannot call the UI from this thread, so we must call the main UI thread and pass a runnable
		            

		               
		                   
		                                        //The random generator creates values between [0,256) for use as RGB values used below to create a random color
		                                        //We call the RelativeLayout object and we change the color.  The first parameter in argb() is the alpha.
		                    viewholder.relative.setBackgroundColor(Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256) ));
		                    
		                    
		                    viewholder.textview1.setTextColor(Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256) ));
		                
		           
		        
		     
		                   
		                    viewholder.relative.setPreventCornerOverlap(true);
		

	}
	
	
	

public static Bitmap captureScreen(View v) {

    Bitmap screenshot = null;
    try {

        if(v!=null) {

            screenshot = Bitmap.createBitmap(v.getMeasuredWidth(),v.getMeasuredHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(screenshot);
            v.draw(canvas);
        }

    }catch (Exception e){
        Log.d("ScreenShotActivity", "Failed to capture screenshot because:" + e.getMessage());
    }

    return screenshot;
}


public static void saveImage(Bitmap bitmap) throws IOException{

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 40, bytes);
    File file = new File(Environment.getExternalStorageDirectory() + File.separator + "test.png");
    file.createNewFile();
    FileOutputStream fo = new FileOutputStream(file);
    fo.write(bytes.toByteArray());
    fo.close();
    shareScreenshot(file);
}


	private static void shareScreenshot(File image) {
		// TODO Auto-generated method stub
		Intent shareScreenIntent = new Intent(
				android.content.Intent.ACTION_SEND);
		shareScreenIntent.setType("image/png");
		Uri myUri = Uri.parse("file://" + image);
		shareScreenIntent.putExtra(Intent.EXTRA_STREAM, myUri);
		context.startActivity(Intent.createChooser(shareScreenIntent,
				"Send ScreenShot..."));	
		
	}//Closed shareScreenshot

}
