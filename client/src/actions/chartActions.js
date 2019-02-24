import axios from 'axios';
import { GET_CHART_DATA, CHART_DATA_LOADING } from './types';

const data = [
  {
    name: 'Page A',
    uv: 400,
    pv: 2400,
    amt: 2400
  },
  {
    name: 'Page B',
    uv: 300,
    pv: 1398,
    amt: 2210
  },
  {
    name: 'Page C',
    uv: 200,
    pv: 9800,
    amt: 2290
  },
  {
    name: 'Page D',
    uv: 2780,
    pv: 3908,
    amt: 2000
  },
  {
    name: 'Page E',
    uv: 1890,
    pv: 4800,
    amt: 2181
  },
  {
    name: 'Page F',
    uv: 2390,
    pv: 3800,
    amt: 2500
  },
  {
    name: 'Page G',
    uv: 3490,
    pv: 4300,
    amt: 2100
  }
];

export const getData = () => dispatch => {
  dispatch(setItemsLoading());
  setTimeout(() => {
    dispatch({
      type: GET_CHART_DATA,
      payload: data
    });
  }, 4000);

  //   axios.get('/api/items').then(res =>
  //     dispatch({
  //       type: GET_ITEMS,
  //       payload: res.data
  //     })
  //   );
};

export const setItemsLoading = () => {
  return {
    type: CHART_DATA_LOADING
  };
};
