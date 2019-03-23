import React, { Component } from 'react';
import EnhancedTableHead from './EnhancedTableHead';
import EnhancedTableToolbar from './EnhancedTableToolbar';
import { withStyles, Table, TableBody, TableCell, TablePagination, TableRow, Paper } from '@material-ui/core';
import { Checkbox, Grow } from '@material-ui/core';
import { Spinner } from 'reactstrap';
import { TransactionCategories } from '../../../constants/categories';
import { TransactionDirections, TransactionTypes } from '../../../constants/transactions';

class RulesTable extends Component {
  state = {
    order: 'asc',
    orderBy: 'ruleName',
    selected: [],
    page: 0,
    rowsPerPage: 5
  };

  handleRequestSort = (event, property) => {
    const orderBy = property;
    let order = 'desc';

    if (this.state.orderBy === property && this.state.order === 'desc') {
      order = 'asc';
    }

    this.setState({ order, orderBy });
  };

  handleSelectAllClick = event => {
    if (event.target.checked) {
      this.setState({ selected: this.props.rules.map(n => n.id) });
      return;
    }
    this.setState({ selected: [] });
  };

  handleCheckboxClick = (event, id) => {
    const { selected } = this.state;
    const selectedIndex = selected.indexOf(id);
    let newSelected = [];

    if (selectedIndex === -1) {
      newSelected = newSelected.concat(selected, id);
    } else if (selectedIndex === 0) {
      newSelected = newSelected.concat(selected.slice(1));
    } else if (selectedIndex === selected.length - 1) {
      newSelected = newSelected.concat(selected.slice(0, -1));
    } else if (selectedIndex > 0) {
      newSelected = newSelected.concat(selected.slice(0, selectedIndex), selected.slice(selectedIndex + 1));
    }

    this.setState({ selected: newSelected });
  };

  handleChangePage = (event, page) => {
    this.setState({ page });
  };

  handleChangeRowsPerPage = event => {
    this.setState({ rowsPerPage: event.target.value });
  };

  handleDelete = () => {
    this.props.handleRulesDelete(this.state.selected);
    this.setState({ selected: [] });
  };

  handleAdd = () => {
    this.props.handleRuleAdd();
  };

  handleRowClick = (event, ruleId) => {
    if (event.target.type === 'checkbox') return;
    this.props.handleRuleEdit(ruleId);
  };

  isSelected = id => this.state.selected.indexOf(id) !== -1;

  render() {
    const { classes, rules } = this.props;
    const { order, orderBy, selected, rowsPerPage, page } = this.state;
    const emptyRows = rowsPerPage - Math.min(rowsPerPage, rules.length - page * rowsPerPage);
    return (
      <Paper className={classes.root}>
        <EnhancedTableToolbar
          numSelected={selected.length}
          handleAdd={this.props.handleRuleAdd}
          handleDelete={this.handleDelete}
        />
        {this.props.loading ? (
          <div className="text-center">
            <Spinner type="grow" color="primary" />
          </div>
        ) : (
          <div className={classes.tableWrapper}>
            <Table className={classes.table} aria-labelledby="tableTitle">
              <EnhancedTableHead
                numSelected={selected.length}
                order={order}
                orderBy={orderBy}
                onSelectAllClick={this.handleSelectAllClick}
                onRequestSort={this.handleRequestSort}
                rowCount={rules.length}
              />
              <TableBody>
                {stableSort(rules, getSorting(order, orderBy))
                  .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                  .map(rule => {
                    const isSelected = this.isSelected(rule.id);
                    const direction = Object.values(TransactionDirections).find(
                      direction => direction.id === rule.direction
                    ).text;
                    const transactionType = Object.values(TransactionTypes).find(
                      type => type.id === rule.transactionType
                    ).text;
                    return (
                      <Grow key={rule.id} in={true} timeout={500}>
                        <TableRow
                          hover
                          onClick={event => this.handleRowClick(event, rule.id)}
                          role="checkbox"
                          aria-checked={isSelected}
                          tabIndex={-1}
                          selected={isSelected}
                        >
                          <TableCell component="th" scope="row" padding="default">
                            {rule.ruleName}
                          </TableCell>
                          <TableCell align="left" padding="default">
                            {Object.values(TransactionCategories).find(cat => cat.id == rule.categoryId).text}
                          </TableCell>
                          <TableCell align="left" padding="default">
                            {rule.partyName}
                          </TableCell>
                          <TableCell align="left" padding="default">
                            {direction}
                          </TableCell>
                          <TableCell align="left" padding="default">
                            {transactionType}
                          </TableCell>
                          <TableCell align="right" padding="default">
                            <Checkbox
                              checked={isSelected}
                              onClick={event => this.handleCheckboxClick(event, rule.id)}
                            />
                          </TableCell>
                        </TableRow>
                      </Grow>
                    );
                  })}
                {emptyRows > 0 && (
                  <TableRow style={{ height: 49 * emptyRows }}>
                    <TableCell colSpan={6} />
                  </TableRow>
                )}
              </TableBody>
            </Table>
          </div>
        )}

        <TablePagination
          rowsPerPageOptions={[5, 10, 25]}
          component="div"
          count={rules.length}
          rowsPerPage={rowsPerPage}
          page={page}
          backIconButtonProps={{
            'aria-label': 'Previous Page'
          }}
          nextIconButtonProps={{
            'aria-label': 'Next Page'
          }}
          onChangePage={this.handleChangePage}
          onChangeRowsPerPage={this.handleChangeRowsPerPage}
        />
      </Paper>
    );
  }
}

const styles = theme => ({
  root: {
    width: '100%',
    marginTop: theme.spacing.unit * 3
  },
  table: {
    minWidth: 1020
  },
  tableWrapper: {
    overflowX: 'auto'
  }
});

export default withStyles(styles)(RulesTable);

const desc = (a, b, orderBy) => {
  if (b[orderBy] < a[orderBy]) {
    return -1;
  }
  if (b[orderBy] > a[orderBy]) {
    return 1;
  }
  return 0;
};

const stableSort = (array, cmp) => {
  const stabilizedThis = array.map((el, index) => [el, index]);
  stabilizedThis.sort((a, b) => {
    const order = cmp(a[0], b[0]);
    if (order !== 0) return order;
    return a[1] - b[1];
  });
  return stabilizedThis.map(el => el[0]);
};

const getSorting = (order, orderBy) => {
  return order === 'desc' ? (a, b) => desc(a, b, orderBy) : (a, b) => -desc(a, b, orderBy);
};
