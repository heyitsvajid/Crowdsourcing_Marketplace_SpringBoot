import React, { Component } from 'react';
import axios, { post } from 'axios';
import swal from 'sweetalert2'
import { withRouter } from 'react-router-dom'

class ImageUpload extends Component {
  constructor(props) {
    super(props);
    this.state = {
      requireImagePath: '',
      file: '',
      imagePreviewUrl: '',
      successMessage: '',
      errorMessage: '',
    };
    this._handleImageChange = this._handleImageChange.bind(this);
    this._handleSubmit = this._handleSubmit.bind(this);
  }

  componentWillMount() {
    let getprofileImageAPI = 'http://localhost:8080/getprofileImage';
    let id = localStorage.getItem('id');
    if (id) {
      var apiPayload = {
        id: id
      }
      debugger
      axios.post(getprofileImageAPI, apiPayload)
        .then(res => {
          debugger
          console.log(res.data);
          if (res.data.errorMsg != '') {
            console.log('No Image Found');
          } else {
            this.setState({
              requireImagePath: res.data.data
            });
          }
        })
        .catch(err => {
          console.error(err);
        });
    }
  }
  renderRows() {
    if (this.state.errorMessage != '') {
      return (
        <p class="text-danger" >{this.state.errorMessage}</p>
      );
    } else if (this.state.successMessage != '') {
      return (
        <p class="alert alert-success" >{this.state.errorMessage}</p>
      );
    }
  }
  _handleSubmit(e) {
    e.preventDefault();
    // TODO: do something with -> this.state.file
    let uploadAPI = 'http://localhost:8080/imageUpload';
    const formData = new FormData();
    formData.append('file', this.state.file);
    formData.append('id', localStorage.getItem('id'));
    const config = {
      headers: {
        'content-type': 'multipart/form-data'
      }
    }
    post(uploadAPI, formData, config).then(function (res) {
      if (res.data.errorMsg != '') {
        swal({
          type: 'error',
          text: res.data.errorMsg,
        })
      } else if (res.data.successMsg != '') {
        swal({
          type: 'success',
          text: res.data.successMsg,
        })

      }
    });
  }

  _handleImageChange(e) {
    e.preventDefault();

    let reader = new FileReader();
    let file = e.target.files[0];

    if(file && file.type == 'image/png'){
      reader.onloadend = () => {
        this.setState({
          file: file,
          imagePreviewUrl: reader.result
        });
      }
      reader.readAsDataURL(file)  
    }else{
      swal({
        type: 'error',
        title: 'File Type',
        text: 'Only PNG image types allowed',
      })
    }
  }

  renderImage() {
    let requireImagePath = this.state.requireImagePath;
    let imagePreviewUrl = this.state.imagePreviewUrl;
    if (imagePreviewUrl) {
      return <img alt="" class="avatar img-circle" src={this.state.imagePreviewUrl} width='200px' height='200px' />;
    } else if (requireImagePath != '') {
      var abc = require('../images/' +  requireImagePath);
      return <img alt="" class="avatar img-circle" src={abc} width='200px' height='200px' />;
    }
    else {
      return <img alt="" src="http://www.investeqcapital.com/images/tlpteam/no-image.png" class="avatar img-circle" width='200px' height='200px' />
    }

  }
  render() {


    return (
      <div>
        <form onSubmit={this._handleSubmit}>
          {this.renderImage()}
          <input class='form-control mt-3' type="file" onChange={this._handleImageChange} required='' /><br />
          <h6 class='mt-2'>Upload a different photo...</h6>
          <button type="submit" class="btn btn-primary mt-2" value="Upload Image" onClick={this._handleSubmit}>Upload Image</button>
        </form>
      </div>
    )
  }

}

export default withRouter(ImageUpload);