import React, { Component } from 'react';
import './App.css';

class App extends Component {
  constructor() {
    super()

    this.state = { data: [], newMessage: "", scalaMessage: "initial message" };
  }

  componentDidMount() {
    this.scalaConnection = new WebSocket('ws://localhost:9000/api/ws')
    this.scalaConnection.onopen = _ => console.log("connection has opened");
    console.log(this.scalaConnection.url);
    this.scalaConnection.onmessage = e => {
      this.setState({ scalaMessage: e.data })
    };

    // for testing: sending a message to the backend every 2 seconds,
    // the service sends it right back
    setInterval(_ => {
      console.log("sent a message");
      this.scalaConnection.send(Math.random());
    }, 2000)
  }

  handleSubmit(e) {
    e.preventDefault()
    this.connection.send()
  }

  render() {
    return (
      <div className="App">
        <div className="App-header">
          <h2>React/Play WebSocket POC</h2>
          <h2>{this.state.scalaMessage}</h2>
        </div>
        <p className="App-body">The React app is sending a message to Play via a WebSocket connection every 2 seconds. Upon receiving the message, Play is echoing the message back. The most recent message received from Play is displayed in the header above.</p>
        <form>
          <input onChange={e => this.setState({ newMessage: e.target })} value={this.state.newMessage}/ >
          <button onClick={this.handleSubmit}>Send Message via HTTP</button>
        </form>
      </div>
    );
  }
}

export default App;
