export const FOOD = { id: 'FOOD', text: 'Food' };
export const ALCOHOL = { id: 'ALCOHOL', text: 'Alcohol' };
export const APPAREL_FASHION = { id: 'APPAREL_FASHION', text: 'Apparel & fashion' };
export const HOME_EQUIPMENT = { id: 'HOME_EQUIPMENT', text: 'Home equipment' };
export const FUEL = { id: 'FUEL', text: 'Fuel' };
export const UTILITIES = { id: 'UTILITIES', text: 'Utilities' };
export const TOBBACO_PRESS = { id: 'TOBBACO_PRESS', text: 'Tobbaco & press' };
export const PHONE_TV_INTERNET = { id: 'PHONE_TV_INTERNET', text: 'Phone / TV / Internet' };
export const FARE = { id: 'FARE', text: 'Fare' };
export const RENT = { id: 'RENT', text: 'Rent' };
export const REAL_ESTATE = { id: 'REAL_ESTATE', text: 'Real estate' };
export const SPORT_AND_LEISURE = { id: 'SPORT_AND_LEISURE', text: 'Sport & leisure' };
export const HEALTH_AND_BEAUTY = { id: 'HEALTH_AND_BEAUTY', text: 'Health & beauty' };
export const ENTERTAINMENT = { id: 'ENTERTAINMENT', text: 'Entertainment' };
export const TRAVELLING_ACCOMODATION = { id: 'TRAVELLING_ACCOMODATION', text: 'Travelling & accomodation' };
export const ELECTRONICS = { id: 'ELECTRONICS', text: 'Electronics' };
export const GAMBLING = { id: 'GAMBLING', text: 'Gambling' };
export const LOANS_AND_MORTGAGES = { id: 'LOANS_AND_MORTGAGES', text: 'Loans & mortgages' };
export const INSURANCE = { id: 'INSURANCE', text: 'Insurance' };
export const GIFTS = { id: 'GIFTS', text: 'Gifts' };
export const OTHER = { id: 'OTHER', text: 'Other' };

export const I_SALARY_OR_WAGE = { id: 'I_SALARY_OR_WAGE', text: 'Salary or wage' };
export const I_PENSION = { id: 'I_PENSION', text: 'Pension' };
export const I_SOCIAL_ASSISTANCE = { id: 'I_SOCIAL_ASSISTANCE', text: 'Welfare' };
export const I_BUSINESS = { id: 'I_BUSINESS', text: 'Business' };
export const I_GAMBLING = { id: 'I_GAMBLING', text: 'Gambling' };
export const I_RENT = { id: 'I_RENT', text: 'Rent' };
export const I_LOANS = { id: 'I_LOANS', text: 'Loans' };
export const I_POCKET_MONEY = { id: 'I_POCKET_MONEY', text: 'Pocket money' };
export const I_GIFTS = { id: 'I_GIFTS', text: 'Gifts' };
export const I_OTHER = { id: 'I_OTHER', text: 'Other' };

export const allIncomingCategories = [
  I_SALARY_OR_WAGE,
  I_PENSION,
  I_SOCIAL_ASSISTANCE,
  I_BUSINESS,
  I_GAMBLING,
  I_RENT,
  I_LOANS,
  I_POCKET_MONEY,
  I_GIFTS,
  I_OTHER
];

export const allOutgoingCategories = [
  FOOD,
  ALCOHOL,
  APPAREL_FASHION,
  HOME_EQUIPMENT,
  FUEL,
  UTILITIES,
  TOBBACO_PRESS,
  PHONE_TV_INTERNET,
  FARE,
  RENT,
  REAL_ESTATE,
  SPORT_AND_LEISURE,
  HEALTH_AND_BEAUTY,
  ENTERTAINMENT,
  TRAVELLING_ACCOMODATION,
  ELECTRONICS,
  GAMBLING,
  LOANS_AND_MORTGAGES,
  INSURANCE,
  GIFTS,
  OTHER
];

export const allCategories = [...allIncomingCategories, ...allOutgoingCategories];
