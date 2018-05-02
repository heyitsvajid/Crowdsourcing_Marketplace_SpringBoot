import React, { Component } from 'react';
import axios, { post } from 'axios';

class ImageTest extends Component {
    constructor(props) {
        super(props);
        this.state = {
            file: '',
        };
        this._handleImageChange = this._handleImageChange.bind(this);
        this._handleSubmit = this._handleSubmit.bind(this);
    }

    _handleSubmit(e) {
        e.preventDefault();
        // TODO: do something with -> this.state.file
        let uploadAPI = 'http://localhost:3001/uploadImage';
        const formData = new FormData();
        formData.append('file', this.state.file);
        formData.append('id', 1);
        const config = {
            headers: {
                'content-type': 'multipart/form-data'
            }
        }
        post(uploadAPI, formData, config).then(function (res) {

            alert('upload success');

        });
    }

    _handleImageChange(e) {
        e.preventDefault();

        let reader = new FileReader();
        let file = e.target.files[0];

        reader.onloadend = () => {
            this.setState({
                file: file,
            });
        }

        reader.readAsDataURL(file)
    }

    render() {


        return (
            <div>
                <form onSubmit={this._handleSubmit}>
                    <input type="file" onChange={this._handleImageChange} /><br />
                    <h6 >Upload photo...</h6>
                    <button type="submit" value="Upload Image" onClick={this._handleSubmit}>Upload Image</button>
                </form>
            </div>
        )
    }

}

export default ImageTest;