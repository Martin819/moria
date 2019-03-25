import React, { Fragment } from 'react';
import FilterSelect from '../../transactions-dashboard/transaction-list/FilterSelect';
import { Grid } from '@material-ui/core/';
import { FILTER_TIME_PERIOD, TimePeriodFilters } from '../../../constants/transactionListFilters';

const StatisticsFilterArea = props => {
  const selectedTimePeriodFilter = Object.values(TimePeriodFilters).find(
    filter => filter.id === props.filters[FILTER_TIME_PERIOD.id]
  );

  return (
    <Fragment>
      <Grid container spacing={16}>
        <Grid item md={12} xs={12}>
          <FilterSelect
            label={FILTER_TIME_PERIOD.text}
            filterType={FILTER_TIME_PERIOD.id}
            selectedFilter={selectedTimePeriodFilter !== undefined ? selectedTimePeriodFilter.id : ''}
            filterOptions={Object.values(TimePeriodFilters)}
            handleFilterChange={props.handleFilterChange}
          />
        </Grid>
      </Grid>
    </Fragment>
  );
};

export default StatisticsFilterArea;
