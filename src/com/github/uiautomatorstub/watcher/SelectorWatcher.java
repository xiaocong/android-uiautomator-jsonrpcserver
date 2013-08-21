package com.github.uiautomatorstub.watcher;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.core.UiWatcher;

/**
 * Created with IntelliJ IDEA.
 * User: b036
 * Date: 8/21/13
 * Time: 1:57 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class SelectorWatcher implements UiWatcher {
    private UiSelector[] conditions = null;

    public SelectorWatcher(UiSelector[] conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean checkForCondition() {
        for (UiSelector s : conditions) {
            UiObject obj = new UiObject(s);
            if (!obj.exists()) return false;
        }
        action();
        return true;
    }

    public abstract void action();
}
