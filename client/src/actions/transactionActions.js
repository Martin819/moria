import {
  GET_TRANSACTIONS,
  TRANSACTIONS_LOADING,
  SET_FILTER,
  UPDATE_TRANSACTION_CATEGORY,
  SPLIT_TRANSACTION,
  UNSPLIT_TRANSACTION
} from './types';
import axios from 'axios';
import { API_CONN } from '../utils/connection';

export const getData = () => async (dispatch, getState) => {
  dispatch(setItemsLoading());
  try {
    const response = await axios.get(`${API_CONN}/transactions`);
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
    url: `${API_CONN}/transactions/update`,
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
  axios({
    method: 'post',
    url: `${API_CONN}/transactions/split`,
    data: transactionSplitDto
  })
    .then(response => {
      console.log(response);
      dispatch({
        type: SPLIT_TRANSACTION,
        payload: response.data
      });
    })
    .catch(error => {
      console.log(error);
    });
};

export const handleTransactionUnsplit = (parentTransactionId, childTransactionId) => dispatch => {
  axios({
    method: 'post',
    url: `${API_CONN}/transactions/removeSplit`,
    data: { id: childTransactionId }
  })
    .then(response => {
      console.log(response);
      dispatch({
        type: UNSPLIT_TRANSACTION,
        payload: {
          parentTransactionId: parentTransactionId,
          childTransactionId: childTransactionId,
          data: response.data
        }
      });
    })
    .catch(error => {
      console.log(error);
    });
};

export const setItemsLoading = () => {
  return {
    type: TRANSACTIONS_LOADING
  };
};
