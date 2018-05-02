
import React, { Component } from 'react';
import axios from "axios";

import {Redirect} from 'react-router-dom';


class profileTest extends Component{
    constructor(){
        super();
        this.state = {
            first_name:"",
            last_name:"",
            email:"",
            phone_number:"",
            about_me:"",
            skills:"",
            user_id:localStorage.getItem('auth_user_id')
        }
    }
    handleUpdate(){
      
      let self=this;
      self.setState({
        first_name:this.refs.first_name,
        last_name:this.refs.last_name,
        email:this.refs.email,
        phone_number:this.refs.phone_number,
        about_me:this.refs.about_me,
        skills:this.refs.about_me,
        success: false,
        user_id:localStorage.getItem("auth_user_id")
        
        
      },() => {
        alert(this.state);
        
        var apiPayload ={
          first_name:this.state.first_name,
          last_name:this.state.last_name,
          email:this.state.email,
          phone_number:this.state.phone_number,
          skill: this.state.skill,
          about_me:this.state.about_me
          
      }
      var url = "http://localhost:8080/users/update-profile/"+localStorage.getItem('auth_user_id');
        axios.post(url,apiPayload)
        .then(function(response){
            console.log(response.data);
            self.setState({success : true});
        }).catch(function (error) {
            console.log(error);
         }); 
 
      });
    }
    componentDidMount(){
        let self= this;
        
        axios.get("http://localhost:8080/users/profile/"+localStorage.getItem('auth_user_id'))
        .then(function(response){

            let data = response.data;
            console.log(response.data);
            if(data.success){
                console.log(data.message);
                self.setState({first_name:data.message.first_name,last_name:data.message.last_name,email:data.message.email,phone_number:data.message.phone_number,about_me:data.message.about_me,skills:data.message.skills});
            }else{
                alert(data.message);
            }
        }).catch(function (error) {
            console.log(error);
         });
    }
    handleChange(event){
      
      let field_name= event.target.name;
      let field_value=  event.target.value
      this.setState({
        [field_name]: field_value
      });
    }
    render(){
      if(this.state.success){
        return <Redirect to="/profile"/>
      }
        return(
                <div>
                 <section className="dashboard-area">
        {/* end /.dashboard_menu_area */}
        <div className="dashboard_contents">
          <div className="container">
            <div className="row">
              <div className="col-md-12">
                <div className="dashboard_title_area">
                  <div className="dashboard__title">
                    <h3>My Profile</h3>
                  </div>
                </div>
              </div>{/* end /.col-md-12 */}
            </div>{/* end /.row */}
            <form action="#" className="setting_form" onSubmit={this.handleUpdate.bind(this)}>
              <div className="row">
                <div className="col-md-6">
                  <div className="information_module">
                    <a className="toggle_title" href="#collapse2" role="button" data-toggle="collapse" aria-expanded="false" aria-controls="collapse1">
                      <h4>Personal Information <span className="lnr lnr-chevron-down" /></h4>
                    </a>
                    <div className="information__set toggle_module collapse in" id="collapse2">
                      <div className="information_wrapper form--fields">
                        <div className="form-group">
                          <label htmlFor="usrname">First Name<sup>*</sup></label>
                          <input ref="first_name" name="first_name" type="text" id="usrname" placeholder="First Name" onChange={this.handleChange.bind(this)} value={this.state.first_name} />
                        </div>
                        <div className="form-group">
                          <label htmlFor="usrname">Last Name<sup>*</sup></label>
                          <input ref="last_name" type="text" name="last_name" id="usrname" placeholder="Last Name" onChange={this.handleChange.bind(this)} value={this.state.last_name} />
                        </div>
                        <div className="form-group">
                          <label htmlFor="emailad">Email Address <sup>*</sup></label>
                          <input ref="email" type="text" name="email" id="emailad" placeholder="Email address" onChange={this.handleChange.bind(this)} value={this.state.email} />
                        </div>
                        <div className="form-group">
                          <label htmlFor="emailad">Phone Number <sup>*</sup></label>
                          <input ref="phone_number" name="phone_number" type="text" id="emailad" placeholder="Email address" onChange={this.handleChange.bind(this)} value={this.state.phone_number} />
                        </div>
                        {/* <div className="form-group">
                          <label htmlFor="country">Skills<sup>*</sup></label>
                          <div className="select-wrap select-wrap2">
                            <select name="country" id="country" multiple="multiple">
                              <option value>Select one</option>
                              <option value="bangladesh">Bangladesh</option>
                              <option value="india">India</option>
                              <option value="uruguye">Uruguye</option>
                              <option value="australia">Australia</option>
                              <option value="neverland">Neverland</option>
                              <option value="atlantis">Atlantis</option>
                            </select>
                            <span className="lnr lnr-chevron-down" />
                          </div>
                        </div> */}
                        <div className="form-group">
                          <label htmlFor="authbio">Skills</label>
                          <textarea ref="skills" name="skills" id="authbio" placeholder="Short brief about yourself or your account..." onChange={this.handleChange.bind(this)} value={this.state.skills} />
                        </div>
                        <div className="form-group">
                          <label htmlFor="authbio">About Me</label>
                          <textarea ref="about_me" name="about_me" id="authbio" placeholder="Short brief about yourself or your account..." onChange={this.handleChange.bind(this)} value={this.state.about_me} />
                        </div>
                      </div>{/* end /.information_wrapper */}
                    </div>{/* end /.information__set */}
                  </div>{/* end /.information_module */}
                  {/* end /.information_module */}
                </div>{/* end /.col-md-6 */}
                <div className="col-md-6">
                  <div className="information_module">
                    <a className="toggle_title" href="#collapse3" role="button" data-toggle="collapse" aria-expanded="false" aria-controls="collapse1">
                      <h4>Profile Image &amp; Cover <span className="lnr lnr-chevron-down" /></h4>
                    </a>
                    <div className="information__set profile_images toggle_module collapse in" id="collapse3">
                      <div className="information_wrapper">
                        <div className="profile_image_area">
                          <img src="https://aazztech.com/demos/themes/html/martplace/dist/images/authplc.png" alt="Author profile area" />
                          <div className="img_info">
                            <p className="bold">Profile Image</p>
                            <p className="subtitle">JPG, GIF or PNG 100x100 px</p>
                          </div>
                          <label htmlFor="cover_photo" className="upload_btn">
                            <input type="file" id="cover_photo" />
                            <span className="btn btn--sm btn--round" aria-hidden="true">Upload Image</span>
                          </label>
                        </div>
                      </div>
                    </div>
                  </div>{/* end /.information_module */}
                  {/* end /.information_module */}
                </div>{/* end /.col-md-6 */}
                <div className="col-md-12">
                  <div className="dashboard_setting_btn">
                    <button type="submit" className="btn btn--round btn--md">Save Change</button>
                  </div>
                </div>{/* end /.col-md-12 */}
              </div>{/* end /.row */}
            </form>{/* end /form */}
          </div>{/* end /.container */}
        </div>{/* end /.dashboard_menu_area */}
      </section>
                </div>
              
        );
    }
}

export default profileTest;