import React, { Fragment } from 'react';
import { ExpansionPanelDetails, Grid, Typography } from '@material-ui/core';
import { Row, Col, Container } from 'reactstrap';
import { TransactionCategories } from '../../../constants/categories';

const TransactionItemPanelDetail = props => {
  const { detail, accountPreferredColor, categories } = props;
  return (
    <ExpansionPanelDetails style={{ borderLeft: `6px solid ${accountPreferredColor}` }}>
      <Grid item container direction="column">
        {categories && (
          <Col className="p-0">
            <hr />
            <Typography variant="title" className="mb-1">
              Category breakdown:
            </Typography>
            {Object.entries(categories).map(entry => {
              const categoryId = entry[0];
              const amount = entry[1];
              const category = Object.values(TransactionCategories).find(c => c.id === parseInt(categoryId));
              const categoryText = category === undefined || category === null ? 'Unknown category' : category.text;
              return (
                <Row noGutters={true}>
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
            <Row noGutters={true}>
              <Col xs={2}>{it[0]}:</Col>
              <Col xs="auto">{it[1]}</Col>
            </Row>
          ))}
        </Col>
      </Grid>
    </ExpansionPanelDetails>
  );
};

export default TransactionItemPanelDetail;
