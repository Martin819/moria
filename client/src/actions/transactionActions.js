import { GET_TRANSACTIONS, TRANSACTIONS_LOADING, SET_FILTER } from './types';
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

export const setItemsLoading = () => {
  return {
    type: TRANSACTIONS_LOADING
  };
};

export const setTransactionFilter = filterId => {
  return {
    type: SET_FILTER,
    payload: filterId
  };
};
