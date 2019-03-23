// Filter types
export const FILTER_TIME_PERIOD = { id: 'FILTER_TIME_PERIOD', text: 'Time period' };
export const FILTER_DIRECTION = { id: 'FILTER_DIRECTION', text: 'Direction' };

export const DirectionFilters = Object.freeze({
  ALL: { id: 'ALL', text: 'All payments' },
  INCOMING: { id: 'INCOMING', text: 'Incoming payments' },
  OUTGOING: { id: 'OUTGOING', text: 'Outgoing payments' }
});

export const TimePeriodFilters = Object.freeze({
  ALL_TIME: { id: 'ALL_TIME', text: 'All time' },
  LAST_WEEK: { id: 'LAST_WEEK', text: 'Last week' },
  LAST_MONTH: { id: 'LAST_MONTH', text: 'Last month' },
  LAST_SIX_MONTHS: { id: 'LAST_SIX_MONTHS', text: 'Last 6 months' },
  LAST_YEAR: { id: 'LAST_YEAR', text: 'Last year' }
});
