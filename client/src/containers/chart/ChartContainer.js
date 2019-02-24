import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { getData } from '../../actions/chartActions';
import CustomAreaChart from '../../components/chart/CustomAreaChart';

class ChartContainer extends Component {
  componentDidMount() {
    this.props.getData();
    console.log(this.props);
  }

  componentDidUpdate() {}

  render() {
    return <CustomAreaChart {...this.props} />;
  }
}

ChartContainer.propTypes = {
  getData: PropTypes.func.isRequired,
  charDdata: PropTypes.object
};

const mapStateToProps = state => ({
  chartData: state.chartData.chartData,
  isLoading: state.chartData.loading
});

export default connect(
  mapStateToProps,
  { getData }
)(ChartContainer);
