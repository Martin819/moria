import React, { Component } from 'react';
import { Collapse, Navbar, NavbarToggler, NavbarBrand, Nav, NavItem, Container } from 'reactstrap';
import { NavLink } from 'react-router-dom';

class AppNavbar extends Component {
  state = {
    isOpen: false
  };

  toggle = () => {
    this.setState({
      isOpen: !this.state.isOpen
    });
  };

  render() {
    return (
      <div>
        <Navbar dark expand="sm" className="mb-5">
          <Container>
            <NavbarBrand className="text-light">Transaction Center of Moria</NavbarBrand>
            <NavbarToggler onClick={this.toggle} />
            <Collapse isOpen={this.state.isOpen} navbar>
              <Nav className="ml-auto" navbar>
                <NavItem>
                  <NavLink exact className="nav-link" to="/">
                    Transactions
                  </NavLink>
                </NavItem>
                <NavItem>
                  <NavLink className="nav-link" to="/rules">
                    Rules
                  </NavLink>
                </NavItem>
                <NavItem>
                  <NavLink className="nav-link" to="/stats">
                    Statistics
                  </NavLink>
                </NavItem>
              </Nav>
            </Collapse>
          </Container>
        </Navbar>
      </div>
    );
  }
}

export default AppNavbar;
