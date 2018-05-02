import React, { Component } from 'react';
import swal from 'sweetalert2'
import axios from 'axios';
import { withRouter } from 'react-router-dom'

class CustomRow extends Component {

    handleProjectClick = (e) => {
        e.preventDefault();
        localStorage.setItem('currentProjectId', e.target.id);
        if (this.props.action === 'dashboard' || this.props.action === 'open') {
            localStorage.setItem('bidNowFlag', true);
            localStorage.setItem('hireFlag', false);
        } else if (this.props.action === 'myprojectsopen') {
            localStorage.setItem('bidNowFlag', false);
            localStorage.setItem('hireFlag', true);
        } else if (this.props.action === 'myprojectsprogress') {
            localStorage.setItem('bidNowFlag', false);
            localStorage.setItem('hireFlag', false);
        }
        window.location.href = 'http://localhost:3000/projectitem'
    }
    handleProfileClick = (e) => {
        let getprofileAPI = 'http://localhost:3001/getprofile';
        e.preventDefault();
        let id = e.target.id;
        var apiPayload = {
            id: id
        };
        axios.post(getprofileAPI, apiPayload)
            .then(res => {
                // eslint-disable-next-line
                if (res.data.errorMsg != '') {
                    this.setState({
                        errorMessage: res.data.errorMsg
                    });
                    // eslint-disable-next-line
                } else if (res.data.successMsg != '') {
                    swal({
                        imageUrl: res.data.data.profile_id ? require('../images/'+id+'.png'):'',
                        imageHeight: 200,
                        showCloseButton: true,
                        title: res.data.data.name,
                        text: res.data.data.about,
                        confirmButtonColor: '#3085d6',
                        confirmButtonText: res.data.data.email,
                        footer: res.data.data.phone? 'Contact : ' + res.data.data.phone:'',
                    })
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
    renderRows() {
        if (this.props.action === 'dashboard') {
            return (
                <tr align='center'>
                    <td><a href='' id={this.props.rowData.id} onClick={this.handleProjectClick} >{this.props.rowData.title}</a></td>
                    <td><a href='' id={this.props.rowData.employer_id} onClick={this.handleProfileClick} >{this.props.rowData.name}</a></td>
                    <td>$ {this.props.rowData.average}</td>
                    <td>$ {this.props.rowData.bid_amount}</td>
                    <td>{this.props.rowData.bid_status}</td>
                </tr>
            );
        } else if (this.props.action === 'open') {
            return (
                <tr align='center'>
                    <td><a href='' id={this.props.rowData.id} onClick={this.handleProjectClick} >{this.props.rowData.title}</a></td>
                    <td><a href='' id={this.props.rowData.employer_id} onClick={this.handleProfileClick} >{this.props.rowData.name}</a></td>
                    <td>{this.props.rowData.main_skill_id}</td>
                    <td>$ {this.props.rowData.average}</td>
                    <td>$ {this.props.rowData.budget_range}</td>
                    <td>{this.props.rowData.budget_period} Days</td>
                    <td>{this.props.rowData.count}</td>
                    <td><button className="buttonAction" type="button"
                        id={this.props.rowData.id} onClick={this.handleProjectClick} >Bid Now</button></td>
                </tr>
            );
        }
        else if (this.props.action === 'myprojectsopen') {
            return (
                <tr align='center'>
                    <td><a href='' id={this.props.rowData.id} onClick={this.handleProjectClick} >{this.props.rowData.title}</a></td>
                    <td>{this.props.rowData.count}</td>
                    <td>$ {this.props.rowData.average}</td>
                    <td>$ {this.props.rowData.budget_range}</td>
                    <td>Open</td>
                </tr>
            );
        } else {
            return (
                <tr align='center'>
                    <td><a href='' id={this.props.rowData.project_id} onClick={this.handleProjectClick} >{this.props.rowData.title}</a></td>
                    <td><a href='' id={this.props.rowData.freelancer_id} onClick={this.handleProfileClick} >{this.props.rowData.name}</a></td>
                    <td>$ {this.props.rowData.bid_amount}</td>
                    <td>{this.props.rowData.end_date}</td>
                    <td>Work In Progress</td>
                </tr>
            )
        }
    }
    render() {

        return (

            <tbody>
                {this.renderRows()}
            </tbody>
        );
    }
}
export default withRouter(CustomRow);