import React, { Component } from 'react';
import '../assets/css/header.css'
import '../assets/css/dropdown.css'
import { Link } from 'react-router-dom';
import axios from 'axios';
import { withRouter } from 'react-router-dom'
import swal from 'sweetalert2'

class Header extends Component {

  logout(e){
    e.preventDefault();
    localStorage.clear();
    let url = 'http://localhost:8080/logout';   
    localStorage.clear();
    debugger
    axios.post(url,{withCredentials: true})
    .then(res => {
      debugger
      // eslint-disable-next-line
        if(res.status == 200){
          this.props.history.push('/');
          swal({
            type: 'success',
            title: 'Logout',
            text: 'Successfully Logged Out',
          })
          }
       
    })
    .catch(err => {
        console.error(err);
    });

  }
  //<img className="logo" src="https://cdn6.f-cdn.com/build/icons/fl-logo.svg" alt="" height="40" width="170" />
  //<a className="brand" href="#">freelancer</a>
  //<img className="mb-4" src={require('../assets/freelancer_32_32.png')} alt="" height="30" width="30"/>
  render() {
    return (
      <div>
      <header>
        <nav className="nav dark-nav fixed-top">
          <div className="container">
            <div className="nav-heading">
              <img className="logo" src="https://cdn6.f-cdn.com/build/icons/fl-logo.svg" alt="" height="40" width="170" />

            </div>
            <div className="menu" id="open-navbar1">
              <ul className="list">
                <li><Link to='/home'><a className={this.props.home}>Home</a></Link ></li>
                <li ><Link to='/dashboard'><a className={this.props.dashboard}>Dashboard</a></Link></li>
                <li ><Link to='/myprojects'><a className={this.props.myprojects}>My Projects</a></Link></li>
                <li>    <Link to="/postproject">  <button type="button" class="btn btn-primary">Post Project</button></Link>
                </li>
                <li class="dropdown mr-2">
                  <a data-toggle="dropdown" class="dropdown-toggle"> <b class="caret">Hi, {localStorage.getItem('name')}</b></a>
                  <h3> </h3>
                  <ul class="dropdown-menu" >
                    <Link to="/profile">  <li><a >Profile</a></li></Link>
                   <li><a href='' onClick={this.logout.bind(this)}>Logout</a></li>
                    <li class="divider"></li>
                  </ul>
                </li>

              </ul>
            </div>
          </div>
        </nav>
      </header>
      <br /><br /><br />
      </div>
    );
  }

}


export default withRouter(Header);
