import React, { Fragment } from 'react';
import { Label, Input } from 'reactstrap';

const FilterSelect = props => {
  return (
    <Fragment>
      <Label for="transactionFilter">{props.label}</Label>
      <Input
        type="select"
        name="transactionFilter"
        id="transactionFilter"
        value={props.selectedFilter}
        onChange={e => props.handleFilterChange(e.target.value, props.filterType)}
      >
        {props.filterOptions.map(fo => {
          return (
            <option key={fo.id} value={fo.id}>
              {fo.text}
            </option>
          );
        })}
      </Input>
    </Fragment>
  );
};

export default FilterSelect;
