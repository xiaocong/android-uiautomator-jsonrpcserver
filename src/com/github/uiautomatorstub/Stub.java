package com.github.uiautomatorstub;

import android.test.FlakyTest;
import android.test.suitebuilder.annotation.LargeTest;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;

/**
 * A working example of a ui automator test.
 * 
 * @author SNI
 */
public class Stub extends UiAutomatorTestCase {

	private static final int TEST_TOLERANCE = 3;
	private static final int PORT = 9008;
	private AsyncHttpServer server = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		server = new AsyncHttpServer();
		server.post("/rpc", new AutomatorHttpHandler());
		server.get("/ping", new HttpServerRequestCallback() {
			@Override
			public void onRequest(AsyncHttpServerRequest request,
					AsyncHttpServerResponse response) {
		        response.send("pong!!!");
			}
		});
		server.listen(PORT);
	}

	@Override
	protected void tearDown() throws Exception {
		server.stop();
		super.tearDown();
	}

	@LargeTest
	@FlakyTest(tolerance = TEST_TOLERANCE)
	public void testUIAutomatorStub() throws InterruptedException {
		Thread.sleep(10000000);
	}
}