import React, { Fragment } from 'react';
import ChartContainer from '../../../containers/statistics/ChartContainer';
import StatisticsFilterContainer from '../../../containers/statistics/StatisticsFilterContainer';
import { Grid } from '@material-ui/core';
import { Spinner } from 'reactstrap';
import { TransactionDirections } from '../../../constants/transactions';

const PieChartArea = props => {
  const { isLoading } = props;
  return (
    <Fragment>
      <Grid container direction="column" alignItems="center" justify="center">
        <h3>Category overview</h3>
        <Grid item xs={12} style={{ minWidth: 200 }}>
          <StatisticsFilterContainer />
        </Grid>
      </Grid>

      <Grid container direction="row" justify="space-between" className="mt-4">
        <Grid item lg={6} xs={12} className={isLoading ? 'text-center' : ''}>
          {!isLoading ? (
            <ChartContainer direction={TransactionDirections.INCOMING.id} />
          ) : (
            <Spinner type="grow" color="primary" />
          )}
        </Grid>
        <Grid item lg={6} xs={12} className={isLoading ? 'text-center' : ''}>
          {!isLoading ? (
            <ChartContainer direction={TransactionDirections.OUTGOING.id} />
          ) : (
            <Spinner type="grow" color="primary" />
          )}
        </Grid>
      </Grid>
    </Fragment>
  );
};

export default PieChartArea;
