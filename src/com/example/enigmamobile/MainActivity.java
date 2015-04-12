package com.example.enigmamobile;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	
    public MediaPlayer BackgroundMusic;
    public MediaPlayer ButtonSound;
    public Enigma myEnigma;
    public boolean onAboutUs;
    public int currId;
    public boolean onmute;
    
    public String defPlate1 = new String("#GNUAHOVBIPWCJQXDKRYELSZFMT");
    public String defPlate2 = new String("#EJOTYCHMRWAFKPUZDINSXBGLQV");
    public String defPlate3 = new String("#BDFHJLNPRTVXZACEGIKMOQSUWY");
    
    public String inner = new String("Inner    : ");
    public String middle = new String("Middle : ");
    public String outer = new String("Outer   : ");
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myEnigma = new Enigma(defPlate1,defPlate2,defPlate3,".");
        onmute = false;
        onAboutUs = false;
        InitMusic();
        showMain();
    }

    public void InitMusic(){
        BackgroundMusic = MediaPlayer.create(MainActivity.this, R.raw.music1);
        ButtonSound = MediaPlayer.create(MainActivity.this, R.raw.button_click);
        BackgroundMusic.start();
        BackgroundMusic.setLooping(true);
    }
     
    public void showMain(){
        setContentView(R.layout.activity_main);
        currId = R.layout.activity_main;
        Button EncButton = (Button) findViewById(R.id.Encrypt);
        

		final ImageButton MuteButton = (ImageButton) findViewById(R.id.Mute);
		if(onmute){
			MuteButton.setImageResource(R.drawable.mute);
		}else{
			MuteButton.setImageResource(R.drawable.unmute);
		}
        
        EncButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ButtonSound.start();
				currId = R.layout.encrypt;
		        setContentView(R.layout.encrypt);

				final TextView textResult = (TextView) findViewById(R.id.tvResult);
				textResult.setText("");
				Button encBut = (Button) findViewById(R.id.enkripsikan);
				encBut.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						EditText inputText = (EditText) findViewById(R.id.inputText);
						
						if(myEnigma.IsInputValid(inputText.getText().toString())){
							myEnigma.StrInput = inputText.getText().toString();
							myEnigma.Encrypt();
							textResult.setText(myEnigma.StrEncrypted);
						}else{
							Toast message = Toast.makeText(MainActivity.this, "Sorry your input is invalid!\nRead help for more information!", Toast.LENGTH_LONG);
							message.show();
						}
					}
				});
			}
		});
        
        Button DecButton = (Button) findViewById(R.id.Decrypt);
        DecButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ButtonSound.start();
				currId = R.layout.decrypt;
		        setContentView(R.layout.decrypt);

				final TextView textResult = (TextView) findViewById(R.id.tvResultDec);
				textResult.setText("");
				Button encBut = (Button) findViewById(R.id.dekripsikan);
				encBut.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						EditText inputText = (EditText) findViewById(R.id.inputTextDec);
						
						if(myEnigma.IsInputValid(inputText.getText().toString())){
							myEnigma.StrInput = inputText.getText().toString();
							myEnigma.Decrypt();
							textResult.setText(myEnigma.StrDecrypted);
						}else{
							Toast message = Toast.makeText(MainActivity.this, "Sorry your input is invalid!\nRead help for more information!", Toast.LENGTH_LONG);
							message.show();
						}
					}
				});
			}
		});
        
        
        
        Button SetButton = (Button) findViewById(R.id.Setting);
        SetButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ButtonSound.start();
				currId = R.layout.setting;
		        setContentView(R.layout.setting);
		        
		        final TextView tvInner, tvMiddle, tvOuter;
		        tvInner = (TextView) findViewById(R.id.tvInnerPlate);
		        tvMiddle= (TextView) findViewById(R.id.tvMiddlePlate);
		        tvOuter = (TextView) findViewById(R.id.tvOuterPlate);
		        
		        TextViewUpdateCurrentPlate(tvInner, tvMiddle, tvOuter);
		        
		        Button useDefault = (Button) findViewById(R.id.useDefault);
		        useDefault.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						myEnigma.Layer1 = defPlate1;
						myEnigma.Layer2 = defPlate2;
						myEnigma.Layer3 = defPlate3;
						TextViewUpdateCurrentPlate(tvInner, tvMiddle, tvOuter);
					}
				});
		        

		        final  EditText newInner, newMiddle, newOuter;
		        newInner = (EditText) findViewById(R.id.newInner);
		        newMiddle = (EditText) findViewById(R.id.newMiddle);
		        newOuter = (EditText) findViewById(R.id.newOuter);
		        
		        Button useEdited = (Button) findViewById(R.id.useEdited);
		        useEdited.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(myEnigma.IsPlateValid(newInner.getText().toString())&&
								myEnigma.IsPlateValid(newOuter.getText().toString())&&
								myEnigma.IsPlateValid(newMiddle.getText().toString())){
							myEnigma.Layer1 = newInner.getText().toString();
							myEnigma.Layer2 = newMiddle.getText().toString();
							myEnigma.Layer3 = newOuter.getText().toString();
							TextViewUpdateCurrentPlate(tvInner, tvMiddle, tvOuter);
						}else{
							Toast message = Toast.makeText(MainActivity.this, "Sorry your input is invalid!\nRead help for more information!", Toast.LENGTH_LONG);
							message.show();
						}
					}
				});
			}
		});
        
        Button HelpButton = (Button) findViewById(R.id.Help);
        HelpButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ButtonSound.start();
				currId = R.layout.help;
		        setContentView(R.layout.help);
			}
		});
        
        Button ExitButton = (Button) findViewById(R.id.Exit);
        ExitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ButtonSound.start();
				exit();
			}
		});


        MuteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(onmute){
					InitMusic();
					MuteButton.setImageResource(R.drawable.unmute);
					onmute = false;
				}else{
					onmute = true;
					BackgroundMusic.release();
					MuteButton.setImageResource(R.drawable.mute);
				}
			}
		});
        
    }
    
    public void TextViewUpdateCurrentPlate(TextView tvInner,TextView tvMiddle,TextView tvOuter){
    	tvInner.setText(inner+myEnigma.Layer1);
        tvMiddle.setText(middle+myEnigma.Layer2);
        tvOuter.setText(outer+myEnigma.Layer3);
    }
    
    public void exit() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		BackgroundMusic.release();
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_android, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.aboutUs) {
	        setContentView(R.layout.about_us);
			ButtonSound.start();
	        onAboutUs = true;
            return true;
        }
        return false;
    }

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		ButtonSound.start();
		if(onAboutUs){
			onAboutUs = false;
			if(currId==R.layout.activity_main)
				showMain();
			else
		        setContentView(currId);
		}else
			showMain();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		ButtonSound.release();
		BackgroundMusic.release();
	}

    /**
     * A placeholder fragment containing a simple view.
     */
   

}
