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
    return <StatisticsDashboard />;
  }
}

StatisticsDashboardContainer.propTypes = {
  getData: PropTypes.func.isRequired
};

export default connect(
  null,
  { getData }
)(StatisticsDashboardContainer);
