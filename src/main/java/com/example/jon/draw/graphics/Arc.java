package com.example.jon.draw.graphics;

import java.io.Serializable;

/**
 * Created by jon on 2016/2/29.
 */
public class Arc implements Serializable{

    public float startAngle;
    public float sweepAngle;
    public Rect rect;

    public Arc(Rect rect, float startAngle, float sweepAngle) {
        this.rect = rect;
        this.startAngle = startAngle;
        this.sweepAngle = sweepAngle;

    }
}
