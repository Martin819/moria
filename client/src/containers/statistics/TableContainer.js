import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { computeTransactionSumsPerCategoryByDirection } from '../../selectors/statisticsSelector';
import StatisticsTable from '../../components/statistics-dashboard/tabular-section/StatisticsTable';

class TableContainer extends Component {
  render() {
    return !this.props.isLoading && this.props.tableData.length > 0 ? (
      <StatisticsTable tableData={this.props.tableData} />
    ) : null;
  }
}

TableContainer.propTypes = {
  tableData: PropTypes.array
};

const makeMapStateToProps = () => {
  const getData = computeTransactionSumsPerCategoryByDirection();
  const mapStateToProps = (state, ownProps) => ({
    tableData: getData(state, ownProps.direction),
    isLoading: state.transactions.loading
  });
  return mapStateToProps;
};

export default connect(makeMapStateToProps)(TableContainer);
