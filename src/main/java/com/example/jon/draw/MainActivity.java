package com.example.jon.draw;


import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.jon.draw.DrawView.DrawView;
import com.example.jon.draw.FileUtils.FileUtils;
import com.example.jon.draw.graphics.Arc;
import com.example.jon.draw.graphics.Circle;
import com.example.jon.draw.graphics.Oval;
import com.example.jon.draw.graphics.Rect;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private DrawView drawView;
    private LinearLayout drawLayout;
    private float start_x;
    private float start_y;
    private float current_x;
    private float current_y;
    private float revise_h;
    private float revise_w;
    private Handler mHandler = new Handler();
    private boolean moving = false;
    private int clicks;
    private Rect rect;
    private Oval oval;
    private Circle circle;
    private Arc arc;
    private Button save_Button;
    private Button open_Button;

    private RadioGroup radioGroup;


    private int command = DrawView.DrawCommand.DRAW_RECT;


    private EditText angle_EditText;
    private float startAngle = 30;
    private float sweepAngle=190;


    private CheckBox move_CheckBox;
    private boolean has_find_which_moving = false;
    private float distance_x;
    private float distance_y;

    private Rect cache_for_move = new Rect(0,0,0,0);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawView = new DrawView(this);

        setContentView(R.layout.content_main);

        drawLayout = (LinearLayout)findViewById(R.id.draw_layout);
        //drawLayout = (LinearLayout)findViewById(R.id.demo_layout);





        drawLayout.addView(drawView);
        drawView.setOnTouchListener(this);

        angle_EditText = (EditText)findViewById(R.id.angle_edittext);


        save_Button = (Button)findViewById(R.id.save_button);
        open_Button = (Button)findViewById(R.id.open_button);

        radioGroup = (RadioGroup)findViewById(R.id.radio);

        move_CheckBox =(CheckBox)findViewById(R.id.move_checkbox);
        move_CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                moving = isChecked;

            }
        });



        save_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FileUtils.savePicture(DrawView.finished_list_Rect, DrawView.finished_list_Oval,
                        DrawView.finished_list_Circle, DrawView.finished_list_Arc);


            }
        });

        open_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUtils.openPicture();
                drawView.drawRect(null, null);
            }
        });




        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rect)
                    command = DrawView.DrawCommand.DRAW_RECT;
                if (checkedId == R.id.oval)
                    command = DrawView.DrawCommand.DRAW_OVAL;
                if (checkedId == R.id.circle)
                    command = DrawView.DrawCommand.DRAW_CIRCLE;
                if (checkedId == R.id.arc)
                    command = DrawView.DrawCommand.DRAW_ARC;
            }
        });

        RadioButton radioButton = (RadioButton)findViewById(R.id.rect);
        radioButton.setChecked(true);

//        save_Button.setVisibility(View.GONE);
//        open_Button.setVisibility(View.GONE);
//        shift_Button.setVisibility(View.GONE);
//        angle_EditText.setVisibility(View.GONE);




    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        RelativeLayout function_layout = (RelativeLayout)findViewById(R.id.function_layout);
        //revise_h = function_layout.getHeight()+145;
        //LinearLayout function_layout = (LinearLayout)findViewById(R.id.demo_f);
        revise_w = 25;
        revise_h = getStatusBarHeight()+function_layout.getHeight();
        revise_w = 0;

        //Log.d("data", "" + revise);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                start_x = event.getX()-revise_w;
                start_y = event.getY()-revise_h;
                if(command == DrawView.DrawCommand.DRAW_RECT){

                    rect = whichRectF_continuedraw(start_x, start_y);

                    if(rect != null){
                        if(rect.left == 0.1f){
                            start_x = rect.right;
                            //左上
                            if(rect.bottom == 0.1f){
                                start_y = rect.top;

                                //左下
                            }else {
                                start_y = rect.bottom;

                            }
                        }else {
                            start_x = rect.left;
                            if(rect.bottom == 0.1f){
                                start_y = rect.top;

                            }else {
                                start_y = rect.bottom;

                            }

                        }
                    }



                }

                if(command == DrawView.DrawCommand.DRAW_OVAL){

                    oval = whichOval_continuedraw(start_x, start_y);
                    Log.d("data","))))))))))))))))))))");


                    if(oval != null){
                        if(oval.rect.left == 0.1f){
                            start_x = oval.rect.right;
                            //左上
                            if(oval.rect.bottom == 0.1f){
                                start_y = oval.rect.top;

                                //左下
                            }else {
                                start_y = oval.rect.bottom;

                            }
                        }else {
                            start_x = oval.rect.left;
                            if(oval.rect.bottom == 0.1f){
                                start_y = oval.rect.top;

                            }else {
                                start_y = oval.rect.bottom;

                            }

                        }
                    }


                }

                if(command == DrawView.DrawCommand.DRAW_CIRCLE) {

                    circle = whichCircle_continuedraw(start_x, start_y);

                    //继续画
                    //circle = null;
                    Log.d("data", "Circle continue drawing~~~~ ");
                    if (circle != null) {
                        if (circle.rect.left == 0.1f) {
                            start_x = circle.rect.right;
                            //左下
                            if (circle.rect.bottom == 0.1f) {
                                start_y = circle.rect.top;

                                //左上
                            } else {
                                start_y = circle.rect.bottom;

                            }
                        } else {
                            start_x = circle.rect.left;
                            if (circle.rect.bottom == 0.1f) {
                                start_y = circle.rect.top;

                            } else {
                                start_y = circle.rect.bottom;

                            }

                        }
                    }

                }


                if(command == DrawView.DrawCommand.DRAW_ARC) {
                    //drawView.drawArc(new Arc(new Rect(50,100,200,400),90,180),null);
                    String s = angle_EditText.getText().toString();
                    if (!s.equals("")) {
                        String[] ss = s.split("\\-");
                        startAngle = Float.parseFloat(ss[0]);
                        sweepAngle = Float.parseFloat(ss[1]);
                    }

                    arc = whichArc_continuedraw(start_x, start_y);

                    if (arc != null) {
                        Log.d("data", "arc continue drawing~~~~");

                        if (arc.rect.left == 0.1f) {
                            start_x = arc.rect.right;
                            //左上
                            if (arc.rect.bottom == 0.1f) {
                                start_y = arc.rect.top;

                                //左下
                            } else {
                                start_y = arc.rect.bottom;

                            }
                        } else {
                            start_x = arc.rect.left;
                            if (arc.rect.bottom == 0.1f) {
                                start_y = arc.rect.top;

                            } else {
                                start_y = arc.rect.bottom;

                            }

                        }

                    }
                }


                break;
            case MotionEvent.ACTION_MOVE:
                current_x = event.getX()-revise_w;
                current_y = event.getY()-revise_h;
                if(moving){
                    Log.d("data", "is moving");

                    if(command == DrawView.DrawCommand.DRAW_RECT){

                        if(!has_find_which_moving){
                            rect = whichRectF_move(start_x, start_y);
                            if(rect == null)
                                break;
                            Log.d("data","//////////////////");
                            cache_for_move.left = rect.left;
                            cache_for_move.right = rect.right;
                            cache_for_move.top = rect.top;
                            cache_for_move.bottom = rect.bottom;

                            has_find_which_moving = true;

                        }

                        if(rect == null)
                            break;
                        Log.d("data","if rectF is not  null!!");

                        distance_x = current_x - start_x;
                        distance_y = current_y - start_y;

                        Log.d("data",start_x+"<x>"+current_x+" = "+distance_x);
                        Log.d("data",start_y+"<y>"+current_y+" = "+distance_y);
                        rect.top = cache_for_move.top + distance_y;
                        rect.bottom = cache_for_move.bottom + distance_y;
                        rect.left = cache_for_move.left + distance_x;
                        rect.right = cache_for_move.right + distance_x;



                        drawView.drawRect(rect,null);
                    }

                    if(command == DrawView.DrawCommand.DRAW_OVAL){

                        if(!has_find_which_moving){
                            oval = whichOval_move(start_x, start_y);
                            if(oval == null)
                                break;
                            cache_for_move.left = oval.rect.left;
                            cache_for_move.right = oval.rect.right;
                            cache_for_move.top = oval.rect.top;
                            cache_for_move.bottom = oval.rect.bottom;

                            has_find_which_moving = true;

                        }




                        if(oval == null)
                            break;
                        Log.d("data","Oval is not  null!!");
                        float distance_x = current_x - start_x;
                        float distance_y = current_y - start_y;

                        oval.rect.top = cache_for_move.top + distance_y;
                        oval.rect.bottom = cache_for_move.bottom + distance_y;
                        oval.rect.left = cache_for_move.left + distance_x;
                        oval.rect.right = cache_for_move.right + distance_x;

                        drawView.drawOval(oval, null);


                    }
                    if(command == DrawView.DrawCommand.DRAW_CIRCLE){

                        if(!has_find_which_moving){
                            circle = whichCircle_move(start_x,start_y);
                            if(circle == null)
                                break;
                            cache_for_move.left = circle.rect.left;
                            cache_for_move.right = circle.rect.right;
                            cache_for_move.top = circle.rect.top;
                            cache_for_move.bottom = circle.rect.bottom;

                            has_find_which_moving = true;
                        }


                        Log.d("data","+++++++++++++++++");
                        if(circle == null)
                            break;
                        Log.d("data","可以找到Circle");
                        float distance_x = current_x - start_x;
                        float distance_y = current_y - start_y;

                        circle.rect.top = cache_for_move.top + distance_y;
                        circle.rect.bottom = cache_for_move.bottom + distance_y;
                        circle.rect.left = cache_for_move.left + distance_x;
                        circle.rect.right = cache_for_move.right + distance_x;

                       ;


                        //画圆
                        drawView.drawCircle(circle,null);




                    }
                    if(command == DrawView.DrawCommand.DRAW_ARC){


                        if(!has_find_which_moving){
                            arc = whichArc_move(start_x,start_y);
                            if(arc == null)
                                break;

                            cache_for_move.left = arc.rect.left;
                            cache_for_move.right = arc.rect.right;
                            cache_for_move.top = arc.rect.top;
                            cache_for_move.bottom = arc.rect.bottom;
                            has_find_which_moving = true;

                        }


                        float distance_x = current_x - start_x;
                        float distance_y = current_y - start_y;
                        arc.rect.top = cache_for_move.top + distance_y;
                        arc.rect.bottom = cache_for_move.bottom + distance_y;
                        arc.rect.left = cache_for_move.left + distance_x;
                        arc.rect.right = cache_for_move.right + distance_x;


                        drawView.drawArc(arc,null);



                    }

                }else {
                    if(command == DrawView.DrawCommand.DRAW_RECT){
                        Log.d("data","111");
                        drawView.drawRect(new Rect(start_x,start_y,current_x,current_y),null);
                    }

                    if(command == DrawView.DrawCommand.DRAW_OVAL){
                        //Log.d("data","222");
                        drawView.drawOval(new Oval(new Rect(start_x, start_y, current_x, current_y)), null);


                    }
                    if(command == DrawView.DrawCommand.DRAW_CIRCLE){

                        Log.d("data", start_x + "::::" + current_x);
                        if((current_x>start_x && current_y<start_y )|| (current_x<start_x && current_y>start_y)){
                            drawView.drawCircle(new Circle(new Rect(start_x,start_y,current_x,start_y-current_x+start_x)),null);


                        }else {
                            drawView.drawCircle(new Circle(new Rect(start_x,start_y,current_x,current_x-start_x+start_y)),null);


                        }


                    }
                    if(command == DrawView.DrawCommand.DRAW_ARC){
                        drawView.drawArc(new Arc(new Rect(start_x,start_y,current_x,current_y),startAngle,sweepAngle),null);


                    }

                }

                break;
            case MotionEvent.ACTION_UP:

                current_x = event.getX()-revise_w;
                current_y = event.getY()-revise_h;
                if(moving){
                    has_find_which_moving = false;
                    move_CheckBox.setChecked(false);


                    break;
                }

                Log.d("data","]]]]]]]]]]____");


                if(command == DrawView.DrawCommand.DRAW_RECT){
                    DrawView.finished_list_Rect.add(new Rect(start_x, start_y, current_x, current_y));
                    drawView.drawRect(new Rect(start_x, start_y, current_x, current_y),null);

                }
                if(command == DrawView.DrawCommand.DRAW_OVAL){
                    DrawView.finished_list_Oval.add(new Oval(new Rect(start_x, start_y, current_x, current_y)));
                    drawView.drawOval(new Oval(new Rect(start_x, start_y, current_x, current_y)), null);


                }
                if(command == DrawView.DrawCommand.DRAW_CIRCLE){


                    if((current_x>start_x && current_y<start_y )|| (current_x<start_x && current_y>start_y)){
                        drawView.drawCircle(new Circle(new Rect(start_x,start_y,current_x,start_y-current_x+start_x)),null);

                        DrawView.finished_list_Circle.add(new Circle(new Rect(start_x, start_y, current_x, start_y-current_x + start_x)));


                    }else {
                        drawView.drawCircle(new Circle(new Rect(start_x,start_y,current_x,current_x-start_x+start_y)),null);

                        DrawView.finished_list_Circle.add(new Circle(new Rect(start_x, start_y, current_x, current_x - start_x + start_y)));


                    }




                }
                if(command == DrawView.DrawCommand.DRAW_ARC){
                    DrawView.finished_list_Arc.add(new Arc(new Rect(start_x,start_y,current_x,current_y),startAngle,sweepAngle));
                    drawView.drawArc(new Arc(new Rect(start_x,start_y,current_x,current_y),startAngle,sweepAngle),null);


                }

                start_x = 0f;
                start_y = 0f;
                current_x = 0f;
                current_y = 0f;
                break;
            default:
                break;

        }
        //Log.d("data", "move" + event.getX());
        return super.onTouchEvent(event);
    }


    private Rect whichRectF_move(float x,float y){
        Rect rect = null;
        for(int i = 0;i<DrawView.finished_list_Rect.size();i++){
            rect= DrawView.finished_list_Rect.get(i);
            Log.d("data",rect.left+"->"+rect.right+"->"+rect.top+"->"+rect.bottom);
            Log.d("data",x+"|||||"+y);
            if(x>rect.left+10 && x<rect.right-10 && y> rect.top+10 && y<rect.bottom-10){
                Log.d("data", "zhao dao la");
                return rect;

            }

        }
        Log.d("data","return null");
        return null;
    }

    private Rect whichRectF_continuedraw(float x,float y){
        if(moving)
            return null;
        Rect rect = null;
        int r = 20;//用户手指头粗···点击的范围浮动
        for(int i=0;i<DrawView.finished_list_Rect.size();i++){
            rect = DrawView.finished_list_Rect.get(i);
            //左下角
            if(rect.left-r<x && x<rect.left+r && y > rect.top-r && y<rect.top+r){
                rect.left = 0.1f;
                rect.top = 0.1f;
                DrawView.finished_list_Rect.remove(i);
                return rect;

            }
            //右下角
            if(rect.right-r<x && x<rect.right+r && y > rect.top-r && y<rect.top+r){
                rect.right = 0.1f;
                rect.top = 0.1f;
                DrawView.finished_list_Rect.remove(i);
                return rect;
            }
            //左上角
            if(x>rect.left-r && x<rect.left+r && y<rect.bottom+r && y>rect.bottom-r){
                rect.left = 0.1f;
                rect.bottom = 0.1f;
                DrawView.finished_list_Rect.remove(i);
                return rect;
            }
            //右上角
            if(x>rect.right-r && x<rect.right+r && y<rect.bottom+r && y>rect.bottom-r){
                rect.right = 0.1f;
                rect.bottom = 0.1f;
                DrawView.finished_list_Rect.remove(i);
                return rect;
            }
        }
        return null;
    }

    private Oval whichOval_move(float x,float y){
        Oval oval = null;
        for(int i = 0;i<DrawView.finished_list_Oval.size();i++){
            oval = DrawView.finished_list_Oval.get(i);

            if(x>oval.rect.left+10 && x<oval.rect.right - 10 && y > oval.rect.top+10 && y<oval.rect.bottom-10){

                Log.d("data", "zhao dao la");
                return oval;

            }
        }
        return null;
    }
    private Oval whichOval_continuedraw(float x,float y){
        if(moving)
            return null;
        Oval oval = null;
        float h = 0;
        float w = 0;
        int r = 20;
        for(int i = 0;i<DrawView.finished_list_Oval.size();i++){
            oval = DrawView.finished_list_Oval.get(i);
            h = oval.rect.top-oval.rect.bottom;
            w = oval.rect.right-oval.rect.left;

            //左边中点
            if(oval.rect.left-r<x && x<oval.rect.left+r && y > oval.rect.top-h/2-r && y<oval.rect.bottom+h/2+r){
                oval.rect.left = 0.1f;
                oval.rect.top = 0.1f;
                DrawView.finished_list_Oval.remove(i);
                return oval;

            }
            //右边中点
            if(oval.rect.right-r<x && x<oval.rect.right+r && y > oval.rect.top-h/2-r && y<oval.rect.bottom+h/2+r){
                oval.rect.right = 0.1f;
                oval.rect.top = 0.1f;
                DrawView.finished_list_Oval.remove(i);
                return oval;
            }
            //上边中点
            if(x>oval.rect.left+w/2-r && x<oval.rect.left+w/2+r && y<oval.rect.bottom+r && y>oval.rect.bottom-r){
                oval.rect.left = 0.1f;
                oval.rect.bottom = 0.1f;
                DrawView.finished_list_Oval.remove(i);
                return oval;
            }
            //下边中点
            if(x>oval.rect.right-w/2-r && x<oval.rect.right-w/2+r && y<oval.rect.bottom+r && y>oval.rect.bottom-r){
                oval.rect.right = 0.1f;
                oval.rect.bottom = 0.1f;
                DrawView.finished_list_Oval.remove(i);
                return oval;
            }



        }
        Log.d("data","return null"+h+":::"+w);

        return null;
    }

    private Circle whichCircle_move(float x,float y){


        Circle circle = null;
        for(int i= 0;i<DrawView.finished_list_Circle.size();i++){
            circle = DrawView.finished_list_Circle.get(i);
            Log.d("data","---------"+circle.rect.left+"::"+circle.rect.right+"::"+circle.rect.top);
            Log.d("data","}}}}}}}"+x+"::"+y);

            if(x>circle.rect.left+10 && x<circle.rect.right - 10 && y > circle.rect.top+10 && y<circle.rect.bottom-10){

                Log.d("data", "zhao dao la");
                return circle;

            }

        }
        return null;
    }

    private Circle whichCircle_continuedraw(float x, float y){
        if(moving)
            return null;
        Circle circle = null;
        float h = 0;
        float w = 0;
        int r = 20;
        for(int i=0;i<DrawView.finished_list_Circle.size();i++){
            circle = DrawView.finished_list_Circle.get(i);



            //左上
            if(circle.rect.left-r<x && x<circle.rect.left+r && y > circle.rect.top-r && y<circle.rect.top+r){
                circle.rect.left = 0.1f;
                circle.rect.top = 0.1f;
                DrawView.finished_list_Circle.remove(i);
                return circle;

            }
            //右上
            if(circle.rect.right-r<x && x<circle.rect.right+r && y > circle.rect.top-r && y<circle.rect.top+r){
                circle.rect.right = 0.1f;
                circle.rect.top = 0.1f;
                DrawView.finished_list_Circle.remove(i);
                return circle;
            }
            //左下
            if(x>circle.rect.left-r && x<circle.rect.left+r && y<circle.rect.bottom+r && y>circle.rect.bottom-r){
                circle.rect.left = 0.1f;
                circle.rect.bottom = 0.1f;
                DrawView.finished_list_Circle.remove(i);
                return circle;
            }
            //右下
            if(x>circle.rect.right-r && x<circle.rect.right+r && y<circle.rect.bottom+r && y>circle.rect.bottom-r){
                circle.rect.right = 0.1f;
                circle.rect.bottom = 0.1f;
                DrawView.finished_list_Circle.remove(i);
                return circle;
            }

        }

        return null;
    }

    private Arc whichArc_move(float x,float y){

        Arc arc = null;
        for(int i = 0;i<DrawView.finished_list_Arc.size();i++){
            arc= DrawView.finished_list_Arc.get(i);

            if(x>arc.rect.left+10 && x<arc.rect.right - 10 && y > arc.rect.top+10 && y<arc.rect.bottom-10){

                Log.d("data", "zhao dao la");
                return arc;

            }

        }
        return null;
    }
    private Arc whichArc_continuedraw(float x,float y){
        if(moving)
            return null;
        Arc arc = null;
        int r = 20;//用户手指头粗···点击的范围浮动
        for(int i=0;i<DrawView.finished_list_Arc.size();i++){
            arc = DrawView.finished_list_Arc.get(i);
            //左下角
            if(arc.rect.left-r<x && x<arc.rect.left+r && y > arc.rect.top-r && y<arc.rect.top+r){
                arc.rect.left = 0.1f;
                arc.rect.top = 0.1f;
                DrawView.finished_list_Arc.remove(i);
                return arc;

            }
            //右下角
            if(arc.rect.right-r<x && x<arc.rect.right+r && y > arc.rect.top-r && y<arc.rect.top+r){
                arc.rect.right = 0.1f;
                arc.rect.top = 0.1f;
                DrawView.finished_list_Arc.remove(i);
                return arc;
            }
            //左上角
            if(x>arc.rect.left-r && x<arc.rect.left+r && y<arc.rect.bottom+r && y>arc.rect.bottom-r){
                arc.rect.left = 0.1f;
                arc.rect.bottom = 0.1f;
                DrawView.finished_list_Arc.remove(i);
                return arc;
            }
            //右上角
            if(x>arc.rect.right-r && x<arc.rect.right+r && y<arc.rect.bottom+r && y>arc.rect.bottom-r){
                arc.rect.right = 0.1f;
                arc.rect.bottom = 0.1f;
                DrawView.finished_list_Arc.remove(i);
                return arc;
            }
        }

        return null;
    }




    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

}
