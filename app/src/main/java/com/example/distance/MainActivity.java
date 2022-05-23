package com.example.distance;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends Activity { // MainActivity 클래스가 상속 받으면서 구현
    private TextView tv_dataview;
    private Button btn_reset;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { // savedInstanceState 으로 변경된 값 유지
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // activity_main.xml 레이아웃 출력

        btn_reset = (Button) findViewById(R.id.btnReset);
        tv_dataview = (TextView) findViewById(R.id.textLabel);

        btn_reset.setOnClickListener(new View.OnClickListener() { // reset 버튼 클릭시 설정
            @Override
            public void onClick(View v) {
                tv_dataview.setText("");

                ParseQuery<ParseObject> query = ParseQuery.getQuery("AccelSensor");
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> list, ParseException e) {
                        if(e == null) {
                            tv_dataview.append("Retrieved " + list.size() + "\n");
                            int max = list.size();
                            if(max > 5)
                                max = 5;
                            for(int i = 0; i < max; i++) {
                                tv_dataview.append("SensorData " + list.get(i).getInt("timestamp") + " => " +
                                        list.get(i).getInt("x") + "/" + list.get(i).getInt("y") + "/" + list.get(i).getInt("z") + "\n");
                            }
                        } else {
                            tv_dataview.setText("Error: " +e.getMessage());
                        }
                    }
                });

                Toast.makeText(MainActivity.this, "설정 초기화", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
