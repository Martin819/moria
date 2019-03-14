import { GET_TRANSACTIONS, TRANSACTIONS_LOADING, SET_FILTER, UPDATE_TRANSACTION_CATEGORY } from './types';
import { transactionsData } from '../utils/exampleResponse';
import axios from 'axios';

export const getData = () => async (dispatch, getState) => {
  console.log('getstate', getState());
  dispatch(setItemsLoading());
  try {
    const response = await axios.get('/transactions');
    dispatch({
      type: GET_TRANSACTIONS,
      payload: response
    });
  } catch (error) {
    console.error(error);
  }
};

export const setTransactionFilter = filterId => {
  return {
    type: SET_FILTER,
    payload: filterId
  };
};

export const handleTransactionCategorySubmit = (transactionId, newCategoryId) => dispatch => {
  console.log(transactionId, newCategoryId);
  // axios put ...
  dispatch({
    type: UPDATE_TRANSACTION_CATEGORY,
    payload: {
      transactionId: transactionId,
      newCategoryId: newCategoryId
    }
  });
};

export const setItemsLoading = () => {
  return {
    type: TRANSACTIONS_LOADING
  };
};
