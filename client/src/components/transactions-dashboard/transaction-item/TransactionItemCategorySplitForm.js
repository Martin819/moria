import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { Button, Form, FormGroup, Input, ButtonDropdown, DropdownToggle, DropdownMenu, Label } from 'reactstrap';

class TransactionItemCategorySplitForm extends Component {
  state = {
    dropdownOpen: false,
    dropdownAmount: '',
    dropdownCategoryId: UNSELECTED
  };

  toggleDropdown = event => {
    const targetName = event.target.name;
    if (
      targetName === 'dropdownAmount' ||
      targetName === 'dropdownCategoryId' ||
      targetName === 'dropdownSubmitButton'
    ) {
      return;
    }
    this.setState({
      dropdownOpen: !this.state.dropdownOpen
    });
  };

  handleChange = event => {
    if (event.target.name === 'dropdownAmount') {
      if (event.target.value > this.props.maxValueToAssign) {
        this.setState({ [event.target.name]: this.props.maxValueToAssign });
        return;
      }
    }
    this.setState({ [event.target.name]: event.target.value });
  };

  handleTransactionSplit = event => {
    event.preventDefault();
    this.props.handleTransactionSplit({
      id: this.props.transactionId,
      amount: this.state.dropdownAmount,
      categoryId: parseInt(this.state.dropdownCategoryId)
    });
    this.setState({ dropdownAmount: '', dropdownCategoryId: UNSELECTED });
  };

  render() {
    const { transactionCategories, maxValueToAssign } = this.props;
    const { dropdownOpen, dropdownAmount, dropdownCategoryId } = this.state;
    const invalidForSubmit = dropdownCategoryId === UNSELECTED || dropdownAmount === '' || dropdownAmount == 0;

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
        {ReactDOM.createPortal(
          <DropdownMenu className="p-2" positionFixed>
            <Form>
              <FormGroup>
                <Label for="">Amount</Label>
                <Input
                  name="dropdownAmount"
                  id="dropdownAmount"
                  placeholder="insert amount"
                  value={dropdownAmount}
                  className="text-right"
                  onChange={e => this.handleChange(e)}
                  bsSize="sm"
                  type="number"
                  min="0.01"
                  max={maxValueToAssign}
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
                    name="dropdownSubmitButton"
                    color={invalidForSubmit ? 'secondary' : 'primary'}
                    className="mt-2"
                    size="sm"
                    onClick={this.handleTransactionSplit}
                    disabled={invalidForSubmit}
                  >
                    Save
                  </Button>
                </div>
              </FormGroup>
            </Form>
          </DropdownMenu>,
          document.body
        )}
      </ButtonDropdown>
    );
  }
}

const UNSELECTED = 'UNSELECTED';

export default TransactionItemCategorySplitForm;
