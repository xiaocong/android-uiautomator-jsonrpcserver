# Purpose

[UIAutomator](http://developer.android.com/tools/testing/testing_ui.html) is a
great tool to perform Android UI testing, but to do it, you have to write java
code, compile it, install the jar, and run. It's a complex steps for all
testers...

This project is to build a light weight jsonrpc server in Android device, so
that we can just write PC side script to write UIAutomator tests.

# Build

- Update `local.properties` file with your android sdk path.
- Set ANDROID_HOME env variable (`.bashrc` or `.bash_profile`).
- Install Ant if you have not.
- Run command:

    $ and build # compile
    $ and install # install jar file to device via adb

# Run the jsonrcp server on Android device

	adb shell uiautomator runtest  uiautomator-stub.jar bundle.jar -c com.github.uiautomatorstub.Stub
	adb forward tcp:8009 tcp:8009 # tcp forward

# How to use

Next is a python example using jsonrpclib. Before you run it, make sure install jsonrpclib via
`pip install jsonrpclib`.

	import jsonrpclib
	server = jsonrpclib.Server('http://localhost:9008/jsonrpc/device')
	server.pressKey("home")
	server.click(selector={"text":"","className":"","description":"Apps"})
	server.click(selector={"text":"Settings","className":"","description":""})
	server.pressKey("back")

# Notes

The jsonrpc API is still under discussion, so currently only some demo APIs have been implemented.
If you have any idea, please email xiaocong@gmail.com or submit tickets on github.

# Dependencies

- [nanohttpd](https://github.com/NanoHttpd/nanohttpd)
- [jsonrpc4j](https://code.google.com/p/jsonrpc4j/)
