import React from 'react-native';

const CameraRollAndroid = React.NativeModules.CameraRollAndroid;
const ANDROID_FILE_PREFIX = 'file:';

function formatToIosCameraRollFormat(imageDataList) {
  return {
    edges: imageDataList.map((imageData) => {
      return {
        node: {
          image: {
            uri: ANDROID_FILE_PREFIX + imageData.uri,
            width: imageData.width,
            height: imageData.height,
          },
        },
      };
    }),
  };
}

export default {
  getPhotos: (fetchParams, onSuccess) => {
    if(fetchParams.after) {
      fetchParams.after = fetchParams.after.replace(ANDROID_FILE_PREFIX, '');
    }
    CameraRollAndroid.getCameraImages(fetchParams, (imageDataList) => {
      onSuccess(formatToIosCameraRollFormat(imageDataList));
    });
  },
};
