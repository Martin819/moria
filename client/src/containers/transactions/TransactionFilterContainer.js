import React, { Component, Fragment } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { setTransactionFilter } from '../../actions/transactionActions';
import TransactionFilter from '../../components/transactions-dashboard/transaction-list/TransactionFilter';

class TransactionFilterContainer extends Component {
  render() {
    return (
      <Fragment>
        {/* TODO: TransactionFilterArea*/}
        <TransactionFilter
          label="Time period"
          selectedFilter={this.props.selectedFilter}
          handleFilterChange={this.props.handleFilterChange}
        />
      </Fragment>
    );
  }
}

const mapStateToProps = state => ({
  selectedFilter: state.transactions.selectedFilter
});

const mapDispatchToProps = (dispatch, ownProps) => {
  return { handleFilterChange: event => dispatch(setTransactionFilter(event.target.value)) };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TransactionFilterContainer);
