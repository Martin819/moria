import React, { Component } from 'react';
import moment from 'moment';
import { withStyles, ExpansionPanel, ExpansionPanelSummary, Typography, Grid, Grow } from '@material-ui/core/';
import { Button, Form, FormGroup, Col, Row, Input } from 'reactstrap';
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
import TransactionItemCategorySplitForm from './TransactionItemCategorySplitForm';

class TransactionItem extends Component {
  constructor(props) {
    super(props);
    this.state = {
      categoryId: UNSELECTED
    };
  }

  componentDidMount() {
    const { categoryId } = this.props;
    if (categoryId !== SpecialCategories.UNCATEGORIZED.id) {
      this.setState({ categoryId: categoryId });
    }
  }

  handleCategoryChange = event => {
    this.setState({ categoryId: parseInt(event.target.value) });
  };

  handleTransactionCategoryUpdate = event => {
    event.stopPropagation();
    event.preventDefault();
    this.props.handleTransactionCategoryUpdate(this.props.id, this.state.categoryId);
  };

  getCategoryEnum = (isParentTransaction, childTransactionsList, transactionCategoryId, transactionValue) => {
    if (isParentTransaction) {
      if (childTransactionsList.length === 2) {
        const categorizedChild = childTransactionsList.find(
          c => c.categoryId !== TransactionCategories.UNCATEGORIZED.id
        );
        if (categorizedChild !== undefined && categorizedChild.amount === transactionValue) {
          return Object.values(TransactionCategories).find(cat => cat.id === categorizedChild.categoryId);
        } else {
          return TransactionCategories.SPLIT;
        }
      } else {
        return TransactionCategories.SPLIT;
      }
    }
    return Object.values(TransactionCategories).find(cat => cat.id === transactionCategoryId);
  };

  render() {
    const {
      classes,
      index,
      id,
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
      childTransactionsList
    } = this.props;

    const isParentTransaction = childTransactionsList !== null && childTransactionsList.length > 0 && parentId === null;
    const isSplitableTransaction = childTransactionsList !== null && parentId === null;
    const valueAmount = isParentTransaction ? originalValue : transactionValueAmount;

    let maxValueToAssign = 0;
    if (isSplitableTransaction) {
      const uncategorizedChild = childTransactionsList.find(
        it => it.categoryId === TransactionCategories.UNCATEGORIZED.id
      );
      if (uncategorizedChild !== undefined) {
        maxValueToAssign = uncategorizedChild.amount;
      } else {
        maxValueToAssign = valueAmount;
      }
    }

    let isSplitOffered = false;
    if (isParentTransaction) {
      isSplitOffered = maxValueToAssign > 0;
    } else {
      isSplitOffered = categoryId === TransactionCategories.UNCATEGORIZED.id;
    }

    const invalidForNewCategorySubmit =
      this.state.categoryId === UNSELECTED ||
      categoryId === this.state.categoryId ||
      transactionType === TransactionTypes.CASH.id ||
      isParentTransaction;

    const categoryEnum = this.getCategoryEnum(isParentTransaction, childTransactionsList, categoryId, valueAmount);
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
      Message: `${direction === TransactionDirections.INCOMING.id ? payeeMessage : payerMessage}`
    };
    let activeDetail;
    switch (transactionType) {
      case TransactionTypes.CARD.id:
        activeDetail = detailCardPayments;
        break;
      case TransactionTypes.CASH.id:
        activeDetail = null;
        break;
      default:
        activeDetail = detailTransfers;
    }

    return (
      <Grow in={true} timeout={500 + 100 * index}>
        <ExpansionPanel className="mb-2">
          <ExpansionPanelSummary
            expandIcon={<ExpandMoreIcon />}
            style={{ borderLeft: `6px solid ${accountPreferredColor}` }}
          >
            <Grid container direction="row">
              <Grid item xs={12} md={1}>
                <Typography className={classes.date}>{moment(valueDate).format('DD MMM YYYY')}</Typography>
              </Grid>
              <Grid item xs={12} md={11} className="ml-xs-3 ml-sm-0">
                <Grid container justify="space-between" alignItems="center">
                  <Grid item xs={9}>
                    <Row>
                      <Col xs={12} lg={4}>
                        <Badge color="warning" pill>
                          {categoryText}
                        </Badge>
                        <Typography className={classes.heading}>{partyDescription}</Typography>
                        <Typography>
                          <span className={classes.secondaryHeading}>{transactionTypeText}</span>
                        </Typography>
                      </Col>
                      <Col xs={12} md={8}>
                        <Grid container justify="flex-start" alignItems="center" className="mt-3">
                          <Grid item>
                            <Form inline>
                              <FormGroup className="p-0 m-0">
                                <Input
                                  type="select"
                                  name="categorySelect"
                                  id="categorySelect"
                                  onChange={e => this.handleCategoryChange(e)}
                                  onClick={e => {
                                    e.stopPropagation();
                                  }}
                                  disabled={isParentTransaction}
                                  value={this.state.categoryId}
                                  bsSize="sm"
                                  style={{ width: 220 }}
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
                                  color={invalidForNewCategorySubmit ? 'secondary' : 'primary'}
                                  type="submit"
                                  className="ml-md-2"
                                  disabled={invalidForNewCategorySubmit}
                                  size="sm"
                                  onClick={this.handleTransactionCategoryUpdate}
                                >
                                  Save
                                </Button>
                              </FormGroup>
                            </Form>
                          </Grid>
                          <Grid item xs={12} md="auto" className="ml-xl-2">
                            {isSplitOffered && (
                              <TransactionItemCategorySplitForm
                                transactionCategories={transactionCategories}
                                handleTransactionSplit={this.props.handleTransactionSplit}
                                transactionId={id}
                                maxValueToAssign={maxValueToAssign}
                              />
                            )}
                          </Grid>
                        </Grid>
                      </Col>
                    </Row>
                  </Grid>

                  <Grid item xs={12} sm={3} className="text-lg-right pt-sm-0 pt-1">
                    {direction === TransactionDirections.OUTGOING.id && (
                      <Typography className={classes.amountNegative}>
                        &#8722;&nbsp;
                        {valueAmount.toLocaleString('cs-cz', {
                          style: 'currency',
                          currency: transactionValueCurrency || 'CZK'
                        })}
                      </Typography>
                    )}
                    {direction === TransactionDirections.INCOMING.id && (
                      <Typography className={classes.amountPositive}>
                        {valueAmount.toLocaleString('cs-cz', {
                          style: 'currency',
                          currency: transactionValueCurrency || 'CZK'
                        })}
                      </Typography>
                    )}
                  </Grid>
                </Grid>
              </Grid>
            </Grid>
          </ExpansionPanelSummary>
          <TransactionItemPanelDetail
            detail={activeDetail}
            accountPreferredColor={accountPreferredColor}
            childTransactionsList={childTransactionsList}
            handleTransactionUnsplit={this.props.handleTransactionUnsplit}
            transactionId={id}
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
