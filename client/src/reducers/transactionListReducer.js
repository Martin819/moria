import { GET_TRANSACTIONS, TRANSACTIONS_LOADING, FILTER_TRANSACTIONS } from '../actions/types';
import { ALL_TIME, LAST_WEEK, LAST_MONTH, LAST_SIX_MONTHS, LAST_YEAR } from '../constants/transactionListFilters';
import moment from 'moment';

const initialState = {
  transactions: [],
  visibleTransactions: [],
  selectedFilter: ALL_TIME.id,
  loading: false
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_TRANSACTIONS: {
      return {
        ...state,
        transactions: action.payload.transactions.sort((t1, t2) => moment(t2.valueDate).diff(t1.valueDate)),
        visibleTransactions: action.payload.transactions,
        loading: false
      };
    }
    case TRANSACTIONS_LOADING: {
      return {
        ...state,
        selectedFilter: ALL_TIME.id,
        loading: true
      };
    }
    case FILTER_TRANSACTIONS: {
      return {
        ...state,
        selectedFilter: action.payload,
        visibleTransactions: filterTransactions(action.payload, state.transactions)
      };
    }
    default:
      return state;
  }
}

const filterTransactions = (selectedFilter, transactions) => {
  switch (selectedFilter) {
    case ALL_TIME.id: {
      return [...transactions];
    }
    case LAST_WEEK.id: {
      return transactions.filter(t => {
        return moment(t.valueDate).isAfter(moment().subtract(7, 'd'));
      });
    }
    case LAST_MONTH.id: {
      return transactions.filter(t => {
        return moment(t.valueDate).isAfter(moment().subtract(1, 'M'));
      });
    }
    case LAST_SIX_MONTHS.id: {
      return transactions.filter(t => {
        return moment(t.valueDate).isAfter(moment().subtract(6, 'M'));
      });
    }
    case LAST_YEAR.id: {
      return transactions.filter(t => {
        return moment(t.valueDate).isAfter(moment().subtract(1, 'y'));
      });
    }
    default:
      return [...transactions];
  }
};
