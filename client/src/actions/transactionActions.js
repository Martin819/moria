import { GET_TRANSACTIONS, TRANSACTIONS_LOADING, FILTER_TRANSACTIONS } from './types';
import { transactionsData } from '../utils/exampleResponse';

export const getData = () => dispatch => {
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
    type: FILTER_TRANSACTIONS,
    payload: filterId
  };
};
