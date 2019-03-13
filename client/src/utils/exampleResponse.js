export const transactionsData = {
  transactions: [
    {
      id: 1614565,
      valueDate: '2018-12-24T15:41:50.126Z',
      transactionType: 'BANK_TRANSFER',
      value: {
        amount: 44000,
        currency: 'CZK'
      },
      partyDescription: 'Unicorn',
      category: 'OTHER',
      message: 'Wage',
      direction: 'INCOMING'
    },
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
        currency: 'CZK'
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
        currency: 'CZK'
      },
      partyDescription: 'Albert',
      category: 'HOME_EQUIPMENT',
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
      category: 'HOME_EQUIPMENT',
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
      category: 'HOME_EQUIPMENT',
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
      category: 'HOME_EQUIPMENT',
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
      category: 'HOME_EQUIPMENT',
      message: 'Additional info',
      direction: 'OUTGOING'
    }
  ]
};

export const rulesData = [
  {
    id: 1,
    name: 'Rule 1',
    partyName: 'Billa',
    partyAccountPrefix: '',
    partyAccountNumber: '123456',
    partyBankCode: '0300',
    category: 'FOOD',
    valueFrom: '',
    valueTo: 500,
    compare: 'LESS_THAN',
    transactionDirection: 'OUTGOING',
    description: 'My rule one, buying food from Billa'
  },
  {
    id: 2,
    name: 'Rule 2',
    partyName: 'Albert',
    partyAccountPrefix: '',
    partyAccountNumber: '123456',
    partyBankCode: '0300',
    category: 'FOOD',
    valueFrom: '',
    valueTo: 500,
    compare: 'LESS_THAN',
    transactionDirection: 'OUTGOING',
    description: 'My rule two'
  },
  {
    id: 3,
    name: 'Rule 3',
    partyName: 'OBI',
    partyAccountPrefix: '',
    partyAccountNumber: '9879856',
    partyBankCode: '0300',
    category: 'HOME_EQUIPMENT',
    valueFrom: 1000,
    valueTo: '',
    compare: 'MORE_THAN',
    transactionDirection: 'OUTGOING',
    description: 'My rule three'
  },
  {
    id: 4,
    name: 'Rule 4',
    partyName: 'ELECTRO WORLD',
    partyAccountPrefix: '',
    partyAccountNumber: '77777',
    partyBankCode: '0300',
    category: 'ELECTRONICS',
    valueFrom: 1000,
    valueTo: '',
    compare: 'MORE_THAN',
    transactionDirection: 'OUTGOING',
    description: 'My rule four'
  }
];
