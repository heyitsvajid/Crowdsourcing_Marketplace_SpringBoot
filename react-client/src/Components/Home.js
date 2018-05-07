import React, { Component } from 'react';
import Header from './Header'
import Footer from './Footer'
import Table from './Table'
import { withRouter } from 'react-router-dom'
import axios from 'axios'
import swal from 'sweetalert2'

class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
            action: 'open',
            tableHeaderData: ['Title', 'Employer', 'Skill', 'Avg Bid', 'Budget Range', 'Budget Period', 'No. of Bids', 'Action'],
            tableRowData: []
        };
    }


    componentWillMount() {
        let url = 'http://localhost:8080/isLoggedIn';
        axios.get(url,{withCredentials: true})
            .then(res => {
                if (res.data.errorMsg === "") {
                    localStorage.setItem('id', res.data.data.id);
                    localStorage.setItem('name', res.data.data.name);
                    localStorage.setItem('email', res.data.data.email);
                    let getOpenProjects = 'http://localhost:8080/getOpenProjects';
                    debugger
                    let id = localStorage.getItem('id');
                    if (id) {
                        var apiPayload = {
                            id: id
                        };
                        axios.post(getOpenProjects, apiPayload,{withCredentials:true})
                            .then(res => {
                                debugger
                                // eslint-disable-next-line
                                if (res.data.errorMsg != '') {
                                    this.setState({
                                        errorMessage: res.data.errorMsg
                                    });
                                    // eslint-disable-next-line
                                } else if (res.data.successMsg != '') {
                                    this.setState({
                                        tableRowData: res.data.data,
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
                    }            }
                else {
                    swal({
                        type: 'error',
                        title: 'Login',
                        text: 'Login Required',
                      })
                    this.props.history.push('/login')
                }
            })
            .catch(err => {
                console.error(err);
            });
    

    
    }
    onBackButtonEvent(e) {
        e.preventDefault();
        this.props.history.push('/home');
    }
    componentDidMount() {
        window.onpopstate = this.onBackButtonEvent.bind(this);

    }
    render() {
        return (
            <div class='container'>
                <Header home={'linkActive'}/>
                <div class="content-wrapper mt-1">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item">
                                <a href="index.html">Home</a>
                            </li>
                            <li class="breadcrumb-item active">Open Projects</li>
                        </ol>
                        <div class="row mt-1">
                            <div class="col-15">
                                <p></p>
                                <div class="col-lg-11 container">
                                    <div class="">
                                        <div>
                                            <Table action={this.state.action} tableHeaderData={this.state.tableHeaderData} tableRowData={this.state.tableRowData} />

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
        

                    {<Footer />}                 </div>
            </div>
        );
    }
}
export default withRouter(Home);