import React, { Component } from 'react';
import './App.css';
import GC from './GC.js'
import LineChart from './LineChart';

class App extends Component {

  state = {
    gcs: [
      {GCType: 'test1', GCTime: 25, Id: 0} //{"GCType":"Young Gen GC","GCTime":623,"Id":16}
    ],
    times: []
  }

  constructor(props) { //-----
    super(props);
    
    //connect to websocket
    console.log("entering connect method in App.js");
    console.log('this1:' + this);
    var ws = new WebSocket('ws://' + window.location.host + '/garbageview'); //http://localhost:8080/garbageview
    console.log("in constructor");
    console.log(window.location.host);
    ws.onopen = function(){
      console.log("connected");
      console.log(window.location.host);
    }
    console.log('this2:' + this);
    ws.onmessage = function(data){
        console.log("data" + data.data);
        var jsonObjectFromSocket = JSON.parse(data.data);
        console.log("jofs = " + data.data);
        console.log(JSON.parse(data.data));
        console.log('at addGC ****');
        console.log('this3:' + this);
        this.addGC(jsonObjectFromSocket);
        console.log("in On message");
    }.bind(this);
    ws.onerror = function(){
      console.log("ERROR WITH WS CONNECTION");
    };
    ws.onclose = this.disconnect;
  } //-----

  componentDidMount() {
    console.log("in comp did mount.----");
  }

  addGC = (gc) => {
    console.log('in addGC ****');
    try{
      let gcsCopy = [...this.state.gcs, gc]
      this.setState({
        gcs: gcsCopy
      })
    }
    catch(err) {
      console.log(err.message);
    }
    console.log('gcs: ', this.state.gcs);
    console.log('times: ', this.state.times)
  }

  getSum = (total, num) => { return total + num; }

  getTime = () => {
    if(this.state.gcs.length > 0) {
      this.state.times.push(this.state.gcs[this.state.gcs.length-1].GCTime)
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