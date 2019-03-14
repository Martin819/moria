export const TransactionDirections = Object.freeze({
  INCOMING: { id: 'INCOMING', text: 'Incoming' },
  OUTGOING: { id: 'OUTGOING', text: 'Outgoing' }
});

export const TransactionValueOperators = Object.freeze({
  LESS_THAN: { id: 'LESS_THAN', text: 'Less than' },
  MORE_THAN: { id: 'MORE_THAN', text: 'More than' },
  BETWEEN: { id: 'BETWEEN', text: 'Between' }
});

export const TransactionTypes = Object.freeze({
  PAYMENT_HOME: { id: 'PAYMENT_HOME', text: 'Payment home' },
  PAYMENT_ABROAD: { id: 'PAYMENT_ABROAD', text: 'Payment abroad' },
  CARD: { id: 'CARD', text: 'Card payment' },
  CASH: { id: 'CASH', text: 'Cash payment' }
});
