import React, { Component, Fragment } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import StatisticsFilterArea from '../../components/statistics-dashboard/filters/StatisticsFilterArea';
import { setStatisticsFilter } from '../../actions/statisticsActions';

class StatisticsFilterContainer extends Component {
  render() {
    return (
      <Fragment>
        <StatisticsFilterArea filters={this.props.filters} handleFilterChange={this.props.handleFilterChange} />
      </Fragment>
    );
  }
}

const mapStateToProps = state => ({
  filters: state.statistics.filters
});

const mapDispatchToProps = (dispatch, ownProps) => {
  return { handleFilterChange: (filterId, filterType) => dispatch(setStatisticsFilter(filterId, filterType)) };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(StatisticsFilterContainer);
