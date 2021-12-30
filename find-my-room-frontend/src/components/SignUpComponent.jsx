import React, {Component} from 'react';
import '../styles/signInUp.css';
import { withRouter } from 'react-router-dom';

var read = "";

class SignUp extends Component{
    constructor(props){
        super(props);
        this.state = {
            firstName: "",
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

    async submitForm (e) {
        e.preventDefault()
        await this.whenSubmit();
        this.props.history.push({
            pathname: '/user-portal',
              state: {email: this.state.email}
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
        await fetch("/api/signup", passJSON).then(resp=>resp.json()).then((data) => {
            console.log(data);
            if(data.toString() == "-1"){
                //if login unsucessful, return -1
                this.props.history.push("/sign-in-up");
                window.location.reload();
            }
            else{
                console.log("new data check: " + data.toString());
                read = data.toString();
                console.log(read);
                document.cookie = "userID="+read+";";
            }
        });
    }

    render(){
        return(
            //create the blue background
                <div className="signInUp">
                <fieldset>
                <form name="signUp" onSubmit={this.submitForm.bind(this)}>
                    <div className="medText">Sign Up</div>
                    <p>Full Name:</p>
                    <input name="firstName" onChange={this.handleChange} value={this.state.firstName} />
                    <p>Email:</p>
                    <input name="email" onChange={this.handleChange} value={this.state.email} />
                    <p>Password:</p>
                    <input name="password" onChange={this.handleChange} value={this.state.password} />
                    <p><button type="submit">Submit</button></p>
                </form>
                </fieldset>
                </div>
        );
    }
}
export default withRouter(SignUp);