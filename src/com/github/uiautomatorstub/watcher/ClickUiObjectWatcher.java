package com.github.uiautomatorstub.watcher;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.github.uiautomatorstub.Log;

/**
 * Created with IntelliJ IDEA.
 * User: b036
 * Date: 8/21/13
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClickUiObjectWatcher extends SelectorWatcher {

    private UiSelector target = null;

    public ClickUiObjectWatcher(UiSelector[] conditions, UiSelector target) {
        super(conditions);
        this.target = target;
    }

    @Override
    public void action() {
        Log.d("ClickUiObjectWatcher triggered!");
        if (target != null) {
            try {
                new UiObject(target).click();
            } catch (UiObjectNotFoundException e) {
                Log.d(e.getMessage());
            }
        }
    }
}
