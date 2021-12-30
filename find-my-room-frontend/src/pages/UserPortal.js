import React, {Component} from 'react';
import UserTopBar from '../components/UserTopBar';
import {Link} from 'react-router-dom';
import edit from '../resources/edit.png';
import trash from '../resources/trash.png';
import "../styles/userPortal.css";
import "../styles/myListings.css";

let userID;

class UserPortal extends Component{
    constructor(props) {
        super(props);
        this.state = {
          error: null,
          isLoaded: false,
          user: null
        };
        this.handleDelete = this.handleDelete.bind(this);
      }
    
    handleDelete(fid, uid) {
        console.log(fid + " " + uid);
        fetch("/api/unfavoriteSublease?userID="+uid +"&subleaseID="+ fid, {
            method: 'GET',
            headers: {'Content-Type': 'application/json'}
            })
            .then(res => console.log("Deleting: " + res))
    }

    componentDidMount() {
        var cookie = document.cookie.toString();
        userID = cookie.split("=").pop();
        console.log(userID);
        console.log("id in portal: "+ userID);
        if (!userID) {
            window.location.href = "sign-in-up"
        } else {
            fetch("/api/getProfile?userID=" + userID)
                .then(res => res.json())
                .then(
                    (result) => {
                        console.log("hewo");
                        console.log(result);
                        this.setState({
                            isLoaded: true,
                            user: result
                        });
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
      }
    render(){
        const {user} = this.state;

        if(user !== null){
            return(
                <div>
                    <UserTopBar />
                    <div className="portalContainer">
                        <div className="portalWelcome"> Welcome back user</div>
                        <div className="topHalf">
                        <div className="lContainer">
                            <div className="listingContainer">
                                <div className="listingTitle">My Listings</div>
                                <div className="flexImages">
                                {
                                        user.postedSubleases.map((image, index) => {
                                            console.log(image.subleaseID); 
                                            return (
                                                <div className="favWrapper">
                                                    <div className="trashButton" >
                                                        <Link to= {
                                                            {
                                                                pathname: "/manage-listing",
                                                                listing: image
                                                            }
                                                        }>
                                                            <img src={edit} alt="edit" className="edit"/>
                                                        </Link>
                                                    </div>
                                                    <Link to= {
                                                            {
                                                                pathname: "/user-sublease", 
                                                                listing: image
                                                            }
                                                        }>
                                                        <img key={index} src={"data:image/jpg;base64, " + image.images[0]?.src } alt={image.images[0]} className="image" />
                                                    </Link>
                                                </div>
                                            );
                                        })
                                }
                                </div>
                                <div class="addListingButton">
                                    <Link to={
                                        {
                                            pathname: "/manage-listing", 
                                        }
                                    }>
                                        <input type="submit" value="Add New Listing" />
                                    </Link>
                                </div>
                        </div>
                        </div>
                    
    
                        {/* my favorites */}
                        <div className="lContainer">
                            <div className="listingContainer">
                                    <div className="listingTitle">My Favorites</div>
                                    <div className="flexImages">
                                    {
                                        user.favoritedSubleases.map((item, index) => {
                                            console.log(item.subleaseID);
                                            return (
                                                <div className="favWrapper">
                                                    <div className="trashButton">
                                                        <img src={trash} alt="trash" className="edit" onClick={this.handleDelete.bind(this, item.subleaseID, userID)}/> {/*add onclick redirect to delete route*/}
                                                    </div>
                                                    <Link to= {
                                                            {
                                                                pathname: "/user-sublease", 
                                                                listing: item
                                                            }
                                                        }>
                                                        <img key={index} src={"data:image/jpg;base64, " + item.images[0]?.src } alt={item.images[0]} className="image" />
                                                    </Link>
                                                </div>
                                            );
                                        })
            
                                    }
                                    </div>
                            </div>
                        </div>
                    </div>
                    </div>
                </div>
            );
        }
        else{
            return(
                <div></div>
            );
        }

        
    }
}
export default UserPortal;