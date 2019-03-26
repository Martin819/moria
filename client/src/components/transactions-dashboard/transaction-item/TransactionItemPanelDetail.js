import React, { Fragment } from 'react';
import { ExpansionPanelDetails, Grid, Typography } from '@material-ui/core';

const TransactionItemPanelDetail = props => {
  const { detail } = props;
  return (
    <ExpansionPanelDetails>
      <Grid container direction="column">
        <Grid item>
          <hr />
          {Object.entries(detail).map((it, i) => (
            <Fragment key={i}>
              <Typography inline variant="overline">
                {it[0]}:{' '}
              </Typography>
              <Typography inline>{it[1]}</Typography>
              <br />
            </Fragment>
          ))}
        </Grid>
        <Grid item />
      </Grid>
    </ExpansionPanelDetails>
  );
};

export default TransactionItemPanelDetail;
