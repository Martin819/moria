import React, { Component, Fragment } from 'react';
import { connect } from 'react-redux';
import { computeAccountBalance } from '../../selectors/statisticsSelector';
import AccountOverviewArea from '../../components/transactions-dashboard/accounts-overview/AccountOverviewArea';

class AccountsContainer extends Component {
  render() {
    return (
      <Fragment>
        <AccountOverviewArea balance={this.props.accountBalance} />
      </Fragment>
    );
  }
}

const makeMapStateToProps = () => {
  const mapStateToProps = state => ({
    accountBalance: computeAccountBalance(state)
  });
  return mapStateToProps;
};

export default connect(
  makeMapStateToProps,
  null
)(AccountsContainer);
