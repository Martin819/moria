import React, { Component } from 'react';
import PropTypes from 'prop-types';
import TransactionFilterContainer from '../../../containers/transactions/TransactionFilterContainer';
import TransactionItem from '../transaction-item/TransactionItem';
import Pagination from 'react-js-pagination';
import { withStyles, Grid } from '@material-ui/core';
import { Spinner } from 'reactstrap';

class TransactionsList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      activePage: 1,
      itemsCountPerPage: 8
    };
  }

  componentDidUpdate(prevProps) {
    if (this.props.transactions.length != prevProps.transactions.length) {
      this.setState({ activePage: 1 });
    }
  }

  handlePageChange = pageNumber => {
    this.setState({ activePage: pageNumber });
  };

  render() {
    const { activePage, itemsCountPerPage } = this.state;
    const {
      classes,
      transactions,
      isLoading,
      handleTransactionCategoryUpdate,
      handleTransactionSplit,
      handleTransactionUnsplit
    } = this.props;

    const nonChildTransactions = transactions.filter(t => t.parentId === null);
    const transactionItems = nonChildTransactions
      .slice((activePage - 1) * itemsCountPerPage, (activePage - 1) * itemsCountPerPage + itemsCountPerPage)
      .map((transaction, index) => {
        return (
          <TransactionItem
            key={transaction.id}
            index={index}
            {...transaction}
            handleTransactionCategoryUpdate={handleTransactionCategoryUpdate}
            handleTransactionSplit={handleTransactionSplit}
            handleTransactionUnsplit={handleTransactionUnsplit}
          />
        );
      });

    return (
      <div className={classes.root}>
        <h2>Transactions</h2>
        <Grid container direction="column" spacing={8}>
          <Grid item xs>
            <TransactionFilterContainer />
          </Grid>
          <Grid item xs>
            {isLoading ? (
              <div className="text-center">
                <Spinner type="grow" color="primary" />
              </div>
            ) : (
              transactionItems
            )}
          </Grid>
          <Grid item container xs justify="center" className="mt-3">
            <Pagination
              itemClass="page-item"
              linkClass="page-link"
              pageRangeDisplayed={5}
              activePage={activePage}
              totalItemsCount={nonChildTransactions.length}
              itemsCountPerPage={itemsCountPerPage}
              onChange={this.handlePageChange}
            />
          </Grid>
        </Grid>
      </div>
    );
  }
}

TransactionsList.propTypes = {
  classes: PropTypes.object.isRequired,
  transactions: PropTypes.array.isRequired,
  isLoading: PropTypes.bool.isRequired
};

const styles = theme => ({
  root: {
    width: '100%',
    marginTop: '20px',
    marginBottom: '70px'
  }
});

export default withStyles(styles)(TransactionsList);
