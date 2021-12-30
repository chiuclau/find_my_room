import React, {Component} from 'react';
import AddListing from '../components/AddListingComponent';
import PropTypes from 'prop-types';
import EditListingComponent from '../components/EditListingComponent';
import UserTopBar from '../components/UserTopBar';

class ManageListings extends Component{
    // parse userID from cookie and pass to components
    static propTypes = {
        listing: PropTypes.object,
    }
    
    render(){
        console.log(this.props.location.listing);
        return(
            <div className="ManageListingsBackground">
                <UserTopBar />
                <div className="manageListingsContainer">
                    <div className="topHalf">
                        <div className="spaceBetween">
                            <AddListing/>
                        </div>
                        <div className="spaceBetween">
                            <EditListingComponent listing={this.props.location.listing}/>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}
export default ManageListings;