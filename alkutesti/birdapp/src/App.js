import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';



class Bird extends Component {

  state = { birdId: 0, value: 0, lkm: 0 };

  handleChange = (e) => {
    this.setState({ value: e.target.value, lkm: e.target.value });

  }

  setId = (i) => {
    this.setState({ value: i.target.value, birdId: i.target.value, })
    console.log(this.state.value);
  }


  render() {

    const { name, id } = this.props.bird;
    return (
      <div>
        <label><span>{name}</span></label>
        <br />
        <input name="birdId" value={id} type="hidden" onChange={this.setId} />
        <input value={this.state.value} onChange={this.handleChange} type="number" />
      </div>);
  }
}

class App extends Component {
  state = { birds: [] };
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1 className="App-title">Welcome to React</h1>
        </header>
        <p className="App-intro">
          To get started, edit <code>src/App.js</code> and save to reload.
        </p>
        {this.state.birds.map(b => (
          <Bird key={b.id} bird={b} />
        ))}
      </div>
    );
  }

  async componentDidMount() {
    const result = await fetch('http://localhost:8080/test', { method: 'GET', }, );
    const body = await result.json();
    this.setState({ birds: body });
    console.log(body)
  }
}

export default App;
