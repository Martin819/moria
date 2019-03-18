import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { computeStatistics } from '../../selectors/transactionSelectors';
import CustomPieChart from '../../components/statistics-dashboard/chart-section/CustomPieChart';
import { Spinner } from 'reactstrap';

class ChartContainer extends Component {
  render() {
    return !this.props.isLoading && this.props.chartData.length > 0 ? (
      <CustomPieChart chartData={this.props.chartData} />
    ) : (
      <Spinner type="grow" color="primary" />
    );
  }
}

ChartContainer.propTypes = {
  chartData: PropTypes.array
};

const makeMapStateToProps = () => {
  const getData = computeStatistics();
  const mapStateToProps = (state, ownProps) => ({
    chartData: getData(state, ownProps.direction),
    isLoading: state.transactions.loading
  });
  return mapStateToProps;
};

export default connect(makeMapStateToProps)(ChartContainer);
