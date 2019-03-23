import {
  GET_RULES,
  RULES_LOADING,
  TOGGLE_RULE_MODAL,
  RULE_ADD,
  RULE_EDIT,
  RULE_SUBMIT_CREATE,
  RULE_SUBMIT_UPDATE,
  RULES_DELETE
} from '../actions/types';

const initialState = {
  rules: [],
  loading: false,
  isModalOpened: false,
  editedRule: null
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_RULES: {
      return {
        ...state,
        rules: action.payload.data,
        loading: false
      };
    }
    case RULES_LOADING: {
      return {
        ...state,
        loading: true
      };
    }
    case TOGGLE_RULE_MODAL: {
      return {
        ...state,
        isModalOpened: !state.isModalOpened
      };
    }
    case RULE_ADD: {
      return {
        ...state,
        editedRule: null,
        isModalOpened: !state.isModalOpened
      };
    }
    case RULE_EDIT: {
      return {
        ...state,
        editedRule: state.rules.find(r => r.id === action.payload),
        isModalOpened: !state.isModalOpened
      };
    }
    case RULE_SUBMIT_CREATE: {
      return {
        ...state,
        rules: [...state.rules, action.payload],
        isModalOpened: !state.isModalOpened
      };
    }
    case RULE_SUBMIT_UPDATE: {
      return {
        ...state,
        rules: state.rules.map(rule => (rule.id === action.payload.id ? action.payload : rule)),
        isModalOpened: !state.isModalOpened
      };
    }
    case RULES_DELETE: {
      return {
        ...state,
        rules: state.rules.filter(rule => !action.payload.includes(rule.id))
      };
    }
    default:
      return state;
  }
}
