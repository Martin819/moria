import { GET_TRANSACTIONS, TRANSACTIONS_LOADING, SET_FILTER } from '../actions/types';
import {
  FILTER_TIME_PERIOD,
  FILTER_DIRECTION,
  TimePeriodFilters,
  DirectionFilters
} from '../constants/transactionListFilters';
import moment from 'moment';

const initialState = {
  transactions: [],
  visibleTransactions: [],
  filters: {
    [FILTER_TIME_PERIOD.id]: TimePeriodFilters.ALL_TIME.id,
    [FILTER_DIRECTION.id]: DirectionFilters.ALL.id
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
      return {
        ...state,
        filters: {
          ...state.filters,
          [action.payload.filterType]: action.payload.filterId
        },
        visibleTransactions: [...state.transactions]
      };
    }
    default:
      return state;
  }
}
