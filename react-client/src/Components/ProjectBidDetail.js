import React, { Component } from 'react';
import axios from 'axios'
import swal from 'sweetalert2'
import { withRouter } from 'react-router-dom'

class ProjectBidDetail extends Component {
  constructor(props) {
    super(props);
    this.state = {
      projectBids: [],
    }
  }
  componentWillMount() {
    let id = localStorage.getItem('currentProjectId');
    let getBidsAPI = 'http://localhost:8080/getProjectBids/'+id;
    let currentUserId = localStorage.getItem('id');
    if (id) {
      var apiPayload = {
        id: id,
        currentUserId: currentUserId
      };
      axios.post(getBidsAPI, apiPayload)
        .then(res => {
          if (res.data.errorMsg != '') {
            this.setState({
              errorMessage: res.data.errorMsg
            });
          } else if (res.data.successMsg != '') {
            this.setState({
              projectBids: res.data.data
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

  renderImage(id, profile_id) {
    if (!profile_id) {
      return <img class="card-img-right flex-auto d-none d-md-block" alt="Thumbnail [200x250]"
        width='200px' height='200px'
        src={require('../images/freelancer_32_32.png')} data-holder-rendered="true" />;
    } else {
      var imageSource = require('../images/' + id + '.png');
      return <img class="card-img-right flex-auto d-none d-md-block" alt="Thumbnail [200x250]"
        width='200px' height='200px'
        src={imageSource} data-holder-rendered="true" />;
    }

  }

  hireEmployer = (e) => {
    let hireEmployerAPI = 'http://localhost:8080/hireFreelancer';
    let projectId = localStorage.getItem('currentProjectId');
    var str = e.target.id;
    var res = str.split("/");
    let freelancerId = res[0];
    var endDate = new Date(new Date().getTime() + (res[1] * 24 * 60 * 60 * 1000));
    if (!projectId || !freelancerId) {
      console.log('Project/Employee ID not found');
      return;
    }
    var apiPayload = { freelancerId: freelancerId, projectId: projectId, endDate: endDate.toDateString() };
    axios.post(hireEmployerAPI, apiPayload)
      .then(res => {
        if (res.data.errorMsg != '') {
          this.setState({
            errorMessage: res.data.errorMsg
          });
        } else {
          this.setState({
            errorMessage: ''
          });
          this.props.history.push('/myprojects');
          swal({
            type: 'success',
            title: 'Hire',
            text: 'Employee Hired',
          })
          setTimeout(function () {
            window.location.href='http://localhost:3000/myprojects'
        }, 5000);        
        }
      })
      .catch(err => {
        console.error(err);
      });
  }


  getProfileName(userId){
    let getprofileAPI = 'http://localhost:8080/getprofile';
    var apiPayload = {
        id: userId
    };
    axios.post(getprofileAPI, apiPayload)
        .then(res => {
            // eslint-disable-next-line
            if (res.data.errorMsg != '') {
                this.setState({
                    errorMessage: res.data.errorMsg
                });
                return "Madhukar";
            } else if (res.data.successMsg != '') {
                return res.data.data.name;
            } else {
              return "Madhukar";
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

    let projectBids = this.state.projectBids.map(bid => {
      return (
        <div>
          <div class="card flex-md-row mb-8 box-shadow h-md-300">
            <div class="card-body d-flex flex-column align-items-start ">
              <h3> <strong class="d-inline-block mb-1 text-primary">{bid.userName}</strong></h3>

              <div class="mb-1"><strong><p class="card-text mb-auto text-muted">Days : {bid.bid_period}</p></strong></div>
              <strong><p class="card-text mb-auto text-muted">Bid : $ {bid.bid_amount}</p></strong>
              {localStorage.getItem('hireFlag') == 'true' ?
                <button type="button" id={bid.userId + '/' + bid.bid_period} onClick={this.hireEmployer} class="btn btn-primary">Hire {bid.name}</button>
                : <div />}
            </div>
            {this.renderImage(bid.userId, bid.profile_id)}

          </div><br /></div>)
    })
    return (
      <div class="col-md-8 mt-5">
        {projectBids}
      </div>

    );
  }
}
export default withRouter(ProjectBidDetail);