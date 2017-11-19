package com.littlenum.hero.svgasample;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hero on 2017/11/18.
 */

public class PreviewActivity extends AppCompatActivity {
    private SVGAImageView mSVGAImageView;
    private boolean playing = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_layout);
        int type = getIntent().getIntExtra("type", 0);
        String img = getIntent().getStringExtra("name");
        mSVGAImageView = findViewById(R.id.imageView);
        SVGAParser parser = new SVGAParser(this);
        switch (type) {
            case 0:
                parser.parse(img, new SVGAParser.ParseCompletion() {
                    @Override
                    public void onComplete(SVGAVideoEntity mSVGAVideoEntity) {
                        SVGADrawable drawable = new SVGADrawable(mSVGAVideoEntity);
                        mSVGAImageView.setImageDrawable(drawable);
                        mSVGAImageView.startAnimation();
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(PreviewActivity.this, "parse error!", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 1:
                URL url = null;
                try {
                    url = new URL(img);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                parser.parse(url, new SVGAParser.ParseCompletion() {
                    @Override
                    public void onComplete(SVGAVideoEntity mSVGAVideoEntity) {
                        SVGADrawable drawable = new SVGADrawable(mSVGAVideoEntity);
                        mSVGAImageView.setImageDrawable(drawable);
                        mSVGAImageView.startAnimation();
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(PreviewActivity.this, "parse error!", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 2:
                parser.parse(img, new SVGAParser.ParseCompletion() {
                    @Override
                    public void onComplete(SVGAVideoEntity mSVGAVideoEntity) {
                        SVGADynamicEntity dynamicItem = new SVGADynamicEntity();
                        SVGADrawable drawable = new SVGADrawable(mSVGAVideoEntity, dynamicItem);
                        TextPaint textPaint = new TextPaint();
                        textPaint.setTextSize(30);
                        textPaint.setFakeBoldText(true);
                        textPaint.setARGB(0xff, 0x00, 0x00, 0x00);
                        textPaint.setShadowLayer((float) 1.0, (float) 0.0, (float) 1.0, Color.BLACK); // 各种配置
                        dynamicItem.setDynamicText("TEXT!", textPaint, "yu11");
                        mSVGAImageView.setImageDrawable(drawable);
                        mSVGAImageView.startAnimation();
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(PreviewActivity.this, "parse error!", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case 3:

                break;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            playing = !playing;
            if (playing) {
                mSVGAImageView.startAnimation();
            } else {
                mSVGAImageView.stopAnimation();
            }
        }
        return super.onTouchEvent(event);
    }
}
