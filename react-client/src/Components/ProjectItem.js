import React, { Component } from 'react';
import { withRouter } from 'react-router-dom'
import Header from './Header'
import BidProjectForm from './BidProjectForm'
import ProjectBidDetail from './ProjectBidDetail'
import Footer from './Footer'
import axios from 'axios';

import {bindActionCreators} from 'redux'
import { currentProject } from '../Actions/index'
import {connect} from 'react-redux'


class ProjectItem extends Component {
    constructor(props) {
        super(props);
        this.state = {
            title: '',
            period: '',
            skill: '',
            average: '',
            description: '',
            budget: '',
            attachment: '',
            bidNowButton: true,
            bidNowForrm: false,
            documentHref: ''
        };
    }

    componentWillMount() {
        let getProjectAPI = 'http://localhost:8080/getProjectById';
        let id = localStorage.getItem('currentProjectId');
        let currentUserId = localStorage.getItem('id');
        let docId=""
        if (id) {

            var apiPayload = {
                id: id,
                currentUserId: currentUserId
            };
            axios.post(getProjectAPI, apiPayload)
                .then(res => {
                     console.log(res.data);
                    // eslint-disable-next-line
                    if (res.data.errorMsg != '') {
                        this.setState({
                            errorMessage: res.data.errorMsg
                        });
                        // eslint-disable-next-line
                    } else if (res.data.successMsg != '') {
                             docId=res.data.data.document_id       
                        this.setState({
                             title: res.data.data.title,
                             skill: res.data.data.skill,
                             description: res.data.data.description,
                             budget: res.data.data.range,
                             period: res.data.data.period,
                             average: res.data.data.average,
                         });
                        this.props.currentProject(res.data.data);

                        let getProjectDocAPI = 'http://localhost:8080/getProjectDocument';
                        if (docId) {
                            var apiDocPayload = {
                                id: docId,
                            };
                            axios.post(getProjectDocAPI, apiDocPayload)
                                .then(res => {
                                    console.log(res.data);
                                    // eslint-disable-next-line
                                    if (res.data.errorMsg != '') {
                                        this.setState({
                                            errorMessage: res.data.errorMsg
                                        });
                                        // eslint-disable-next-line
                                    } else if (res.data.successMsg != '') {
                                         this.setState({
                                             documentHref: res.data.data.link
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
        let projectId = localStorage.getItem('currentProjectId');
        let userId = localStorage.getItem('id');
        let checkBidAPI = 'http://localhost:8080/getProjectBids/'+projectId;

        var apiPayload = {userId: userId, projectId: projectId};
        axios.post(checkBidAPI, apiPayload)
            .then(res => {
                if (res.data.errorMsg != '') {
                    this.setState({
                        errorMessage: res.data.errorMsg
                    });
                } else {
                    let bids = res.data.data;
                    bids.forEach(element => {
                        if(element.userId==userId){
                            this.setState({
                                bidNowButton: false
                            });
                        }
                            
                    });
                }
            })
            .catch(err => {
                console.error(err);
            });


    }

    handlebidNowButton() {
        this.setState({
            bidNowForm: !this.state.bidNowForm
        });
    }

    bidNowForm() {
        if (this.state.bidNowForm) {
            return (
                <BidProjectForm />
            );
        }
    }
    bidNow() {
        // eslint-disable-next-line
        if (localStorage.getItem('bidNowFlag') == 'true') {
            if (this.state.bidNowButton) {
                return (
                    <button className="btn btn-lg btn-success btn-sm" type="submit"
                        onClick={this.handlebidNowButton.bind(this)} >Bid Now</button>
                );
            } else {
                return (<button className="btn btn-lg btn-success btn-sm" type='submit'
                    onClick={this.handlebidNowButton.bind(this)}>Update Bid</button>);
            }
        } else {
            return;
        }
    }

    renderFile() {
        if (this.state.documentHref != '') {
            var abc = require('../files/_' + this.state.documentHref);
            return <a href={abc} download>Download Attachment</a>
        }
    }
    render() {
        return (

            <div class="container">
                <Header />
                <div>
                    <div class="content-wrapper">
                        <ol class="breadcrumb mt-2">
                            <li class="breadcrumb-item">
                                <a href="">Project</a>
                            </li>
                            <li class="breadcrumb-item active">Project Details</li>
                        </ol>
                    </div>
                    <div class='row'>

                        <div class='col md-6'>
                            <div class="row mt-3 ml-5">
                                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 " >
                                    <div class="panel panel-info">
                                        <div class="row panel-heading">
                                            <h3 class="panel-title">{ this.props.project.title }</h3>
                                        </div>
                                        <div>
                                            <div>
                                                {this.bidNow()}                                                <div class="mt-4 ml-5">
                                                    <table class="table">
                                                        <tbody>
                                                            <tr>
                                                                <td><strong>Skills </strong></td>
                                                                <td>{ this.props.project.skill }</td>
                                                            </tr>
                                                            <tr>
                                                                <td><strong>Budget</strong></td>
                                                                <td><strong>$</strong> { this.props.project.range }</td>
                                                            </tr>
                                                            <tr>
                                                                <td><strong>Period</strong></td>
                                                                <td>{ this.props.project.period } Day/s</td>
                                                            </tr>
                                                            <tr>
                                                                <td><strong>Average Bid</strong></td>
                                                                <td><strong>$</strong> { this.props.project.average }</td>
                                                            </tr>
                                                            <tr>
                                                                <td><strong>Details</strong></td>
                                                                <td>{this.renderFile()}
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td><strong>Description</strong></td>
                                                                <td><textarea rows="5" cols='50' disabled value={ this.props.project.description } ></textarea></td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>

                        <div class='col md-6 mt-5' hide='true'>
                            {this.bidNowForm()}
                        </div>

                    </div>
                    <hr />
                    <div>  <div class="content-wrapper">
                        <ol class="breadcrumb mt-2">
                            <li class="breadcrumb-item">
                                <a href="">Project</a>
                            </li>
                            <li class="breadcrumb-item active">Project Bids</li>
                        </ol>
                    </div>
                        <ProjectBidDetail />
                    </div>


                </div>
                <div class='mt-5' >
                    {<Footer />}
                </div></div>
        );
    }
}
function mapStateToProps(state){
	return {
		project:state.currentProject
	}
}
function matchDispatchToProps(dispatch) {
    return bindActionCreators({ currentProject: currentProject }, dispatch);
}
export default connect(mapStateToProps,matchDispatchToProps)(withRouter(ProjectItem));