import React, { Fragment } from 'react';
import { Row, Col, FormGroup, FormFeedback, Label, Input } from 'reactstrap';

const prefixRegex = /^[0-9]{0,6}$/;
const bankAccountRegex = /^[0-9]{0,10}$/;
const bankCodeRegex = /^[0-9]{4}$/;

const RuleFormBodyBankTransfers = props => {
  const {
    partyAccountPrefix,
    partyAccountNumber,
    partyBankCode,
    handleChange,
    constantSymbol,
    variableSymbol,
    specificSymbol,
    payerMessage,
    payeeMessage
  } = props;

  return (
    <Fragment>
      <FormGroup row>
        <Label for="partyAccount" sm={2}>
          Party account
        </Label>
        <Col sm={2}>
          <Input
            name="partyAccountPrefix"
            id="partyAccountPrefix"
            className="text-right"
            placeholder="Prefix"
            invalid={!prefixRegex.test(partyAccountPrefix)}
            value={partyAccountPrefix}
            bsSize="sm"
            maxLength="6"
            onChange={e => handleChange(e)}
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
            invalid={!bankAccountRegex.test(partyAccountNumber)}
            value={partyAccountNumber}
            bsSize="sm"
            maxLength="10"
            onChange={e => handleChange(e)}
          />
          <FormFeedback>Invalid.</FormFeedback>
        </Col>
        {'/'}
        <Col sm={3}>
          <Input
            name="partyBankCode"
            id="partyBankCode"
            placeholder="Bank code"
            invalid={partyBankCode !== '' && !bankCodeRegex.test(partyBankCode)}
            value={partyBankCode}
            bsSize="sm"
            maxLength="4"
            onChange={e => handleChange(e)}
          />
          <FormFeedback>Invalid.</FormFeedback>
        </Col>
      </FormGroup>
      <Row form>
        <Col md={4}>
          <FormGroup>
            <Label for="constantSymbol">Constant symbol</Label>
            <Input
              name="constantSymbol"
              id="constantSymbol"
              placeholder="Rule name"
              onChange={e => this.props.handleChange(e)}
              value={constantSymbol}
              bsSize="sm"
            />
          </FormGroup>
        </Col>
        <Col md={4}>
          <FormGroup>
            <Label for="variableSymbol">Variable symbol</Label>
            <Input
              name="variableSymbol"
              id="variableSymbol"
              bsSize="sm"
              value={variableSymbol}
              onChange={e => this.props.handleChange(e)}
            />
          </FormGroup>
        </Col>
        <Col md={4}>
          <FormGroup>
            <Label for="specificSymbol">Specific symbol</Label>
            <Input
              name="specificSymbol"
              id="specificSymbol"
              bsSize="sm"
              value={specificSymbol}
              onChange={e => this.props.handleChange(e)}
            />
          </FormGroup>
        </Col>
      </Row>
      <FormGroup row>
        <Label for="payerMessage" sm={2}>
          Payer msg.
        </Label>
        <Col sm={10}>
          <Input
            name="payerMessage"
            id="payerMessage"
            placeholder="Payer message includes"
            value={payerMessage}
            bsSize="sm"
            onChange={e => this.props.handleChange(e)}
          />
        </Col>
      </FormGroup>
      <FormGroup row>
        <Label for="payeeMessage" sm={2}>
          Payee msg.
        </Label>
        <Col sm={10}>
          <Input
            name="payeeMessage"
            id="payeeMessage"
            placeholder="Payee message includes"
            value={payeeMessage}
            bsSize="sm"
            onChange={e => this.props.handleChange(e)}
          />
        </Col>
      </FormGroup>
    </Fragment>
  );
};

export default RuleFormBodyBankTransfers;
