import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { computeStatistics, sumTransactions } from '../../selectors/statisticsSelector';
import CustomPieChart from '../../components/statistics-dashboard/chart-section/CustomPieChart';

class ChartContainer extends Component {
  render() {
    return this.props.chartData.length > 0 ? (
      <CustomPieChart chartData={this.props.chartData} transactionSum={this.props.transactionSum} />
    ) : (
      <div className="text-center">
        <h2>There is no data for this filter option period</h2>
      </div>
    );
  }
}

ChartContainer.propTypes = {
  chartData: PropTypes.array
};

const makeMapStateToProps = () => {
  const computeStats = computeStatistics();
  const sum = sumTransactions();
  const mapStateToProps = (state, ownProps) => ({
    chartData: computeStats(state, ownProps.direction),
    transactionSum: sum(state, ownProps.direction),
    isLoading: state.transactions.loading
  });
  return mapStateToProps;
};

export default connect(makeMapStateToProps)(ChartContainer);
