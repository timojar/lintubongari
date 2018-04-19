import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import Input from '../src/comp/Form';



class Stamp extends Component {



  render() {

    const { name, time } = this.props.stamp;
    return (
      <div>
        <label>{time.toString()} bird: <span class="bird">{name.toString()} </span> </label>
        <br />
      </div>);
  }
}



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
  state = { birds: [], stamps: [], callback: '' };
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1 className="App-title">Welcome to React</h1>
        </header>
        <div id="stamps">
          <h2>Havainnot:</h2>
          {this.state.stamps.map(s => (
            <Stamp key={s.id} stamp={s} />
          ))}
        </div>
        <p className="App-intro">
          To get started, edit <code>src/App.js</code> and save to reload.
        </p>
        <div class="birds">
          {this.state.birds.map(b => (
            <Bird callback={() => this.callback()} key={b.id} bird={b} />
          ))}</div>

        <Input callback={() => this.callback()}  />

      </div>
    );
  }

  async callback() {
    const result = await fetch('http://localhost:8080/test/', { method: 'GET', }, );
    const data = await result.json();
    console.log('data' + data);
    this.setState({ birds: data });
    const stamps = this.sortStamps(this.state.birds);
    this.setState({ stamps: stamps });
    console.log(this.state.stamps);

  }


  async componentDidMount() {
    const result = await fetch('http://localhost:8080/test/', { method: 'GET', }, );
    const body = await result.json();
    this.setState({ birds: body });
    const stamps = this.sortStamps(this.state.birds)
    this.setState({ stamps: stamps });
    console.log(this.state.stamps);


  }

  sortStamps(m) {
    let stamps = [];
    m.forEach((x) => {
      x.birdTime.forEach((k) => {
        stamps.push({ time: new Date(k), name: x.name, id: k.length })
      });
    });
    stamps.sort((a, b) => a.time - b.time);

    return stamps;
  }



}

export default App;
