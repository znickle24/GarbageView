import React, { Component } from 'react';
import './App.css';
import GC from './GC.js'
import LineChartOverheads from './LineChartOverheads';
import LineChartTimes from './LineChartTimes';

class App extends Component {

  state = {//{GCType: 'test1', GCTime: 25, Id: 0}
    gcs: [],
    times: [],
    overheads: []
  }

  constructor(props) {
    super(props);
    //connect to websocket
    var ws = new WebSocket('ws://' + window.location.host + '/garbageview'); //http://localhost:8080/garbageview
    ws.onopen = function(){
      console.log("connected");
    }
    ws.onmessage = function(data){
      console.log("in onMessage");
      var jsonObjectFromSocket = JSON.parse(data.data);
      this.addGC(jsonObjectFromSocket);
    }.bind(this);
    ws.onerror = function(){
      console.log("ERROR WITH WS CONNECTION");
    };
    ws.onclose = this.disconnect;
  }

  componentDidMount() {
    console.log("in comp did mount.----");
  }

  addGC = (gc) => {
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
    console.log('overheads: ', this.state.overheads)
  }

  /**
   * Counts the number of JSON entries in the gcs array
   * This is the number for GC events that the server has sent over to the React socket
   */
  countGC = () => { return this.state.gcs.length; }

  getSum = (total, num) => { return total + num; }

  getTime = () => {
    if(this.state.gcs.length > 0) {
      this.state.times.push(this.state.gcs[this.state.gcs.length-1].GCTime)
    }
    console.log('times: ', this.state.times)
    return this.state.times.reduce(this.getSum, 0)
  }

  getOverhead = () => {
    if(this.state.gcs.length > 0) {
      this.state.overheads.push(this.state.gcs[this.state.gcs.length-1].GCOverhead);
    }
    console.log('overheads: ', this.state.overheads);
    var totalOverhead = this.state.overheads.reduce(this.getSum, 0);
    console.log("totes over" + totalOverhead);
    var count = this.state.overheads.length;
    console.log("coutnsss" + count);
    var avg = totalOverhead / count;
    console.log("she avg." + avg);
    return avg.toFixed(2) * 1;
  }
  
  render() {
    return (
      <div className="App">
        <h1>Welcome to GarbageView</h1>
        <p>Number of Garbage Collection Events: {this.countGC()}</p>
        <p>Total time of all GC events: {this.getTime()} ms</p>
        <p>List of Garbage Collectors:</p>
        <GC gcs = {this.state.gcs}/>
        <LineChartTimes times = {this.state.times}/>
        <p>Average percent overhead: {this.getOverhead()}</p>
        <LineChartOverheads overheads = {this.state.overheads}/>
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