import React, { Fragment } from 'react';
import { Col, FormGroup, FormFeedback, Label, Input } from 'reactstrap';

const prefixRegex = /^[0-9]{0,6}$/;
const bankAccountRegex = /^[0-9]{0,10}$/;
const bankCodeRegex = /^[0-9]{4}$/;

const RuleFormBodyBankTransfers = props => {
  const { partyAccountPrefix, partyAccountNumber, partyBankCode, handleChange } = props;
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
    </Fragment>
  );
};

export default RuleFormBodyBankTransfers;
