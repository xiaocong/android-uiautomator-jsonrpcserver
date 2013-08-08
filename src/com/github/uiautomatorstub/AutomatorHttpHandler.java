package com.github.uiautomatorstub;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.json.JSONObject;

import com.googlecode.jsonrpc4j.JsonRpcServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;

public class AutomatorHttpHandler implements HttpServerRequestCallback {

	class AutomatorServiceImpl implements AutomatorService {
		@Override
		public String ping() {
			return "pong";
		}
	}

	private static Charset charset = Charset.forName("UTF-8");
	private static CharsetEncoder encoder = charset.newEncoder();
	private static CharsetDecoder decoder = charset.newDecoder();

	private JsonRpcServer jsonRpcServer = new JsonRpcServer(
			new AutomatorServiceImpl(), AutomatorService.class);

	@Override
	public void onRequest(AsyncHttpServerRequest request,
			AsyncHttpServerResponse response) {
		Log.d("Get Request...");
		Log.d(request.getBody().toString());
		Log.d(Integer.toString(request.getBody().length()));
		String msg = ((JSONObject)request.getBody().get()).toString();
		Log.d(msg);
		response.send(msg);
		// response.write(encoder.encode(CharBuffer.wrap(msg)));
	}
}
