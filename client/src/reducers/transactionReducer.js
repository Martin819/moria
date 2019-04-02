import {
  GET_TRANSACTIONS,
  TRANSACTIONS_LOADING,
  SET_FILTER,
  UPDATE_TRANSACTION_CATEGORY,
  SPLIT_TRANSACTION
} from '../actions/types';
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

const colors = ['red', 'green', 'orange', 'blue'];

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_TRANSACTIONS: {
      return {
        ...state,
        transactions: action.payload.data
          .map(t => Object.assign({}, t, { accountPreferredColor: colors[Math.floor(Math.random() * colors.length)] }))
          .sort((t1, t2) => moment(t2.valueDate).diff(t1.valueDate)),
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
    case UPDATE_TRANSACTION_CATEGORY: {
      return {
        ...state,
        transactions: state.transactions.map(t => {
          if (t.id === action.payload.transactionId) {
            t.categoryId = action.payload.newCategoryId;
            return t;
          }
          return t;
        }),
        visibleTransactions: [...state.transactions]
      };
    }
    case SPLIT_TRANSACTION: {
      return {
        ...state,
        transactions: state.transactions.map(t => {
          if (t.id === action.payload.id) {
            return {
              ...t,
              categories: {
                //TODO
              }
            };
          }
          return t;
        }),
        visibleTransactions: [...state.transactions]
      };
    }
    default:
      return state;
  }
}
