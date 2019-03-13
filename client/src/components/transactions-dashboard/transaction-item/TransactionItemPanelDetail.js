import React from 'react';
import { ExpansionPanelDetails, Grid, Typography } from '@material-ui/core';

const TransactionItemPanelDetail = props => {
  const { message, id } = props;
  return (
    <ExpansionPanelDetails>
      <Grid container direction="column">
        <Grid item>
          <hr />
          <Typography>
            Message: {message} <br /> ID: {id}
          </Typography>
        </Grid>
        <Grid item />
      </Grid>
    </ExpansionPanelDetails>
  );
};

export default TransactionItemPanelDetail;
