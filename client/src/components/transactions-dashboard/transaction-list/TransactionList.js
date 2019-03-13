import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import { Grid } from '@material-ui/core/';
import TransactionItem from '../transaction-item/TransactionItem';
import { Spinner } from 'reactstrap';
import TransactionFilterContainer from '../../../containers/transactions/TransactionFilterContainer';

const TransactionsList = props => {
  const { classes, transactions, isLoading } = props;
  const transactionItems = transactions.map(transaction => {
    return (
      <TransactionItem
        key={transaction.id}
        {...transaction}
        handleTransactionCategorySubmit={props.handleTransactionCategorySubmit}
      />
    );
  });

  return (
    <div className={classes.root}>
      <h2>Transactions</h2>
      <Grid container direction="column" spacing={8}>
        <Grid item xs>
          <TransactionFilterContainer />
        </Grid>
        <Grid item xs>
          {isLoading ? (
            <div className="text-center">
              <Spinner type="grow" color="primary" />
            </div>
          ) : (
            transactionItems
          )}
        </Grid>
      </Grid>
    </div>
  );
};

TransactionsList.propTypes = {
  classes: PropTypes.object.isRequired
};

const styles = theme => ({
  root: {
    width: '100%',
    marginTop: '20px',
    marginBottom: '70px'
  }
});

export default withStyles(styles)(TransactionsList);
