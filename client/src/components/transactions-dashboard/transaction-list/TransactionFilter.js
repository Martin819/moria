import React from 'react';
import { Form, FormGroup, Label, Input } from 'reactstrap';
import { NONE, LAST_WEEK, LAST_MONTH, LAST_SIX_MONTHS, LAST_YEAR } from '../../../constants/transactionListFilters';

const TransactionFilter = props => {
  console.log(props.selectedFilter);
  return (
    <div>
      <Form>
        <FormGroup>
          <Label for="transactionFilter">Filter transactions</Label>
          <Input
            type="select"
            name="transactionFilter"
            id="transactionFilter"
            value={props.selectedFilter || NONE.id}
            onChange={e => props.handleFilterChange(e)}
          >
            <option value={NONE.id}>{NONE.text}</option>
            <option value={LAST_WEEK.id}>{LAST_WEEK.text}</option>
            <option value={LAST_MONTH.id}>{LAST_MONTH.text}</option>
            <option value={LAST_SIX_MONTHS.id}>{LAST_SIX_MONTHS.text}</option>
            <option value={LAST_YEAR.id}>{LAST_YEAR.text}</option>
          </Input>
        </FormGroup>
      </Form>
    </div>
  );
};

export default TransactionFilter;