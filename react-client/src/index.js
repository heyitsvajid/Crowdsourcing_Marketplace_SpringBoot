import React from 'react';
import ReactDOM from 'react-dom';
import LogIn from './Components/LogIn';
import SignUp from './Components/SignUp';
import Home from './Components/Home';
import Index from './Components/Index';
import Dashboard from './Components/Dashboard';
import ProjectItem from './Components/ProjectItem';
import MyProjects from './Components/MyProjects';
import Profile from './Components/Profile';
import PostProject from './Components/PostProject';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import './assets/css/bootstrap.min.css'
import './assets/css/bootstrap-responsive.min.css'
import profileTest from './Components/profileTest';

import { createStore } from 'redux';
import allReducers from './Reducers';
import { Provider } from 'react-redux';

const store = createStore(allReducers);

ReactDOM.render(
  <Provider store={store}>
    <Router>
      <div>
        <Route exact path="/myprojects" component={MyProjects} />
        <Route exact path="/postproject" component={PostProject} />
        <Route exact path="/profile" component={Profile} />
        <Route exact path="/" component={Index} />
        <Route exact path="/home" component={Home} />
        <Route exact path="/dashboard" component={Dashboard} />
        <Route exact path="/login" component={LogIn} />
        <Route exact path="/signup" component={SignUp} />
        <Route exact path="/projectitem" component={ProjectItem} />
      </div>
    </Router>
  </Provider>,
  document.getElementById('root')
)

