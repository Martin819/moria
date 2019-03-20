import { GET_RULES, TOGGLE_RULE_MODAL, RULES_LOADING, RULE_ADD, RULE_EDIT, RULE_SUBMIT } from './types';
import axios from 'axios';

export const getRules = () => async dispatch => {
  dispatch(setItemsLoading());
  try {
    const response = await axios.get('/rules/getAll');
    dispatch({
      type: GET_RULES,
      payload: response
    });
  } catch (error) {
    console.error(error);
  }
};

export const handleRuleAdd = () => {
  return {
    type: RULE_ADD
  };
};

export const handleRuleEdit = ruleId => dispatch => {
  console.log(ruleId);
  dispatch({
    type: RULE_EDIT,
    payload: ruleId
  });
};

export const handleRuleSubmit = newRule => dispatch => {
  console.log(newRule);
  return {
    type: RULE_SUBMIT
  };
};

export const toggleModal = () => {
  return {
    type: TOGGLE_RULE_MODAL
  };
};

export const setItemsLoading = () => {
  return {
    type: RULES_LOADING
  };
};
