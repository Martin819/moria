import React, { Component, Fragment } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { getData } from '../../actions/transactionActions';
import TransactionList from '../../components/transactions-dashboard/transaction-list/TransactionList';
import AccountBalanceInfo from '../../components/transactions-dashboard/account-balance-info/AccountBalanceInfo';

class TransactionListContainer extends Component {
  componentDidMount() {
    this.props.getData();
  }

  render() {
    return (
      <Fragment>
        <AccountBalanceInfo />
        <TransactionList {...this.props} />
      </Fragment>
    );
  }
}

const mapStateToProps = state => ({
  transactions: state.transactions.visibleTransactions,
  isLoading: state.transactions.loading
});

export default connect(
  mapStateToProps,
  { getData }
)(TransactionListContainer);
