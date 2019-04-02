import {
  GET_TRANSACTIONS,
  TRANSACTIONS_LOADING,
  SET_FILTER,
  UPDATE_TRANSACTION_CATEGORY,
  SPLIT_TRANSACTION,
  UNSPLIT_TRANSACTION
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
        }
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
        })
      };
    }
    case SPLIT_TRANSACTION: {
      return {
        ...state,
        transactions: state.transactions.map(t => {
          if (t.id === action.payload.id) {
            return {
              ...t,
              childTransactionsList: action.payload.childTransactionsList,
              originalValue: action.payload.originalValue,
              transactionValueAmount: action.payload.transactionValueAmount
            };
          }
          return t;
        })
      };
    }
    case UNSPLIT_TRANSACTION: {
      return {
        ...state,
        transactions: state.transactions.map(t => {
          if (t.id == action.payload.parentTransactionId) {
            return {
              ...t,
              childTransactionsList: action.payload.data.childTransactionsList,
              originalValue: action.payload.data.originalValue,
              transactionValueAmount: action.payload.data.transactionValueAmount
            };
          }
          return t;
        })
      };
    }
    default:
      return state;
  }
}
