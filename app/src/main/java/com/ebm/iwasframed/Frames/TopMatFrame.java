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
import android.util.Log;
import android.util.TypedValue;

import com.ebm.iwasframed.R;
import com.ebm.iwasframed.Rational;


public class TopMatFrame extends AppCompatImageView{


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
    double overLap=0;

    SharedPreferences sharedPreferences = getContext().getSharedPreferences("Params", Context.MODE_PRIVATE);
    SharedPreferences.Editor edt=sharedPreferences.edit();
    public TopMatFrame(Context context) {
        super(context);
        paint = new Paint();
        this.context=context;
    }


    public TopMatFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TopMatFrame(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }



    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int color = sharedPreferences.getInt("color", R.color.black);
        int canvasW = getWidth();
        int canvasH = getHeight();
        Point centerOfCanvas = new Point(canvasW / 2, canvasH / 2);
        frameWidth = sharedPreferences.getFloat("Framewidth", 0.0f);
        frameHeight = sharedPreferences.getFloat("Frameheight", 0.0f);
        frame = sharedPreferences.getBoolean("FRAME", false);
        width = sharedPreferences.getFloat("Imagewidth", 0.0f);
        height = sharedPreferences.getFloat("Imageheight", 0.0f);
        fraction = sharedPreferences.getBoolean("DecimalFraction", false);
        overLap = sharedPreferences.getFloat("OverLap", 0.0f);

        maxHeight = sharedPreferences.getFloat("MaxHeight", 0.0f);
        maxWidth = sharedPreferences.getFloat("MaxWidth", 0.0f);
        boolean showResults = sharedPreferences.getBoolean("RESULTS", false);

        boolean inches = sharedPreferences.getBoolean("UNIT", false);
        boolean centiMetrics = sharedPreferences.getBoolean("METRICS", false);
        String divide = sharedPreferences.getString("Convert", null);
        String TripleDivide = sharedPreferences.getString("TripleConvert", null);
        double rectW = 0;
        double rectH = 0;


        if (divide != "0.0") {
            offset = Rational.fractionToDecimal(divide);
        }

        if (TripleDivide != "0.0") {
            tripleOffset = Rational.fractionToDecimal(TripleDivide);
        }



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

        Log.wtf("MaxWidth", String.valueOf(maxWidth));
        Log.wtf("MaxHeight", String.valueOf(maxHeight));

        if(frameWidth>=maxWidth){
            frameWidth=maxWidth;
        }

//        if(width>=maxWidth){
//            width=maxWidth-3;
//        }
//
//        if(height>=maxHeight){
//            height=maxHeight-3;
//        }

        if(frameHeight>=maxHeight){
            frameHeight=maxHeight;
        }


        if(doubleMat || tripleMat){
            overLap=0;
        }



        Log.wtf("Frame-Image = WidthGap", String.valueOf(frameWidth)+String.valueOf("-"+width+" = ")+String.valueOf(frameWidth-width));
        Log.wtf("Frame-Image = HeightGap", String.valueOf(frameHeight)+String.valueOf("-"+height+" = ")+String.valueOf(frameHeight-height));

        if(frameWidth-width<=2.1){
            width=width-3+(frameWidth-width);
        }

        if(frameHeight-height<=2.4){
            height=height-3+(frameHeight-height);
        }

        doubleMat = sharedPreferences.getBoolean("DoubleMat",false);
        tripleMat = sharedPreferences.getBoolean("TripleMat", false);

        if(frame){
            float frameRectw=0;
            float frameRecth=0;
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
            int framew=(int) frameRectw;
            int frameh=(int)frameRecth;

            int frameLeft = centerOfCanvas.x - (framew / 2);
            int frameTop = centerOfCanvas.y - (frameh / 2);
            int frameRight = centerOfCanvas.x + (framew / 2);
            int frameBottom = centerOfCanvas.y + (frameh / 2);
            Paint framePaint = new Paint();
            framePaint.setColor(color);
            framePaint.setStyle(Paint.Style.STROKE);
            framePaint.setStrokeWidth(50);

            canvas.drawRect(frameLeft,frameTop,frameRight,frameBottom,framePaint);
            drawTextoutSide(textWidth,textHeight,canvas,frameLeft,frameRight,frameBottom,frameTop);


            if(inches==true){
                //get into inches

                    rectW = width * 75;  //convert to pixel
                    rectH = height * 75;

            }
            else if(centiMetrics==true){
                    //get into centimetrics and convert to inches
                    rectW = (width) * 75;  //convert to pixel
                    rectH = (height) * 75;

            }

            int rectWidth=(int)rectW-(int)overLap;
            int rectheight=(int)rectH-(int)overLap;

            left = centerOfCanvas.x - (rectWidth / 2);
            top = centerOfCanvas.y - (rectheight / 2);
            right = centerOfCanvas.x + (rectWidth / 2);
            bottom = centerOfCanvas.y + (rectheight / 2);
            paint = new Paint();
            paint.setColor(color);
            paint.setStyle(Paint.Style.STROKE);

            int stroke1=35;
            paint.setStrokeWidth(stroke1);

            if(width!=0) {
                canvas.drawRect(left, top, right, bottom, paint);
            }
                if (width != 0) {
                    if (showResults) {
                            drawTextinSide((imageTextWidth), (imageTextHeight), canvas, left, right, bottom, top);

                    }else {
                        drawTextinSide(imageTextWidth, imageTextHeight, canvas, left, right, bottom, top);
                    }

//                    drawText(text,canvas,left,right,bottom,top,(int)overLap);

                    if(showResults) {
                        if (inches) {
                            drawCalculationText(overLap, textWidth, textHeight, imageTextWidth, imageTextHeight, canvas
                                    , frameLeft, frameRight, frameBottom, frameTop);
                        } else if (centiMetrics) {
                            drawCalculationText(overLap, textWidth, textHeight, imageTextWidth, imageTextHeight, canvas
                                    , frameLeft, frameRight, frameBottom, frameTop);
                        }
                    }
                }

        }
    }


    public void drawCalculationText(double overlap,double fwidth,double fheight,double mWidth,double mHeight,
                                    Canvas canvas,int left,int right,int bottom,int top){

        boolean inches=sharedPreferences.getBoolean("UNIT",false);
        boolean centiMetrics=sharedPreferences.getBoolean("METRICS",false);

        double w=(fwidth-mWidth)/2;
        double h=(fheight-mHeight)/2;
        overlap = overlap/2;

        if(doubleMat){
            if(tripleMat){
                w = w-tripleOffset;
                h = h-tripleOffset;
            }
            else {

            }
        }
        else{
            w = w + overlap;
            h = h + overlap;
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

                textWidth=v +"\"";
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
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(Color.RED);
        textPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        textPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics metric = textPaint.getFontMetrics();

        Rect newRect = new Rect(left, top, right, bottom);

        int xCenter = newRect.centerX();
        int yCenter = newRect.centerY();

        canvas.drawText(textHeight, xCenter, top+70, textPaint);
        canvas.drawText(textHeight, xCenter, bottom-40, textPaint);

        canvas.save();
        canvas.rotate(-90,right, yCenter);
        canvas.drawText(textWidth, right, yCenter-40, textPaint);
        canvas.restore();


        canvas.save();
        canvas.rotate(-90,left,yCenter);
        canvas.drawText(textWidth, left, yCenter+70, textPaint);
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
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        Paint.FontMetrics metric = textPaint.getFontMetrics();

        Rect newRect = new Rect(left, top, right, bottom);

        int xCenter = newRect.centerX();
        int yCenter = newRect.centerY();

        canvas.drawText(textWidth, xCenter, top+12, textPaint);
        canvas.save();
        canvas.rotate(-90,left,yCenter);
        canvas.drawText(textHeight, left, yCenter+12, textPaint);
        canvas.restore();
    }


    public void drawTextinSide(double width,double height,Canvas canvas, int left,int right,int bottom,int top){
        String textWidth="";
        String textHeight="";
        boolean inches=sharedPreferences.getBoolean("UNIT",false);
        boolean centiMetrics=sharedPreferences.getBoolean("METRICS",false);


        if(doubleMat){
            if(tripleMat){
                width = width +(tripleOffset*2);
                height = height +(tripleOffset*2);

            }
            else {

            }

        }

        Log.d("TripleOffset", String.valueOf(tripleOffset));
        Log.d("Overlap", String.valueOf(overLap));

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
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        textPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics metric = textPaint.getFontMetrics();

        Rect newRect = new Rect(left, top, right, bottom);

        int xCenter = newRect.centerX();
        int yCenter = newRect.centerY();

        canvas.drawText(textWidth, xCenter, top+8, textPaint);
        canvas.save();
        canvas.rotate(-90,left,yCenter);
        canvas.drawText(textHeight, left, yCenter+8, textPaint);
        canvas.restore();
    }
}