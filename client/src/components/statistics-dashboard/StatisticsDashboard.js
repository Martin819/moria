import React, { Fragment } from 'react';
import TableContainer from '../../containers/statistics/TableContainer';
import { Grid, withStyles } from '@material-ui/core';
import { TransactionDirections } from '../../constants/transactions';
import PieChartArea from './chart-section/PieChartArea';
import CustomBarChart from './chart-section/CustomBarChart';

const StatisticsDashboard = props => {
  return (
    <Fragment>
      <h1>Statistics</h1>
      <Grid container direction="column">
        <Grid container item xs={12} style={{ minWidth: 200 }} justify="center">
          <h3>Income / Expense last 12 months</h3>
          {props.barChartData.length > 0 && !props.isLoading ? (
            <CustomBarChart barChartData={props.barChartData} />
          ) : null}
        </Grid>

        <PieChartArea isLoading={props.isLoading} />

        <Grid container direction="row" justify="space-evenly" className="mt-4">
          <Grid item lg={6} xs={12} className="p-2">
            <TableContainer direction={TransactionDirections.INCOMING.id} />
          </Grid>
          <Grid item lg={6} xs={12} className="p-2">
            <TableContainer direction={TransactionDirections.OUTGOING.id} />
          </Grid>
        </Grid>
        <Grid container direction="column" alignItems="center" />
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
