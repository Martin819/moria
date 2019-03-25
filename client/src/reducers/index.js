import { combineReducers } from 'redux';
import statisticsReducer from './statisticsReducer';
import transactionReducer from './transactionReducer';
import ruleReducer from './ruleReducer';

export default combineReducers({
  statistics: statisticsReducer,
  transactions: transactionReducer,
  rules: ruleReducer
});
