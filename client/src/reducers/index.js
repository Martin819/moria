import { combineReducers } from 'redux';
import chartReducer from './chartReducer';
import transactionListReducer from './transactionListReducer';
import ruleReducer from './ruleReducer';

export default combineReducers({
  chartData: chartReducer,
  transactions: transactionListReducer,
  rules: ruleReducer
});
