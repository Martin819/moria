import React, { Component, Fragment } from 'react';
import { Provider } from 'react-redux';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import store from './store';
import AppNavbar from './components/navbar/AppNavbar';
import StatisticsDashboardContainer from './containers/statistics/StatisticsDashboardContainer';
import TransactionsDashboard from './components/transactions-dashboard/TransactionsDashboard';
import RulesSettings from './components/rules-settings/RulesSettings';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

class App extends Component {
  render() {
    return (
      <Provider store={store}>
        <Router>
          <Fragment>
            <AppNavbar />
            <div className="app-container">
              <Switch>
                <Route exact path="/" component={TransactionsDashboard} />
                <Route exact path="/rules" component={RulesSettings} />
                <Route exact path="/stats" component={StatisticsDashboardContainer} />
              </Switch>
            </div>
          </Fragment>
        </Router>
      </Provider>
    );
  }
}

export default App;
