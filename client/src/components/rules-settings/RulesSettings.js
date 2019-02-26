import React, { Component } from 'react';
import RulesTable from './rules-table/RulesTable';

export default class RulesSettings extends Component {
  render() {
    return (
      <div>
        <h1>Rules</h1>
        <RulesTable />
      </div>
    );
  }
}
