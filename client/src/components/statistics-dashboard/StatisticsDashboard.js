import React, { Fragment } from 'react';
import TableContainer from '../../containers/statistics/TableContainer';
import { Grid, withStyles } from '@material-ui/core';
import { TransactionDirections } from '../../constants/transactions';
import CustomBarChart from './chart-section/CustomBarChart';
import CategoryOverviewAreaContainer from '../../containers/statistics/CategoryOverviewAreaContainer';

const StatisticsDashboard = props => {
  return (
    <Fragment>
      <h1>Statistics</h1>
      <Grid container direction="column">
        <Grid container item xs={12} style={{ minWidth: 200 }} justify="center">
          {props.barChartData.length > 0 && !props.isLoading ? (
            <Fragment>
              <h3>Incomes / Expenses last 12 months</h3>
              <CustomBarChart barChartData={props.barChartData} />
            </Fragment>
          ) : null}
        </Grid>
        <Grid item xs={12} className="mt-4">
          <CategoryOverviewAreaContainer />
        </Grid>

        <Grid container direction="row" justify="space-evenly" className="mt-5">
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
