package com.github.uiautomatorstub;

/**
 * Created with IntelliJ IDEA.
 * User: b036
 * Date: 8/13/13
 * Time: 10:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class Point {
    public int getX() {
        return _x;
    }

    public void setX(int x) {
        this._x = x;
    }

    public int getY() {
        return _y;
    }

    public void setY(int y) {
        this._y = y;
    }

    public Point(int x, int y) {
        this._x = x;
        this._y = y;
    }

    private int _x;
    private int _y;
}
