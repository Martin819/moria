import React, { Component } from 'react';
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
      <div>
        <AccountBalanceInfo />
        <TransactionList {...this.props} />
      </div>
    );
  }
}

const mapStateToProps = state => ({
  transactions: state.transactions.transactions,
  isLoading: state.transactions.loading
});

export default connect(
  mapStateToProps,
  { getData }
)(TransactionListContainer);
