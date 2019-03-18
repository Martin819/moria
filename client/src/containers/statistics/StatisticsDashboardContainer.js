import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { getData } from '../../actions/transactionActions';
import StatisticsDashboard from '../../components/statistics-dashboard/StatisticsDashboard';

class StatisticsDashboardContainer extends Component {
  componentDidMount() {
    this.props.getData();
  }

  render() {
    return <StatisticsDashboard isLoading={this.props.isLoading} />;
  }
}

StatisticsDashboardContainer.propTypes = {
  getData: PropTypes.func.isRequired,
  isLoading: PropTypes.bool.isRequired
};

const mapStateToProps = state => ({
  isLoading: state.transactions.loading
});

export default connect(
  mapStateToProps,
  { getData }
)(StatisticsDashboardContainer);
