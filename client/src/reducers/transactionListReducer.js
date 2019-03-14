import { GET_TRANSACTIONS, TRANSACTIONS_LOADING, SET_FILTER } from '../actions/types';
import {
  FILTER_TIME_PERIOD,
  FILTER_DIRECTION,
  ALL_TIME,
  LAST_WEEK,
  LAST_MONTH,
  LAST_SIX_MONTHS,
  LAST_YEAR,
  BOTH,
  allFilters
} from '../constants/transactionListFilters';
import moment from 'moment';

const initialState = {
  transactions: [],
  visibleTransactions: [],
  filters: {
    [FILTER_TIME_PERIOD.id]: ALL_TIME.id,
    [FILTER_DIRECTION.id]: BOTH.id
  },

  loading: false
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_TRANSACTIONS: {
      return {
        ...state,
        transactions: action.payload.data.sort((t1, t2) => moment(t2.valueDate).diff(t1.valueDate)),
        visibleTransactions: action.payload.data,
        loading: false
      };
    }
    case TRANSACTIONS_LOADING: {
      return {
        ...state,
        loading: true
      };
    }
    case SET_FILTER: {
      const selectedFilter = allFilters.find(f => f.id === action.payload);
      return {
        ...state,
        filters: {
          ...state.filters,
          [selectedFilter.type]: selectedFilter.id
        },
        visibleTransactions: filterTransactions(action.payload, state)
      };
    }
    default:
      return state;
  }
}

const filterTransactions = (selectedTimePeriodFilter, state) => {
  switch (selectedTimePeriodFilter) {
    case ALL_TIME.id: {
      return [...state.transactions];
    }
    case LAST_WEEK.id: {
      return state.transactions.filter(t => {
        return moment(t.valueDate).isAfter(moment().subtract(7, 'd'));
      });
    }
    case LAST_MONTH.id: {
      return state.transactions.filter(t => {
        return moment(t.valueDate).isAfter(moment().subtract(1, 'M'));
      });
    }
    case LAST_SIX_MONTHS.id: {
      return state.transactions.filter(t => {
        return moment(t.valueDate).isAfter(moment().subtract(6, 'M'));
      });
    }
    case LAST_YEAR.id: {
      return state.transactions.filter(t => {
        return moment(t.valueDate).isAfter(moment().subtract(1, 'y'));
      });
    }
    default:
      return [...state.visibleTransactions];
  }
};
