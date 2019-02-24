import { combineReducers } from 'redux';
import chartReducer from './chartReducer';
import transactionListReducer from './transactionListReducer';

export default combineReducers({
  chartData: chartReducer,
  transactions: transactionListReducer
});
