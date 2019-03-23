import { createSelector } from 'reselect';
import _ from 'lodash';
import { TransactionCategories } from '../constants/categories';
import { TransactionDirections } from '../constants/transactions';

const getTransactionsByDirection = (state, direction) =>
  state.transactions.transactions.filter(t => t.direction === direction);

const getAllTransactions = state => state.transactions.transactions;

export const computeStatistics = () =>
  createSelector(
    [getTransactionsByDirection],
    transactions => {
      return _.chain(transactions)
        .groupBy(t => t.categoryId)
        .map((t, categoryId) => {
          return {
            name: Object.values(TransactionCategories).find(cat => cat.id == categoryId).text,
            value: _.sumBy(t, 'transactionValueAmount')
          };
        })
        .value();
    }
  );

export const sumTransactions = () =>
  createSelector(
    [getTransactionsByDirection],
    transactions => {
      return _.chain(transactions)
        .sumBy('transactionValueAmount')
        .value();
    }
  );

export const computeAccountBalance = createSelector(
  [getAllTransactions],
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
