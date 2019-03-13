import React, { Fragment, Component } from 'react';
import moment from 'moment';
import { withStyles, ExpansionPanel, ExpansionPanelSummary, Typography, Grid, Grow } from '@material-ui/core/';
import { Button, Form, FormGroup, Col, Row, Label, Input } from 'reactstrap';
import { allOutgoingCategories } from '../../../constants/categories';
import { Badge } from 'reactstrap';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import TransactionItemPanelDetail from './TransactionItemPanelDetail';

class TransactionItem extends Component {
  constructor(props) {
    super(props);
    this.state = {
      categoryId: props.category
    };
  }

  handleCategoryChange = event => {
    this.setState({ categoryId: event.target.value });
  };

  handleTransactionCategorySubmit = event => {
    event.stopPropagation();
    event.preventDefault();
    this.props.handleTransactionCategorySubmit(this.props.id, this.state.categoryId);
  };

  render() {
    const {
      classes,
      id,
      partyDescription,
      valueDate,
      transactionType,
      value,
      message,
      category,
      direction
    } = this.props;

    return (
      <Fragment>
        <Grow in={true} timeout={500}>
          <ExpansionPanel>
            <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
              <Grid container direction="row" spacing={24}>
                <Grid item>
                  <Typography className={classes.date}>{moment(valueDate).format('DD MMM YYYY')}</Typography>
                </Grid>
                <Grid item xs>
                  <Grid container justify="space-between" alignItems="center">
                    <Grid item xs={9}>
                      <Row>
                        <Col sm={4}>
                          <Badge color="warning" pill>
                            {category}
                          </Badge>
                          <Typography className={classes.heading}>{partyDescription}</Typography>
                          <Typography>
                            <span className={classes.secondaryHeading}>{transactionType}</span>
                          </Typography>
                        </Col>
                        <Col sm={4}>
                          <Form>
                            <FormGroup inline className="mt-3 mr-2">
                              <Row form>
                                <Col sm={10} className="p-0">
                                  <Input
                                    type="select"
                                    name="categorySelect"
                                    id="categorySelect"
                                    onChange={e => this.handleCategoryChange(e)}
                                    onClick={e => {
                                      e.stopPropagation();
                                    }}
                                    value={this.state.categoryId}
                                    bsSize="sm"
                                  >
                                    {allOutgoingCategories.map(c => (
                                      <option key={c.id} value={c.id}>
                                        {c.text}
                                      </option>
                                    ))}
                                  </Input>
                                </Col>
                                <Col sm={2} className="p-0">
                                  <Button
                                    color="primary"
                                    type="submit"
                                    className="ml-md-2"
                                    disabled={category === this.state.categoryId}
                                    size="sm"
                                    onClick={this.handleTransactionCategorySubmit}
                                  >
                                    Save
                                  </Button>
                                </Col>
                              </Row>
                            </FormGroup>
                          </Form>
                        </Col>
                      </Row>
                    </Grid>

                    <Grid item xs className="text-md-right">
                      {direction === 'INCOMING' ? (
                        <Typography className={classes.amountPositive}>
                          {value.amount.toLocaleString('cs-cz', { style: 'currency', currency: value.currency })}
                        </Typography>
                      ) : (
                        <Typography className={classes.amountNegative}>
                          &#8722;&nbsp;
                          {value.amount.toLocaleString('cs-cz', { style: 'currency', currency: value.currency })}
                        </Typography>
                      )}
                    </Grid>
                  </Grid>
                </Grid>
              </Grid>
            </ExpansionPanelSummary>
            <TransactionItemPanelDetail message={message} id={id} category={category} />
          </ExpansionPanel>
        </Grow>
      </Fragment>
    );
  }
}

TransactionItem.propTypes = {};

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
