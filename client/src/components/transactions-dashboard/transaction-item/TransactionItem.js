import React, { Fragment } from 'react';
import PropTypes from 'prop-types';
import moment from 'moment';
import { withStyles, ExpansionPanel, ExpansionPanelSummary, Typography, Grid, Grow } from '@material-ui/core/';
import { Badge } from 'reactstrap';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import TransactionItemPanelDetail from './TransactionItemPanelDetail';

const TransactionItem = props => {
  const { classes, id, partyDescription, valueDate, transactionType, value, message, category, direction } = props;
  return (
    <Fragment>
      <Grow in={true} timeout={500}>
        <ExpansionPanel>
          <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
            <Grid container direction="row" spacing={24}>
              <Grid item>
                <Typography className={classes.date}>{moment(valueDate).format('DD MMM YYYY')}</Typography>
              </Grid>
              <Grid item xs>
                <Grid container justify="space-between" alignItems="center">
                  <Grid item>
                    <Badge color="warning" pill>
                      {category}
                    </Badge>
                    <Typography className={classes.heading}>{partyDescription}</Typography>
                    <Typography>
                      <span className={classes.secondaryHeading}>{transactionType}</span>
                    </Typography>
                  </Grid>
                  <Grid item>
                    {direction === 'INCOMING' ? (
                      <Typography className={classes.amountPositive}>
                        {value.amount.toLocaleString('cs-cz', { style: 'currency', currency: value.currency })}
                      </Typography>
                    ) : (
                      <Typography className={classes.amountNegative}>
                        {'- ' + value.amount.toLocaleString('cs-cz', { style: 'currency', currency: value.currency })}
                      </Typography>
                    )}
                  </Grid>
                </Grid>
              </Grid>
            </Grid>
          </ExpansionPanelSummary>
          <TransactionItemPanelDetail
            message={message}
            id={id}
            category={category}
            handleTransactionCategorySubmit={props.handleTransactionCategorySubmit}
          />
        </ExpansionPanel>
      </Grow>
    </Fragment>
  );
};

TransactionItem.propTypes = {};

const styles = theme => ({
  heading: {
    fontSize: theme.typography.pxToRem(15),
    fontWeight: 600,
    marginTop: -2
  },
  secondaryHeading: {
    fontSize: theme.typography.pxToRem(15),
    fontWeight: 100
  },
  date: {
    fontSize: theme.typography.pxToRem(15),
    paddingTop: 3
  },
  amountPositive: {
    fontSize: theme.typography.pxToRem(18),
    color: 'green',
    fontWeight: 600
  },
  amountNegative: {
    fontSize: theme.typography.pxToRem(18),
    color: 'red',
    fontWeight: 600
  }
});

export default withStyles(styles)(TransactionItem);
