import React, { Fragment } from 'react';
import ChartContainer from '../../containers/statistics/ChartContainer';
import StatisticsFilterContainer from '../../containers/statistics/StatisticsFilterContainer';
import TableContainer from '../../containers/statistics/TableContainer';
import { Grid, withStyles } from '@material-ui/core';
import { Spinner } from 'reactstrap';
import { TransactionDirections } from '../../constants/transactions';

const StatisticsDashboard = props => {
  return (
    <Fragment>
      <h1>Statistics</h1>
      <Grid container direction="column">
        <Grid container direction="column" alignItems="center">
          <Grid item xs={12} style={{ minWidth: 200 }}>
            <StatisticsFilterContainer />
          </Grid>
        </Grid>
        <Grid container direction="row" justify="space-evenly" className="mt-4">
          <Grid item lg={6} xs={12} className={props.isLoading ? 'text-center' : ''}>
            {!props.isLoading ? (
              <ChartContainer direction={TransactionDirections.INCOMING.id} />
            ) : (
              <Spinner type="grow" color="primary" />
            )}
          </Grid>
          <Grid item lg={6} xs={12} className={props.isLoading ? 'text-center' : ''}>
            {!props.isLoading ? (
              <ChartContainer direction={TransactionDirections.OUTGOING.id} />
            ) : (
              <Spinner type="grow" color="primary" />
            )}
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
