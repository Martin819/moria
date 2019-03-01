// Filter types
export const FILTER_TIME_PERIOD = { id: 'FILTER_TIME_PERIOD', text: 'Time period' };
export const FILTER_DIRECTION = { id: 'FILTER_DIRECTION', text: 'Direction' };

// Time period filters
export const ALL_TIME = { id: 'ALL_TIME', text: 'All time', type: FILTER_TIME_PERIOD.id };
export const LAST_WEEK = { id: 'LAST_WEEK', text: 'Last week', type: FILTER_TIME_PERIOD.id };
export const LAST_MONTH = { id: 'LAST_MONTH', text: 'Last month', type: FILTER_TIME_PERIOD.id };
export const LAST_SIX_MONTHS = { id: 'LAST_SIX_MONTHS', text: 'Last 6 months', type: FILTER_TIME_PERIOD.id };
export const LAST_YEAR = { id: 'LAST_YEAR', text: 'Last year', type: FILTER_TIME_PERIOD.id };

// Transaction direction filters
export const BOTH = { id: 'BOTH', text: 'All payments', type: FILTER_DIRECTION.id };
export const INCOMING = { id: 'INCOMING', text: 'Incoming payments', type: FILTER_DIRECTION.id };
export const OUTGOING = { id: 'OUTGOING', text: 'Outgoing payments', type: FILTER_DIRECTION.id };

export const allFilterTypes = [FILTER_TIME_PERIOD, FILTER_DIRECTION];
export const allFilters = [ALL_TIME, LAST_WEEK, LAST_MONTH, LAST_SIX_MONTHS, LAST_YEAR, BOTH, INCOMING, OUTGOING];

export const timePeriodFilters = [ALL_TIME, LAST_WEEK, LAST_MONTH, LAST_SIX_MONTHS, LAST_YEAR];
export const directionFilters = [BOTH, INCOMING, OUTGOING];
