import React, {Component} from 'react';
import SignIn from '../components/SignInComponent';
import SignUp from '../components/SignUpComponent';

class SignInUP extends Component{
    render(){
        return( 
            <div className="signInBackground">
                <SignIn />
                <SignUp />
            </div>
        );
    }
}
export default SignInUP;