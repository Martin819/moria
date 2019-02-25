import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import TransactionItem from '../transaction-item/TransactionItem';
import { Spinner } from 'reactstrap';
import TransactionFilterContainer from '../../../containers/transactions/TransactionFilterContainer';

const TransactionsList = props => {
  const { classes, transactions, isLoading } = props;
  const transactionItems = transactions.map(transaction => {
    return <TransactionItem key={transaction.id} {...transaction} />;
  });
  return (
    <div className={classes.root}>
      <h2>Transactions</h2>
      <TransactionFilterContainer />
      {isLoading ? (
        <div className="text-center">
          <Spinner type="grow" color="primary" />
        </div>
      ) : (
        transactionItems
      )}
    </div>
  );
};

TransactionsList.propTypes = {
  classes: PropTypes.object.isRequired
};

const styles = theme => ({
  root: {
    width: '55%',
    marginTop: '20px'
  },
  spinner: {
    maxWidth: '100%',
    margin: 'auto'
  }
});

export default withStyles(styles)(TransactionsList);
