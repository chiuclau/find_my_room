import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import '../styles/topBar.css';
import GuestTopBar from '../components/GuestTopBar';

class Home extends Component{
    render(){
        return(
            <div>
                <GuestTopBar /> 
                <div className="welcome">
                    <div className="medText">University of Southern California</div>
                    <div className="bigText">Find My Room</div>
                    <div className="browseButton">
                        <Link to="/guest-browse" > 
                            <div className="browseText">Browse</div>
                        </Link>
                    </div>
                </div>


            </div>
        );
    }
}

export default Home;