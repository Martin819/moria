export const transactionsData = {
  transactions: [
    {
      id: 1,
      valueDate: '2018-12-25T15:41:50.126Z',
      transactionType: 'CARD',
      value: {
        amount: 220,
        currency: 'CZK'
      },
      partyDescription: 'Billa',
      category: 'ELECTRONICS',
      message: 'Additional info',
      direction: 'OUTGOING'
    },
    {
      id: 2,
      valueDate: '2019-02-24T15:41:50.126Z',
      transactionType: 'CARD',
      value: {
        amount: 2200,
        currency: 'EUR'
      },
      partyDescription: 'Tesco',
      category: 'FOOD',
      message: 'Additional info',
      direction: 'OUTGOING'
    },
    {
      id: 3,
      valueDate: '2019-01-27T15:41:50.126Z',
      transactionType: 'CARD',
      value: {
        amount: 250,
        currency: 'USD'
      },
      partyDescription: 'Albert',
      category: 'HOME EQUIPMENT',
      message: 'Additional info',
      direction: 'OUTGOING'
    },
    {
      id: 4,
      valueDate: '2019-01-27T15:41:50.126Z',
      transactionType: 'CARD',
      value: {
        amount: 1500,
        currency: 'CZK'
      },
      partyDescription: 'Albert',
      category: 'HOME EQUIPMENT',
      message: 'Additional info',
      direction: 'OUTGOING'
    },
    {
      id: 5,
      valueDate: '2019-01-27T15:41:50.126Z',
      transactionType: 'CARD',
      value: {
        amount: 888,
        currency: 'CZK'
      },
      partyDescription: 'Albert',
      category: 'HOME EQUIPMENT',
      message: 'Additional info',
      direction: 'OUTGOING'
    },
    {
      id: 6,
      valueDate: '2019-01-25T15:41:50.126Z',
      transactionType: 'CARD',
      value: {
        amount: 777,
        currency: 'CZK'
      },
      partyDescription: 'Albert',
      category: 'HOME EQUIPMENT',
      message: 'Additional info',
      direction: 'OUTGOING'
    },
    {
      id: 7,
      valueDate: '2019-01-25T15:41:50.126Z',
      transactionType: 'CARD',
      value: {
        amount: 333,
        currency: 'CZK'
      },
      partyDescription: 'Albert',
      category: 'HOME EQUIPMENT',
      message: 'Additional info',
      direction: 'OUTGOING'
    }
  ]
};

export const rulesData = [
  {
    id: 1,
    name: 'Rule 1',
    category: 'FOOD',
    conditions: [],
    default: true
  },
  {
    id: 2,
    name: 'Rule 2',
    category: 'ELECTRONICS',
    conditions: [],
    default: true
  },
  {
    id: 3,
    name: 'Rule 3',
    category: 'HOME_EQUIPMENTS',
    conditions: [],
    default: true
  }
];
