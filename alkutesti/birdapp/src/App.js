import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';



class Bird extends Component {

  state = { name: '' };

  async handleClick() {
    console.log('Tämä lintu on: ', this.props.bird.name, ' sen tunnus on: ', this.props.bird.id, " ja niitä on havaittu: ", this.props.bird.birdTime);
    const { bird } = this.props;
    const body = JSON.stringify(bird);
    await fetch('http://localhost:8080/new', { method: 'POST', body })
    this.props.callback();

  }

  render() {
    const c = this.props.callback;
    const { name, id, quantity, birdTime } = this.props.bird;
    return (
      <div>
        <label><span>{name} </span></label>
        <br />
        <label>lkm: <span>{birdTime.length}</span></label>
        <br />
        <button onClick={() => this.handleClick()}>
          Click me
      </button>
        <br />
        <br />
      </div>);
  }
}

class App extends Component {
  state = { birds: [], callback: '' };
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
          <Bird callback={() => this.callback()} key={b.id} bird={b} />
        ))}
      </div>
    );
  }

  async callback() {
    const result = await fetch('http://localhost:8080/test/', { method: 'GET', }, );
    const data = await result.json();
    console.log('data' + data);
    console.log(data);
    this.setState({ birds: data });


  }


  async componentDidMount() {
    const result = await fetch('http://localhost:8080/test/', { method: 'GET', }, );
    const body = await result.json();
    console.log(body)
    this.setState({ birds: body });


  }
}

export default App;
