package com.github.uiautomatorstub;

import com.googlecode.jsonrpc4j.JsonRpcParam;

public interface AutomatorService {
	String ping();
	boolean pressKey(String key);
	boolean click(@JsonRpcParam("selector") Selector selector);
}
