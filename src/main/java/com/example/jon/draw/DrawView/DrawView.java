package com.example.jon.draw.DrawView;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.View;

import com.example.jon.draw.graphics.Arc;
import com.example.jon.draw.graphics.Circle;
import com.example.jon.draw.graphics.Oval;
import com.example.jon.draw.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jon on 2016/2/27.
 */
public class DrawView extends View {

    //保存底图
    private Bitmap mBitmap;
    private Paint mPaint;
    private Canvas mCanvas;
    public static List<Rect> finished_list_Rect = new ArrayList<Rect>();
    public static List<Oval> finished_list_Oval = new ArrayList<Oval>();
    public static List<Circle> finished_list_Circle = new ArrayList<Circle>();
    public static List<Arc> finished_list_Arc = new ArrayList<Arc>();

    private boolean rect_has_drawed = false;
    private boolean oval_has_drawed = false;
    private boolean circle_has_drawed = false;
    private boolean arc_has_drawed = false;
    private int firstCommand = -1;

    public DrawView(Context context) {
        super(context);
        mBitmap = Bitmap.createBitmap(400,600, Bitmap.Config.RGB_565);
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(7.0f);
        mPaint.setStyle(Paint.Style.STROKE);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);



    }
    public void drawRect(Rect rect,Bitmap bitmap){
        rect_has_drawed = true;

        if(bitmap == null){
            mBitmap = Bitmap.createBitmap(400,600, Bitmap.Config.RGB_565);
            mCanvas = new Canvas(mBitmap);
            mCanvas.drawColor(Color.WHITE);
            firstCommand = DrawCommand.DRAW_RECT;

        }
        if(!oval_has_drawed)
            drawOval(null,mBitmap);
        if(!circle_has_drawed)
            drawCircle(null, mBitmap);
        if(!arc_has_drawed)
            drawArc(null,mBitmap);




        //清空画布

        Rect temp = null;

        for(int i = 0;i<finished_list_Rect.size();i++){
            temp = finished_list_Rect.get(i);

            mCanvas.drawRect(temp.left, temp.top, temp.right, temp.bottom, mPaint);

        }

        if(rect != null)
            mCanvas.drawRect(rect.left, rect.top, rect.right, rect.bottom, mPaint);

        if(firstCommand == DrawCommand.DRAW_RECT){
            rect_has_drawed = false;
            oval_has_drawed = false;
            circle_has_drawed = false;
            arc_has_drawed = false;

        }

        invalidate();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void drawOval(Oval oval,Bitmap bitmap){
        oval_has_drawed = true;
        if(bitmap == null){
            mBitmap = Bitmap.createBitmap(400,600, Bitmap.Config.RGB_565);
            mCanvas = new Canvas(mBitmap);
            mCanvas.drawColor(Color.WHITE);
            firstCommand = DrawCommand.DRAW_OVAL;

        }
        if(!rect_has_drawed)
            drawRect(null,mBitmap);
        if(!circle_has_drawed)
            drawCircle(null, mBitmap);
        if(!arc_has_drawed)
            drawArc(null,mBitmap);



        Oval temp = null;
        for(int i = 0;i<finished_list_Oval.size();i++){
            temp = finished_list_Oval.get(i);
            mCanvas.drawOval(temp.rect.left, temp.rect.top, temp.rect.right, temp.rect.bottom, mPaint);
        }

        if(oval != null)
            mCanvas.drawOval(oval.rect.left,oval.rect.top,oval.rect.right,oval.rect.bottom,mPaint);


        if(firstCommand == DrawCommand.DRAW_OVAL){
            rect_has_drawed = false;
            oval_has_drawed = false;
            circle_has_drawed = false;
            arc_has_drawed = false;

        }
        invalidate();


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void drawCircle(Circle circle,Bitmap bitmap){
        circle_has_drawed = true;
        if(bitmap == null){
            mBitmap = Bitmap.createBitmap(400,600, Bitmap.Config.RGB_565);
            mCanvas = new Canvas(mBitmap);
            mCanvas.drawColor(Color.WHITE);
            firstCommand = DrawCommand.DRAW_CIRCLE;

        }
        if(!rect_has_drawed)
            drawRect(null,mBitmap);
        if(!oval_has_drawed)
            drawOval(null, mBitmap);
        if(!arc_has_drawed)
            drawArc(null,mBitmap);


        Circle temp = null;
        for(int i =0;i<finished_list_Circle.size();i++){
            temp = finished_list_Circle.get(i);
            mCanvas.drawOval(temp.rect.left, temp.rect.top, temp.rect.right, temp.rect.bottom, mPaint);
        }

        if(circle != null){

            mCanvas.drawOval(circle.rect.left, circle.rect.top, circle.rect.right, circle.rect.bottom, mPaint);
        }

        if(firstCommand == DrawCommand.DRAW_CIRCLE){
            rect_has_drawed = false;
            oval_has_drawed = false;
            circle_has_drawed = false;
            arc_has_drawed = false;

        }
        invalidate();


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void drawArc(Arc arc,Bitmap bitmap){
        arc_has_drawed = true;

        if(bitmap == null){
            mBitmap = Bitmap.createBitmap(400,600, Bitmap.Config.RGB_565);
            mCanvas = new Canvas(mBitmap);
            mCanvas.drawColor(Color.WHITE);
            firstCommand = DrawCommand.DRAW_ARC;

        }
        if(!rect_has_drawed)
            drawRect(null,mBitmap);
        if(!oval_has_drawed)
            drawOval(null, mBitmap);
        if(!circle_has_drawed)
            drawCircle(null, mBitmap);


        Arc temp = null;
        for(int i = 0;i<finished_list_Arc.size();i++){
            temp = finished_list_Arc.get(i);
            mCanvas.drawArc(temp.rect.left,temp.rect.top,temp.rect.right,temp.rect.bottom,temp.startAngle,temp.sweepAngle,false,mPaint);

        }

        if(arc != null){
            mCanvas.drawArc(arc.rect.left,arc.rect.top,arc.rect.right,arc.rect.bottom,arc.startAngle,arc.sweepAngle,false,mPaint);
        }

        if(firstCommand == DrawCommand.DRAW_ARC){
            rect_has_drawed = false;
            oval_has_drawed = false;
            circle_has_drawed = false;
            arc_has_drawed = false;

        }

        invalidate();
    }

    public static class DrawCommand{
        public static int DRAW_RECT = 0;
        public static int DRAW_OVAL = 1;
        public static int DRAW_CIRCLE = 2;
        public static int DRAW_ARC = 3;
    }
}
