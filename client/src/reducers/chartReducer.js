import { GET_CHART_DATA, CHART_DATA_LOADING } from '../actions/types';

const initialState = {
  chartData: [],
  loading: false
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_CHART_DATA: {
      return {
        ...state,
        chartData: action.payload,
        loading: false
      };
    }
    case CHART_DATA_LOADING: {
      return {
        ...state,
        loading: true
      };
    }
    default:
      return state;
  }
}
