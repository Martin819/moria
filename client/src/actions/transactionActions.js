import { GET_TRANSACTIONS, TRANSACTIONS_LOADING, SET_FILTER, UPDATE_TRANSACTION_CATEGORY } from './types';
import { transactionsData } from '../utils/exampleResponse';

export const getData = () => (dispatch, getState) => {
  console.log('getstate', getState());
  dispatch(setItemsLoading());
  setTimeout(() => {
    dispatch({
      type: GET_TRANSACTIONS,
      payload: transactionsData
    });
  }, 1000);

  //   axios.get('/api/items').then(res =>
  //     dispatch({
  //       type: GET_ITEMS,
  //       payload: res.data
  //     })
  //   );
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
