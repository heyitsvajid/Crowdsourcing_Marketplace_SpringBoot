import React, { Component } from 'react';
import { withRouter } from 'react-router-dom'
import axios from 'axios';
import swal from 'sweetalert2'

class BidProjectForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            amount: '',
            period: '',
            update:false

        }
    }

    componentWillMount(){
        let checkBidAPI = 'http://localhost:3001/checkBid';
        let projectId = localStorage.getItem('currentProjectId');
        let userId = localStorage.getItem('id');
        var apiPayload = {userId: userId, projectId: projectId};
        axios.post(checkBidAPI, apiPayload)
            .then(res => {
                if (res.data.errorMsg != '') {
                    this.setState({
                        errorMessage: res.data.errorMsg
                    });
                } else {
                    if(res.data.data.update==true){
                        this.setState({
                            amount: res.data.data.amount,
                            period: res.data.data.period,
                            update : true           
                        });
                  
                    }else{
                        this.setState({
                            update : false           
                        });
                    }
                    
                }
            })
            .catch(err => {
                console.error(err);
            });
    }

    handleUserInput = (e) => {
        const name = e.target.name;
        const value = e.target.value;
        this.setState({ [name]: value });
    }
    handleSubmit(e) {
        e.preventDefault();
        let postBidAPI = 'http://localhost:3001/postBid';
        let period = this.state.period;
        let amount = this.state.amount;
        let update = this.state.update;
        let projectId = localStorage.getItem('currentProjectId');
        let userId = localStorage.getItem('id');
        if (!period || !amount) {
            return;
        }
        if (!projectId || !userId) {
            console.log('Project/Employee ID not found');
            return;
        }
        var apiPayload = { update:update,userId: userId, projectId: projectId, period: period, amount: amount };
        axios.post(postBidAPI, apiPayload)
            .then(res => {
                if (res.data.errorMsg != '') {
                    this.setState({
                        errorMessage: res.data.errorMsg
                    });
                } else {
                    this.setState({
                        errorMessage: ''
                    });
                    swal({
                        type: 'success',
                        title: 'Post Bid',
                        text: res.data.successMsg,
                      })
                      setTimeout(function () {
                        window.location.href='http://localhost:3000/projectitem'
                    }, 5000);
                      
                }
            })
            .catch(err => {
                console.error(err);
            });
    }
    renderRows() {
        if (this.state.errorMessage != '') {
            return (
                <p class="text-danger" >{this.state.errorMessage}</p>
            );
        }
    }
    render() {
        return (
            <div className="text-center divclasscenter"  >
                <form className="form-signup" method="POST">
                    <h1 className="h3 mb-3 font-weight-normal">Enter Bid Details</h1>
                    <div className="panel panel-default">
                        {this.renderRows()}
                    </div>
                    <input type="number" name='amount' className="form-control" placeholder="Amount in $"
                        required="" autoFocus="" value={this.state.amount} onChange={this.handleUserInput} /><br />
                    <input type="number" name='period' className="form-control" placeholder="Period in days"
                        value={this.state.period} onChange={this.handleUserInput} required="" /><br />
                    <button className="btn btn-lg btn-success" type="submit"
                        onClick={this.handleSubmit.bind(this)} >Submit Bid</button>
                    <div className="modal-footer">
                    </div>
                </form>
            </div>
        );
    }
}
export default withRouter(BidProjectForm);