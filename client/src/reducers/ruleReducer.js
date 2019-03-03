import { GET_RULES, RULES_LOADING, TOGGLE_RULE_MODAL, RULE_EDIT, RULE_SUBMIT } from '../actions/types';

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
        rules: action.payload,
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
    case RULE_EDIT: {
      return {
        ...state,
        editedRule: state.rules.find(r => r.id === action.payload),
        isModalOpened: !state.isModalOpened
      };
    }
    case RULE_SUBMIT: {
      return {
        ...state,
        isModalOpened: !state.isModalOpened
      };
    }
    default:
      return state;
  }
}
