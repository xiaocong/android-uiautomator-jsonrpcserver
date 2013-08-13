package com.github.uiautomatorstub;

/**
 * Created with IntelliJ IDEA.
 * User: b036
 * Date: 8/13/13
 * Time: 10:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class Rect {
    private int _top;
    private int _bottom;
    private int _left;
    private int _right;

    public Rect(android.graphics.Rect rect) {
        this._bottom = rect.bottom;
        this._top = rect.top;
        this._right = rect.right;
        this._left = rect.left;
    }

    public int getTop() {
        return _top;
    }

    public void setTop(int top) {
        this._top = top;
    }

    public int getBottom() {
        return _bottom;
    }

    public void setBottom(int bottom) {
        this._bottom = bottom;
    }

    public int getLeft() {
        return _left;
    }

    public void setLeft(int left) {
        this._left = left;
    }

    public int getRight() {
        return _right;
    }

    public void setRight(int right) {
        this._right = right;
    }
}
