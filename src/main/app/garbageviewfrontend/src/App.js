import React, { Component } from 'react';
import './App.css';
import GC from './GC.js'
import LineChart from './LineChart';

class App extends Component {

  state = {
    gcs: [
      {gctype: 'test1', gctime: 25, id: 0}
    ],
    times: [25, 15,100, 23]
  }

  constructor(props) {
    super(props);
    // this.connect();

    console.log("entering connect method in App.js");
    var ws = new WebSocket('ws://' + window.location.host + '/garbageview'); //http://localhost:8080/garbageview
    console.log("in constructor");
    console.log(window.location.host);
    ws.onopen = function(){
      console.log("connected");
      console.log(window.location.host);
    }
    ws.onmessage = function(data){
        // showJSON(data.data);
        // this.showJSON();
        console.log("in On message");
    }
    ws.onerror = function(){
      console.log("ERROR WITH WS CONNECTION");
    };
    ws.onclose = this.disconnect;

  }

  componentDidMount() {
    console.log("in comp did mount.----")
  }

  // connect() {
  //   console.log("entering connect method in App.js");
  //   this.ws = new WebSocket('ws://' + window.location.host + '/garbageview'); //http://localhost:8080/garbageview
  //   console.log(window.location.host);
  //   this.ws.onopen = function(){
  //     console.log("connected");
  //   }
  //   this.ws.onmessage = function(data){
  //       // showJSON(data.data);
  //       this.showJSON();
  //   }
  //   this.ws.onerror = function(){
  //     console.log("ERROR WITH WS CONNECTION");
  //   };
  //   this.ws.onclose = this.disconnect;
  // }

  // disconnect() {
  //     if (this.ws != null) {
  //       this.ws.close();
  //     }
  //     // setConnected(false);
  //     console.log("Disconnected");
  // }

  // function showJSON(message) {
  //     $("#greetings").append(" " + message + "");
  // }
  // showJSON() {
  //   message = "hello!";
  //   $("#greetings").append(" " + message + "");
  // }

  addGC = (gc) => {
    let gcsCopy = [...this.state.gcs, gc]
    this.setState({
      gcs: gcsCopy
    })
    console.log('gcs: ', this.state.gcs);
    console.log('times: ', this.state.times)
  }

  getSum = (total, num) => { return total + num; }

  getTime = () => {
    if(this.state.gcs.length > 0) {
      this.state.times.push(this.state.gcs[this.state.gcs.length-1].gctime)
    }
    console.log('times: ', this.state.times)
    return this.state.times.reduce(this.getSum, 0)
  }

  /**
   * Counts the number of JSON entries in the gcs array
   * This is the number for GC events that the server has sent over to the React socket
   */
  countGC = () => { return this.state.gcs.length; }
  
  render() {
    return (
      <div className="App">
        <h1>Welcome to GarbageView</h1>
        <p>Number of Garbage Collectors: {this.countGC()}</p>
        <p>Total time of all GC events: {this.getTime()}</p>
        <p>List of Garbage Collectors:</p>
        <GC gcs = {this.state.gcs}/>
        <LineChart times = {this.state.times}/>
      </div>
    );
  }
}

export default App;

  // addTime = (time) => {
  //   console.log('in addTime!!!!!!!!!!')
  //   let timesCopy = [...this.state.times, time]
  //   this.setState({
  //     times: timesCopy
  //   })
  //   console.log('times: ', this.state.times)
  // }