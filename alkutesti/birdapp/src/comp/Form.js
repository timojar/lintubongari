import React, { Component } from 'react';





class Input extends Component {

    state = { value: '', event: '' };




    async handleClick(e) {

        const body = JSON.stringify(this.state.value);
        await fetch('http://localhost:8080/newbird', { method: 'POST', body })
    
        this.props.callback();
       
      }


    render() {
        return (
            <div>
                <label>Uusi laji</label>
                <br />
                <input  value={this.state.value} onChange={e => { this.setState({ value: e.target.value }) }} type="text" />
                <br />
                <button onClick={() => this.handleClick()}>Lisää uusi lintulaji</button>
            </div>);
    }

}







export default Input;
