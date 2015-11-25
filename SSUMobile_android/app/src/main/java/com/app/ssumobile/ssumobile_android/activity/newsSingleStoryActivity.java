package com.app.ssumobile.ssumobile_android.activity;

import android.content.Context;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ssumobile.ssumobile_android.R;
import com.koushikdutta.ion.Ion;

public class newsSingleStoryActivity extends AppCompatActivity {

    private TextView title;
    private TextView published;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_single_story);

        title = (TextView) findViewById(R.id.singlenewstitle);
        title.setText(getIntent().getStringExtra("Title"));

        // check for image
        String imageurl = getIntent().getStringExtra("ImageURL");
        if (imageurl != ""){
            // load image

            ImageView imageView = (ImageView) findViewById(R.id.singlesnewsimage);
            Ion.with(imageView)
                    .placeholder(R.drawable.ssu_paw)
                    .error(R.drawable.ssu_paw)
                    .load(imageurl);
        }

        published = (TextView) findViewById(R.id.singlenewspublished);
        published.setText(getIntent().getStringExtra("Published"));

        content = (TextView) findViewById(R.id.singlenewscontent);
        content.setText(getIntent().getStringExtra("Content"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_single_story, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
