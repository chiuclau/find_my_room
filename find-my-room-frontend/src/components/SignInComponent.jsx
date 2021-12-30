import React, {Component} from 'react';
import '../styles/signInUp.css';
import { withRouter, Link } from 'react-router-dom'

var read = "";

class SignIn extends Component{
    constructor(props){
        super(props);
        this.state = {
            password: "",
            email: "",
            id: ""
        };
        this.handleChange = this.handleChange.bind(this);
    }

      handleChange(event){
        this.setState({
          [event.target.name] : event.target.value
        })
      }

    async whenSubmit(){
        console.log("email: " + this.state.email)
        const passJSON = {
            method: "POST",
            headers: {"Content-Type": "application/x-www-form-encoded"},
            body: JSON.stringify({email: this.state.email, password: this.state.password})//JSON.stringify
        };
        //call api
        await fetch("/api/login", passJSON).then(resp=>resp.json()).then((data) => {
            console.log(data);
            if(data.toString() == "-1"){
                //if login unsucessful, return -1
                console.log("here");
                /*this.props.history.push("/sign-in-up");*/
                //window.location.reload();
                alert("Invalid sign in credentials");
            }
            else{
                console.log("new data check: " + data.toString());
                read = data.toString();
                console.log(read);
                document.cookie = "userID="+read+";";
                this.props.history.push({
                    pathname: '/user-portal',
                      state: {email: this.state.email}
                  })
            }
        });
    }

    async submitForm (e) {
        e.preventDefault()
        await this.whenSubmit();
        /*this.props.history.push({
            pathname: '/user-portal',
              state: {email: this.state.email}
          })*/
    }

    render(){
        return(
            //create the blue background
            
                <div className="signInUp">
                <fieldset>
                <form onSubmit={ this.submitForm.bind(this) }>
                    <div className="medText">Sign In</div>
                    <p>Email:</p>
                    <input name="email" value={this.state.value} onChange={this.handleChange}/>
                    <p>Password:</p>
                    <input name="password" value={this.state.value} onChange={this.handleChange}/>
                    <p><button type="submit">Submit</button></p>
                </form>
                </fieldset>
                </div>
        );
    }
}
    export default withRouter(SignIn);