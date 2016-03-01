package com.example.jon.draw.graphics;

import java.io.Serializable;

/**
 * Created by jon on 2016/2/29.
 */
public class Circle implements Serializable{
   public Rect rect;

    public Circle(Rect rect) {
        this.rect = rect;
    }
}
