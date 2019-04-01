import { GET_TRANSACTIONS, TRANSACTIONS_LOADING, SET_FILTER, UPDATE_TRANSACTION_CATEGORY } from './types';
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

export const setTransactionFilter = (filterId, filterType) => {
  return {
    type: SET_FILTER,
    payload: { filterId: filterId, filterType: filterType }
  };
};

export const handleTransactionCategoryUpdate = (transactionId, newCategoryId) => dispatch => {
  axios({
    method: 'put',
    url: '/transactions/update',
    data: { id: transactionId, categoryId: newCategoryId }
  })
    .then(response => {
      console.log(response);
      dispatch({
        type: UPDATE_TRANSACTION_CATEGORY,
        payload: {
          transactionId: transactionId,
          newCategoryId: newCategoryId
        }
      });
    })
    .catch(error => {
      console.log(error);
    });
};
export const handleTransactionSplit = transactionSplitDto => dispatch => {
  console.log(transactionSplitDto);
};

export const setItemsLoading = () => {
  return {
    type: TRANSACTIONS_LOADING
  };
};
