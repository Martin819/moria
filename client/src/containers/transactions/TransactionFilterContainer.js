import React, { Component, Fragment } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { setTransactionFilter } from '../../actions/transactionActions';
import TransactionFilterArea from '../../components/transactions-dashboard/transaction-list/TransactionFilterArea';

class TransactionFilterContainer extends Component {
  render() {
    return (
      <Fragment>
        <TransactionFilterArea filters={this.props.filters} handleFilterChange={this.props.handleFilterChange} />
      </Fragment>
    );
  }
}

const mapStateToProps = state => ({
  filters: state.transactions.filters
});

const mapDispatchToProps = (dispatch, ownProps) => {
  return { handleFilterChange: event => dispatch(setTransactionFilter(event.target.value)) };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TransactionFilterContainer);
