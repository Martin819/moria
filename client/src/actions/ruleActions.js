import { GET_RULES, TOGGLE_RULE_MODAL, RULES_LOADING, RULE_EDIT, RULE_SUBMIT } from './types';
import { rulesData } from '../utils/exampleResponse';

export const getRules = () => dispatch => {
  dispatch(setItemsLoading());
  setTimeout(() => {
    dispatch({
      type: GET_RULES,
      payload: rulesData
    });
  }, 1000);
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
