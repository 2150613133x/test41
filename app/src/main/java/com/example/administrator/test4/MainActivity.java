package com.example.administrator.test4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

import org.bouncycastle.util.encoders.Base64;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private String brand;
    private String path;
    private TextView tv_content;
    private EditText et_input;
    private ImageView img;
    private Spinner spdown;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO Finish this code
        data_list = new ArrayList<String>();
        data_list.add("苹果");
        data_list.add("华为");
        data_list.add("OPPO");
        spdown = (Spinner)findViewById(R.id.spdown) ;

        arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, data_list);
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spdown.setAdapter(arr_adapter);;

        tv_content = (TextView) findViewById(R.id.tv_content);
        et_input = (EditText) findViewById(R.id.et_input);
        //设置下拉框监听器
        //添加spinner监听事件
        spdown.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                 brand = arr_adapter.getItem(arg2);
                //设置显示当前选择的项
                arg0.setVisibility(View.VISIBLE);
                path = Select.choice(brand);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        //扫描监听器
        findViewById(R.id.btnSan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 0);
            }
        });

        //生成代码监听器
        findViewById(R.id.btn_generate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = et_input.getText().toString();         //文字内容为  et_input  空间接收到的信息
                //文本为空情况下
                if (str.equals("")) {
                    Toast.makeText(MainActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    // 位图
                    try {
                        /**
                         * 参数：1.文本 2 3.二维码的宽高 4.二维码中间的那个logo
                         */
                        //位图
                        Bitmap bitmap = EncodingUtils.createQRCode(str, 500, 500, null);
                        // 设置图片
                        img.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            String [] index = result.split("/sig=");
            String message = index[0];
            String signature = index[1];
            String genElement = null;
            String publicKey = null;
            try {
                genElement = getParam.GetValueByKey(path,"genElement");
                publicKey =  getParam.GetValueByKey(path,"publicKey");
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] g = Base64.decode(genElement);
            byte[] y = Base64.decode(publicKey);
            try {

                if(Blsver.verify(message.getBytes("GBK"),g,y, Base64.decode(signature)) == true) {
                    tv_content.setText("产品序列号："+message+"   该产品属于本公司");
                }else {
                    tv_content.setText("该产品不属于本公司");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
