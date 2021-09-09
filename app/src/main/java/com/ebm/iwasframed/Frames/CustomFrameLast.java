package com.ebm.iwasframed.Frames;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.ebm.iwasframed.R;

/**
 * Created by Vickyy on 12/7/2017.
 */

public class CustomFrameLast extends AppCompatImageView {
    Paint paint;
    int top,left,right,bottom;
    Context context;
    float width=0;
    float height=0;
    float frameWidth=0;
    float frameHeight=0;
    boolean frame=false;
    boolean fraction;
    SharedPreferences sharedPreferences = getContext().getSharedPreferences("Params", Context.MODE_PRIVATE);


    public CustomFrameLast(Context context) {
        super(context);
        paint = new Paint();
        this.context=context;
    }


    public CustomFrameLast(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFrameLast(Context context, AttributeSet attrs, int defStyle) {
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
        int matoneColor=sharedPreferences.getInt("MATONECOLOR",0);
        int matTwoColor=sharedPreferences.getInt("MATTWOCOLOR",0);
        int matThreeColor=sharedPreferences.getInt("MATTHREECOLOR",0);
        frame=sharedPreferences.getBoolean("FRAME",false);
        width = sharedPreferences.getFloat("Imagewidth",0.0f);
        height = sharedPreferences.getFloat("Imageheight",0.0f);
        fraction=sharedPreferences.getBoolean("DecimalFraction",false);
        double overLap=sharedPreferences.getFloat("OverLap",0.0f);

        String text=sharedPreferences.getString("TEXT",null);
        boolean inches=sharedPreferences.getBoolean("UNIT",false);
        boolean centiMetrics=sharedPreferences.getBoolean("METRICS",false);
        double thickness=sharedPreferences.getFloat("Doublethickness",0.0f);
        double tThickness=sharedPreferences.getFloat("Triplethickness",0.0f);
        String divide=sharedPreferences.getString("Convert",null);
        String TripleDivide=sharedPreferences.getString("TripleConvert",null);
        double rectW=0;
        double rectH=0;
        double frameStrokeWidth=sharedPreferences.getFloat("FRAMESTROKEWIDTH",0.0f);
        double frameStrokeHeight=sharedPreferences.getFloat("FRAMESTROKEHEIGHT",0.0f);





        if(frame) {
            float frameRectw = 0;
            float frameRecth = 0;
            if (inches == true) {
                //get into inches
                frameRectw = frameWidth * 75;  //convert to pixel
                frameRecth = frameHeight * 75;
            } else if (centiMetrics == true) {
                //get into centimetrics and convert to inches
                frameRectw = (frameWidth / 2.54f) * 75;  //convert to pixel
                frameRecth = (frameHeight / 2.54f) * 75;
            }
            int framew = (int) frameRectw;
            int frameh = (int) frameRecth;

            int frameLeft = centerOfCanvas.x - (framew / 2);
            int frameTop = centerOfCanvas.y - (frameh / 2);
            int frameRight = centerOfCanvas.x + (framew / 2);
            int frameBottom = centerOfCanvas.y + (frameh / 2);
            Paint framePaintleft = new Paint();
            framePaintleft.setColor(color);
            framePaintleft.setStyle(Paint.Style.FILL);
           // framePaintleft.setStrokeWidth(20);
          //  framePaintleft

            Paint framePaintTopBottom=new Paint();
            framePaintTopBottom.setColor(color);
            framePaintTopBottom.setStyle(Paint.Style.STROKE);
            framePaintTopBottom.setStrokeWidth((int)frameStrokeWidth*75/2);




/*
            canvas.drawLine(frameLeft-10, frameTop, frameRight+10, frameTop, framePaintTopBottom);
            canvas.drawLine(frameLeft-10, frameBottom, frameRight+10, frameBottom, framePaintTopBottom);
            canvas.drawLine(frameLeft, frameTop, frameLeft, frameBottom, framePaintleft);
            canvas.drawLine(frameRight, frameTop, frameRight, frameBottom, framePaintleft);*/
           canvas.drawRect(frameLeft, frameTop, frameRight, frameBottom, framePaintleft);
            // drawTextoutSide(frameWidth,frameHeight,canvas,frameLeft,frameRight,frameBottom,frameTop);


            if (inches == true) {
                //get into inches
                rectW = width * 75;  //convert to pixel
                rectH = height * 75;
            } else if (centiMetrics == true) {
                //get into centimetrics and convert to inches
                rectW = (width / 2.54) * 75;  //convert to pixel
                rectH = (height / 2.54) * 75;
            }

            int rectWidth = (int) rectW - (int) overLap;
            int rectheight = (int) rectH - (int) overLap;

            left = centerOfCanvas.x - (rectWidth / 2);
            top = centerOfCanvas.y - (rectheight / 2);
            right = centerOfCanvas.x + (rectWidth / 2);
            bottom = centerOfCanvas.y + (rectheight / 2);
            paint = new Paint();
            if (matoneColor != 0) {
                paint.setColor(matoneColor);
            } else {
                paint.setColor(color);
            }




            int stroke1 = (int) overLap;

            boolean doubleMat = sharedPreferences.getBoolean("DoubleMat", false);
            boolean tripleMat = sharedPreferences.getBoolean("TripleMat", false);

            if (doubleMat) {
                double matHeight = sharedPreferences.getFloat("MatHeight", 0.0f);
                double matWidth = sharedPreferences.getFloat("MatWidth", 0.0f);

                double matW = 0;
                double matH = 0;
                if (centiMetrics) {
                    matW = (width) / 2.54 * 75;
                    matH = (height) / 2.54 * 75;
                } else if (inches) {
                    matW = (width) * 75;
                    matH = (height) * 75;
                }

               /* double matw=matW+(int) thickness+(int)overLap;
                double math=matH+(int) thickness+(int)overLap;*/

                double matw = matW + thickness;
                double math = matH + thickness;


                int widthInside = (int) matw;
                int heightInside = (int) math;
                int l = centerOfCanvas.x - (widthInside / 2);
                int t = centerOfCanvas.y - (heightInside / 2);
                int r = centerOfCanvas.x + (widthInside / 2);
                int b = centerOfCanvas.y + (heightInside / 2);
                Paint paint1 = new Paint();
                if (matTwoColor != 0) {
                    paint1.setColor(matTwoColor);
                } else {
                    paint1.setColor(color);
                }
                paint1.setStyle(Paint.Style.STROKE);

                if (thickness % 2 == 1) {
                    thickness += 2;
                }
                if (stroke1 % 2 == 1) {
                    stroke1 += 2;
                }

                paint1.setStrokeWidth((int) thickness);


                paint.setStrokeWidth((int) stroke1);
               // paint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 5));
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat("MatHeight", width);
                editor.putFloat("MatWidth", height);
                editor.commit();



            //    paint.setPathEffect(new DashPathEffect(new float[] {10,10}, 5));

                paint.setColor(Color.WHITE);
                paint.setStyle(Paint.Style.FILL);
                paint.setStrokeWidth(stroke1);
                canvas.drawRect(left, top, right, bottom, paint);

                if (matoneColor != 0) {
                    paint.setColor(matoneColor);
                } else {
                    paint.setColor(Color.WHITE);
                }
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawRect(left, top, right, bottom, paint);

                canvas.drawRect(l, t, r, b, paint1);
               /* if (width != 0) {
                    drawTextinSide(width, height, canvas, left, right, bottom, top);
                    drawText(text,canvas,left,right,bottom,top,(int)overLap);
                    drawText((String.valueOf(divide).toString()),canvas,l,r,b,t,(int)thickness/4);
                    if(inches) {
                        drawCalculationText(thickness / 75, frameWidth, frameHeight, widthInside / 75, heightInside / 75, canvas
                                , frameLeft, frameRight, frameBottom, frameTop);
                    }
                    else if(centiMetrics){
                        drawCalculationText(thickness / 75, frameWidth, frameHeight, widthInside*2.54 / 75, heightInside*2.54 / 75, canvas
                                , frameLeft, frameRight, frameBottom, frameTop);
                    }
*/
            }
            else if(tripleMat){
                double matHeight = sharedPreferences.getFloat("MatHeight",0.0f);
                double matWidth = sharedPreferences.getFloat("MatWidth",0.0f);



                double matW =0;  //-2 here
                double matH =0;
                if(centiMetrics) {
                    matW = (width) / 2.54 * 75;
                    matH = (height) / 2.54 * 75;
                }
                else if(inches){
                    matW = (width) * 75;
                    matH = (height)  * 75;
                }

                if(thickness%2==1){
                    thickness+=2;
                }
                if(tThickness%2==2){
                    tThickness+=2;
                }


            /*    double matw=matW+(int) thickness+(int)overLap;
                double math=matH+(int) thickness+(int)overLap;*/


                double matw=matW +thickness;
                double math=matH+thickness;

                int triplematWidth=(int)matw;
                int triplematHeight=(int)math;

                int l = centerOfCanvas.x - (triplematWidth / 2);
                int t = centerOfCanvas.y - (triplematHeight / 2);
                int r = centerOfCanvas.x + (triplematWidth / 2);
                int b = centerOfCanvas.y + (triplematHeight / 2);  //inside

                double tripleWidth=0;
                double tripleHeight=0;

                if(centiMetrics) {
                    tripleWidth = (width) / 2.54 * 75;
                    tripleHeight = (height) / 2.54 * 75;
                }
                else if(inches){
                    tripleWidth = (width) * 75;
                    tripleHeight = (height)  * 75;
                }



              /*  double tmatw=tripleWidth+(3*thickness)+overLap;
                double tmath=tripleHeight+(3*thickness)+overLap;*/

                double tmatw=tripleWidth+overLap+tThickness;
                double tmath=tripleHeight+overLap+tThickness;

                int tripleOutwidth=(int)tmatw;
                int tripleOutHieght=(int)tmath;

                int tripleLeft=centerOfCanvas.x-(tripleOutwidth/2);
                int tripleTop=centerOfCanvas.y-(tripleOutHieght/2);
                int tripleRight=centerOfCanvas.x+(tripleOutwidth/2);
                int tripleBottom=centerOfCanvas.y+(tripleOutHieght/2);

                Paint triplePaint=new Paint();
                if(matThreeColor!=0){
                    triplePaint.setColor(matThreeColor);
                }
                else {
                    triplePaint.setColor(Color.WHITE);
                }
                triplePaint.setStyle(Paint.Style.STROKE);
                triplePaint.setStrokeWidth((int)tThickness);

                Paint paint1 = new Paint();
                if(matTwoColor!=0){

                    paint1.setColor(matTwoColor);
                }
                else{
                    paint1.setColor(color);
                }
                paint1.setStyle(Paint.Style.STROKE);
                paint1.setStrokeWidth((int) thickness);



              //  paint.setStrokeWidth((int)stroke1);  //mat  //mattwo
             //   paint.setPathEffect(new DashPathEffect(new float[] {10,10}, 5));

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat("MatHeight",triplematHeight);
                editor.putFloat("MatWidth",triplematWidth);
                editor.commit();







                paint.setColor(Color.WHITE);
                paint.setStyle(Paint.Style.FILL);
                paint.setStrokeWidth(stroke1);
                canvas.drawRect(left, top, right, bottom, paint);

                if (matoneColor != 0) {
                    paint.setColor(matoneColor);
                } else {
                    paint.setColor(Color.WHITE);
                }
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawRect(left, top, right, bottom, paint);
                canvas.drawRect(l, t, r, b, paint1); //outside
                canvas.drawRect(tripleLeft,tripleTop,tripleRight,tripleBottom,triplePaint); //triplemat
                /*if (width != 0) {
                    drawTextinSide(width, height, canvas, left, right, bottom, top);
                    drawText(text,canvas,left,right,bottom,top,(int)overLap);
                    drawText((String.valueOf(divide).toString()),canvas,l,r,b,t,(int)thickness/4);
                    drawText((String.valueOf(TripleDivide).toString()),canvas,tripleLeft,tripleRight,tripleBottom,tripleTop,(int)tThickness/4);
                    if(inches) {
                        drawCalculationText(tThickness / 75, frameWidth, frameHeight, tripleOutwidth / 75, tripleOutHieght / 75, canvas
                                , frameLeft, frameRight, frameBottom, frameTop);
                    }
                    else if(centiMetrics){
                        drawCalculationText(tThickness / 75, frameWidth, frameHeight, tripleOutwidth*2.54 / 75, tripleOutHieght*2.54 / 75, canvas
                                , frameLeft, frameRight, frameBottom, frameTop);
                    }
                }*/
            }
            else {

               /* if(stroke1%2==1){
                    stroke1+=2;
                }*/
                paint.setColor(Color.WHITE);
                paint.setStyle(Paint.Style.FILL);
                paint.setStrokeWidth(stroke1);
                canvas.drawRect(left, top, right, bottom, paint);

                if (matoneColor != 0) {
                    paint.setColor(matoneColor);
                } else {
                    paint.setColor(Color.WHITE);
                }
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawRect(left, top, right, bottom, paint);

                /*if (width != 0) {
                    drawTextinSide(width, height, canvas, left, right, bottom, top);
                    drawText(text,canvas,left,right,bottom,top,(int)overLap);
                    if(inches) {
                        drawCalculationText(overLap / 75, frameWidth, frameHeight, width, height, canvas
                                , frameLeft, frameRight, frameBottom, frameTop);
                    }
                    else if(centiMetrics){
                        drawCalculationText(overLap / 75, frameWidth, frameHeight, width, height, canvas
                                , frameLeft, frameRight, frameBottom, frameTop);
                    }
                }*/
            }


        }
            }
    }







