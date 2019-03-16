import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { getData } from '../../actions/chartActions';
import CustomAreaChart from '../../components/chart/CustomAreaChart';
import computeStatistics from '../../selectors/transactionSelectors';
import CustomPieChart from '../../components/chart/CustomPieChart';

class ChartContainer extends Component {
  componentDidMount() {
    this.props.getData();
    console.log(this.props.chartData);
  }

  componentDidUpdate() {}

  render() {
    return <CustomPieChart {...this.props} />;
  }
}

ChartContainer.propTypes = {
  getData: PropTypes.func.isRequired,
  chartDdata: PropTypes.object
};

const mapStateToProps = state => ({
  chartData: computeStatistics(state),
  isLoading: state.chartData.loading
});

export default connect(
  mapStateToProps,
  { getData }
)(ChartContainer);
