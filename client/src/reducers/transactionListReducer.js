import { GET_TRANSACTIONS, TRANSACTIONS_LOADING } from '../actions/types';

const initialState = {
  transactions: [],
  loading: false
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_TRANSACTIONS: {
      return {
        ...state,
        transactions: action.payload.transactions,
        loading: false
      };
    }
    case TRANSACTIONS_LOADING: {
      return {
        ...state,
        loading: true
      };
    }
    default:
      return state;
  }
}
