import { createSelector } from 'reselect';
import _ from 'lodash';
import { TransactionCategories } from '../constants/categories';

const getTransactions = state => state.transactions.transactions;

const computeStatistics = createSelector(
  [getTransactions],
  transactions => {
    return _.chain(transactions)
      .groupBy('category')
      .map((t, category) => {
        return {
          name: TransactionCategories[category].text,
          value: _.sumBy(t, 'transactionValueAmount')
        };
      })
      .value();
  }
);

export default computeStatistics;
