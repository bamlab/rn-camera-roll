import React, {
  Component,
  Image,
  Platform,
  PropTypes,
  ListView,
  View,
  Text,
} from 'react-native';
import CameraRoll from 'rn-camera-roll';

const styles = {
  container: {
    flex: 1,
    backgroundColor: '#F5FCFF',
  },
  imageGrid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'center',
  },
  image: {
    width: 100,
    height: 100,
    margin: 10,
  },
};

let PHOTOS_COUNT_BY_FETCH = 24;

export default class CameraRollGallery extends Component {

  constructor(props) {
    super(props);

    this.ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
    this.lastPhotoFetched = undefined; // Using `null` would crash ReactNative CameraRoll on iOS.
    this.images = [];
    this.state = this.getDataSourceState();
    this.fetchPhotos();
  }

  getDataSourceState() {
    return {
      dataSource: this.ds.cloneWithRows(this.images),
    };
  }

  getPhotosFromCameraRollData(data) {
    return data.edges.map((asset) => {
      return asset.node.image;
    });
  }

  onPhotosFetchedSuccess(data) {
    const newPhotos = this.getPhotosFromCameraRollData(data);
    console.log(data);
    this.images = this.images.concat(newPhotos);
    this.setState(this.getDataSourceState());
    if (newPhotos.length) this.lastPhotoFetched = newPhotos[newPhotos.length - 1].uri;
  }

  onPhotosFetchError(err) {
    // Handle error here
    console.log(err);
  }

  fetchPhotos(count = PHOTOS_COUNT_BY_FETCH, after) {
    CameraRoll.getPhotos({
      first: count,
      after,
    }, this.onPhotosFetchedSuccess.bind(this), this.onPhotosFetchError.bind(this));
  }

  onEndReached() {
    this.fetchPhotos(PHOTOS_COUNT_BY_FETCH, this.lastPhotoFetched);
  }

  render() {
    return (
      <View style={styles.container}>
        <ListView
          contentContainerStyle={styles.imageGrid}
          dataSource={this.state.dataSource}
          onEndReached={this.onEndReached.bind(this)}
          onEndReachedThreshold={100}
          showsVerticalScrollIndicator={false}
          renderRow={(image) => {return (
            <View>
              <Image
                style={styles.image}
                source={{ uri: image.uri }}
              />
            </View>
          )}}
        />
      </View>
    );
  }
}
