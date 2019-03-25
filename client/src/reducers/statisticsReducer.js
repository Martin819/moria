import { SET_STATISTICS_FILTER } from '../actions/types';
import {
  FILTER_TIME_PERIOD,
  FILTER_DIRECTION,
  TimePeriodFilters,
  DirectionFilters
} from '../constants/transactionListFilters';

const initialState = {
  filters: {
    [FILTER_TIME_PERIOD.id]: TimePeriodFilters.ALL_TIME.id,
    [FILTER_DIRECTION.id]: DirectionFilters.ALL.id
  }
};

export default function(state = initialState, action) {
  switch (action.type) {
    case SET_STATISTICS_FILTER: {
      return {
        ...state,
        filters: {
          ...state.filters,
          [action.payload.filterType]: action.payload.filterId
        }
      };
    }
    default:
      return state;
  }
}
