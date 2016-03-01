package com.example.jon.draw.graphics;

import java.io.Serializable;

/**
 * Created by jon on 2016/2/29.
 */
public class Rect implements Serializable {
    public float top;
    public float bottom;
    public float left;
    public float right;

    public Rect(float left, float top, float right, float bottom) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;

    }
}
