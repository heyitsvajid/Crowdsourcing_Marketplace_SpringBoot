import React, { Component } from 'react';
import Header from './Header'
import Footer from './Footer'
import ProfileForm from './ProfileForm'
import { withRouter } from 'react-router-dom'
import { Redirect } from 'react-router-dom';
import axios from 'axios';
import swal from 'sweetalert2'

class Profile extends Component {

    onBackButtonEvent(e) {
        e.preventDefault();
        return <Redirect to="/profile" />
    }

    componentDidMount() {
        window.onpopstate = this.onBackButtonEvent.bind(this);
    }

    componentWillMount() {
        let url = 'http://localhost:8080/isLoggedIn';
        axios.get(url, { withCredentials: true })
            .then(res => {

                if (res.data.errorMsg === "") {
                    localStorage.setItem('id', res.data.data.id);
                    localStorage.setItem('name', res.data.data.name);
                    localStorage.setItem('email', res.data.data.email);
                }
                else {
                    this.props.history.push('/login')
                    swal({
                        type: 'error',
                        title: 'Login',
                        text: 'Login Required',
                      })
                }
            })
            .catch(err => {
                console.error(err);
            });

    }

    render() {
        return (
            <div class='container' >
                <Header /> <div class="content-wrapper mt-1">
                    <div class="container-fluid">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item">
                                <a href="index.html">Profile</a>    </li>
                            <li class="breadcrumb-item active">Update Profile Here</li>
                        </ol>
                        <div class="row mt-1 ml-3">
                            <div class="col-12">

                                <div class="col-lg-10">
                                    <div class="panel panel-default">


                                        <div>
                                            <ProfileForm />
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>

                    <Footer />                </div>
            </div>

        );
    }
}
export default withRouter(Profile);