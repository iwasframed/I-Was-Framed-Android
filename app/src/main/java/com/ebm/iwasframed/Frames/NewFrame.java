package com.ebm.iwasframed.Frames;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.ebm.iwasframed.Config;
import com.ebm.iwasframed.Rational;

/**
 * Created by Usman on 1/25/2018.
 */

public class NewFrame extends AppCompatImageView {


    SharedPreferences preferences = getContext().getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
    float frameWidth=0, frameHeight=0;
    boolean centiMetrics, inches;
    float frameRectw;
    float frameRecth;
    Point centerOfCanvas;
    int frameColor;
    String widthOverlap, heightOverlap;
    float maxWidth, maxHeight;
    double textWidth, textHeight;
    float widthTop, widthBottom, heightLeft, heightRight;


    public NewFrame(Context context) {
        super(context);
    }

    public NewFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewFrame(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        centerOfCanvas();
        getValues();
        checkMaxWidthAndHeight();
        convertValues();

        int framew=(int) frameRectw;
        int frameh=(int)frameRecth;

        int frameLeft = centerOfCanvas.x - (framew / 2);
        int frameTop = centerOfCanvas.y - (frameh / 2);
        int frameRight = centerOfCanvas.x + (framew / 2);
        int frameBottom = centerOfCanvas.y + (frameh / 2);
        Paint framePaint = new Paint();
        framePaint.setColor(frameColor);
        framePaint.setStyle(Paint.Style.STROKE);
        framePaint.setStrokeWidth(60);


        canvas.drawRect(frameLeft,frameTop,frameRight,frameBottom,framePaint);
        drawTextoutSide(textWidth,textHeight,canvas,frameLeft,frameRight,frameBottom,frameTop);


    }


    private void checkMaxWidthAndHeight() {
        if(frameWidth>=maxWidth){
            frameWidth=maxWidth;
        }
        if(frameHeight>=maxHeight){
            frameHeight=maxHeight;
        }
    }

    private void centerOfCanvas() {
        int canvasW = getWidth();
        int canvasH = getHeight();
        centerOfCanvas = new Point(canvasW / 2, canvasH / 2);
    }

    private void convertValues() {
        if(inches==true){
            //get into inches
            frameRectw = frameWidth*75;  //convert to pixel
            frameRecth = frameHeight*75;
        }
        else if(centiMetrics==true){
            //get into centimetrics and convert to inches
            frameRectw = (frameWidth)*75;  //convert to pixel
            frameRecth = (frameHeight)*75;
        }
        else {
            frameRectw = (frameWidth)*75;  //convert to pixel
            frameRecth = (frameHeight)*75;
        }
    }

    private void getValues() {
        SharedPreferences sp = getContext().getSharedPreferences("Params", Context.MODE_PRIVATE);
        inches = sp.getBoolean("UNIT",false);
        centiMetrics = sp.getBoolean("METRICS",false);

        frameWidth = preferences.getFloat(Config.FRAME_WIDTH,0);
        frameHeight = preferences.getFloat(Config.FRAME_HEIGHT,0);
        frameColor = preferences.getInt(Config.FRAME_COLOR, Color.BLACK);
        widthOverlap = preferences.getString(Config.FRAME_WIDTH_OVERLAP,"0");
        heightOverlap = preferences.getString(Config.FRAME_HEIGHT_OVERLAP,"0");
        maxWidth = preferences.getFloat(Config.MAX_WIDTH,0);
        maxHeight = preferences.getFloat(Config.MAX_HEIGHT,0);
        widthTop = preferences.getFloat(Config.FRAME_WIDTH_TOP,0);
        widthBottom = preferences.getFloat(Config.FRAME_WIDTH_BOTTOM,0);
        heightLeft = preferences.getFloat(Config.FRAME_HEIGHT_LEFT,0);
        heightRight = preferences.getFloat(Config.FRAME_HEIGHT_RIGHT,0);


        textWidth = frameWidth;
        textHeight = frameHeight;

    }


    public void drawTextoutSide(double width, double height, Canvas canvas, int left, int right, int bottom, int top){
        String textWidth="";
        String textHeight="";


        if(widthOverlap.equals("0")){
            widthOverlap="";
        }
        if(heightOverlap.equals("0")){
            heightOverlap="";
        }




        float resultWidth =(float) (width+widthTop+widthBottom);
        float resultHeight =(float) (height+heightLeft+heightRight);


        String v = String.valueOf(resultWidth);
        String x = String.valueOf(resultHeight);




        if(v.contains(".")){
            String[] partsW = v.split("\\.");
            String part1 = partsW[0];
            String part2 = 0+"."+partsW[1];
            String c = Rational.convertDecimalToFraction(Double.parseDouble(part2));
            if(c.equals("0/1")){
                v=part1;
            }
            else {
                v = part1 + "  " + c;
            }
        }

        if(x.contains(".")){
            String[] partsH = x.split("\\.");
            String part1 = partsH[0];
            String part2 = 0+"."+partsH[1];

            String c = Rational.convertDecimalToFraction(Double.parseDouble(part2));
            if(c.equals("0/1")){
                x=part1;
            }
            else {
                x = part1 + "  " + c;
            }
        }

        if(resultWidth==0 || resultHeight==0){
            return;
        }

        if(inches){
                textWidth= v +"\"";
                textHeight=x+"\"";

            }
            else if(centiMetrics){
                textWidth=String.format("%.2f",resultWidth);  //convert pixel into meter
                textWidth+="cm";

                textHeight=String.format("%.2f",resultHeight);  //convert pixel into meter
                textHeight+="cm";
            }
            else{
            textWidth= v +"\"";
            textHeight=x+"\"";

        }




        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));



        Rect newRect = new Rect(left, top, right, bottom);

        int xCenter = newRect.centerX();
        int yCenter = newRect.centerY();


        canvas.drawText(textWidth, xCenter, top+12, textPaint);
        canvas.save();
        canvas.rotate(-90,left,yCenter);
        canvas.drawText(textHeight, left, yCenter+12, textPaint);
        canvas.restore();
    }
}
