import React, { Component } from 'react';
import moment from 'moment';
import { withStyles, ExpansionPanel, ExpansionPanelSummary, Typography, Grid, Grow } from '@material-ui/core/';
import {
  Button,
  Form,
  FormGroup,
  Col,
  Row,
  Input,
  ButtonDropdown,
  DropdownToggle,
  DropdownMenu,
  Label
} from 'reactstrap';
import {
  OutgoingTransactionCategories,
  TransactionCategories,
  IncomingTransactionCategories,
  SpecialCategories
} from '../../../constants/categories';
import { Badge } from 'reactstrap';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import TransactionItemPanelDetail from './TransactionItemPanelDetail';
import { TransactionTypes, TransactionDirections } from '../../../constants/transactions';

class TransactionItem extends Component {
  constructor(props) {
    super(props);
    this.state = {
      categoryId: UNSELECTED,
      dropdownOpen: false
    };
  }

  componentDidMount() {
    const { categoryId } = this.props;
    if (categoryId !== SpecialCategories.UNCATEGORIZED.id) {
      this.setState({ categoryId: categoryId });
    }
  }

  toggleDropdown = () => {
    this.setState({
      dropdownOpen: !this.state.dropdownOpen
    });
  };

  handleCategoryChange = event => {
    this.setState({ categoryId: parseInt(event.target.value) });
  };

  handleTransactionCategoryUpdate = event => {
    event.stopPropagation();
    event.preventDefault();
    this.props.handleTransactionCategoryUpdate(this.props.id, this.state.categoryId);
  };

  render() {
    const {
      classes,
      index,
      accountPreferredColor,
      direction,
      partyDescription,
      transactionPartyAccountPrefix,
      transactionPartyAccountAccountNumber,
      transactionPartyAccountBankCode,
      transactionAdditionalInfoDomesticConstantSymbol,
      transactionAdditionalInfoDomesticVariableSymbol,
      payeeMessage,
      payerMessage,
      transactionAdditionalInfoCardMerchantName,
      transactionValueAmount,
      transactionValueCurrency,
      valueDate,
      transactionType,
      categoryId,
      parentId,
      originalValue,
      categories
    } = this.props;

    const isParentTransaction = categories !== null && parentId === null;
    const valueAmount = isParentTransaction ? originalValue : transactionValueAmount;
    const categoryEnum = isParentTransaction
      ? TransactionCategories.SPLIT
      : Object.values(TransactionCategories).find(cat => cat.id === categoryId);
    const categoryText = categoryEnum === undefined ? 'Unknown' : categoryEnum.text;
    const transactionTypeEnum = Object.values(TransactionTypes).find(type => type.id === transactionType);
    const transactionTypeText = transactionTypeEnum === undefined ? 'Unknown' : transactionTypeEnum.text;
    const transactionCategories =
      direction === TransactionDirections.INCOMING.id ? IncomingTransactionCategories : OutgoingTransactionCategories;
    const detailCardPayments = { 'Merchant name': transactionAdditionalInfoCardMerchantName };
    const detailTransfers = {
      'Party account': `${transactionPartyAccountPrefix}-${transactionPartyAccountAccountNumber}/${transactionPartyAccountBankCode}`,
      'Variable symbol': transactionAdditionalInfoDomesticVariableSymbol,
      'Constant symbol': transactionAdditionalInfoDomesticConstantSymbol,
      'Payer message': payerMessage,
      'Payee message': payeeMessage
    };

    return (
      <Grow in={true} timeout={500 + 100 * index}>
        <ExpansionPanel className="mb-2">
          <ExpansionPanelSummary
            expandIcon={<ExpandMoreIcon />}
            style={{ borderLeft: `6px solid ${accountPreferredColor}` }}
          >
            <Grid container direction="row" spacing={24}>
              <Grid item>
                <Typography className={classes.date}>{moment(valueDate).format('DD MMM YYYY')}</Typography>
              </Grid>
              <Grid item xs>
                <Grid container justify="space-between" alignItems="center">
                  <Grid item xs={9}>
                    <Row>
                      <Col xs={4}>
                        <Badge color="warning" pill>
                          {categoryText}
                        </Badge>
                        <Typography className={classes.heading}>{partyDescription}</Typography>
                        <Typography>
                          <span className={classes.secondaryHeading}>{transactionTypeText}</span>
                        </Typography>
                      </Col>
                      <Col xs={8}>
                        <Grid container justify="flex-start" alignItems="center" className="mt-3">
                          <Grid item>
                            <Form inline>
                              <FormGroup>
                                <Input
                                  type="select"
                                  name="categorySelect"
                                  id="categorySelect"
                                  onChange={e => this.handleCategoryChange(e)}
                                  onClick={e => {
                                    e.stopPropagation();
                                  }}
                                  disabled={transactionType === TransactionTypes.CASH.id}
                                  value={this.state.categoryId}
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

                                <Button
                                  color="primary"
                                  type="submit"
                                  className="ml-md-2"
                                  disabled={
                                    this.state.categoryId === UNSELECTED ||
                                    categoryId === this.state.categoryId ||
                                    transactionType === TransactionTypes.CASH.id
                                  }
                                  size="sm"
                                  onClick={this.handleTransactionCategoryUpdate}
                                >
                                  Save
                                </Button>
                              </FormGroup>
                            </Form>
                          </Grid>
                          <Grid item className="ml-md-2">
                            {transactionType === TransactionTypes.CASH.id && (
                              <ButtonDropdown
                                isOpen={this.state.dropdownOpen}
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
                                        name=""
                                        id=""
                                        placeholder="100.00"
                                        // value={this.props.valueTo}
                                        className="text-right"
                                        // onChange={e => this.props.handleChange(e)}
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
                                        name="categorySelect"
                                        id="categorySelect"
                                        onChange={e => this.handleCategoryChange(e)}
                                        value={this.state.categoryId}
                                        bsSize="sm"
                                        style={{ width: 200 }}
                                      >
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
                                          onClick={this.handleTransactionCategoryUpdate}
                                        >
                                          SAVE
                                        </Button>
                                      </div>
                                    </FormGroup>
                                  </Form>
                                </DropdownMenu>
                              </ButtonDropdown>
                            )}
                          </Grid>
                        </Grid>
                      </Col>
                    </Row>
                  </Grid>

                  <Grid item xs className="text-md-right">
                    {direction === 'OUTGOING' && (
                      <Typography className={classes.amountNegative}>
                        &#8722;&nbsp;
                        {valueAmount.toLocaleString('cs-cz', {
                          style: 'currency',
                          currency: transactionValueCurrency
                        })}
                      </Typography>
                    )}
                    {direction === 'INCOMING' && (
                      <Typography className={classes.amountPositive}>
                        {valueAmount.toLocaleString('cs-cz', {
                          style: 'currency',
                          currency: transactionValueCurrency
                        })}
                      </Typography>
                    )}
                  </Grid>
                </Grid>
              </Grid>
            </Grid>
          </ExpansionPanelSummary>
          <TransactionItemPanelDetail
            detail={transactionType === TransactionTypes.CARD.id ? detailCardPayments : detailTransfers}
            accountPreferredColor={accountPreferredColor}
            categories={categories}
          />
        </ExpansionPanel>
      </Grow>
    );
  }
}

const UNSELECTED = 'UNSELECTED';

const styles = theme => ({
  heading: {
    fontSize: theme.typography.pxToRem(15),
    fontWeight: 600,
    marginTop: -2
  },
  secondaryHeading: {
    fontSize: theme.typography.pxToRem(15),
    fontWeight: 100
  },
  date: {
    fontSize: theme.typography.pxToRem(15),
    paddingTop: 3
  },
  amountPositive: {
    fontSize: theme.typography.pxToRem(18),
    color: 'green',
    fontWeight: 600
  },
  amountNegative: {
    fontSize: theme.typography.pxToRem(18),
    color: 'red',
    fontWeight: 600
  }
});

export default withStyles(styles)(TransactionItem);
