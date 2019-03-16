import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { getData } from '../../actions/transactionActions';
import { computeStatistics } from '../../selectors/transactionSelectors';
import CustomPieChart from '../../components/chart/CustomPieChart';
import { Spinner } from 'reactstrap';

class ChartContainer extends Component {
  componentDidMount() {
    this.props.getData();
  }

  render() {
    return !this.props.isLoading && this.props.chartData.length > 0 ? (
      <CustomPieChart chartData={this.props.chartData} />
    ) : (
      <Spinner type="grow" color="primary" />
    );
  }
}

ChartContainer.propTypes = {
  getData: PropTypes.func.isRequired,
  chartDdata: PropTypes.object
};

const mapStateToProps = state => ({
  chartData: computeStatistics(state),
  isLoading: state.transactions.loading
});

export default connect(
  mapStateToProps,
  { getData }
)(ChartContainer);
