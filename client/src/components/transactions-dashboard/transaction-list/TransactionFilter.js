import React, { Fragment } from 'react';
import { Form, FormGroup, Label, Input } from 'reactstrap';
import { ALL_TIME, LAST_WEEK, LAST_MONTH, LAST_SIX_MONTHS, LAST_YEAR } from '../../../constants/transactionListFilters';

const TransactionFilter = props => {
  return (
    <Fragment>
      <Label for="transactionFilter">{props.label}</Label>
      <Input
        type="select"
        name="transactionFilter"
        id="transactionFilter"
        value={props.selectedFilter || ALL_TIME.id}
        onChange={e => props.handleFilterChange(e)}
      >
        <option value={ALL_TIME.id}>{ALL_TIME.text}</option>
        <option value={LAST_WEEK.id}>{LAST_WEEK.text}</option>
        <option value={LAST_MONTH.id}>{LAST_MONTH.text}</option>
        <option value={LAST_SIX_MONTHS.id}>{LAST_SIX_MONTHS.text}</option>
        <option value={LAST_YEAR.id}>{LAST_YEAR.text}</option>
      </Input>
    </Fragment>
  );
};

export default TransactionFilter;
