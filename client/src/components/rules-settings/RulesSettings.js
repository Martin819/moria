import React, { Component } from 'react';
import RulesAreaContainer from '../../containers/rules/RulesAreaContainer';

export default class RulesSettings extends Component {
  render() {
    return (
      <div>
        <h1>Rules</h1>
        <RulesAreaContainer />
      </div>
    );
  }
}
