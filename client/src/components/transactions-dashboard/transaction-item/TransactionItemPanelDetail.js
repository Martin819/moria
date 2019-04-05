import React, { Component } from 'react';
import { ExpansionPanelDetails, Grid, Typography, IconButton } from '@material-ui/core';
import ClearIcon from '@material-ui/icons/Clear';
import { Row, Col } from 'reactstrap';
import { TransactionCategories } from '../../../constants/categories';

class TransactionItemPanelDetail extends Component {
  handleTransactionUnsplit = (event, childTransactionId) => {
    event.preventDefault();
    this.props.handleTransactionUnsplit(this.props.transactionId, childTransactionId);
  };

  render() {
    const { detail, accountPreferredColor, childTransactionsList } = this.props;
    return (
      <ExpansionPanelDetails style={{ borderLeft: `6px solid ${accountPreferredColor}` }}>
        <Grid item container direction="column">
          {childTransactionsList && childTransactionsList.length > 0 && (
            <Col className="p-0">
              <hr />
              <Typography variant="title" className="mb-1">
                Split into categories:
              </Typography>
              {childTransactionsList
                .filter(childTransaction => childTransaction.amount > 0)
                .sort((a, b) => b.amount - a.amount)
                .map((childTransaction, i) => {
                  const categoryId = childTransaction.categoryId;
                  const amount = childTransaction.amount;
                  const category = Object.values(TransactionCategories).find(c => c.id === parseInt(categoryId));
                  const categoryText = category === undefined || category === null ? 'Unknown category' : category.text;
                  const isUncategorized = categoryId == TransactionCategories.UNCATEGORIZED.id;
                  return (
                    <Row noGutters={true} key={i}>
                      <Col xs="auto">
                        <IconButton
                          aria-label="Delete"
                          className="p-0 mb-1"
                          disabled={isUncategorized}
                          onClick={e => this.handleTransactionUnsplit(e, childTransaction.id)}
                        >
                          <ClearIcon fontSize="small" color={isUncategorized ? 'disabled' : 'error'} />
                        </IconButton>
                      </Col>
                      <Col xs={2}> {categoryText}:</Col>
                      <Col xs="auto">
                        {amount.toLocaleString('cs-cz', {
                          style: 'currency',
                          currency: 'CZK'
                        })}
                      </Col>
                    </Row>
                  );
                })}
            </Col>
          )}

          {detail && (
            <Col className="p-0">
              <hr />
              <Typography variant="title" className="mb-1">
                Detail:
              </Typography>
              {Object.entries(detail).map((it, i) => (
                <Row noGutters={true} key={i}>
                  <Col xs={2}>{it[0]}:</Col>
                  <Col xs="auto">{it[1]}</Col>
                </Row>
              ))}
            </Col>
          )}
        </Grid>
      </ExpansionPanelDetails>
    );
  }
}

export default TransactionItemPanelDetail;
