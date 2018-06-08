# Crowdsourcing Marketplace using Spring Boot
To demonstrate the use of stateless RESTful web services by creating prototype of Freelancer web application. (www.freelancer.com).

```
Individual assignment for CMPE 273 - Enterprise Distributed Systems course during Software Engineering.
```

## User stories:

* I can Sign Up, Sign In and Logout from application.
* As an authenticated user, I can bid on projects posted.
* As an authenticated user, I can Post Project and get bids from other users.
* As an authenticated user, I can Hire freelancer on the basis of bids received.
* As an authenticated user, I can Check open projects and bid on projects posted by other users.
* As an authenticated user, I can Check project completion date when freelancer is hired.
* As an authenticated user, I can Create and edit profile information.

> For Detailed Description check [Project Report](https://docs.google.com/document/d/1oX7vKfWftTV8vgo_s_IeOpl5158lZsKEFVypW7q8KHs/edit?usp=sharing)


## System Design
> Applications uses a simple Client-Server architecture

* Client Side : ReactJS (HTML5 and Bootstrap)
```
Consists of total 20 React components. 
Effective modularisation is used in each component so as to increase reusability.
```

* Server Side : Java, Springboot, Hibernate

```
Consists of 18 APIs to serve client requests.
```

* Database :  MySQL
```
Consists of mainly 4 tables.
User : To store user related information
Project : To stores project details posted by user.
Attachments : Stores server path for attachments like project document and user profile pictures
Bid : To store bids
```


## System Architecture
![Architecture](/architecture.jpg)


### Technology stack

<table>
<thead>
<tr>
<th>Area</th>
<th>Technology</th>
</tr>
</thead>
<tbody>
	<tr>
		<td>Front-End</td>
		<td>React, Redux, React Router, Bootstrap, HTML5, CSS3, Javascript ( ES6 )</td>
	</tr>
	<tr>
		<td>Back-End</td>
		<td>Java, Springboot, Hibernate</td>
	</tr>
	<tr>
		<td>API Testing</td>
		<td>JUnit, Postman</td>
	</tr>
	<tr>
		<td>Database</td>
		<td>MySQL</td>
	</tr>
	<tr>
		<td>Performance Testing</td>
		<td>JMeter</td>
	</tr>
  	<tr>
		<td>NPM Modules</td>
		<td>BCrypt, Multiparty, Axios</td>
	</tr>
</tbody>
</table>
<br/>


### Steps to run application:

* Create database schema 
* Go to path : react-client
* npm install
* npm start 
> This will start ReactJS server on 3000

## 📝 Author
[<img src="" align="right" height="100">](https://github.com/heyitsvajid)

##### Vajid Kagdi <kbd> [Github](https://github.com/heyitsvajid) / [LinkedIn](https://www.linkedin.com/in/heyitsvajid) / [E-Mail](mailto:vajid9@gmail.com)</kbd>
