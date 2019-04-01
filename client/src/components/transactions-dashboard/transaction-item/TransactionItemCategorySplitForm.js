import React, { Component } from 'react';
import { Button, Form, FormGroup, Input, ButtonDropdown, DropdownToggle, DropdownMenu, Label } from 'reactstrap';

class TransactionItemCategorySplitForm extends Component {
  state = {
    dropdownOpen: false,
    dropdownAmount: '',
    dropdownCategoryId: UNSELECTED
  };

  toggleDropdown = () => {
    this.setState({
      dropdownOpen: !this.state.dropdownOpen
    });
  };

  handleChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  handleTransactionSplit = event => {
    event.preventDefault();
    this.props.handleTransactionSplit({
      transactionId: this.props.transactionId,
      amount: this.state.dropdownAmount,
      categoryId: parseInt(this.state.dropdownCategoryId)
    });
  };

  render() {
    const { transactionCategories } = this.props;
    const { dropdownOpen, dropdownAmount, dropdownCategoryId } = this.state;
    return (
      <ButtonDropdown
        isOpen={dropdownOpen}
        toggle={this.toggleDropdown}
        onClick={e => {
          e.stopPropagation();
        }}
      >
        <DropdownToggle caret size="sm" color="warning">
          Split..
        </DropdownToggle>
        <DropdownMenu className="p-2" positionFixed>
          <Form>
            <FormGroup>
              <Label for="">Amount</Label>
              <Input
                name="dropdownAmount"
                id="dropdownAmount"
                placeholder="100.00"
                value={dropdownAmount}
                className="text-right"
                onChange={e => this.handleChange(e)}
                bsSize="sm"
                type="number"
                min="0.01"
                step="0.01"
              />
              <Label for="" className="mt-2">
                Into category
              </Label>
              <Input
                type="select"
                name="dropdownCategoryId"
                id="dropdownCategoryId"
                onChange={e => this.handleChange(e)}
                value={dropdownCategoryId}
                bsSize="sm"
                style={{ width: 200 }}
              >
                <option disabled value={UNSELECTED}>
                  -- select a category --
                </option>
                {Object.values(transactionCategories).map(c => (
                  <option key={c.id} value={c.id}>
                    {c.text}
                  </option>
                ))}
              </Input>
              <div className="text-right">
                <Button
                  color="primary"
                  className="mt-2"
                  size="sm"
                  onClick={this.handleTransactionSplit}
                  disabled={dropdownCategoryId === UNSELECTED || dropdownAmount === ''}
                >
                  SAVE
                </Button>
              </div>
            </FormGroup>
          </Form>
        </DropdownMenu>
      </ButtonDropdown>
    );
  }
}

const UNSELECTED = 'UNSELECTED';

export default TransactionItemCategorySplitForm;
