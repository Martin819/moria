import React, { Fragment } from 'react';
import { Col, FormGroup, FormFeedback, Label, Input } from 'reactstrap';
import { allCategories } from '../../../constants/rules';

class RuleFormGroup extends React.Component {
  render() {
    return (
      <Fragment>
        <FormGroup row>
          <Label for="name" sm={2}>
            Rule name
          </Label>
          <Col sm={4}>
            <Input
              name="name"
              id="name"
              placeholder="Rule name"
              onChange={e => this.props.handleChange(e)}
              value={this.props.name}
            />
          </Col>
        </FormGroup>
        <FormGroup row>
          <Label for="category" sm={2}>
            Category
          </Label>
          <Col sm={4}>
            <Input
              type="select"
              name="category"
              id="category"
              value={this.props.category}
              onChange={e => this.props.handleChange(e)}
            >
              {allCategories.map(c => (
                <option key={c.id} value={c.id}>
                  {c.text}
                </option>
              ))}
            </Input>
          </Col>
        </FormGroup>
        <FormGroup row>
          <Label for="partyName" sm={2}>
            Party name
          </Label>
          <Col sm={10}>
            <Input
              name="partyName"
              id="partyName"
              placeholder="Party name includes"
              onChange={e => this.props.handleChange(e)}
              value={this.props.partyName}
            />
          </Col>
        </FormGroup>
        <FormGroup row>
          <Label for="amount" sm={2}>
            Amount
          </Label>
          <Col sm={4}>
            <Input type="select" name="compare" id="compare" onChange={e => this.props.handleChange(e)}>
              <option>Less than</option>
              <option>More than</option>
            </Input>
          </Col>
          <Col sm={5}>
            <Input
              invalid={isNaN(this.props.amount)}
              name="amount"
              id="amount"
              placeholder="100.00"
              value={this.props.amount}
              className="text-right"
              onChange={e => this.props.handleChange(e)}
            />
            <FormFeedback>Please specify a valid number.</FormFeedback>
          </Col>
          <Label for="amount" sm={1}>
            CZK
          </Label>
        </FormGroup>
        <FormGroup row>
          <Label for="transactionType" sm={2}>
            Direction
          </Label>
          <Col sm={10}>
            <Input type="select" name="transactionType" id="transactionType" onChange={e => this.props.handleChange(e)}>
              <option>Incoming</option>
              <option>Outgoing</option>
              <option>Both</option>
            </Input>
          </Col>
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
}

export default RuleFormGroup;
