import React from 'react';
import { withStyles } from '@material-ui/core/styles';
import { Card, CardContent, Typography, Grow } from '@material-ui/core';

const AccountBalanceInfo = props => {
  const { classes } = props;
  const balance = props.balance || 0;
  return (
    <div>
      <h2>Accounts</h2>
      <Grow in={true} timeout={500}>
        <Card className={classes.card}>
          <CardContent>
            <Typography className={classes.title} color="textSecondary" gutterBottom>
              Account info:
            </Typography>
            <Typography className={classes.pos} component="p">
              717717717/0800
              <br />
              John Doe
            </Typography>
            <Typography className={classes.title} color="textSecondary" gutterBottom>
              Account balance:
            </Typography>
            <Typography variant="h5" component="h2">
              {balance.toLocaleString('cs-cz', { style: 'currency', currency: 'CZK' })}
            </Typography>
          </CardContent>
        </Card>
      </Grow>
    </div>
  );
};

const styles = {
  card: {
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
