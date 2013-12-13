package com.github.uiautomatorstub;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import android.os.Build;
import com.android.uiautomator.core.UiDevice;
import com.googlecode.jsonrpc4j.JsonRpcServer;

import fi.iki.elonen.NanoHTTPD;

public class AutomatorHttpServer extends NanoHTTPD {

    public AutomatorHttpServer(int port) {
        super(port);
    }

    private Map<String, JsonRpcServer> router = new HashMap<String, JsonRpcServer>();

    public void route(String uri, JsonRpcServer rpc) {
        router.put(uri, rpc);
    }

    @Override
    public Response serve(String uri, Method method,
                          Map<String, String> headers, Map<String, String> params,
                          Map<String, String> files) {
        Log.d(String.format("URI: %s, Method: %s, Header: %s, params, %s, files: %s", uri, method, headers, params, files));

        if ("/stop".equals(uri)) {
            stop();
            return new Response("Server stopped!!!");
        } else if ("/api/screenshot".equals(uri)) {
            if (Build.VERSION.SDK_INT < 17)
                return new Response(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "API level less than 17.");
            File f = new File(AutomatorServiceImpl.STORAGE_PATH, "screenshot.png");
            float scale = 1.0f;
            if (params.containsKey("scale")) {
                try {
                    scale = Float.parseFloat(params.get("scale"));
                } catch (NumberFormatException e) {
                }
            }
            int quality = 100;
            if (params.containsKey("quality")) {
                try {
                    quality = Integer.parseInt(params.get("quality"));
                } catch (NumberFormatException e) {
                }
            }
            UiDevice.getInstance().takeScreenshot(f, scale, quality);
            try {
                return new Response(Response.Status.OK, "image/png", new FileInputStream(f));
            } catch (FileNotFoundException e) {
                Log.e(e.getMessage());
                return new Response(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "Internal Server Error!!!");
            }
        } else if (router.containsKey(uri)) {
            JsonRpcServer jsonRpcServer = router.get(uri);
            ByteArrayInputStream is = new ByteArrayInputStream(params.get("NanoHttpd.QUERY_STRING").getBytes());
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                jsonRpcServer.handle(is, os);
                return new Response(Response.Status.OK, "application/json", new ByteArrayInputStream(os.toByteArray()));
            } catch (IOException e) {
                return new Response(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "Internal Server Error!!!");
            }
        } else
            return new Response(Response.Status.NOT_FOUND, MIME_PLAINTEXT, "Not Found!!!");
    }

}
