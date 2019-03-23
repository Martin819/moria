import React, { Component, Fragment } from 'react';
import { connect } from 'react-redux';
import {
  getRules,
  toggleModal,
  handleRuleSubmitCreate,
  handleRuleSubmitUpdate,
  handleRuleAdd,
  handleRuleEdit,
  handleRulesDelete
} from '../../actions/ruleActions';
import RulesTable from '../../components/rules-settings/rules-table/RulesTable';
import RuleFormController from '../../components/rules-settings/rules-form/RuleFormController';
import { bindActionCreators } from 'redux';

class RulesAreaContainer extends Component {
  componentDidMount() {
    this.props.getRules();
  }

  render() {
    return (
      <Fragment>
        <RulesTable
          rules={this.props.rules}
          loading={this.props.loading}
          toggleModal={this.props.toggleModal}
          handleRuleAdd={this.props.handleRuleAdd}
          handleRuleEdit={ruleId => this.props.handleRuleEdit(ruleId)}
          handleRulesDelete={this.props.handleRulesDelete}
        />
        {this.props.isModalOpened && (
          <RuleFormController
            editedRule={this.props.editedRule}
            isModalOpened={this.props.isModalOpened}
            toggleModal={this.props.toggleModal}
            handleRuleSubmitCreate={state => this.props.handleRuleSubmitCreate(state)}
            handleRuleSubmitUpdate={state => this.props.handleRuleSubmitUpdate(state)}
          />
        )}
      </Fragment>
    );
  }
}

const mapStateToProps = state => ({
  rules: state.rules.rules,
  editedRule: state.rules.editedRule,
  isModalOpened: state.rules.isModalOpened,
  loading: state.rules.loading
});

const mapDispatchToProps = (dispatch, ownProps) => {
  return bindActionCreators(
    {
      getRules,
      toggleModal,
      handleRuleSubmitCreate,
      handleRuleSubmitUpdate,
      handleRulesDelete,
      handleRuleAdd,
      handleRuleEdit
    },
    dispatch
  );
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RulesAreaContainer);
