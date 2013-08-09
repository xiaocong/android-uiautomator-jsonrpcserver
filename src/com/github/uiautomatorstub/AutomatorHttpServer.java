package com.github.uiautomatorstub;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.jsonrpc4j.JsonRpcServer;

import fi.iki.elonen.NanoHTTPD;

public class AutomatorHttpServer extends NanoHTTPD {

	public AutomatorHttpServer(int port) {
		super(port);
	}

	private static Charset charset = Charset.forName("UTF-8");
	private static CharsetEncoder encoder = charset.newEncoder();
	private static CharsetDecoder decoder = charset.newDecoder();

	private Map<String, JsonRpcServer> router = new HashMap<String, JsonRpcServer>();

	public void route(String uri, JsonRpcServer rpc) {
		router.put(uri, rpc);
	}
	
	@Override
	public Response serve(String uri, Method method,
			Map<String, String> headers, Map<String, String> parms,
			Map<String, String> files) {
		Log.d(String.format("URI: %s, Method: %s, Header: %s, parms, %s, files: %s", uri, method, headers, parms, files));
		
		if ("/stop".equals(uri)) {
			stop();
			return new Response("Server stopped!!!");
		}
		else if (router.containsKey(uri)) {
			JsonRpcServer jsonRpcServer = router.get(uri);
			ByteArrayInputStream is = new ByteArrayInputStream(parms.get("NanoHttpd.QUERY_STRING").getBytes());
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			try {
				jsonRpcServer.handle(is, os);
				return new Response(Response.Status.OK, "application/json", new ByteArrayInputStream((os.toByteArray())));
			} catch (IOException e) {
				return new Response(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "Internal Server Error!!!");
			}
		} else
			return new Response(Response.Status.NOT_FOUND, MIME_PLAINTEXT, "Not Found!!!");
	}
}
