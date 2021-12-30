import React, {Component} from 'react';
import PropTypes from 'prop-types';
import GuestTopBar from '../components/GuestTopBar';
import '../styles/sublease.css';

class Sublease extends Component{
    constructor(props) {
        super(props);
        this.state = {
          error: null,
          isLoaded: false,
          user: null
        };
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

    render(){
        return(
            //build a carousel 
            <div>
                <GuestTopBar />
                <div className="subleaseWrapper">
                    <div className="imageCarousel">
                    {
                        this.props.location.listing.images.map((image) => (
                            <img src={"data:image/jpg;base64," + image.src } alt="images" className="listingImage" />
                        ))
                    }
                    </div>
                    <div className="infoBackground">
                        <div className="leftSub">
                            <div className="description">Rent: ${this.props.location.listing.price}</div>
                            <div className="description">Address: {this.props.location.listing.address} </div>
                            <div className="description">Room-type: {this.props.location.listing.numBeds} bed(s)</div>
                        </div>
                        <div className="leftSub">
                            <div className="description">Location: {this.props.location.listing.direction}</div>
                            <div className="description">Size: {this.props.location.listing.sqFootage}</div>
                            <div className="description">Date Available: {this.props.location.listing.dateAvailability}</div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default Sublease;