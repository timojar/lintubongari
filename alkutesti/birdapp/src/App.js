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
        
        <div id="stamps">
          <h2>Havainnot:</h2>
          {this.state.stamps.map((s, i) => (
            <Stamp key={s.time.getTime()} stamp={s} />
          ))}
        </div>
        
        <div class="birds">
          {this.state.birds.map(b => (
            <Bird callback={() => this.callback()} key={b.id} bird={b} />
          ))}</div>
          
        <Input callback={() => this.callback()}  />
      </div>
    );
  }

  async callback() {  
    const hello = 'hello';    
    const result = await fetch('http://localhost:8080/test/', { method: 'GET', }, );
    const birds = await result.json();
    const stamps = this.sortStamps(birds);
    this.setState({ birds, stamps });
  }


  async componentDidMount() {
    const result = await fetch('http://localhost:8080/test/', { method: 'GET', }, );
    const birds = await result.json();
    const stamps = this.sortStamps(birds)
    this.setState({ birds, stamps });
  }

  sortStamps(birds) {
    const stamps = [];
    birds.forEach(bird => {
      bird.birdTime.forEach(timeStampStr => {
        stamps.push({
          time: new Date(timeStampStr),
          name: bird.name,
          id: stamps.length + 1
        });
      });
    });
    stamps.sort((a, b) => a.time - b.time);

    return stamps;
  }



}

export default App;
