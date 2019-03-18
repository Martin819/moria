import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { computeStatistics, sumTransactions } from '../../selectors/transactionSelectors';
import CustomPieChart from '../../components/statistics-dashboard/chart-section/CustomPieChart';

class ChartContainer extends Component {
  render() {
    return this.props.chartData.length > 0 ? (
      <CustomPieChart chartData={this.props.chartData} transactionSum={this.props.transactionSum} />
    ) : null;
  }
}

ChartContainer.propTypes = {
  chartData: PropTypes.array
};

const makeMapStateToProps = () => {
  const getData = computeStatistics();
  const sum = sumTransactions();
  const mapStateToProps = (state, ownProps) => ({
    chartData: getData(state, ownProps.direction),
    transactionSum: sum(state, ownProps.direction),
    isLoading: state.transactions.loading
  });
  return mapStateToProps;
};

export default connect(makeMapStateToProps)(ChartContainer);
