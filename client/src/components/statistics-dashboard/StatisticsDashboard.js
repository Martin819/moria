import React, { Fragment } from 'react';
import ChartContainer from '../../containers/chart/ChartContainer';
import TransactionFilterContainer from '../../containers/transactions/TransactionFilterContainer';
import { Grid, withStyles } from '@material-ui/core';

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
            <ChartContainer />
          </Grid>
          <Grid item lg={6} xs={12} className="text-center">
            <ChartContainer />
          </Grid>
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
