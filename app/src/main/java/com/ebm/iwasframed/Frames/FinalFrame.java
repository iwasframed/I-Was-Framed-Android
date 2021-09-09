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

import com.ebm.iwasframed.R;
import com.ebm.iwasframed.Rational;

/**
 * Created by Usman on 1/12/2018.
 */

public class FinalFrame extends AppCompatImageView {


    Paint paint;
    int top,left,right,bottom;
    Context context;
    float width=0;
    float height=0;
    float frameWidth=0;
    float frameHeight=0;
    float maxWidth=0;
    float maxHeight=0;
    boolean frame=false;
    boolean fraction;
    boolean doubleMat=false;

    float textHeight=0;
    float textWidth=0;
    float imageTextWidth=0;
    float imageTextHeight=0;
    double offset=0;
    double tripleOffset;
    boolean tripleMat =false;
    double overlap;
    int mat1Color, mat2Color,mat3Color;
    int doubleLeft=0,doubleRight=0,doubleBottom=0, doubleTop=0;
    boolean inches, cm;


    SharedPreferences sharedPreferences = getContext().getSharedPreferences("Params", Context.MODE_PRIVATE);
    SharedPreferences.Editor edt=sharedPreferences.edit();


    public FinalFrame(Context context) {
        super(context);
        paint = new Paint();
        this.context=context;
    }


    public FinalFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FinalFrame(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }




    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int color = sharedPreferences.getInt("color", R.color.black);
        int canvasW = getWidth();
        int canvasH = getHeight();

        Point centerOfCanvas = new Point(canvasW / 2, canvasH / 2);
        frameWidth=sharedPreferences.getFloat("Framewidth",0.0f);
        frameHeight=sharedPreferences.getFloat("Frameheight",0.0f);

        mat1Color=sharedPreferences.getInt("Mat1Color",0);
        mat2Color = sharedPreferences.getInt("Mat2Color",0);
        mat3Color = sharedPreferences.getInt("Mat3Color",0);

        frame=sharedPreferences.getBoolean("FRAME",false);
        width = sharedPreferences.getFloat("Imagewidth",0.0f);
        height = sharedPreferences.getFloat("Imageheight",0.0f);
        fraction=sharedPreferences.getBoolean("DecimalFraction",false);
        overlap =sharedPreferences.getFloat("OverLap",0.0f);

        maxHeight = sharedPreferences.getFloat("MaxHeight", 0.0f);
        maxWidth = sharedPreferences.getFloat("MaxWidth", 0.0f);

        inches=sharedPreferences.getBoolean("UNIT",false);
        cm=sharedPreferences.getBoolean("METRICS",false);

        String divide=sharedPreferences.getString("Convert",null);
        boolean showResults = sharedPreferences.getBoolean("RESULTS", false);

        String TripleDivide=sharedPreferences.getString("TripleConvert",null);
        double rectW=0;
        double rectH=0;

        if (divide != "0.0") {
            offset = Rational.fractionToDecimal(divide);
        }

        if (TripleDivide != "0.0") {
            tripleOffset = Rational.fractionToDecimal(TripleDivide);
        }

        float st1=35;
        float st2=35;




        textHeight=frameHeight;
        textWidth=frameWidth;
        imageTextHeight=height;
        imageTextWidth=width;
        if(width==0|| width == 1 ) {

        }
        else if(width==2 || height == 2){

        } else if( width==3 || height == 3){
            width=(float) 2.3;
            height = (float) 2.3;
        }

        else {
            width = width - (float) 1.5;
            height = height - (float) 1.5;
        }
//
//        Log.wtf("MaxWidth", String.valueOf(maxWidth));
//        Log.wtf("MaxHeight", String.valueOf(maxHeight));

        if(frameWidth>=maxWidth){
            frameWidth=maxWidth;
        }

        if(width>=maxWidth){
            width=maxWidth-3;
        }
        if(height>=maxHeight){
            height=maxHeight-3;
        }

        if(frameHeight>=maxHeight){
            frameHeight=maxHeight;
        }

        if(frameWidth-width<=2.1){
            width=width-3+(frameWidth-width);
        }

        if(frameHeight-height<=2.4){
            height=height-3+(frameHeight-height);
        }

        doubleMat = sharedPreferences.getBoolean("DoubleMat",false);
        tripleMat = sharedPreferences.getBoolean("TripleMat", false);


        if(doubleMat || tripleMat){
            overlap=0;
        }

        if(frame){
            float frameRectw=0;
            float frameRecth=0;
            if(inches==true){
                //get into inches
                frameRectw = frameWidth*75;  //convert to pixel
                frameRecth = frameHeight*75;
            }
            else if(cm==true){
                //get into centimetrics and convert to inches
                frameRectw = (frameWidth)*75;  //convert to pixel
                frameRecth = (frameHeight)*75;
            }
            int framew=(int) frameRectw;
            int frameh=(int)frameRecth;

            int frameLeft = centerOfCanvas.x - (framew / 2);
            int frameTop = centerOfCanvas.y - (frameh / 2);
            int frameRight = centerOfCanvas.x + (framew / 2);
            int frameBottom = centerOfCanvas.y + (frameh / 2);
            Paint framePaint = new Paint();

            if(mat1Color!=0){
                framePaint.setColor(mat1Color);
            }else {
                framePaint.setColor(getResources().getColor(R.color.frame));
            }
            framePaint.setStyle(Paint.Style.FILL);
            framePaint.setStrokeWidth(80);

            canvas.drawRect(frameLeft,frameTop,frameRight,frameBottom,framePaint);



            if(inches==true){
                    rectW = width * 75;  //convert to pixel
                    rectH = height * 75;
            }
            else if(cm==true){
                    //get into centimetrics and convert to inches
                    rectW = (width) * 75;  //convert to pixel
                    rectH = (height) * 75;

            }


            if(tripleMat){
                float wd = (width*17)/100;
                float ht = (height*17)/100;

                double doubleW = width+wd;
                double doubleH = height+ht;

                int doubleWidth = (int) doubleW * 75;
                int doubleHeight = (int) doubleH * 75;

                doubleLeft = centerOfCanvas.x - (doubleWidth / 2);
                doubleTop = centerOfCanvas.y - (doubleHeight / 2);
                doubleRight = centerOfCanvas.x + (doubleWidth / 2);
                doubleBottom = centerOfCanvas.y + (doubleHeight / 2);

                Paint doublePaint = new Paint();
                if(mat2Color!=0){
                    doublePaint.setColor(mat2Color);
                }
                else {
                    doublePaint.setColor(getResources().getColor(R.color.frame1));
                }
                doublePaint.setStyle(Paint.Style.FILL);
                doublePaint.setStrokeWidth(st2);
                canvas.drawRect(doubleLeft,doubleTop,doubleRight,doubleBottom,doublePaint);

            }


            int rectWidth=(int)rectW;
            int rectheight=(int)rectH;

            left = centerOfCanvas.x - (rectWidth / 2);
            top = centerOfCanvas.y - (rectheight / 2);
            right = centerOfCanvas.x + (rectWidth / 2);
            bottom = centerOfCanvas.y + (rectheight / 2);
            paint = new Paint();
            if(mat3Color!=0){
                paint.setColor(mat3Color);
            }
            else {
                paint.setColor(color);
            }
            paint.setStyle(Paint.Style.FILL);

            paint.setStrokeWidth(st1);

            if(width!=0) {
                if(doubleMat)
                canvas.drawRect(left, top, right, bottom, paint);
            }


            float wd = (width*7)/100;
            float ht = (height*7)/100;

            double w = width-wd;
            double h = height-ht;

            int wi = (int) w * 75;
            int he = (int) h * 75;

            int l = centerOfCanvas.x - (wi / 2);
            int t = centerOfCanvas.y - (he / 2);
            int r = centerOfCanvas.x + (wi / 2);
            int b = centerOfCanvas.y + (he / 2);

            Paint p = new Paint();
            p.setColor(getResources().getColor(R.color.AppColor));
            p.setStyle(Paint.Style.FILL);
            p.setStrokeWidth(20);
            canvas.drawRect(l,t,r,b,p);

            saveImageSize((float) w, (float) h);

            if(showResults){
                drawTextoutSide(textWidth,textHeight,canvas,frameLeft,frameRight,frameBottom,frameTop);
                drawTextinSide(imageTextWidth,imageTextHeight,canvas, l,r,b,t);
                drawCalculationText(textWidth,textHeight,canvas,frameLeft,frameRight,frameBottom,frameTop);
                if(doubleMat) {
                    drawDoubleMatText(canvas, left, right, bottom, top);
                    if(tripleMat){
                        drawTripleMatText(canvas,doubleLeft, doubleRight, doubleBottom, doubleTop);
                    }
                }
            }

        }
    }

    public void drawCalculationText(double fwidth,double fheight,Canvas canvas,int left,int right,int bottom,int top){

        boolean inches=sharedPreferences.getBoolean("UNIT",false);
        boolean centiMetrics=sharedPreferences.getBoolean("METRICS",false);
        double h,w;

        if(tripleMat) {
            h = fheight - offset - tripleOffset - imageTextHeight - offset - tripleOffset;
            w = fwidth - offset - tripleOffset - imageTextWidth - offset - tripleOffset;
        }
        else if(doubleMat){
            h = fheight - offset  - imageTextHeight - offset;
            w = fwidth - offset  - imageTextWidth - offset;
        }
        else {
            h = fheight- imageTextHeight;
            w = fwidth - imageTextWidth;
        }

        if(h!=0){
            w=w/2;
            h=h/2;
        }

        edt.putFloat("FRAMESTROKEWIDTH",(float) w);
        edt.putFloat("FRAMESTROKEHEIGHT",(float)h);
        edt.commit();
       String v = String.valueOf(w);
        String x = String.valueOf(h);

        if(v.contains(".")){
            String[] partsW = v.split("\\.");
            String part1 = partsW[0];
            String part2 = 0+"."+partsW[1];
            String c = Rational.convertDecimalToFraction(Double.parseDouble(part2));
            if(c.equals("0/1")){
                v=part1;
            }
            else {
                if(part1.equals("0")){
                    v=c;
                }
                else {
                    v = part1 + "  " + c;
                }
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
                if(part1.equals("0")){
                    x=c;
                }
                else {
                    x = part1 + "  " + c;
                }
            }
        }

        String textWidth="";
        String textHeight="";
        if (fraction==true){
            if(inches){
                textWidth=String.format("%.3f",w);
                textWidth+="in";
                textHeight=String.format("%.3f",h);
                textHeight+="in";
            }
            else if(centiMetrics){
                textWidth=String.format("%.3f",w/100);  //convert pixel into meter
                textWidth+="m";
                textHeight=String.format("%.3f",h/100);  //convert pixel into meter
                textHeight+="m";
            }
            else{

            }
        }
        else if(!fraction){
            if(inches){
                String w1=String.valueOf(w);
                String h1=String.valueOf(h);  //coonvert them into string

                textWidth=v +"\"";  //get after point in fraction


                textHeight=x+"\"";


            }
            else if(centiMetrics){
                textWidth=String.format("%.3f",w);  //convert pixel into meter
                textWidth+="cm";

                textHeight=String.format("%.3f",h);  //convert pixel into meter
                textHeight+="cm";
            }
            else{

            }
        }
        String hexColor="";
        if(mat1Color!=0) {
            hexColor = String.format("#%06X", (0xFFFFFF & mat1Color));
        }

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        if(hexColor.equals("#FFFFFF")){
            textPaint.setColor(Color.BLACK);
        }
        else {
            textPaint.setColor(Color.WHITE);
        }
        textPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        Rect newRect = new Rect(left, top, right, bottom);

        int xCenter = newRect.centerX();
        int yCenter = newRect.centerY();

        canvas.drawText(textHeight, xCenter, top+40, textPaint);
        canvas.drawText(textHeight, xCenter, bottom-20, textPaint);

        canvas.save();
        canvas.rotate(-90,right, yCenter);
        canvas.drawText(textWidth, right, yCenter-20, textPaint);
        canvas.restore();


        canvas.save();
        canvas.rotate(-90,left,yCenter);
        canvas.drawText(textWidth, left, yCenter+40, textPaint);
        canvas.restore();

    }

    public void drawTextoutSide(double width, double height, Canvas canvas, int left, int right, int bottom, int top){
        String textWidth="";
        String textHeight="";
        boolean inches=sharedPreferences.getBoolean("UNIT",false);
        boolean centiMetrics=sharedPreferences.getBoolean("METRICS",false);


        String v = String.valueOf(width);
        String x = String.valueOf(height);

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



        if (fraction==true){
            if(inches){
                textWidth=String.valueOf(width);
                textWidth+="in";
                textHeight=String.valueOf(height);
                textHeight+="in";
            }
            else if(centiMetrics){
                textWidth=String.format("%.2f",width/100);  //convert pixel into meter
                textWidth+="m";
                textHeight=String.format("%.2f",height/100);  //convert pixel into meter
                textHeight+="m";

            }
            else{

            }
        }
        else if(!fraction){
            if(inches){
                textWidth=v+"\"";
                textHeight=x+"\"";

            }
            else if(centiMetrics){
                textWidth=String.format("%.2f",width);  //convert pixel into meter
                textWidth+="cm";

                textHeight=String.format("%.2f",height);  //convert pixel into meter
                textHeight+="cm";
            }
            else{

            }

        }


        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        Paint.FontMetrics metric = textPaint.getFontMetrics();

        Rect newRect = new Rect(left, top, right, bottom);

        int xCenter = newRect.centerX();
        int yCenter = newRect.centerY();

        canvas.drawText(textWidth, xCenter, top-5, textPaint);
        canvas.save();
        canvas.rotate(-90,left,yCenter);
        canvas.drawText(textHeight, left, yCenter-5, textPaint);
        canvas.restore();
    }

    public void drawTextinSide(double width,double height,Canvas canvas, int left,int right,int bottom,int top){
        String textWidth="";
        String textHeight="";
        boolean inches=sharedPreferences.getBoolean("UNIT",false);
        boolean centiMetrics=sharedPreferences.getBoolean("METRICS",false);
/*
        if(start==true && inches==true){
            text="0";

        }*/

        String v = String.valueOf(width);
        String x = String.valueOf(height);




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

        if (fraction==true){
            if(inches){
                textWidth=String.valueOf(width);
                textWidth+="in";
                textHeight=String.valueOf(height);
                textHeight+="in";
            }
            else if(centiMetrics){
                textWidth=String.format("%.2f",width/100);  //convert pixel into meter
                textWidth+="m";
                textHeight=String.format("%.2f",height/100);  //convert pixel into meter
                textHeight+="m";

            }
            else{

            }
        }
        else if(!fraction){
            if(inches){
                textWidth=String.valueOf(v)+"\"";
                textHeight=String.valueOf(x)+"\"";

            }
            else if(centiMetrics){
                textWidth=String.format("%.2f",width);  //convert pixel into meter
                textWidth+="cm";

                textHeight=String.format("%.2f",height);  //convert pixel into meter
                textHeight+="cm";
            }
            else{

            }


        }


        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        Paint.FontMetrics metric = textPaint.getFontMetrics();

        Rect newRect = new Rect(left, top, right, bottom);

        int xCenter = newRect.centerX();
        int yCenter = newRect.centerY();

        canvas.drawText(textWidth, xCenter, top+40, textPaint);
        canvas.save();
        canvas.rotate(-90,left,yCenter);
        canvas.drawText(textHeight, left, yCenter+40, textPaint);
        canvas.restore();
    }


    public void drawDoubleMatText(Canvas canvas, int left, int right, int bottom, int top){

        String text = String.valueOf(offset);

        if(inches) {
            if (text.contains(".")) {
                String[] partsW = text.split("\\.");
                String part1 = partsW[0];
                String part2 = 0 + "." + partsW[1];
                String c = Rational.convertDecimalToFraction(Double.parseDouble(part2));
                if (c.equals("0/1")) {
                    text = part1 + "\"";
                } else {
                    if (part1.equals("0")) {
                        text = c + "\"";
                    } else {
                        text = part1 + "  " + c + "\"";
                    }
                }
            }
        }
        else {
            text = text+" cm";
        }

        String hexColor="";
        if(mat2Color!=0) {
            hexColor = String.format("#%06X", (0xFFFFFF & mat3Color));
        }

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        if(hexColor.equals("#FFFFFF")){
            textPaint.setColor(Color.BLACK);
        }
        else {
            textPaint.setColor(Color.WHITE);
        }
        textPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 8, getResources().getDisplayMetrics()));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Rect newRect = new Rect(left, top, right, bottom);

        int xCenter = newRect.centerX();
        int yCenter = newRect.centerY();

        canvas.save();

        if(!tripleMat){
            canvas.drawText(text, xCenter, top + 15, textPaint);
            canvas.drawText(text, xCenter, bottom - 4, textPaint);


            canvas.rotate(-90, left, yCenter);
            canvas.drawText(text, left, yCenter + 15, textPaint);
//        canvas.restore();

            canvas.rotate(+90, left, yCenter);

            canvas.rotate(-90, right, yCenter);
            canvas.drawText(text, right, yCenter - 3, textPaint);
            canvas.restore();
        }
        else {
            canvas.drawText(text, xCenter + 35, top + 15, textPaint);
            canvas.drawText(text, xCenter + 35, bottom - 4, textPaint);


            canvas.rotate(-90, left, yCenter);
            canvas.drawText(text, left - 40, yCenter + 15, textPaint);
//        canvas.restore();

            canvas.rotate(+90, left, yCenter);

            canvas.rotate(-90, right, yCenter);
            canvas.drawText(text, right - 40, yCenter - 3, textPaint);
            canvas.restore();
        }
    }

    public void drawTripleMatText(Canvas canvas, int left, int right, int bottom, int top){


        String text = String.valueOf(tripleOffset);

        if(inches) {
            if (text.contains(".")) {
                String[] partsW = text.split("\\.");
                String part1 = partsW[0];
                String part2 = 0 + "." + partsW[1];
                String c = Rational.convertDecimalToFraction(Double.parseDouble(part2));
                if (c.equals("0/1")) {
                    text = part1 + "\"";
                } else {
                    if (part1.equals("0")) {
                        text = c + "\"";
                    } else {
                        text = part1 + "  " + c + "\"";
                    }
                }
            }
        }
        else {
            text = text+" cm";
        }



        String hexColor="";
        if(mat2Color!=0) {
            hexColor = String.format("#%06X", (0xFFFFFF & mat2Color));
        }

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        if(hexColor.equals("#FFFFFF")){
            textPaint.setColor(Color.BLACK);
        }
        else {
            textPaint.setColor(Color.WHITE);
        }
        textPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 8, getResources().getDisplayMetrics()));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        Rect newRect = new Rect(left, top, right, bottom);

        int xCenter = newRect.centerX();
        int yCenter = newRect.centerY();

        canvas.drawText(text, xCenter, top+15, textPaint);
        canvas.drawText(text, xCenter, bottom-3, textPaint);
        canvas.save();


        canvas.rotate(-90,left,yCenter);
        canvas.drawText(text, left, yCenter+15, textPaint);
        canvas.restore();

        canvas.rotate(-90,right,yCenter);
        canvas.drawText(text, right,yCenter-3, textPaint);
//        canvas.restore();
    }


    void saveImageSize(float width, float height){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Params", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("FinalImageWidth", width);
        editor.putFloat("FinalImageHeight", height);
        editor.commit();
    }


}