import React, { Fragment, Component } from 'react';
import { ExpansionPanelDetails, Grid, Typography, IconButton } from '@material-ui/core';
import ClearIcon from '@material-ui/icons/Clear';
import { Row, Col, Container } from 'reactstrap';
import { TransactionCategories } from '../../../constants/categories';

class TransactionItemPanelDetail extends Component {
  handleTransactionUnsplit = (event, id) => {
    event.preventDefault();
    const transactionUnsplitDto = {
      transactionId: parseInt(this.props.transactionId),
      id: id
    };
    this.props.handleTransactionUnsplit(transactionUnsplitDto);
  };
  render() {
    const { detail, accountPreferredColor, categories } = this.props;
    return (
      <ExpansionPanelDetails style={{ borderLeft: `6px solid ${accountPreferredColor}` }}>
        <Grid item container direction="column">
          {categories && (
            <Col className="p-0">
              <hr />
              <Typography variant="title" className="mb-1">
                Category breakdown:
              </Typography>
              {Object.entries(categories).map((entry, i) => {
                const categoryId = entry[0];
                const amount = entry[1];
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
                        onClick={e => this.handleTransactionUnsplit(e, i)}
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
        </Grid>
      </ExpansionPanelDetails>
    );
  }
}

export default TransactionItemPanelDetail;
