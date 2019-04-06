import {
  GET_RULES,
  TOGGLE_RULE_MODAL,
  RULES_LOADING,
  RULE_ADD,
  RULE_EDIT,
  RULE_SUBMIT_CREATE,
  RULE_SUBMIT_UPDATE,
  RULES_DELETE
} from './types';
import axios from 'axios';
import { API_CONN } from '../utils/connection';

export const getRules = () => async dispatch => {
  dispatch(setItemsLoading());
  try {
    const response = await axios.get(`${API_CONN}/rules`);
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

export const handleRuleSubmitCreate = newRule => dispatch => {
  axios({
    method: 'post',
    url: `${API_CONN}/rules`,
    data: newRule
  })
    .then(response => {
      dispatch({
        type: RULE_SUBMIT_CREATE,
        payload: response.data
      });
    })
    .catch(error => {
      console.log(error);
    });
};

export const handleRuleSubmitUpdate = editedRule => dispatch => {
  axios({
    method: 'put',
    url: `${API_CONN}/rules`,
    data: editedRule
  })
    .then(response => {
      console.log(response);
      dispatch({
        type: RULE_SUBMIT_UPDATE,
        payload: response.data
      });
    })
    .catch(error => {
      console.log(error);
    });
};
export const handleRulesDelete = ruleIds => dispatch => {
  axios({
    method: 'post',
    url: `${API_CONN}/rules/remove`,
    data: ruleIds
  })
    .then(response => {
      console.log(response);
      dispatch({
        type: RULES_DELETE,
        payload: ruleIds
      });
    })
    .catch(error => {
      console.log(error);
    });
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
