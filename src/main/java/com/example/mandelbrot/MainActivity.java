package com.example.mandelbrot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import java.util.Objects;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity{
        private static final String TAG = "MyApp";
        Button Lbtn;
        Button Rbtn;
        Button Okbtn;
        EditText cntr;
        EditText hexclr;
        DrawView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        Lbtn = (Button) findViewById(R.id.lastbtn);
        Rbtn = (Button) findViewById(R.id.nextbtn);
        Okbtn = (Button) findViewById(R.id.okbtn);
        cntr = (EditText) findViewById(R.id.cntr);
        hexclr = (EditText) findViewById(R.id.hexclr);
        img = (DrawView) findViewById(R.id.img);
        Lbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int h = img.getHeight();
                int w = img.getWidth();
                int tmd = (int)(h-w)/2;
                int rad = (h-2*tmd)/2;
                String qwe = "#"+hexclr.getText().toString();
                String tmp = cntr.getText().toString();
                try{
                    Color.parseColor(qwe);
                }catch (IllegalArgumentException e){
                    qwe = "#12e6f6";
                    hexclr.setText("12e6f6");
                }
                try{
                    Float.parseFloat(tmp);
                }catch (IllegalArgumentException e){
                    tmp = "1.0";
                }
                float value = Float.parseFloat(tmp);
                if(value > 0){
                    cntr.setText(Objects.toString(value-1),null);
                    img.refresh(value,-1,qwe,tmd,rad);
                }
            }
        });
        Okbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int h = img.getHeight();
                int w = img.getWidth();
                int tmd = (int)(h-w)/2;
                int rad = (h-2*tmd)/2;
                String qwe = "#"+hexclr.getText().toString();
                String tmp = cntr.getText().toString();
                try{
                    Color.parseColor(qwe);
                }catch (IllegalArgumentException e){
                    qwe = "#12e6f6";
                    hexclr.setText("12e6f6");
                }
                try{
                    Float.parseFloat(tmp);
                }catch (IllegalArgumentException e){
                    tmp = "1.0";
                }
                float value = Float.parseFloat(tmp);
                cntr.setText(Objects.toString(value,null));
                img.refresh(value,0,qwe,tmd,rad);
            }
        });
        Rbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int h = img.getHeight();
                int w = img.getWidth();
                int tmd = (h-w)/2;
                int rad = (h-2*tmd)/2;
                String qwe = "#"+hexclr.getText().toString();
                String tmp = cntr.getText().toString();
                try{
                    Color.parseColor(qwe);
                }catch (IllegalArgumentException e){
                    qwe = "#12e6f6";
                    hexclr.setText("12e6f6");
                }
                try{
                    Float.parseFloat(tmp);
                }catch (IllegalArgumentException e){
                    tmp = "1.0";
                }
                float value = Float.parseFloat(tmp);
                cntr.setText(Objects.toString(value+1),null);
                img.refresh(value,1,qwe,tmd,rad);
            }
        });
    }
}

class DrawView extends View{
    float value = 1;
    int bf = 0;
    int fps = 32;
    int tmd = 37;
    int rad = 450;
    String color = "#12e6f6";
    float[] x = new float[12000];
    float[] y = new float[12000];
    boolean ok = false;
    public void dots(){
        for(int i = 0; i < 12000; i++){
            x[i] = (float)Math.cos(Math.PI*i/6000)*rad + rad + 10;
            y[i] = (float)Math.sin(Math.PI*i/6000)*rad + rad + tmd;
        }
        ok = true;
    }
    Paint p = new Paint();
    public DrawView(Context context) {
        super(context);
    }
    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!ok){
            dots();
        }
        double pi =(float)Math.PI;
        canvas.drawColor(Color.parseColor("#333333"));
        p.setColor(Color.parseColor(color));
        p.setStrokeWidth(3);

        for(int i = 0; i < 12000; i+= 60){
            int n = (int)(i*value);
            canvas.drawCircle(x[i],y[i],3,p);
            canvas.drawLine(x[i], y[i], x[n%12000],y[n%12000], p);
        }
        if(fps > 0 ){
            value += 1.0/32*bf;
            fps--;
            invalidate();
        }
    }
    public void refresh(float val, int ln, String qwe, int ft, int rads){
        ok = false;
        tmd = ft;
        rad = rads-10;
        color = qwe;
        bf = ln;
        fps = 32;
        value = val;
        invalidate();
    }
}
