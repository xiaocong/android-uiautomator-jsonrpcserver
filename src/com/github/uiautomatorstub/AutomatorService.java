package com.github.uiautomatorstub;

import com.googlecode.jsonrpc4j.JsonRpcParam;

public interface AutomatorService {
	String ping();
	boolean wakeUp();
	String packageName();
	boolean pressKey(String key);
	boolean swipe(@JsonRpcParam("selector") Selector selector, @JsonRpcParam("dir") String dir);
	boolean click(@JsonRpcParam("selector") Selector selector);
	boolean click(int x, int y);
}
