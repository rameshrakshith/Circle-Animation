package com.proj3.RakshithRamesh.circle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
//Rakshith Ramesh


public class CircleDrawView extends View implements View.OnTouchListener {

    static Float X = 0f, Y = 0f, startRadius = 0f;
    static String defaultCircleColor = ActivityModeSelector.defaultColor;
    static Float xCoordinateDeleteOnDown = 0f, yCoordinateDeleteOnDown = 0f, xCoordinateDeleteOnUp = 0f, yCoordinateDeleteOnUp = 0f;
    static Float xCoordinateMoveOnDown = 0f, yCoordinateMoveOnDown = 0f;
    static Float xDirectionVelocity = 0f, yDirectionVelocity = 0f;
    static VelocityTracker velocity;

    private boolean onSwipe = false;
    private DrawCircleAnimation startDrawingCircle;
    static List<Circle> circles = new ArrayList<Circle>();
    static Integer canvasHeight = 0;
    static Integer canvasWidth = 0;
    private Circle circleObject;
    Paint circleFrame;

    public CircleDrawView(Context context) {
        super(context);
    }

    public CircleDrawView(Context context, AttributeSet xmlAttributes) {
        super(context, xmlAttributes);
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        int actionType = action & MotionEvent.ACTION_MASK;
        switch (actionType) {
            case MotionEvent.ACTION_DOWN:
                return functionActionDown(event);
            case MotionEvent.ACTION_UP:
                return functionActionUp(event);
            case MotionEvent.ACTION_MOVE:
                return functionActionMove(event);
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                onSwipe = false;
                return false;
        }
        return false;
    }

    private boolean functionActionDown(MotionEvent event) {

        onSwipe = true;

        if (ActivityModeSelector.drawModeOn) {
            X = event.getX();
            Y = event.getY();
            startRadius = 0f;
            circleObject = new Circle(X, Y, startRadius, defaultCircleColor);
            CircleDrawView.circles.add(circleObject);
            startDrawingCircle = new DrawCircleAnimation();
            Timer timer = new Timer(true);
            timer.scheduleAtFixedRate(startDrawingCircle,0,75);

        }

        if (ActivityModeSelector.deleteModeOn) {
            xCoordinateDeleteOnDown = event.getX();
            yCoordinateDeleteOnDown = event.getY();
        }

        if (ActivityModeSelector.moveModeOn) {
            xCoordinateMoveOnDown = event.getX();
            yCoordinateMoveOnDown = event.getY();
            velocity = VelocityTracker.obtain();
            velocity.addMovement(event);
        }
        return true;
    }

    private boolean functionActionUp(MotionEvent event) {

        if (!onSwipe) return false;
        if (ActivityModeSelector.drawModeOn) {
            startDrawingCircle.cancel();
            startRadius = 0f;
        }

        if (ActivityModeSelector.deleteModeOn) {
            xCoordinateDeleteOnUp = event.getX(); yCoordinateDeleteOnUp = event.getY();
            for (int i = 0; i < CircleDrawView.circles.size(); i++) {
                Float centerPointDistanceOnDown = ((float) Math.sqrt(Math.pow((xCoordinateDeleteOnDown - CircleDrawView.circles.get(i).getX()), 2) + Math.pow((yCoordinateDeleteOnDown - CircleDrawView.circles.get(i).getY()), 2)));
                Float centerPointDistanceOnUp = ((float) Math.sqrt(Math.pow((xCoordinateDeleteOnUp - CircleDrawView.circles.get(i).getX()), 2) + Math.pow((yCoordinateDeleteOnUp - CircleDrawView.circles.get(i).getY()), 2)));
                if (centerPointDistanceOnDown < CircleDrawView.circles.get(i).getRadius() && centerPointDistanceOnUp < CircleDrawView.circles.get(i).getRadius()) {
                    CircleDrawView.circles.remove(CircleDrawView.circles.get(i));
                    i -= 1;
                }
            }
        }

        if (ActivityModeSelector.moveModeOn) {
            velocity.computeCurrentVelocity(1000);
            xDirectionVelocity = velocity.getXVelocity();
            yDirectionVelocity = velocity.getYVelocity();
            for (Circle eachCircle : CircleDrawView.circles) {
                double distanceX = xCoordinateMoveOnDown - eachCircle.getX();
                double distanceY = yCoordinateMoveOnDown - eachCircle.getY();
                Float centerToPointDistance = ((float) Math.sqrt(Math.pow(distanceX, 2) + Math.pow((distanceY), 2)));
                if (centerToPointDistance < eachCircle.getRadius()) {
                    eachCircle.setCanMoveFlag(1);
                    eachCircle.setXDirectionVelocity(xDirectionVelocity); eachCircle.setYDirectionVelocity(yDirectionVelocity);
                }
            }
        }
        return true;
    }
    
    private boolean functionActionMove(MotionEvent event) {

        if (!onSwipe) return false;
        if (ActivityModeSelector.moveModeOn) {
            velocity.addMovement(event);
        }
        return true;

    }

    
    
    @Override
    protected void onDraw(Canvas canvas) {
        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();
        circleFrame = new Paint();
        circleFrame.setStyle(Paint.Style.STROKE);
        circleFrame.setStrokeWidth(6);
        for (int i = 0; i < circles.size(); i++) {
           
            circles.get(i).setColor(defaultCircleColor);
            canvas.drawCircle(circles.get(i).getX(), circles.get(i).getY(), circles.get(i).getRadius(), circleFrame);
        }
        if (ActivityModeSelector.moveModeOn) {
            for (Circle eachCircle : CircleDrawView.circles) {
                if (eachCircle.getCanMoveFlag() == 1) {
                    Integer maximumVelocity = 100;
                    eachCircle.setX(eachCircle.getX() + (eachCircle.getXDirectionVelocity() / maximumVelocity));
                    eachCircle.setY(eachCircle.getY() + (eachCircle.getYDirectionVelocity() / maximumVelocity));
                    if (eachCircle.getRadius() + eachCircle.getX() > canvasWidth || eachCircle.getX() < eachCircle.getRadius()) {
                        eachCircle.setXDirectionVelocity(eachCircle.getXDirectionVelocity() * -1);
                    }
                    if (eachCircle.getRadius() + eachCircle.getY() > canvasHeight || eachCircle.getY() < eachCircle.getRadius()) {
                        eachCircle.setYDirectionVelocity(eachCircle.getYDirectionVelocity() * -1);
                    }
                }
            }
        }
        circleFrame.setColor(Color.BLACK);
        canvas.drawCircle(X, Y, startRadius, circleFrame);
        invalidate();
    }



    class DrawCircleAnimation extends TimerTask{

        @Override
        public void run() {
            int increaseRadiusBy = 20;
            circleObject.setRadius(circleObject.getRadius()+increaseRadiusBy);
        }
    }
}