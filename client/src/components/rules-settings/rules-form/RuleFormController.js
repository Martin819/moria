import React from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Form } from 'reactstrap';
import RuleFormBody from './RuleFormBody';
import { TransactionValueOperators, TransactionDirections, TransactionTypes } from '../../../constants/transactions';
import { allIncomingCategories } from '../../../constants/categories';

class RuleFormController extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      name: '',
      category: allIncomingCategories[0].id,
      transactionType: TransactionTypes.PAYMENT_HOME.id,
      transactionDirection: TransactionDirections.INCOMING.id,
      partyName: '',
      partyAccountPrefix: '',
      partyAccountNumber: '',
      partyBankCode: '',
      compare: TransactionValueOperators.LESS_THAN.id,
      valueFrom: '',
      valueTo: '',
      description: ''
    };
  }

  componentDidMount() {
    const { editedRule } = this.props;
    if (editedRule !== null) {
      this.setState({
        name: editedRule.name,
        category: editedRule.category,
        transactionType: editedRule.transactionType,
        transactionDirection: editedRule.transactionDirection,
        partyName: editedRule.partyName,
        partyAccountPrefix: editedRule.partyAccountPrefix,
        partyAccountNumber: editedRule.partyAccountNumber,
        partyBankCode: editedRule.partyBankCode,
        compare: editedRule.compare,
        valueFrom: editedRule.valueFrom.toString() || '',
        valueTo: editedRule.valueTo.toString() || '',
        description: editedRule.description
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
              <RuleFormBody handleChange={this.handleChange} {...this.state} />
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

export default RuleFormController;
