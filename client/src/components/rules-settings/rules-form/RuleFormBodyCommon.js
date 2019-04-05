import React, { Fragment } from 'react';
import { Row, Col, FormGroup, FormFeedback, Label, Input } from 'reactstrap';
import { IncomingTransactionCategories, OutgoingTransactionCategories } from '../../../constants/categories';
import { TransactionValueOperators, TransactionDirections, TransactionTypes } from '../../../constants/transactions';
import RuleFormBodyBankTransfers from './RuleFormBodyBankTransfers';
import RuleFormBodyCardPayments from './RuleFormBodyCardPayments';

class RuleFormBodyCommon extends React.Component {
  render() {
    console.log(this.props);
    const amountChoiceComponent = this.createAmountChoice(this.props.compare || TransactionValueOperators.LESS_THAN.id);
    const activeCategories =
      this.props.direction === 'INCOMING' ? IncomingTransactionCategories : OutgoingTransactionCategories;
    return (
      <Fragment>
        <Row form>
          <Col md={6}>
            <FormGroup>
              <Label for="ruleName">Rule name</Label>
              <Input
                name="ruleName"
                id="ruleName"
                placeholder="Rule name"
                onChange={e => this.props.handleChange(e)}
                value={this.props.ruleName}
                bsSize="sm"
              />
            </FormGroup>
          </Col>
          <Col md={6}>
            <FormGroup>
              <Label for="categoryId">Target category</Label>
              <Input
                type="select"
                name="categoryId"
                id="categoryId"
                bsSize="sm"
                value={this.props.categoryId}
                onChange={e => this.props.handleChange(e)}
              >
                {Object.values(activeCategories).map(c => (
                  <option key={c.id} value={c.id}>
                    {c.text}
                  </option>
                ))}
              </Input>
            </FormGroup>
          </Col>
        </Row>
        <Row form>
          <Col md={6}>
            <FormGroup>
              <Label for="transactionType">Transaction type</Label>
              <Input
                type="select"
                name="transactionType"
                id="transactionType"
                value={this.props.transactionType}
                onChange={e => this.props.handleChange(e)}
                bsSize="sm"
              >
                {Object.values(TransactionTypes)
                  .filter(type => type !== TransactionTypes.CASH.id)
                  .map(type => (
                    <option key={type.id} value={type.id}>
                      {type.text}
                    </option>
                  ))}
              </Input>
            </FormGroup>
          </Col>
          <Col md={6}>
            <FormGroup>
              <Label for="direction">Direction</Label>
              <Input
                type="select"
                name="direction"
                id="direction"
                bsSize="sm"
                value={this.props.direction}
                onChange={e => this.props.handleChange(e)}
              >
                {Object.values(TransactionDirections).map(direction => (
                  <option key={direction.id} value={direction.id}>
                    {direction.text}
                  </option>
                ))}
              </Input>
            </FormGroup>
          </Col>
        </Row>
        <FormGroup row>
          <Label for="partyName" sm={2}>
            Party name
          </Label>
          <Col sm={10}>
            <Input
              name="partyName"
              id="partyName"
              placeholder="Party name includes"
              value={this.props.partyName}
              bsSize="sm"
              onChange={e => this.props.handleChange(e)}
            />
          </Col>
        </FormGroup>
        <FormGroup row>
          <Label for="amount" sm={2}>
            Amount (CZK)
          </Label>
          <Col sm={3}>
            <Input
              type="select"
              name="compare"
              id="compare"
              value={this.props.compare}
              bsSize="sm"
              onChange={e => this.props.handleChange(e)}
            >
              {Object.values(TransactionValueOperators).map(o => {
                return (
                  <option key={o.id} value={o.id}>
                    {o.text}
                  </option>
                );
              })}
            </Input>
          </Col>
          {amountChoiceComponent}
        </FormGroup>

        {this.props.transactionType === TransactionTypes.CARD.id ? (
          <RuleFormBodyCardPayments {...this.props} />
        ) : (
          <RuleFormBodyBankTransfers {...this.props} />
        )}
      </Fragment>
    );
  }

  createAmountChoice = compare => {
    switch (compare) {
      case TransactionValueOperators.LESS_THAN.id:
        return (
          <Col sm={6}>
            <Input
              invalid={isNaN(this.props.valueTo)}
              name="valueTo"
              id="valueTo"
              placeholder="100.00"
              value={this.props.valueTo}
              className="text-right"
              onChange={e => this.props.handleChange(e)}
              bsSize="sm"
              type="number"
              min="0.01"
              step="0.01"
            />
            <FormFeedback>Please specify a valid number.</FormFeedback>
          </Col>
        );
      case TransactionValueOperators.MORE_THAN.id:
        return (
          <Col sm={6}>
            <Input
              invalid={isNaN(this.props.valueFrom)}
              name="valueFrom"
              id="valueFrom"
              placeholder="100.00"
              value={this.props.valueFrom}
              className="text-right"
              onChange={e => this.props.handleChange(e)}
              bsSize="sm"
              type="number"
              min="0.01"
              step="0.01"
            />
            <FormFeedback>Please specify a valid number.</FormFeedback>
          </Col>
        );
      default:
        return (
          <Fragment>
            <Col sm={3}>
              <Input
                invalid={isNaN(this.props.valueFrom)}
                name="valueFrom"
                id="valueFrom"
                placeholder="100.00"
                value={this.props.valueFrom}
                className="text-right"
                onChange={e => this.props.handleChange(e)}
                bsSize="sm"
                type="number"
                min="0.01"
                step="0.01"
              />
              <FormFeedback>Please specify a valid number.</FormFeedback>
            </Col>
            {'–'}
            <Col sm={3}>
              <Input
                invalid={isNaN(this.props.valueTo)}
                name="valueTo"
                id="valueTo"
                placeholder="500.00"
                value={this.props.valueTo}
                className="text-right"
                onChange={e => this.props.handleChange(e)}
                bsSize="sm"
                type="number"
                min="0.01"
                step="0.01"
              />
              <FormFeedback>Please specify a valid number.</FormFeedback>
            </Col>
          </Fragment>
        );
    }
  };
}

export default RuleFormBodyCommon;
