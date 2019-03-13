import React, { Fragment } from 'react';
import { Row, Col, FormGroup, FormFeedback, Label, Input } from 'reactstrap';
import { allOutgoingCategories, allIncomingCategories } from '../../../constants/categories';
import { allTransactionDirections, allTransactionTypes } from '../../../constants/transactions';

const prefixRegex = /^[0-9]{0,6}$/;
const bankAccountRegex = /^[0-9]{0,10}$/;
const bankCodeRegex = /^[0-9]{4}$/;

class RuleFormBody extends React.Component {
  render() {
    console.log(this.props);
    const amountChoiceComponent = this.createAmountChoice(this.props.compare || 'LESS_THAN');
    const activeCategories =
      this.props.transactionDirection === 'INCOMING' ? allIncomingCategories : allOutgoingCategories;
    return (
      <Fragment>
        <Row form>
          <Col md={6}>
            <FormGroup>
              <Label for="name">Rule name</Label>
              <Input
                name="name"
                id="name"
                placeholder="Rule name"
                onChange={e => this.props.handleChange(e)}
                value={this.props.name}
                bsSize="sm"
              />
            </FormGroup>
          </Col>
          <Col md={6}>
            <FormGroup>
              <Label for="category">Category</Label>
              <Input
                type="select"
                name="category"
                id="category"
                bsSize="sm"
                value={this.props.category}
                onChange={e => this.props.handleChange(e)}
              >
                {activeCategories.map(c => (
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
                onChange={e => this.props.handleChange(e)}
                bsSize="sm"
              >
                {allTransactionTypes.map(type => (
                  <option key={type.id} value={type.id}>
                    {type.text}
                  </option>
                ))}
              </Input>
            </FormGroup>
          </Col>
          <Col md={6}>
            <FormGroup>
              <Label for="transactionDirection">Direction</Label>
              <Input
                type="select"
                name="transactionDirection"
                id="transactionDirection"
                bsSize="sm"
                value={this.props.transactionDirection}
                onChange={e => this.props.handleChange(e)}
              >
                {allTransactionDirections.map(direction => (
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
          <Label for="partyAccountPrefix" sm={2}>
            Party account
          </Label>
          <Col sm={2}>
            <Input
              name="partyAccountPrefix"
              id="partyAccountPrefix"
              className="text-right"
              placeholder="Prefix"
              invalid={!prefixRegex.test(this.props.partyAccountPrefix)}
              value={this.props.partyAccountPrefix}
              bsSize="sm"
              maxLength="6"
              onChange={e => this.props.handleChange(e)}
            />
            <FormFeedback>Invalid.</FormFeedback>
          </Col>
          {'-'}
          <Col sm={4}>
            <Input
              name="partyAccountNumber"
              id="partyAccountNumber"
              className="text-right"
              placeholder="Account number"
              invalid={!bankAccountRegex.test(this.props.partyAccountNumber)}
              value={this.props.partyAccountNumber}
              bsSize="sm"
              maxLength="10"
              onChange={e => this.props.handleChange(e)}
            />
            <FormFeedback>Invalid.</FormFeedback>
          </Col>
          {'/'}
          <Col sm={3}>
            <Input
              name="partyBankCode"
              id="partyBankCode"
              placeholder="Bank code"
              invalid={this.props.partyBankCode !== '' && !bankCodeRegex.test(this.props.partyBankCode)}
              value={this.props.partyBankCode}
              bsSize="sm"
              maxLength="4"
              onChange={e => this.props.handleChange(e)}
            />
            <FormFeedback>Invalid.</FormFeedback>
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
              <option value={'LESS_THAN'}>Less than</option>
              <option value={'MORE_THAN'}>More than</option>
              <option value={'BETWEEN'}>Between</option>
            </Input>
          </Col>
          {amountChoiceComponent}
        </FormGroup>

        <FormGroup row>
          <Label for="description" sm={2}>
            Description
          </Label>
          <Col sm={10}>
            <Input
              type="textarea"
              name="description"
              id="description"
              value={this.props.description}
              onChange={e => this.props.handleChange(e)}
            />
          </Col>
        </FormGroup>
      </Fragment>
    );
  }

  createAmountChoice = compare => {
    switch (compare) {
      case 'LESS_THAN':
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
      case 'MORE_THAN':
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
            {'-'}
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

export default RuleFormBody;
