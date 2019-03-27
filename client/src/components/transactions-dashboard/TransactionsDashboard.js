import React, { Fragment } from 'react';
import TransactionListContainer from '../../containers/transactions/TransactionListContainer';
import AccountsContainer from '../../containers/transactions/AccountsContainer';

const TransactionsDashboard = () => {
  return (
    <Fragment>
      <AccountsContainer />
      <TransactionListContainer />
    </Fragment>
  );
};

export default TransactionsDashboard;
