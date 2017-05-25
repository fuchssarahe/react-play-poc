import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {
  constructor() {
    super()

    this.state = { data: "" };
  }

  componentDidMount() {
    fetch('/api/test', {
      accept: 'application/json',
    })
    .then(resp => resp.json())
    .then(data => {
      console.log(data);
      this.setState({ data: data.body });
    })
  }

  render() {
    return (
      <div className="App">
        <div className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h2>Welcome to React</h2>
        </div>
        <p className="App-intro">
          {this.state.data}
        </p>
      </div>
    );
  }
}

export default App;
