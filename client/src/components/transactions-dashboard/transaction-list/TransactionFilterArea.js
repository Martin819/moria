import React, { Fragment } from 'react';
import TransactionFilter from './TransactionFilter';
import { Grid } from '@material-ui/core/';
import {
  FILTER_TIME_PERIOD,
  FILTER_DIRECTION,
  timePeriodFilters,
  directionFilters
} from '../../../constants/transactionListFilters';

const TransactionFilterArea = props => {
  const selectedTimePeriodFilter = timePeriodFilters.filter(timePeriodFilter => {
    return Object.values(props.filters).includes(timePeriodFilter.id);
  });
  const selectedDirectionFilter = directionFilters.filter(directionFilter => {
    return Object.values(props.filters).includes(directionFilter.id);
  });
  return (
    <Fragment>
      <Grid container spacing={16}>
        <Grid item md={6} xs={12}>
          <TransactionFilter
            label={FILTER_TIME_PERIOD.text}
            selectedFilter={selectedTimePeriodFilter[0] !== undefined ? selectedTimePeriodFilter[0].id : ''}
            filterOptions={timePeriodFilters}
            handleFilterChange={props.handleFilterChange}
          />
        </Grid>
        <Grid item md={6} xs={12}>
          <TransactionFilter
            label={FILTER_DIRECTION.text}
            selectedFilter={selectedDirectionFilter !== undefined ? selectedDirectionFilter[0].id : ''}
            filterOptions={directionFilters}
            handleFilterChange={props.handleFilterChange}
          />
        </Grid>
      </Grid>
    </Fragment>
  );
};

export default TransactionFilterArea;
