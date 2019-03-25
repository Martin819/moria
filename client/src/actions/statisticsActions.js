import { SET_STATISTICS_FILTER } from './types';

export const setStatisticsFilter = (filterId, filterType) => {
  return {
    type: SET_STATISTICS_FILTER,
    payload: { filterId: filterId, filterType: filterType }
  };
};
