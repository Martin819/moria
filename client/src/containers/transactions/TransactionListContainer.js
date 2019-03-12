import React, { Component, Fragment } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { getData, handleTransactionCategorySubmit } from '../../actions/transactionActions';
import TransactionList from '../../components/transactions-dashboard/transaction-list/TransactionList';
import AccountBalanceInfo from '../../components/transactions-dashboard/account-balance-info/AccountBalanceInfo';
import { bindActionCreators } from 'redux';

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

const mapDispatchToProps = (dispatch, ownProps) => {
  return bindActionCreators({ getData, handleTransactionCategorySubmit }, dispatch);
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TransactionListContainer);
