import React, { Component } from 'react';
import { withRouter } from 'react-router-dom'
import { post } from 'axios';
import { Link } from 'react-router-dom';
import swal from 'sweetalert2'

class ProjectForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      file: '',
      successMessage: '',
      errorMessage: '',
      title: '',
      description: '',
      skill: '',
      budget: '',
      period: '',
    }
    this._handleChangeFile = this._handleChangeFile.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }
  handleUserInput = (e) => {
    const name = e.target.name;
    const value = e.target.value;
    this.setState({ [name]: value })

  }
  handleSubmit(e) {
    e.preventDefault();
    let postProjectAPI = 'http://localhost:8080/postProject';
    let title = this.state.title.trim();
    let description = this.state.description;
    let skill = this.state.skill;
    let budget = this.state.budget;
    let period = this.state.period;
    let id = localStorage.getItem('id');

    if (!title || !description || !budget || !skill || !period) {
      swal({
        type: 'error',
        title: 'Post Project',
        text: 'Please complete the form',
      })
      return;
    }

    const formData = new FormData();
    formData.append('file', this.state.file);
    formData.append('id', id);
    formData.append('title', title);
    formData.append('description', description);
    formData.append('skill', skill);
    formData.append('budget', budget);
    formData.append('period', period);
    const config = {
      headers: {
        'content-type': 'multipart/form-data'
      }
    }
    post(postProjectAPI, formData, config).then(function (res) {
      // eslint-disable-next-line
      if (res.data.errorMsg != '') {
        swal({
          type: 'error',
          title: 'Post Project',
          text: 'Error Posting Project',
          // eslint-disable-next-line
        })} else if (res.data.successMsg != '') {
          swal({
            type: 'success',
            title: 'Post Project',
            text: 'Project posted successfully',
          })
          // this.setState({
          //   file: '',
          //   successMessage: '',
          //   errorMessage: '',
          //   title: '',
          //   description: '',
          //   skill: '',
          //   budget: '',
          //   period: '',
          // });
        
      }
    });
  }
  _handleChangeFile(e) {
    e.preventDefault();
    let reader = new FileReader();
    let file = e.target.files[0];
    // eslint-disable-next-line
    if (file && file.type == 'application/pdf') {
      reader.onloadend = () => {
        this.setState({
          file: file,
        });
      }
      reader.readAsDataURL(file)
    }
    else {
      swal({
        type: 'error',
        title: 'File Upload',
        text: 'Only PDF attachments allowed',
      })
    }
  }
  render() {
    return (
      <div className="container">
        <div class="row">
          <div class="col-md-9 personal-info">
            <h3>Project Info</h3>
            <hr />

            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-lg-3 control-label"><strong>Title</strong></label>
                <div class="col-lg-8">
                  <input class="form-control" type="text" name="title"
                    placeholder="Title" required="" value={this.state.title} onChange={this.handleUserInput} />
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-3 control-label"><strong>Description</strong></label>
                <div class="col-md-8">
                  <textarea class="form-control" rows="5" name="description"
                    placeholder="Description" required="" value={this.state.description} onChange={this.handleUserInput}></textarea>
                </div>
              </div>
              <div class="form-group">
                <label class="col-lg-3 control-label"><strong>Main Skill</strong></label>
                <div class="col-lg-8">
                  <input class="form-control" type="text" name="skill"
                    placeholder="Skill" required="" value={this.state.skill} onChange={this.handleUserInput} />
                </div>
              </div>
              <div class="form-group">
                <label class="col-lg-3 control-label"><strong>Budget Range</strong></label>
                <div class="col-lg-8">
                  <input class="form-control" type="text" name="budget"
                    placeholder="Like 100-200" required="" value={this.state.budget} onChange={this.handleUserInput} />
                </div>
              </div>
              <div class="form-group">
                <label class="col-lg-3 control-label"><strong>Budget Period</strong></label>
                <div class="col-lg-8">
                  <input class="form-control" type="number" name="period"
                    placeholder="Period in Days" required="" value={this.state.period} onChange={this.handleUserInput} />
                </div>
              </div>
              <div class="form-group">
                <label class="col-lg-3 control-label"><strong>Attachment</strong></label>
                <div class="col-lg-8">
                  <input type="file" class="form-control" onChange={this._handleChangeFile} />            </div>
              </div>
              <div class="form-group">
                <label class="col-md-3 control-label"></label>
                <div class="col-md-8">
                  <input type="submit" class="btn btn-primary"
                    value="Post Project" required="" onClick={this.handleSubmit.bind(this)} />
                  <span></span>
                  <Link to='/home'> <input type="reset" class="btn btn-default" value="Cancel" /></Link>
                </div>
              </div>
            </form>
          </div>
        </div>


        <hr />
      
      </div>
    );
  }
}

export default withRouter(ProjectForm);