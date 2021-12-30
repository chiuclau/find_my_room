import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import '../styles/topBar.css';
import UserTopBar from '../components/UserTopBar';

class Home extends Component{
    render(){
        return(
            <div>
                <UserTopBar /> 
                <div className="welcome">
                    <div className="medText">University of Southern California</div>
                    <div className="bigText">Find My Room</div>
                    <div className="browseButton">
                        <Link to="/user-browse" > 
                            <div className="browseText">Browse</div>
                        </Link>
                    </div>
                </div>


            </div>
        );
    }
}

export default Home;