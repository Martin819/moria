export const IncomingTransactionCategories = Object.freeze({
  I_SALARY_OR_WAGE: { id: 'I_SALARY_OR_WAGE', text: 'Salary or wage' },
  I_PENSION: { id: 'I_PENSION', text: 'Pension' },
  I_SOCIAL_ASSISTANCE: { id: 'I_SOCIAL_ASSISTANCE', text: 'Welfare' },
  I_BUSINESS: { id: 'I_BUSINESS', text: 'Business' },
  I_GAMBLING: { id: 'I_GAMBLING', text: 'Gambling' },
  I_RENT: { id: 'I_RENT', text: 'Rent' },
  I_LOANS: { id: 'I_LOANS', text: 'Loans' },
  I_POCKET_MONEY: { id: 'I_POCKET_MONEY', text: 'Pocket money' },
  I_GIFTS: { id: 'I_GIFTS', text: 'Gifts' },
  I_OTHER: { id: 'I_OTHER', text: 'Other' }
});

export const OutgoingTransactionCategories = Object.freeze({
  FOOD: { id: 'FOOD', text: 'Food' },
  ALCOHOL: { id: 'ALCOHOL', text: 'Alcohol' },
  APPAREL_FASHION: { id: 'APPAREL_FASHION', text: 'Apparel & fashion' },
  HOME_EQUIPMENT: { id: 'HOME_EQUIPMENT', text: 'Home equipment' },
  FUEL: { id: 'FUEL', text: 'Fuel' },
  UTILITIES: { id: 'UTILITIES', text: 'Utilities' },
  TOBACCO_AND_PRESS: { id: 'TOBACCO_AND_PRESS', text: 'Tobacco & press' },
  PHONE_TV_INTERNET_ETC: { id: 'PHONE_TV_INTERNET_ETC', text: 'Phone / TV / Internet' },
  FARE: { id: 'FARE', text: 'Fare' },
  RENT: { id: 'RENT', text: 'Rent' },
  REAL_ESTATE: { id: 'REAL_ESTATE', text: 'Real estate' },
  SPORT_AND_LEISURE: { id: 'SPORT_AND_LEISURE', text: 'Sport & leisure' },
  HEALTH_AND_BEAUTY: { id: 'HEALTH_AND_BEAUTY', text: 'Health & beauty' },
  ENTERTAINMENT: { id: 'ENTERTAINMENT', text: 'Entertainment' },
  TRAVELLING_AND_ACCOMMODATION: { id: 'TRAVELLING_AND_ACCOMMODATION', text: 'Travelling & accommodation' },
  ELECTRONICS: { id: 'ELECTRONICS', text: 'Electronics' },
  GAMBLING: { id: 'GAMBLING', text: 'Gambling' },
  LOANS_AND_MORTGAGES: { id: 'LOANS_AND_MORTGAGES', text: 'Loans & mortgages' },
  INSURANCE: { id: 'INSURANCE', text: 'Insurance' },
  GIFTS: { id: 'GIFTS', text: 'Gifts' },
  OTHER: { id: 'OTHER', text: 'Other' }
});

export const TransactionCategories = Object.freeze({
  ...IncomingTransactionCategories,
  ...OutgoingTransactionCategories
});
