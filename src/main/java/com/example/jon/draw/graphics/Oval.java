package com.example.jon.draw.graphics;

import java.io.Serializable;

/**
 * Created by jon on 2016/2/29.
 */
public class Oval implements Serializable {
    public Rect rect;

    public Oval(Rect rect) {
        this.rect = rect;
    }
}
