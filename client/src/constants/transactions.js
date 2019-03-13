export const INCOMING = { id: 'INCOMING', text: 'Incoming' };
export const OUTGOING = { id: 'OUTGOING', text: 'Outgoing' };
export const allTransactionDirections = [INCOMING, OUTGOING];

export const LESS_THAN = { id: 'LESS_THAN', text: 'Less than' };
export const MORE_THAN = { id: 'MORE_THAN', text: 'More than' };
export const BETWEEN = { id: 'BETWEEN', text: 'Between' };
export const allTransactionValueOperators = [LESS_THAN, MORE_THAN, BETWEEN];

export const PAYMENT_HOME = { id: 'PAYMENT_HOME', text: 'Payment home' };
export const PAYMENT_ABROAD = { id: 'PAYMENT_ABROAD', text: 'Payment abroad' };
export const CARD = { id: 'CARD', text: 'Card payment' };
export const CASH = { id: 'CASH', text: 'Cash payment' };
export const allTransactionTypes = [PAYMENT_HOME, PAYMENT_ABROAD, CARD, CASH];
