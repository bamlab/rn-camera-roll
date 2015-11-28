# React Native Camera Roll

## Table of contents

* [Example](https://github.com/bamlab/rn-camera-roll#example)
* [Setup](https://github.com/bamlab/rn-camera-roll#setup)
* [Usage](https://github.com/bamlab/rn-camera-roll#usage)

## Example

Checkout [this example](https://github.com/bamlab/rn-camera-roll/tree/master/example) of a basic gallery app with infinite scroll:  
https://github.com/bamlab/rn-camera-roll/tree/master/example

![alt text](https://raw.githubusercontent.com/bamlab/rn-camera-roll/master/example/screenshot.png "Logo Title Text 1")

## Setup

First, install the package:
```
npm install rn-camera-roll
```

Then, follow those instructions:

### iOS

The Camera Roll iOS API is part of `react-native`.
You have to import `node_modules/react-native/Libraries/CameraRoll/RCTCameraRoll.xcodeproj`
by following the [libraries linking instructions](https://facebook.github.io/react-native/docs/linking-libraries-ios.html#here-the-few-steps-to-link-your-libraries-that-contain-native-code).

### Android

Update your gradle files by running:
```
react-native link rn-camera-roll
```

Then register the package into your `MainActivity`:
```java
package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.facebook.react.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;

// IMPORT HERE
import fr.bamlab.rncameraroll.CameraRollPackage;
// ---

public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {

    private ReactInstanceManager mReactInstanceManager;
    private ReactRootView mReactRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReactRootView = new ReactRootView(this);

        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setBundleAssetName("index.android.bundle")
                .setJSMainModuleName("index.android")
                .addPackage(new MainReactPackage())

                // REGISTER PACKAGE HERE
                .addPackage(new CameraRollPackage())
                // ---
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();

        mReactRootView.startReactApplication(mReactInstanceManager, "example", null);

        setContentView(mReactRootView);
    }

    ...
```

## Usage

You can use the `getPhotos` API as you would with the iOS API with the `after` and the `first` params.

```javascript
var CameraRoll = require('rn-camera-roll');

onPhotosFetchedSuccess(data) {
    var photos = data.edges.map((asset) => {
      return asset.node.image;
    });
    console.log(photos);
  }

  onPhotosFetchError() {
    // TODO: Handle error
  }

  fetchPhotos(count = 10, after) {
    CameraRoll.getPhotos({
      // take the first n photos after given photo uri
      first: count,
      // after
      after: "file:/storage/emulated/0/DCIM/Camera/IMG_20151126_115520477.jpg",
    }, this.onPhotosFetchedSuccess.bind(this), this.onPhotosFetchError.bind(this));
  }
```
