import React, { Component } from 'react';
import './App.css';
import GC from './GC.js'
import { subscribeToTimer } from './api';
import LineChart from './LineChart';
// import NetPerformance from './NetPerformance';
// import NetPerformance2 from './NetPerformance2';
// import TimePerformance from './TimePerformance';

class App extends Component {
  state = {
    gcs: [
      {gctype: 'test1', gctime: 25, id: 0}
    ],
    times: [
    ]
  }

  constructor(props) {
    super(props);
  
    subscribeToTimer((err, gc) => 
      this.addGC(gc));
  }

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