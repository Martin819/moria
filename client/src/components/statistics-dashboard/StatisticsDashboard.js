import React, { Fragment } from 'react';
import ChartContainer from '../../containers/statistics/ChartContainer';
import TransactionFilterContainer from '../../containers/transactions/TransactionFilterContainer';
import { Grid, withStyles } from '@material-ui/core';
import { TransactionDirections } from '../../constants/transactions';
import StatisticsTable from './tabular-section/StatisticsTable';
import TableContainer from '../../containers/statistics/TableContainer';

const StatisticsDashboard = props => {
  return (
    <Fragment>
      <h1>Statistics</h1>
      <Grid container direction="column">
        <Grid container direction="column" alignItems="center">
          <Grid item xs={12} style={{ minWidth: 200 }}>
            <TransactionFilterContainer />
          </Grid>
        </Grid>
        <Grid container direction="row" justify="space-evenly" className="mt-4">
          <Grid item lg={6} xs={12} className="text-center">
            <ChartContainer direction={TransactionDirections.INCOMING.id} />
          </Grid>
          <Grid item lg={6} xs={12} className="text-center">
            <ChartContainer direction={TransactionDirections.OUTGOING.id} />
          </Grid>
        </Grid>
      </Grid>
      <Grid container direction="row" justify="space-evenly" className="mt-4">
        <Grid item lg={6} xs={12} className="p-2">
          <TableContainer direction={TransactionDirections.INCOMING.id} />
        </Grid>
        <Grid item lg={6} xs={12} className="p-2">
          <TableContainer direction={TransactionDirections.OUTGOING.id} />
        </Grid>
      </Grid>
    </Fragment>
  );
};

const styles = {
  border: {
    width: 1,
    height: 300,
    borderStyle: 'solid',
    borderWidth: '0px 1px 0px 0px'
  }
};

export default withStyles(styles)(StatisticsDashboard);
