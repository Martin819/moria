import React, { Fragment } from 'react';
import FilterSelect from './FilterSelect';
import { Grid } from '@material-ui/core/';
import {
  FILTER_TIME_PERIOD,
  FILTER_DIRECTION,
  TimePeriodFilters,
  DirectionFilters
} from '../../../constants/transactionListFilters';

const TransactionFilterArea = props => {
  const selectedTimePeriodFilter = Object.values(TimePeriodFilters).find(
    filter => filter.id === props.filters[FILTER_TIME_PERIOD.id]
  );
  const selectedDirectionFilter = Object.values(DirectionFilters).find(
    filter => filter.id === props.filters[FILTER_DIRECTION.id]
  );

  return (
    <Fragment>
      <Grid container spacing={16}>
        <Grid item md={6} xs={12}>
          <FilterSelect
            label={FILTER_TIME_PERIOD.text}
            filterType={FILTER_TIME_PERIOD.id}
            selectedFilter={selectedTimePeriodFilter !== undefined ? selectedTimePeriodFilter.id : ''}
            filterOptions={Object.values(TimePeriodFilters)}
            handleFilterChange={props.handleFilterChange}
          />
        </Grid>
        <Grid item md={6} xs={12}>
          <FilterSelect
            label={FILTER_DIRECTION.text}
            filterType={FILTER_DIRECTION.id}
            selectedFilter={selectedDirectionFilter !== undefined ? selectedDirectionFilter.id : ''}
            filterOptions={Object.values(DirectionFilters)}
            handleFilterChange={props.handleFilterChange}
          />
        </Grid>
      </Grid>
    </Fragment>
  );
};

export default TransactionFilterArea;
