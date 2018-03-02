import React from 'react';
import { StyleSheet, Text, View, Image, Button } from 'react-native';

class Bananas extends React.Component {
  constructor(props) {
    super(props);
    // this.state = {isShowingText: true};

    // Toggle the state every second
    // setInterval(() => {
    //   this.setState(previousState => {
    //     return { isShowingText: !previousState.isShowingText };
    //   });
    // }, 1000);

    this.styles = StyleSheet.create({
      bigblue: {
        color: 'blue',
        fontWeight: 'bold',
        fontSize: 30,
      },
      red: {
        color: 'red',
      },
    });
  }

  render() {
    // let pic = this.state.isShowingText ? {
    let pic = {
      uri: 'https://upload.wikimedia.org/wikipedia/commons/d/de/Bananavarieties.jpg'
    };
    let onPressLearnMore = () => console.log('wow');
    return (
      <View>
        <Image source={pic} style={{ width: 193, height: 110 }} />
        <Text style={this.styles.bigblue}>Hohoh</Text>
        <Button
          onPress={onPressLearnMore}
          title="Learn More"
          color="#841584"
          accessibilityLabel="Learn more about this purple button"
        />
      </View>
    );
  }
}


class MyFirstReactComp extends React.Component {
  render() {
    return <Text>{this.props.myName}</Text>;
  }
}

export default class App extends React.Component {
  render() {
    return (
      <View style={styles.container}>
        <Text>Unbelievable</Text>
        <MyFirstReactComp myName="woojin" />
        <Bananas />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
