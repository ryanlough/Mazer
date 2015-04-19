package ryan.mazer;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.games.Games;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

//import gms.common.api.*;
//import com.google.example.games.basegameutils.BaseGameActivity;

public class DroidzActivity extends Activity {
    /** Called when the activity is first created. */
	
	private static final String TAG = DroidzActivity.class.getSimpleName();
	public MediaPlayer mediaPlayer;
	public static boolean playMusic = true;
	public static boolean showLine = true;
	public static int width;
	public static int height;
	
	public static float currScore;
	public static float high3 = 0;
	public static float high4 = 0;
	public static float high5 = 0;
	
	public int n;
    private int currView;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get from the SharedPreferences
        SharedPreferences settings = getApplicationContext().getSharedPreferences("setting", MODE_MULTI_PROCESS);
        playMusic = settings.getBoolean("playMusic", true);
        high3 = settings.getFloat("3", 0);
        high4 = settings.getFloat("4", 0);
        high5 = settings.getFloat("5", 0);
        showLine = settings.getBoolean("showLine", true);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        DroidzActivity.width = metrics.widthPixels;
        DroidzActivity.height = metrics.heightPixels;
        // set our MainGamePanel as the View
        //g = new GUI(this, DroidzActivity.width, DroidzActivity.height);
        setContentView(R.layout.main);
        this.currView = R.layout.main;
        Log.d(TAG, "View added");
        
        //Games.Leaderboards.submitScore(getApiClient(), ""+R.string.leaderboard_mazer, 12345L);
    }
    
    public void changeTog(View view) {
        DroidzActivity.showLine = !DroidzActivity.showLine;
    }
    
    public void changeMusic(View view) {
        ImageButton img = (ImageButton) this.findViewById(R.id.imageButton2);
        DroidzActivity.playMusic = !DroidzActivity.playMusic;
        
        if(playMusic) {
            mediaPlayer = MediaPlayer.create(this, R.raw.presenterator);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            Log.d(TAG, "Music added");
            img.setImageResource(R.drawable.sound);
        }
        else {
            mediaPlayer.stop();
            img.setImageResource(R.drawable.nosound);
        }
    }
    
    public void newTips(View view) {
        this.setContentView(R.layout.tips);
        this.currView = R.layout.tips;
    }

    public void newGame(View view) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        Log.d(TAG, metrics.widthPixels + " " + metrics.heightPixels);
        // set our MainGamePanel as the View
        setContentView(new MainGamePanel(this, metrics.widthPixels-1, metrics.heightPixels, n));
        this.currView = -1;
    }
    
    public void newThree(View view) {
        this.n = 3;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        Log.d(TAG, metrics.widthPixels + " " + metrics.heightPixels);
        // set our MainGamePanel as the View
        setContentView(new MainGamePanel(this, metrics.widthPixels-1, metrics.heightPixels, n));
        this.currView = -1;
    }
    
    public void newFour(View view) {
        this.n = 4;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        Log.d(TAG, metrics.widthPixels + " " + metrics.heightPixels);
        // set our MainGamePanel as the View
        setContentView(new MainGamePanel(this, metrics.widthPixels-1, metrics.heightPixels, n));
        this.currView = -1;
    }
    
    public void newFive(View view) {
        this.n = 5;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        Log.d(TAG, metrics.widthPixels + " " + metrics.heightPixels);
        // set our MainGamePanel as the View
        setContentView(new MainGamePanel(this, metrics.widthPixels-1, metrics.heightPixels, n));
        this.currView = -1;
    }
    
    public void newGame(int n) {
        this.n = n;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        Log.d(TAG, metrics.widthPixels + " " + metrics.heightPixels);
        // set our MainGamePanel as the View
        setContentView(new MainGamePanel(this, metrics.widthPixels-1, metrics.heightPixels, n));
        this.currView = -1;
    }
    
    public void newRestart() {
        this.setContentView(R.layout.gameover);
        if (this.n == 3 && DroidzActivity.high3 == DroidzActivity.currScore) {
            ((TextView) findViewById(R.id.text2)).setText("NEW HIGH SCORE");
        }
        if (this.n == 4 && DroidzActivity.high4 == DroidzActivity.currScore) {
            ((TextView) findViewById(R.id.text2)).setText("NEW HIGH SCORE");
        }
        if (this.n == 5 && DroidzActivity.high5 == DroidzActivity.currScore) {
            ((TextView) findViewById(R.id.text2)).setText("NEW HIGH SCORE");
        }
        // Update the TextView
        TextView text = (TextView) findViewById(R.id.text3);
        text.setText("You lasted " + currScore + " seconds.");
        
        // Look up the AdView as a resource and load a request.
        AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        this.currView = R.layout.gameover;
    }
    
    public void newGUI(View view) {
        setContentView(R.layout.main);
        this.currView = R.layout.main;
    }
    
    public void setSettings(View view) {
        setContentView(R.layout.settings);
        
        ToggleButton tog = (ToggleButton) this.findViewById(R.id.toggleButton1);
        if(DroidzActivity.showLine) {
            tog.setChecked(true);
        }
        else {
            tog.setChecked(false);
        }
        this.currView = R.layout.settings;
    }
    
    public void setAbout(View view) {
        setContentView(R.layout.about);
        this.currView = R.layout.about;
    }
    
    public void setScoreboard(View view) {
        setContentView(R.layout.score);
        
        // Update the TextViews
        TextView text = (TextView) findViewById(R.id.text1);
        text.setText("Size 3: " + DroidzActivity.high3 + " seconds");
        text = (TextView) findViewById(R.id.text2);
        text.setText("Size 4: " + DroidzActivity.high4 + " seconds");
        text = (TextView) findViewById(R.id.text3);
        text.setText("Size 5: " + DroidzActivity.high5 + " seconds");
        this.currView = R.layout.score;
    }
    
	@Override
	protected void onDestroy() {
		Log.d(TAG, "Destroying...");
		super.onDestroy();
	}

    @Override
	protected void onStop() {
		Log.d(TAG, "Stopping...");
		if(playMusic) {
		    mediaPlayer.stop();
		}
		SharedPreferences settings = getApplicationContext().getSharedPreferences("setting", MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("playMusic", playMusic);
        editor.putFloat("3", DroidzActivity.high3);
        editor.putFloat("4", DroidzActivity.high4);
        editor.putFloat("5", DroidzActivity.high5);
        editor.putBoolean("showLine", showLine);
        
        // Apply the edits!
        editor.apply();
		super.onStop();
	}
	
	@Override
    protected void onStart() {
        if(playMusic) {
            mediaPlayer = MediaPlayer.create(this, R.raw.presenterator);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            Log.d(TAG, "Music added");
        }
        super.onStart();
    }
	
	@Override
	protected void onResume() {
	    
	    if (!GooglePlayServicesUtil.getErrorString(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)).equals("SUCCESS")) {
	        GooglePlayServicesUtil.getErrorDialog(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this), this, 0);
	    }
	    super.onResume();
	}
    
	@Override
	public void onBackPressed() {
	   Log.d("CDA", "onBackPressed Called");
	   Log.d("CDA", "" + R.layout.main);
	  if (this.currView != R.layout.main) {
	       setContentView(R.layout.main);
	       this.currView = R.layout.main;
	   }
	   else {
	       super.onBackPressed();
	  }
	}

//    @Override
    public void onSignInFailed() {
        // TODO Auto-generated method stub
        
    }

//    @Override
    public void onSignInSucceeded() {
        // TODO Auto-generated method stub
        
    }
}