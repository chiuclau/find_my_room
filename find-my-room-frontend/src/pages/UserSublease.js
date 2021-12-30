import React, {Component} from 'react';
import PropTypes from 'prop-types';
import UserTopBar from '../components/UserTopBar';
import heart from '../resources/heart.png';
import '../styles/sublease.css';

let userID;

class Sublease extends Component{

    constructor(props) {
        super(props);
        this.state = {
          error: null,
          isLoaded: false,
          user: null
        };
        this.showContact = this.showContact.bind(this);
        this.handleFavorite = this.handleFavorite.bind(this);
    }

    static propTypes = {
        listing: PropTypes.object.isRequired,
    }

    componentDidMount() {
        fetch("/api/getUser?userID=" + this.props.location.listing.authorID) 
          .then(res => res.json())
          .then(
            (result) => {
                console.log(result);
              this.setState({
                isLoaded: true,
                user: result
              });
              console.log(this.state.user); 
            },

            (error) => {
              console.log("there is an error: " + error);
              this.setState({
                isLoaded: false,
                error
              });
            }
          )
      }

    showContact(){
        console.log(this.state.user);
        document.getElementById("contact").innerHTML = this.state.user.email;
    }

    handleFavorite() {
        console.log("printing cookie: " + this.props.location.listing);
        let cookie = document.cookie.toString();
        userID = cookie.split("=").pop();

        console.log(this.props.location.listing.subleaseID + " " + userID);
        fetch("/api/favoriteSublease?userID=" + userID + "&subleaseID=" + this.props.location.listing.subleaseID, {
            method: 'GET',
            headers: {'Content-Type': 'application/json'}
            })
            .then(res => console.log("Adding favorite: " + res));
    }

    render(){
        console.log(this.props.listing);
        return(
            //build a carousel 
            <div>
                <UserTopBar />
                <div className="subleaseWrapper">
                    <div className="imageCarousel">
                    {
                        this.props.location.listing.images.map((image) => (
                            <img src={"data:image/jpg;base64," + image.src } alt="images" className="listingImage" />
                        ))
                    }
                    </div>
                    <div className="infoBackground">
                        <div className="leftSublease">
                            <div className="description">Rent: ${this.props.location.listing.price}</div>
                            <div className="description">Address: {this.props.location.listing.address} </div>
                            <div className="description">Room-type: {this.props.location.listing.numBeds} bed(s)</div>
                        </div>
                        <div className="leftSublease">
                            <div className="description">Location: {this.props.location.listing.direction}</div>
                            <div className="description">Size: {this.props.location.listing.sqFootage}</div>
                            <div className="description">Date Available: {this.props.location.listing.dateAvailability}</div>
                        </div>
                        <div className="rightSublease">
                            <div className="contactWrap">
                                <div className="contactButton">
                                    <div id="contact" className="contactText"  onClick={this.showContact}>Contact Subletter</div>
                                </div>
                            </div>
                        </div>
                        <div className="heartWrap">
                            <img src={heart} alt="heart" className="home" onClick={this.handleFavorite}/>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default Sublease;