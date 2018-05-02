import React, { Component } from 'react';
import Header from './Header'
import Footer from './Footer'
import ProjectForm from './ProjectForm'
import { withRouter } from 'react-router-dom'

class PostProject extends Component {
    render() {
        return (
            <div className='container'>
                <Header /> 
                <div class="content-wrapper mt-1">
                    <div class="container-fluid " >
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item">
                                <a href="index.html">Project</a>    </li>
                            <li class="breadcrumb-item active">Complete Details to post project</li>
                        </ol>
                        <div class="row mt-1 d-flex card card-body mb-2">
                            <div class="col-12">
                               
                                <div class="col-lg-10">
                                    <div class="panel panel-default">
                                       

                                        <div>
                                        <ProjectForm />
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
export default withRouter(PostProject);