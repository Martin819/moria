import React, { Component, Fragment } from 'react';
import { connect } from 'react-redux';
import { getRules, toggleModal, handleRuleSubmit, handleRuleEdit } from '../../actions/ruleActions';
import RulesTable from '../../components/rules-settings/rules-table/RulesTable';
import RuleModalForm from '../../components/rules-settings/rules-form/RuleModalForm';
import { bindActionCreators } from 'redux';

class RulesAreaContainer extends Component {
  componentDidMount() {
    this.props.getRules();
    console.log(this.props);
  }

  render() {
    return (
      <Fragment>
        <RulesTable
          rules={this.props.rules}
          loading={this.props.loading}
          toggleModal={this.props.toggleModal}
          handleRuleEdit={ruleId => this.props.handleRuleEdit(ruleId)}
        />
        {this.props.isModalOpened && (
          <RuleModalForm
            editedRule={this.props.editedRule}
            isModalOpened={this.props.isModalOpened}
            toggleModal={this.props.toggleModal}
            handleRuleSubmit={state => this.props.handleRuleSubmit(state)}
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
  return bindActionCreators({ getRules, toggleModal, handleRuleSubmit, handleRuleEdit }, dispatch);
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RulesAreaContainer);
