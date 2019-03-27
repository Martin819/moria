import React from 'react';
import { withStyles } from '@material-ui/core/styles';
import { Card, CardContent, Typography, Grow } from '@material-ui/core';

const AccountBalanceInfo = props => {
  const { classes, accountInfo } = props;
  const { accountNumber, accountOwner, accountPreferredColor } = accountInfo;
  const balance = accountInfo.balance || 0;
  return (
    <div>
      <Card className={classes.card} style={{ borderLeft: `6px solid ${accountPreferredColor}` }}>
        <CardContent>
          <Typography className={classes.title} color="textSecondary" gutterBottom>
            Account info:
          </Typography>
          <Typography className={classes.pos} component="p">
            {accountNumber}
            <br />
            {accountOwner}
          </Typography>
          <Typography className={classes.title} color="textSecondary" gutterBottom>
            Account balance:
          </Typography>
          <Typography variant="h5" component="h2">
            {balance.toLocaleString('cs-cz', { style: 'currency', currency: 'CZK' })}
          </Typography>
        </CardContent>
      </Card>
    </div>
  );
};

const styles = {
  card: {
    minWidth: 220,
    maxWidth: 275
  },
  bullet: {
    display: 'inline-block',
    margin: '0 2px',
    transform: 'scale(0.8)'
  },
  title: {
    fontSize: 14
  },
  pos: {
    marginBottom: 12
  }
};

export default withStyles(styles)(AccountBalanceInfo);
