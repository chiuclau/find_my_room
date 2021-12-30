import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import home from '../resources/home.png';
import user from '../resources/user.png';
import '../styles/topBar.css';

class UserTopBar extends Component{
    render(){
        return(
            <div className="container">
                <Link to="/user-home">
                    <img src={home} alt="home" className="home"/>
                </Link>
                    <div className="userContianer">
                        <div className="userPage">
                            <Link to="user-portal">
                                <img src={user} alt="user" className="home"/>   
                            </Link>
                        </div>
                    </div>
            </div>
        );
    }
}

export default UserTopBar;