import React, { Fragment } from 'react';
import { Col, FormGroup, Label, Input } from 'reactstrap';
import moment from 'moment';
import _ from 'lodash';
import TimePicker from 'rc-time-picker';
import 'rc-time-picker/assets/index.css';

const RuleFormBodyCardPayments = props => {
  const { bookingTimeFrom: timeFrom, bookingTimeTo: timeTo, handleTimePickerChange } = props;
  return (
    <Fragment>
      <FormGroup row>
        <Label for="time" sm={2}>
          Time
        </Label>
        <Col sm={2}>
          <TimePicker
            showSecond={false}
            disabledHours={() => (timeTo ? _.range(moment(timeTo, 'HH:mm').hour() + 1, 24) : [])}
            disabledMinutes={() =>
              timeFrom && timeTo && moment(timeFrom, 'HH:mm').hour() === moment(timeTo, 'HH:mm').hour()
                ? _.range(moment(timeTo, 'HH:mm').minute() + 1, 60)
                : []
            }
            value={timeFrom ? moment(timeFrom, 'HH:mm') : null}
            onChange={value => handleTimePickerChange('timeFrom', value)}
            hideDisabledOptions
          />
        </Col>
        {'-'}
        <Col sm={2}>
          <TimePicker
            showSecond={false}
            disabledHours={() => (timeFrom ? _.range(0, moment(timeFrom, 'HH:mm').hour()) : [])}
            disabledMinutes={() =>
              timeFrom && timeTo && moment(timeFrom, 'HH:mm').hour() === moment(timeTo, 'HH:mm').hour()
                ? _.range(0, moment(timeFrom, 'HH:mm').minute())
                : []
            }
            value={timeTo ? moment(timeTo, 'HH:mm') : null}
            onChange={value => handleTimePickerChange('timeTo', value)}
            hideDisabledOptions
          />
        </Col>
      </FormGroup>
      <FormGroup row>
        <Label for="cardNumber" sm={2}>
          Card number
        </Label>
        <Col sm={10}>
          <Input
            name="cardNumber"
            id="cardNumber"
            placeholder="Card number"
            maxLength="19"
            value={props.cardNumber}
            bsSize="sm"
            onChange={e => props.handleChange(e)}
          />
        </Col>
      </FormGroup>
    </Fragment>
  );
};

export default RuleFormBodyCardPayments;
