import { createSelector } from 'reselect';
import _ from 'lodash';
import moment from 'moment';
import { TransactionCategories } from '../constants/categories';
import { TransactionDirections } from '../constants/transactions';
import { filterByTimePeriod } from './transactionSelector';
import { FILTER_TIME_PERIOD } from '../constants/transactionListFilters';

const getTransactionsByDirection = (state, direction) =>
  state.transactions.transactions.filter(t => t.direction === direction);
const getAllTransactions = state => state.transactions.transactions;
const getAllFilters = state => state.statistics.filters;

const filterTransactions = (transactions, filters) => {
  Object.entries(filters).forEach(filter => {
    const key = filter[0];
    const value = filter[1];
    switch (key) {
      case FILTER_TIME_PERIOD.id: {
        transactions = filterByTimePeriod(transactions, value);
        break;
      }
      default:
    }
  });
  return transactions;
};

export const computeStatistics = () =>
  createSelector(
    [getTransactionsByDirection, getAllFilters],
    (transactions, filters) => {
      transactions = filterTransactions(transactions, filters);
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

export const computeBarchartData = createSelector(
  [getAllTransactions],
  transactions => {
    const incomingTransactions = transactions.filter(t => t.direction === TransactionDirections.INCOMING.id);
    const outgoingTransactions = transactions.filter(t => t.direction === TransactionDirections.OUTGOING.id);
    let now = moment();
    let data = [];
    for (let i = 0; i < 11; i++) {
      const currentMonth = now.month();
      const currentYear = now.year();
      const monthlyIncomingTransactions = incomingTransactions.filter(
        t => moment(t.valueDate).month() === currentMonth && moment(t.valueDate).year() === currentYear
      );
      const monthlyOutgoingTransactions = outgoingTransactions.filter(
        t => moment(t.valueDate).month() === currentMonth && moment(t.valueDate).year() === currentYear
      );

      const monthlyIncome = _.sumBy(monthlyIncomingTransactions, 'transactionValueAmount');
      const monthlyExpense = _.sumBy(monthlyOutgoingTransactions, 'transactionValueAmount');
      data = [
        { name: `${now.format('MMM')} '${now.format('YY')}`, Income: monthlyIncome, Expense: monthlyExpense },
        ...data
      ];
      now.subtract(1, 'M');
    }
    return data;
  }
);

export const sumTransactions = () =>
  createSelector(
    [getTransactionsByDirection, getAllFilters],
    (transactions, filters) => {
      transactions = filterTransactions(transactions, filters);
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
