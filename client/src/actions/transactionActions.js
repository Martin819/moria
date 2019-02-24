import { GET_TRANSACTIONS, TRANSACTIONS_LOADING } from './types';
const data = {
  transactions: [
    {
      id: 1,
      valueDate: '29 Jan 2019',
      transactionType: 'CARD',
      value: {
        amount: 220,
        currency: 'CZK'
      },
      partyDescription: 'Billa',
      category: 'ELECTRONICS',
      message: 'Additional info'
    },
    {
      id: 2,
      valueDate: '30 Jan 2019',
      transactionType: 'CARD',
      value: {
        amount: 2200,
        currency: 'EUR'
      },
      partyDescription: 'Tesco',
      category: 'FOOD',
      message: 'Additional info'
    },
    {
      id: 3,
      valueDate: '31 Jan 2019',
      transactionType: 'CARD',
      value: {
        amount: 250,
        currency: 'USD'
      },
      partyDescription: 'Albert',
      category: 'HOME EQUIPMENT',
      message: 'Additional info'
    }
  ]
};
export const getData = () => dispatch => {
  dispatch(setItemsLoading());
  setTimeout(() => {
    dispatch({
      type: GET_TRANSACTIONS,
      payload: data
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
