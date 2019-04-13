import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { computeTransactionSumsPerCategoryByDirection, sumTransactions } from '../../selectors/statisticsSelector';
import { TransactionDirections } from '../../constants/transactions';
import CategoryOverviewArea from '../../components/statistics-dashboard/chart-section/CategoryOverviewArea';
import { Spinner } from 'reactstrap';
import { setStatisticsFilter } from '../../actions/statisticsActions';

class CategoryOverviewAreaContainer extends Component {
  render() {
    return !this.props.isLoading ? (
      <CategoryOverviewArea {...this.props} />
    ) : (
      <div className="text-center">
        <Spinner type="grow" color="primary" />
      </div>
    );
  }
}

CategoryOverviewAreaContainer.propTypes = {
  chartData: PropTypes.array
};

const makeMapStateToProps = () => {
  const computePieChart = computeTransactionSumsPerCategoryByDirection();
  const sum = sumTransactions();
  const mapStateToProps = state => ({
    incomeChartData: computePieChart(state, TransactionDirections.INCOMING.id),
    expensesChartData: computePieChart(state, TransactionDirections.OUTGOING.id),
    incomeSum: sum(state, TransactionDirections.INCOMING.id),
    expensesSum: sum(state, TransactionDirections.OUTGOING.id),
    isLoading: state.transactions.loading,
    filters: state.statistics.filters
  });
  return mapStateToProps;
};
const mapDispatchToProps = (dispatch, ownProps) => {
  return { handleFilterChange: (filterId, filterType) => dispatch(setStatisticsFilter(filterId, filterType)) };
};

export default connect(
  makeMapStateToProps,
  mapDispatchToProps
)(CategoryOverviewAreaContainer);
