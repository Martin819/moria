export const OutgoingTransactionCategories = Object.freeze({
  FOOD: { id: 111, text: 'Food' },
  ALCOHOL: { id: 112, text: 'Alcohol' },
  APPAREL_FASHION: { id: 113, text: 'Apparel & fashion' },
  HOME_EQUIPMENT: { id: 114, text: 'Home equipment' },
  FUEL: { id: 115, text: 'Fuel' },
  UTILITIES: { id: 116, text: 'Utilities' },
  TOBACCO_AND_PRESS: { id: 117, text: 'Tobacco & press' },
  PHONE_TV_INTERNET_ETC: { id: 118, text: 'Phone / TV / Internet' },
  FARE: { id: 119, text: 'Fare' },
  RENT: { id: 120, text: 'Rent' },
  REAL_ESTATE: { id: 121, text: 'Real estate' },
  SPORT_AND_LEISURE: { id: 122, text: 'Sport & leisure' },
  HEALTH_AND_BEAUTY: { id: 123, text: 'Health & beauty' },
  ENTERTAINMENT: { id: 124, text: 'Entertainment' },
  TRAVELLING_AND_ACCOMMODATION: { id: 125, text: 'Travelling & accommodation' },
  ELECTRONICS: { id: 126, text: 'Electronics' },
  GAMBLING: { id: 127, text: 'Gambling' },
  LOANS_AND_MORTGAGES: { id: 128, text: 'Loans & mortgages' },
  INSURANCE: { id: 129, text: 'Insurance' },
  GIFTS: { id: 130, text: 'Gifts' },
  OTHER: { id: 131, text: 'Other' }
});

export const IncomingTransactionCategories = Object.freeze({
  I_SALARY_OR_WAGE: { id: 11, text: 'Salary / wage' },
  I_PENSION: { id: 12, text: 'Pension' },
  I_SOCIAL_ASSISTANCE: { id: 13, text: 'Welfare' },
  I_BUSINESS: { id: 14, text: 'Business' },
  I_GAMBLING: { id: 15, text: 'Gambling' },
  I_RENT: { id: 16, text: 'Rent' },
  I_LOANS: { id: 17, text: 'Loans' },
  I_POCKET_MONEY: { id: 18, text: 'Pocket money' },
  I_GIFTS: { id: 19, text: 'Gifts' },
  I_OTHER: { id: 20, text: 'Other' }
});

export const SpecialCategories = Object.freeze({
  UNCATEGORIZED: { id: 0, text: 'Uncategorized' },
  SPLIT: { id: -1, text: 'Various categories' }
});

export const TransactionCategories = Object.freeze({
  ...IncomingTransactionCategories,
  ...OutgoingTransactionCategories,
  ...SpecialCategories
});
