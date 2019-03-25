import { createSelector } from 'reselect';
import moment from 'moment';
import {
  FILTER_TIME_PERIOD,
  FILTER_DIRECTION,
  TimePeriodFilters,
  DirectionFilters
} from '../constants/transactionListFilters';
import { TransactionDirections } from '../constants/transactions';

const getAllTransactions = state => state.transactions.transactions;
const getAllFilters = state => state.transactions.filters;

export const filterTransactions = () =>
  createSelector(
    [getAllTransactions, getAllFilters],
    (transactions, filters) => {
      Object.entries(filters).forEach(filter => {
        const key = filter[0];
        const value = filter[1];
        switch (key) {
          case FILTER_TIME_PERIOD.id: {
            transactions = filterByTimePeriod(transactions, value);
            break;
          }
          case FILTER_DIRECTION.id: {
            transactions = filterByDirection(transactions, value);
            break;
          }
          default:
        }
      });
      return transactions;
    }
  );

export const filterByTimePeriod = (transactions, activeFilter) => {
  switch (activeFilter) {
    case TimePeriodFilters.ALL_TIME.id: {
      return [...transactions];
    }
    case TimePeriodFilters.LAST_WEEK.id: {
      return transactions.filter(t => {
        return moment(t.valueDate).isAfter(moment().subtract(7, 'd'));
      });
    }
    case TimePeriodFilters.LAST_MONTH.id: {
      return transactions.filter(t => {
        return moment(t.valueDate).isAfter(moment().subtract(1, 'M'));
      });
    }
    case TimePeriodFilters.LAST_SIX_MONTHS.id: {
      return transactions.filter(t => {
        return moment(t.valueDate).isAfter(moment().subtract(6, 'M'));
      });
    }
    case TimePeriodFilters.LAST_YEAR.id: {
      return transactions.filter(t => {
        return moment(t.valueDate).isAfter(moment().subtract(1, 'y'));
      });
    }
    default:
      return [...transactions];
  }
};

const filterByDirection = (transactions, activeFilter) => {
  switch (activeFilter) {
    case DirectionFilters.ALL.id: {
      return transactions;
    }
    case DirectionFilters.INCOMING.id: {
      return transactions.filter(t => {
        return t.direction === TransactionDirections.INCOMING.id;
      });
    }
    case DirectionFilters.OUTGOING.id: {
      return transactions.filter(t => {
        return t.direction === TransactionDirections.OUTGOING.id;
      });
    }
    default:
      return transactions;
  }
};
