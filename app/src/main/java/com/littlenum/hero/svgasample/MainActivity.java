package com.littlenum.hero.svgasample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private ImageView mImageView;
    private ArrayAdapter<String> mArrayAdapter;
    private List<Node> mNodes = new ArrayList<>();
    private String[] mItems = new String[]{"download", "dynamic text", "yy-one", "yy-alarm", "yy-angel", "yy-emptystate", "yy-emptystate", "yy-posche", "yy-rose1", "yy-rose2", "yy-happybirthday", "my-ironman"};
    private String[] mUrl = new String[]{"http://legox.yy.com/svga/svga-me/angel", "angel", "750x80", "alarm", "angel", "EmptyState", "EmptyState_JSON", "posche", "rose_1.5.0", "rose_2.0.0", "birthday", "iron"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNodes();
        initView();
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void initNodes() {
        mNodes.add(new Node(1, mUrl[0]));
        mNodes.add(new Node(2, ""));
        for (int i = 2; i < mUrl.length; i++) {
            mNodes.add(new Node(0, mUrl[i]));
        }
    }

    private void initView() {
        mListView = findViewById(R.id.listView);
        mImageView = findViewById(R.id.imageView);
        mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mItems);
        mListView.setAdapter(mArrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> mAdapterView, View mView, int mI, long mL) {
                Intent it = new Intent(MainActivity.this, PreviewActivity.class);
                it.putExtra("type", mNodes.get(mI).type);
                it.putExtra("name", mUrl[mI] + ".svga");
                MainActivity.this.startActivity(it);
            }
        });
    }

    class Node {
        int type;
        String name;

        public Node(int type, String name) {
            this.type = type;
            this.name = name;
        }
    }
}
