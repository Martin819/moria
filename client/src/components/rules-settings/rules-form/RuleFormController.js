import React from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Form } from 'reactstrap';
import RuleFormBodyCommon from './RuleFormBodyCommon';
import { TransactionValueOperators, TransactionDirections, TransactionTypes } from '../../../constants/transactions';
import { IncomingTransactionCategories } from '../../../constants/categories';

class RuleFormController extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
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
      this.setState({
        ruleName: editedRule.ruleName,
        partyName: editedRule.partyName,
        categoryId: editedRule.categoryId,
        direction: editedRule.direction,
        transactionType: editedRule.transactionType,
        valueFrom: editedRule.valueFrom.toString() || '',
        valueTo: editedRule.valueTo.toString() || '',
        partyAccountPrefix: editedRule.partyAccountPrefix,
        partyAccountNumber: editedRule.partyAccountNumber,
        partyBankCode: editedRule.partyBankCode,
        payerMessage: editedRule.payerMessage,
        payeeMessage: editedRule.payeeMessage,
        constantSymbol: editedRule.constantSymbol,
        variableSymbol: editedRule.variableSymbol,
        specificSymbol: editedRule.specificSymbol,
        bookingTimeFrom: editedRule.bookingTimeFrom,
        bookingTimeTo: editedRule.bookingTimeTo,
        cardNumber: editedRule.cardNumber,
        compare: editedRule.compare
      });
    }
  }

  handleSubmit = event => {
    event.preventDefault();
    this.props.toggleModal();
    this.props.handleRuleSubmit({ ...this.state });
  };

  handleChange = event => {
    this.setState({ [event.target.name]: event.target.value });
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

export default RuleFormController;
