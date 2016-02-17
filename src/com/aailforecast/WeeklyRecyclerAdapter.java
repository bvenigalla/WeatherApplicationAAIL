package com.aailforecast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.aailforecast.HourlyRecyclerAdapter.WeatherViewHolder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WeeklyRecyclerAdapter extends
		RecyclerView.Adapter<WeeklyRecyclerAdapter.WeeklyViewHolder> {

	List<RequiredWeekly> weeklylists;
	static Context context;
	Bitmap bitmap1;

	public WeeklyRecyclerAdapter(Context context,
			List<RequiredWeekly> weeklylists) {
		// TODO Auto-generated constructor stub
		this.weeklylists = weeklylists;
		System.out.println("consssssssssssstttttttttttttttt" + weeklylists);
		this.context = context;

	}

	@Override
	public WeeklyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.weekly_layout, null);
		WeeklyViewHolder viewHolder = new WeeklyViewHolder(view);
		System.out.println("lsistsststssttststststststs" + weeklylists);
		return viewHolder;
	}

	public class WeeklyViewHolder extends RecyclerView.ViewHolder {
		protected TextView textview1;
		protected TextView textview2;
		protected TextView textview3;
		protected TextView textview4;
		protected ImageView image;

		protected TextView textview5;
		protected TextView textview6;
		protected TextView textview7;
		protected TextView textview8;
		protected TextView textview9;

		 protected TextView textview10;
		 protected TextView textview11;
		 protected CardView cardview;
		 Button button1;
			LinearLayout relative;

		// protected TextView textview12;
		// protected TextView textview13;
		// protected TextView textview14;
		// protected TextView textview15;
		// protected TextView textview16;
		// protected TextView textview17;
		// protected TextView textview18;
		// protected TextView textview19;
		// protected TextView textview20;
		// protected TextView textview21;
		// protected TextView textview22;
		// protected TextView textview23;
		// protected TextView textview24;
		// protected TextView textview25;

		public WeeklyViewHolder(View view) {
			super(view);

			this.textview1 = (TextView) view.findViewById(R.id.day);
			this.textview2 = (TextView) view.findViewById(R.id.temperature);
			this.image = (ImageView) view.findViewById(R.id.image);
			this.textview3 = (TextView) view.findViewById(R.id.description);
			this.textview4 = (TextView) view.findViewById(R.id.humdyn);
			this.textview5 = (TextView) view.findViewById(R.id.cloudcoverdyn);
			this.textview6 = (TextView) view.findViewById(R.id.pressuredyn);
			this.textview7 = (TextView) view.findViewById(R.id.dewpointdyn);
			this.textview8 = (TextView) view.findViewById(R.id.risedyn);
			this.textview9 = (TextView) view.findViewById(R.id.setdyn);
			this.textview10 = (TextView) view.findViewById(R.id.feelslikedyn);
			this.textview11 = (TextView) view.findViewById(R.id.windspeeddyn);
			this.cardview = (CardView) view.findViewById(R.id.cardview);
			this.button1 = (Button) view.findViewById(R.id.button1);
			relative = (LinearLayout) view.findViewById(R.id.weeklylinear);
		}

	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return (null != weeklylists ? weeklylists.size() : 0);
	}

	@SuppressLint("ResourceAsColor") @Override
	public void onBindViewHolder(final WeeklyViewHolder weeklyViewHolder, int i) {
		// TODO Auto-generated method stub

		RequiredWeekly element = weeklylists.get(i);

		System.out
				.println("temperaturemaxsetting" + element.getTempeatureMax());
		System.out.println("printitrtrttttttttttttttttttttttttt" + weeklylists);

		weeklyViewHolder.textview1.setText(element.getDay());
		// weeklyViewHolder.textview2.setText(element.getTempeatureMax());
		weeklyViewHolder.textview2.setText(String.valueOf(((((element
				.getTempeatureMax() - 32) * 5) / 9))
				+ "°C"
				+ "/"
				+ ((((element.getTemperatureMin() - 32) * 5) / 9)) + "°C"));
		weeklyViewHolder.image.setBackgroundResource(R.drawable.partlycloudy);
		weeklyViewHolder.textview3.setText(element.getSummary());
		weeklyViewHolder.textview4.setText((String.valueOf(Math.round((element
				.getHumidity() * 100)))) + "%");
		weeklyViewHolder.textview5.setText(String.valueOf(Math.round(element
				.getCloudcover() * 100)) + "%");
		weeklyViewHolder.textview6
				.setText(String.valueOf(element.getPressure()) + "mb");
		weeklyViewHolder.textview7
				.setText((((element.getDewpoint() - 32) * 5) / 9) + "°C");
		weeklyViewHolder.textview8.setText(element.getSunrisetime());
		weeklyViewHolder.textview9.setText(element.getSunsettime());
		weeklyViewHolder.textview10.setText((((element.getApparentTemperatureMax()-32)*5)/9)+"°C"+"/"+(((element.getApparentTemperatureMin()-32)*5)/9)+"°C");
		weeklyViewHolder.textview11.setText(String.valueOf(element.getWindspeed())+"mph");
		System.out.println("elementsprinting" + element.getApparentTemperatureMin());
		// System.out.println("printelements"+element.getDay()+element.getTime());
		
	
//		 if(i==0){
//		       weeklyViewHolder.cardview.setBackgroundColor(Color.RED);
//		 }
//		 if(i==1){
//		       weeklyViewHolder.cardview.setBackgroundColor(Color.CYAN);
//		 }
//		 if(i==2){
//		       weeklyViewHolder.cardview.setBackgroundColor(R.color.slateblue);
//		 }
//		 if(i==3){
//		       weeklyViewHolder.cardview.setBackgroundColor(Color.GRAY);
//		 }
//		 if(i==4){
//		       weeklyViewHolder.cardview.setBackgroundColor(Color.LTGRAY);
//		 }
//		 if(i==5){
//		       weeklyViewHolder.cardview.setBackgroundColor(Color.YELLOW);
//		 }
//		 if(i==6){
//		       weeklyViewHolder.cardview.setBackgroundColor(Color.BLUE);
//		 }
		
weeklyViewHolder.button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//take screenshot
	            bitmap1 = captureScreen(weeklyViewHolder.relative);

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
