import { combineReducers } from 'redux';
import chartReducer from './chartReducer';
import transactionReducer from './transactionReducer';
import ruleReducer from './ruleReducer';

export default combineReducers({
  chartData: chartReducer,
  transactions: transactionReducer,
  rules: ruleReducer
});
