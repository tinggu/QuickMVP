package me.tinggu.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import me.tinggu.sample.girl.GirlActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        LogUtils.i("baozi", "aaaaaaaaaaaaaaaaa");
//        LogUtils.i("baozi", PhoneStateUtils.getIMEI(this));
//        LogUtils.i("baozi", PhoneStateUtils.getSN(this));
//        LogUtils.i("baozi", PhoneStateUtils.getPhoneModel());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Button button = (Button) findViewById(R.id.btn_meizi);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GirlActivity.class);
//                startActivity(intent);
                ActivityCompat.startActivity(MainActivity.this, intent, null);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
