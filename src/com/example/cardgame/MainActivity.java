package com.example.cardgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity {

	long seed = System.nanoTime();
	List<ImageButton> buttonList = new ArrayList<ImageButton>();
	List<Integer> imageBackgroundSources = new ArrayList<Integer>();
	List<Integer> widgetIds = new ArrayList<Integer>();
	List<CustomButton> CustomButtons = new ArrayList<CustomButton>();
	List<MediaPlayer> mediaPlayers = new ArrayList<MediaPlayer>();
	int count;
	TextView ev;
	CustomButton previousButton;
	CustomButton currentButton;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState != null) {
			// Restore value of members from saved state
			// count = savedInstanceState.getInt("countKey");
		} else {
			this.initPlay();
			this.startPlay();
			ImageButton startPlaybutton = (ImageButton) findViewById(R.id.playButton);
			Button.OnClickListener startPlayListener = new Button.OnClickListener() {
				public void onClick(View dummy) {
					startPlay();
				}
			};
			startPlaybutton.setOnClickListener(startPlayListener);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle state) {
		state.putInt("countKey", count);
		super.onSaveInstanceState(state);
	}

	public void initPlay() {

		// Initialize Buttons, allocate random images for each button.
		imageBackgroundSources.add(R.drawable.minion2);
		imageBackgroundSources.add(R.drawable.minion4);
		imageBackgroundSources.add(R.drawable.minion5);
		imageBackgroundSources.add(R.drawable.minion22);
		imageBackgroundSources.add(R.drawable.minion23);
		imageBackgroundSources.add(R.drawable.minion24);
		imageBackgroundSources.add(R.drawable.minion25);
		imageBackgroundSources.add(R.drawable.minion31);

		mediaPlayers.add(MediaPlayer.create(MainActivity.this, R.raw.elo));
		mediaPlayers.add(MediaPlayer.create(MainActivity.this, R.raw.banana));
		mediaPlayers.add(MediaPlayer.create(MainActivity.this, R.raw.laugh));
		mediaPlayers.add(MediaPlayer.create(MainActivity.this, R.raw.hmmm));
		mediaPlayers.add(MediaPlayer.create(MainActivity.this, R.raw.paput));
		mediaPlayers.add(MediaPlayer.create(MainActivity.this, R.raw.what));
		mediaPlayers.add(MediaPlayer.create(MainActivity.this, R.raw.woohaha));
		mediaPlayers.add(MediaPlayer.create(MainActivity.this, R.raw.paratu));

		widgetIds.add(R.id.imageButton0);
		widgetIds.add(R.id.imageButton1);
		widgetIds.add(R.id.imageButton2);
		widgetIds.add(R.id.imageButton3);
		widgetIds.add(R.id.imageButton4);
		widgetIds.add(R.id.imageButton5);
		widgetIds.add(R.id.imageButton6);
		widgetIds.add(R.id.imageButton7);
		widgetIds.add(R.id.imageButton8);
		widgetIds.add(R.id.imageButton9);
		widgetIds.add(R.id.imageButton10);
		widgetIds.add(R.id.imageButton11);
		widgetIds.add(R.id.imageButton12);
		widgetIds.add(R.id.imageButton13);
		widgetIds.add(R.id.imageButton14);
		widgetIds.add(R.id.imageButton15);

	}

	public void startPlay() {
		count = 0;
		CustomButtons.clear();
		previousButton = null;
		Collections.shuffle(widgetIds, new Random(seed));
		for (int i = 0; i < 16; i++) {
			CustomButton button1 = new CustomButton(i, widgetIds.get(i));
			CustomButtons.add(button1);
		}

		ev = (TextView) findViewById(R.id.textView1);
		ev.setText("0");
	}

	class CustomButton {
		int id;
		int imageBackgroundSource;
		ImageButton button;
		boolean matched;
		MediaPlayer mp;

		public CustomButton(int n, int widgetId) {
			matched = false;
			if (n >= 8)
				this.id = n - 8;
			else
				this.id = n;
			imageBackgroundSource = imageBackgroundSources.get(id);
			mp = mediaPlayers.get(id);
			button = (ImageButton) findViewById(widgetId);
			Log.d("Buttongroup", String.valueOf(this.id));
			button.setVisibility(View.VISIBLE);
			this.turnFaceDown();

			Button.OnClickListener listener = new Button.OnClickListener() {
				public void onClick(View dummy) {
					if (previousButton == null)
						count++;
					if ((previousButton != null)
							&& !(previousButton.getButton() == button)) {
						count++;
						if (previousButton.equals(CustomButton.this)) {
							previousButton.setMatch();
							previousButton.turnFaceUp();
							setMatch();
						}
						for (CustomButton otherButton : CustomButtons) {
							otherButton.turnFaceDown();
						}
					}
					turnFaceUp();
					playSound();
					previousButton = CustomButton.this;
					ev.setText(String.valueOf(count));
				}
			};
			button.setOnClickListener(listener);
		};

		public ImageButton getButton() {
			return button;
		}

		public int getId() {
			return this.id;
		}

		public boolean equals(CustomButton button) {
			return this.id == button.getId();

		}

		public void turnFaceUp() {
			this.button.setBackgroundResource(imageBackgroundSource);
		}

		public void turnFaceDown() {
			if (!this.matched)
				button.setBackgroundResource(R.drawable.icon2);
			else
				button.setVisibility(View.INVISIBLE);
		}

		public void setMatch() {
			this.matched = true;
		}

		public void playSound() {
			mp.start();
		}
	}

}
