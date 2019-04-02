import React, { Component, Fragment } from 'react';
import { connect } from 'react-redux';
import {
  getData,
  handleTransactionCategoryUpdate,
  handleTransactionSplit,
  handleTransactionUnsplit
} from '../../actions/transactionActions';
import TransactionList from '../../components/transactions-dashboard/transaction-list/TransactionList';
import { bindActionCreators } from 'redux';
import { computeAccountBalance } from '../../selectors/statisticsSelector';
import { filterTransactions } from '../../selectors/transactionSelector';

class TransactionListContainer extends Component {
  componentDidMount() {
    this.props.getData();
  }

  render() {
    return (
      <Fragment>
        <TransactionList {...this.props} />
      </Fragment>
    );
  }
}

const makeMapStateToProps = () => {
  const getVisibleTransactions = filterTransactions();
  const mapStateToProps = state => ({
    transactions: getVisibleTransactions(state),
    isLoading: state.transactions.loading,
    accountBalance: computeAccountBalance(state)
  });
  return mapStateToProps;
};

const mapDispatchToProps = (dispatch, ownProps) => {
  return bindActionCreators(
    { getData, handleTransactionCategoryUpdate, handleTransactionSplit, handleTransactionUnsplit },
    dispatch
  );
};

export default connect(
  makeMapStateToProps,
  mapDispatchToProps
)(TransactionListContainer);
