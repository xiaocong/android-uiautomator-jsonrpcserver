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
	adb forward tcp:9008 tcp:9008 # tcp forward

# How to use

Next is a python example using jsonrpclib. Before you run it, make sure install jsonrpclib via
`pip install jsonrpclib`.

	import jsonrpclib
	server = jsonrpclib.Server('http://localhost:9008/jsonrpc/device')

    class SelectorBuilder(object):
        _keys = {
            "text": (0x01L, None),  # MASK_TEXT,
            "textContains": (0x02L, None),  # MASK_TEXTCONTAINS,
            "textMatches": (0x04L, None),  # MASK_TEXTMATCHES,
            "textStartsWith": (0x08L, None),  # MASK_TEXTSTARTSWITH,
            "className": (0x10L, None),  # MASK_CLASSNAME
            "classNameMatches": (0x20L, None),  # MASK_CLASSNAMEMATCHES
            "description": (0x40L, None),  # MASK_DESCRIPTION
            "descriptionContains": (0x80L, None),  # MASK_DESCRIPTIONCONTAINS
            "descriptionMatches": (0x0100L, None),  # MASK_DESCRIPTIONMATCHES
            "descriptionStartsWith": (0x0200L, None),  # MASK_DESCRIPTIONSTARTSWITH
            "checkable": (0x0400L, False),  # MASK_CHECKABLE
            "checked": (0x0800L, False),  # MASK_CHECKED
            "clickable": (0x1000L, False),  # MASK_CLICKABLE
            "longClickable": (0x2000L, False),  # MASK_LONGCLICKABLE,
            "scrollable": (0x4000L, False),  # MASK_SCROLLABLE,
            "enabled": (0x8000L, False),  # MASK_ENABLED,
            "focusable": (0x010000L, False),  # MASK_FOCUSABLE,
            "focused": (0x020000L, False),  # MASK_FOCUSED,
            "selected": (0x040000L, False),  # MASK_SELECTED,
            "packageName": (0x080000L, None),  # MASK_PACKAGENAME,
            "packageNameMatches": (0x100000L, None),  # MASK_PACKAGENAMEMATCHES,
            "resourceId": (0x200000L, None),  # MASK_RESOURCEID,
            "resourceIdMatches": (0x400000L, None),  # MASK_RESOURCEIDMATCHES,
            "index": (0x800000L, 0),  # MASK_INDEX,
            "instance": (0x01000000L, 0),  # MASK_INSTANCE,
            "fromParent": (0x02000000L, None),  # MASK_FROMPARENT,
            "childSelector": (0x04000000L, None)  # MASK_CHILDSELECTOR
            }
        _mask = "mask"
        def __init__(self, **kwargs):
            self._dict = {}
            self._dict[self._mask] = 0
            for k, v in self._keys.items():
                self._dict[k] = v[1]  # set the default value
            for k, v in kwargs.items():
                if k in self._keys:
                    self[k] = v
        def __getitem__(self, k):
            return self._dict[k]
        def __setitem__(self, k, v):
            if k in self._keys:
                self._dict[k] = v  # call the method in superclass
                self._dict[self._mask] = self[self._mask] | self._keys[k][0]
        def __delitem__(self, k):
            if k in self._keys:
                self[k] = self._keys[k][1]
                self[self._mask] = self[self._mask] & ~self._keys[k][0]
        def build(self):
            d = self._dict.copy()
            for k, v in d.items():
                #if isinstance(v, SelectorBuilder):
                # TODO workaround.
                # something wrong in the module loader, likely SelectorBuilder was loaded as another object...
                if k in ["childSelector", "fromParent"] and v is not None:
                    d[k] = v.build()
            return d

    server.wakeUp()
	server.pressKey("home")
	server.click(SelectorBuilder(description="Apps").build())
	server.click(SelectorBuilder(text="Clock").build())
	server.pressKey("back")

# Notes

The jsonrpc API is still under discussion, so currently only some demo APIs have been implemented.
If you have any idea, please email xiaocong@gmail.com or submit tickets on github.

# Dependencies

- [nanohttpd](https://github.com/NanoHttpd/nanohttpd)
- [jsonrpc4j](https://code.google.com/p/jsonrpc4j/)
