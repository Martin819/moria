import React from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Form } from 'reactstrap';
import RuleFormGroup from './RuleFormGroup';

class RuleModalForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      ruleName: '',
      category: '',
      partyName: '',
      compare: '',
      amount: '',
      transactionType: '',
      description: ''
    };
  }

  handleSubmit = event => {
    event.preventDefault();
    this.props.toggleModal();
    this.props.handleRuleSubmit({ ...this.state });
    console.log('fire');
  };

  handleChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  render() {
    console.log(this.props);
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
              <RuleFormGroup handleChange={this.handleChange} {...this.state} />
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

export default RuleModalForm;
