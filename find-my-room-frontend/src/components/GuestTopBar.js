import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import home from '../resources/home.png';
import '../styles/topBar.css';

class GuestTopBar extends Component{
    onHomeClick= () => {
        console.log("home click");
    }
    onHome = (e) =>{
        e.push("/subleases");
    }
    render(){
        return(
            <div className="container">
                <Link to="/">
                            <img src={home} alt="home" className="home"/>
                        </Link>
                    <div className="signUpContainer">
                        <div className="signUpButton">
                            <Link to="sign-in-up">
                                <div className="text">Sign in/Sign up</div>
                            </Link>
                        </div>
                    </div>
            </div>
        );
    }
}

export default GuestTopBar;