import React from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Form } from 'reactstrap';
import RuleFormBodyCommon from './RuleFormBodyCommon';
import { TransactionValueOperators, TransactionDirections, TransactionTypes } from '../../../constants/transactions';
import {
  IncomingTransactionCategories,
  OutgoingTransactionCategories,
  TransactionCategories
} from '../../../constants/categories';

class RuleFormController extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      id: '',
      ruleName: '',
      partyName: '',
      categoryId: IncomingTransactionCategories.I_SALARY_OR_WAGE.id,
      direction: TransactionDirections.INCOMING.id,
      transactionType: TransactionTypes.PAYMENT_HOME.id,
      valueFrom: '',
      valueTo: '',
      partyAccountPrefix: '',
      partyAccountNumber: '',
      partyBankCode: '',
      payerMessage: '',
      payeeMessage: '',
      constantSymbol: '',
      variableSymbol: '',
      specificSymbol: '',
      bookingTimeFrom: '',
      bookingTimeTo: '',
      cardNumber: '',
      compare: TransactionValueOperators.LESS_THAN.id
    };
  }

  componentDidMount() {
    const { editedRule } = this.props;
    if (editedRule !== null) {
      let compare;
      if (editedRule.valueFrom === null) {
        compare = TransactionValueOperators.LESS_THAN.id;
      } else if (editedRule.valueTo === null) {
        compare = TransactionValueOperators.MORE_THAN.id;
      } else {
        compare = TransactionValueOperators.BETWEEN.id;
      }

      this.setState({
        id: editedRule.id,
        ruleName: editedRule.ruleName || '',
        partyName: editedRule.partyName || '',
        categoryId: editedRule.categoryId || '',
        direction: editedRule.direction || '',
        transactionType: editedRule.transactionType || '',
        valueFrom: editedRule.valueFrom || '',
        valueTo: editedRule.valueTo || '',
        partyAccountPrefix: editedRule.partyAccountPrefix || '',
        partyAccountNumber: editedRule.partyAccountNumber || '',
        partyBankCode: editedRule.partyBankCode || '',
        payerMessage: editedRule.payerMessage || '',
        payeeMessage: editedRule.payeeMessage || '',
        constantSymbol: editedRule.constantSymbol || '',
        variableSymbol: editedRule.variableSymbol || '',
        specificSymbol: editedRule.specificSymbol || '',
        bookingTimeFrom: editedRule.bookingTimeFrom || '',
        bookingTimeTo: editedRule.bookingTimeTo || '',
        cardNumber: editedRule.cardNumber || '',
        compare: compare
      });
    }
  }

  handleSubmit = event => {
    event.preventDefault();
    const newRule = this.validateForm({ ...this.state });
    this.nullEmptyValues(newRule);
    const { editedRule } = this.props;
    if (editedRule !== undefined && editedRule !== null) {
      this.props.handleRuleSubmitUpdate(newRule);
    } else {
      this.props.handleRuleSubmitCreate(newRule);
    }
  };

  nullEmptyValues = rule => {
    Object.entries(rule).forEach(entry => {
      const key = entry[0];
      const value = entry[1];
      if (typeof value === 'string' || value instanceof String) {
        if (value.trim() === '') {
          rule[key] = null;
        }
      }
    });
  };

  validateForm = newRule => {
    if (newRule.transactionType === TransactionTypes.CARD.id) {
      bankTransferFields.forEach(field => (newRule[field] = ''));
    } else {
      cardFields.forEach(field => (newRule[field] = ''));
    }

    if (newRule.compare === TransactionValueOperators.LESS_THAN.id) {
      newRule['valueFrom'] = '';
    } else if (newRule.compare === TransactionValueOperators.MORE_THAN.id) {
      newRule['valueTo'] = '';
    }

    delete newRule.compare;
    return newRule;
  };

  handleChange = event => {
    if (event.target.name === 'direction') {
      if (event.target.value === TransactionDirections.INCOMING.id) {
        this.setState({
          [event.target.name]: event.target.value,
          categoryId: IncomingTransactionCategories.I_SALARY_OR_WAGE.id
        });
      } else {
        this.setState({ [event.target.name]: event.target.value, categoryId: OutgoingTransactionCategories.FOOD.id });
      }
    } else if (event.target.name === 'cardNumber') {
      this.handleCreditCardInputChange(event);
    } else {
      this.setState({ [event.target.name]: event.target.value });
    }
  };

  handleCreditCardInputChange = event => {
    let value = event.target.value;
    let lastNumberPart = value.slice(value.lastIndexOf('-') + 1);
    if (lastNumberPart.length === 5) value = value.slice(0, value.length - 1) + '-' + value.slice(value.length - 1);
    if (value.lastIndexOf('-') === value.length - 1) value = value.slice(0, value.length - 1);
    lastNumberPart = value.slice(value.lastIndexOf('-') + 1);
    if (/^$|\d{1,4}$/.test(lastNumberPart)) {
      this.setState({ [event.target.name]: value });
    }
  };

  handleTimePickerChange = (name, value) => {
    if (value === null) {
      this.setState({ bookingTimeFrom: '', bookingTimeTo: '' });
      return;
    }
    const formattedValue = value.format('HH:mm');
    if (name === TIME_FROM) {
      if (this.state.bookingTimeTo === '') {
        this.setState({ bookingTimeFrom: formattedValue, bookingTimeTo: '23:59' });
      } else {
        this.setState({ bookingTimeFrom: formattedValue });
      }
    } else {
      if (this.state.bookingTimeFrom === '') {
        this.setState({ bookingTimeFrom: '00:00', bookingTimeTo: formattedValue });
      } else {
        this.setState({ bookingTimeTo: formattedValue });
      }
    }
  };

  render() {
    return (
      <div>
        <Modal
          size="lg"
          isOpen={this.props.isModalOpened}
          toggle={this.props.toggleModal}
          className={this.props.className}
        >
          <Form onSubmit={this.handleSubmit}>
            <ModalHeader toggle={this.props.toggleModal}>Rule setup</ModalHeader>
            <ModalBody>
              <RuleFormBodyCommon
                handleChange={this.handleChange}
                handleTimePickerChange={this.handleTimePickerChange}
                {...this.state}
              />
            </ModalBody>
            <ModalFooter>
              <Button color="primary" type="submit">
                Save
              </Button>
              <Button color="secondary" onClick={this.props.toggleModal}>
                Cancel
              </Button>
            </ModalFooter>
          </Form>
        </Modal>
      </div>
    );
  }
}

const TIME_FROM = 'timeFrom';
const TIME_TO = 'timeTo';
const cardFields = ['bookingTimeFrom', 'bookingTimeTo', 'cardNumber'];
const bankTransferFields = [
  'partyAccountNumber',
  'partyAccountPrefix',
  'partyBankCode',
  'payerMessage',
  'payeeMessage',
  'constantSymbol',
  'variableSymbol',
  'specificSymbol'
];

export default RuleFormController;
