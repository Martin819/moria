import React, { Fragment } from 'react';
import { Row, Col, FormGroup, FormFeedback, Label, Input } from 'reactstrap';

const prefixRegex = /^[0-9]{0,8}$/;
const bankAccountRegex = /^[0-9]{0,12}$/;
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
            maxLength="8"
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
            maxLength="12"
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
              placeholder="Constant symbol"
              onChange={e => handleChange(e)}
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
              placeholder="Variable symbol"
              bsSize="sm"
              value={variableSymbol}
              onChange={e => handleChange(e)}
            />
          </FormGroup>
        </Col>
        <Col md={4}>
          <FormGroup>
            <Label for="specificSymbol">Specific symbol</Label>
            <Input
              name="specificSymbol"
              id="specificSymbol"
              placeholder="Specific symbol"
              bsSize="sm"
              value={specificSymbol}
              onChange={e => handleChange(e)}
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
            onChange={e => handleChange(e)}
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
            onChange={e => handleChange(e)}
          />
        </Col>
      </FormGroup>
    </Fragment>
  );
};

export default RuleFormBodyBankTransfers;
