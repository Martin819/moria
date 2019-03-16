import { createSelector } from 'reselect';
import _ from 'lodash';
import { TransactionCategories } from '../constants/categories';
import { TransactionDirections } from '../constants/transactions';

const getTransactions = state => state.transactions.transactions;

export const computeStatistics = createSelector(
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

export const computeAccountBalance = createSelector(
  [getTransactions],
  transactions => {
    const incomingTransactions = transactions.filter(t => t.direction === TransactionDirections.INCOMING.id);
    const gains = _.chain(incomingTransactions)
      .sumBy('transactionValueAmount')
      .value();

    const outgoingTransactions = transactions.filter(t => t.direction === TransactionDirections.OUTGOING.id);
    const losses = _.chain(outgoingTransactions)
      .sumBy('transactionValueAmount')
      .value();

    return _.subtract(gains, losses);
  }
);
