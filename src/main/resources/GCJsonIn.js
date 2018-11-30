import React, { Component } from 'react'
import gcJson from './gcJson.json'

class GCJsonIn extends Component {
  state = {
    gctype: null,
    gctime: null,
  }

  // handleSubmit = (e) => {
  //   e.preventDefault();
  //   this.props.addGC(this.state);
  // }

  render() {
    return (
      //I am just returning these within this, and not using the add json that I have in the app.js
      //I would prefer to use that addGC from the app .js
      <div>{
        gcJson.map(function(gc){
        return <li>{gc.gctype} - {gc.gctime}</li>;
        })}
      </div>
    )
  }
}

export default GCJsonIn;