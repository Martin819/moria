import React from 'react';
import AccountBalanceInfo from './AccountBalanceInfo';
import { Grid } from '@material-ui/core';

const dummyData = [
  { accountNumber: '873113211/2010', accountOwner: 'Legolas', accountPreferredColor: 'orange', balance: 5000 },
  { accountNumber: '512345678/6210', accountOwner: 'Bilbo Baggins', accountPreferredColor: 'green', balance: 80000 },
  { accountNumber: '791919191/2700', accountOwner: 'Gandalf the White', accountPreferredColor: 'blue', balance: 133004 }
];

const AccountOverviewArea = props => {
  const realAccount = {
    accountNumber: '717717717/0800',
    accountOwner: 'Gimli, son of Gloin',
    accountPreferredColor: 'red',
    balance: props.balance || 0
  };
  const accounts = [realAccount, ...dummyData];
  return (
    <div>
      <h2>Accounts</h2>
      <Grid container direction="row" spacing={24}>
        {accounts.map((account, i) => {
          return (
            <Grid item key={i}>
              <AccountBalanceInfo accountInfo={account} />
            </Grid>
          );
        })}
      </Grid>
    </div>
  );
};

export default AccountOverviewArea;
