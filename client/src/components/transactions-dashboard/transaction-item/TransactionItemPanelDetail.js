import React, { Component } from 'react';
import { ExpansionPanelDetails, Grid, Typography } from '@material-ui/core';
import { Button, Form, FormGroup, Col, Row, Label, Input } from 'reactstrap';
import { allCategories } from '../../../constants/categories';

class TransactionItemPanelDetail extends Component {
  constructor(props) {
    super(props);
    this.state = {
      categoryId: props.category
    };
  }

  handleCategoryChange = event => {
    this.setState({ categoryId: event.target.value });
  };

  handleSubmit = event => {
    event.preventDefault();
    this.props.handleTransactionCategorySubmit(this.props.id, this.state.categoryId);
  };

  render() {
    return (
      <ExpansionPanelDetails>
        <Grid container direction="column">
          <Grid item>
            <Typography>
              Message: {this.props.message} <br /> ID: {this.props.id}
            </Typography>
          </Grid>
          <Grid item>
            <hr />
            <Form>
              <Col sm={8} className="p-0">
                <FormGroup inline className="mb-0 mr-sm-2">
                  <Label for="categorySelect" className="mr-sm-2">
                    Change category
                  </Label>
                  <Row form>
                    <Col sm={6} className="p-0">
                      <Input
                        type="select"
                        name="categorySelect"
                        id="categorySelect"
                        onChange={e => this.handleCategoryChange(e)}
                        value={this.state.categoryId}
                        bsSize="sm"
                      >
                        {allCategories.map(c => (
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
                        className="ml-2"
                        disabled={this.props.category === this.state.categoryId}
                        size="sm"
                        onClick={this.handleSubmit}
                      >
                        Save
                      </Button>
                    </Col>
                  </Row>
                </FormGroup>
              </Col>
            </Form>
          </Grid>
        </Grid>
      </ExpansionPanelDetails>
    );
  }
}

export default TransactionItemPanelDetail;
