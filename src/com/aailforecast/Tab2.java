package com.aailforecast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
 
/**
 * Created by hp1 on 21-01-2015.
 */
public class Tab2 extends Fragment {
	
	Button bt;
	 @Override
	    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
	        View v =inflater.inflate(R.layout.tab_2,container,false);
	        
	        bt  = (Button) v.findViewById(R.id.button1);
	        
	        bt.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(getContext(), "Hello", Toast.LENGTH_SHORT).show();
				}
			});
	        return v;
	    }
}