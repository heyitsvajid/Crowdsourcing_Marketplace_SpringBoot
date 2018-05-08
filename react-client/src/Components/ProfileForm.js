import React, { Component } from 'react';
import axios from 'axios';
import Image from './Image';
import { withRouter } from 'react-router-dom'
import swal from 'sweetalert2'

class ProfileForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      successMessage: '',
      errorMessage: '',
      name: '',
      email: '',
      password: '',
      about: '',
      phone: '',
      skills: '',
    }
  }

  handleUserInput = (e) => {
    const name = e.target.name;
    const value = e.target.value;
    this.setState({ [name]: value })

  }
  renderRows() {
    if (this.state.errorMessage != '') {
      return (
        <p class="text-danger" >{this.state.errorMessage}</p>
      );
    } else if (this.state.successMessage != '') {
      return (
        <p class="alert alert-success" >{this.state.successMessage}</p>
      );
    }
  }

  componentWillMount() {
    let getprofileAPI = 'http://localhost:8080/getprofile';
    let id = localStorage.getItem('id');
    if (id) {
      var apiPayload = {
        id: id
      };
      axios.post(getprofileAPI, apiPayload)
        .then(res => {
          if (res.data.errorMsg != '') {
            this.setState({
              errorMessage: res.data.errorMsg
            });
          } else if (res.data.successMsg != '') {
            this.setState({
              name: res.data.data.name,
              email: res.data.data.email,
              about: res.data.data.about,
              phone: res.data.data.phone,
              skills: res.data.data.skills
            });

          } else {
            this.setState({
              errorMessage: 'Unknown error occurred'
            });
          }
        })
        .catch(err => {
          console.error(err);
        });
    }
  }

  handleSubmit(e) {
    e.preventDefault();
    let updateProfileAPI = 'http://localhost:8080/updateProfile';
    let name = this.state.name.trim();
    let email = this.state.email.trim();
    let password = this.state.password;
    let phone = this.state.phone;
    let about = this.state.about;
    let skills = this.state.skills;
    debugger
    let id = localStorage.getItem('id');

    if (!name || !email) {
      swal({
        type: 'error',
        title: 'Update',
        text: 'Name, email and password cannot be blank',
      })
      return;
    }
    var apiPayload = {
      name: name,
      email: email,
      password: password,
      phone: phone,
      about: about,
      skills: skills,
      id: Number(id)
    };
    axios.post(updateProfileAPI, apiPayload)
      .then(res => {
        if (res.data.errorMsg != '') {
          this.setState({
            errorMessage: res.data.errorMsg
          });
        } else if (res.data.successMsg != '') {
          this.setState({
            successMessage: res.data.successMsg
          });
        } else {
          this.setState({
            errorMessage: 'Unknown error occurred'
          });
        }
      })
      .catch(err => {
        console.error(err);
      });
  }
  render() {
    return (
      <div>
        <div class="container">
          <hr />
          <div class="row">
            <div class="col-md-3">
              <div class="text-center">
                <Image />
              </div>
            </div>

            <div class="col-md-9 personal-info">
              <div>
                {this.renderRows()}
              </div>
              <h3>Personal Info</h3>

              <form class="form-horizontal" >
                <div class="form-group">
                  <label class="col-lg-3 control-label">Name</label>
                  <div class="col-lg-8">
                    <input class="form-control" type="text" name="name"
                      placeholder="Name" required="" value={this.state.name} onChange={this.handleUserInput} />
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-lg-3 control-label">Email</label>
                  <div class="col-lg-8">
                    <input class="form-control" type="email" name="email"
                      placeholder="Email" required="" value={this.state.email} onChange={this.handleUserInput} />
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-lg-3 control-label">Skills</label>
                  <div class="col-lg-8">
                    <input class="form-control" type="text" name="skills"
                      placeholder="Comma Separated Skills" value={this.state.skills} onChange={this.handleUserInput} />
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-lg-3 control-label">Phone</label>
                  <div class="col-lg-8">
                    <input class="form-control" type="number" name="phone"
                      placeholder="Phone" value={this.state.phone} onChange={this.handleUserInput} />
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label">About</label>
                  <div class="col-md-8">
                    <textarea class="form-control" rows="5" name="about"
                      placeholder="About Me" value={this.state.about} onChange={this.handleUserInput}></textarea>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-md-3 control-label"></label>
                  <div class="col-md-8">
                    <input type="submit" class="btn btn-primary" value="Save Changes"
                      onClick={this.handleSubmit.bind(this)} />
                    <span></span>
                    <input type="reset" class="btn btn-default" value="Cancel" />
                  </div>
                </div>
              </form>
            </div>
          </div>

        </div>
        <hr />

      </div>
    );
  }
}

export default withRouter(ProfileForm);

