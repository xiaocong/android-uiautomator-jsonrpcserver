package com.github.uiautomatorstub;

import android.test.FlakyTest;
import android.test.suitebuilder.annotation.LargeTest;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.JsonRpcServer;

/**
 * A working example of a ui automator test.
 * 
 * @author SNI
 */
public class Stub extends UiAutomatorTestCase {

	private static final int TEST_TOLERANCE = 3;
	private static final int PORT = 9008;
	private AutomatorHttpServer server = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		server = new AutomatorHttpServer(PORT);
		server.route("/jsonrpc/0", new JsonRpcServer(new ObjectMapper(),
				new AutomatorServiceImpl(), AutomatorService.class));
		server.start();
	}

	@Override
	protected void tearDown() throws Exception {
		server.stop();
		server = null;
		super.tearDown();
	}

	@LargeTest
	@FlakyTest(tolerance = TEST_TOLERANCE)
	public void testUIAutomatorStub() throws InterruptedException {
		while (server.isAlive())
			Thread.sleep(100);
	}
}