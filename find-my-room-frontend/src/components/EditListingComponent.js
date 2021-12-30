import React, {Component} from 'react';
import PropTypes from 'prop-types';
import '../styles/editListings.css';

class EditListingComponent extends Component{
    constructor(props){ 
        super(props); 
        this.state = { 
            error: null,
            isLoaded: false,
            subleaseID: "",
            title: "",
            numBeds: 1,
            location: "North",
            rent: 0,
            descrip: "",
            base64Images: [],
            images: [],
            startPos: 0
        };
        this.handleChangeTitle = this.handleChangeTitle.bind(this);
        this.handleChangeNumBeds = this.handleChangeNumBeds.bind(this);
        this.handleChangeLocation = this.handleChangeLocation.bind(this);
        this.handleChangeRent = this.handleChangeRent.bind(this);
        this.handleChangeDescrip = this.handleChangeDescrip.bind(this);

        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleClickDelete = this.handleClickDelete.bind(this);
    }
    
    static propTypes = {
        listing: PropTypes.object,
    }

    componentDidMount() {
        console.log(this.props.listing)
        if(this.props.listing) {
            this.setState({subleaseID: this.props.listing.subleaseID});
            this.setState({title: this.props.listing.address}); // now holds address I guess
            this.setState({numBeds: this.props.listing.numBeds});
            this.setState({location: this.props.listing.direction});
            this.setState({rent: this.props.listing.price});
            this.setState({descrip: this.props.listing.sqFootage}); // now holds date available
            this.setState({images: this.props.listing.images}); // array of image objects
            this.setState({startPos: this.props.listing.images.length}); 

            console.log(this.props.listing.images)
            console.log(this.props.listing.images[0].src)
            console.log(this.props.listing.images.length)
            // for all images of this sublease
            for (var i = 0; i < this.props.listing.images.length; i++) {
                this.state.base64Images.push(this.props.listing.images[i].src)
            }
        }
    }


    handleChangeTitle(e) { 
        this.setState({title: e.target.value});
        fetch("/api/updateSublease?id=" + this.state.subleaseID + "&field=address&value=" + e.target.value //, {
            // method: 'GET',
            // headers: {'Content-Type': 'application/json'}
            //})
        )
    }
    handleChangeNumBeds(e) { 
        this.setState({numBeds: e.target.value});
        fetch("/api/updateSublease?id=" + this.state.subleaseID + "&field=numBeds&value=" + e.target.value //, {
            // method: 'GET',
            // headers: {'Content-Type': 'application/json'}
            //})
        )
    }
    handleChangeLocation(e) { 
        this.setState({location: e.target.value});
        fetch("/api/updateSublease?id=" + this.state.subleaseID + "&field=direction&value=" + e.target.value //, {
            // method: 'GET',
            // headers: {'Content-Type': 'application/json'}
            //})
        )
    }
    handleChangeRent(e) { 
        this.setState({rent: e.target.value});
        fetch("/api/updateSublease?id=" + this.state.subleaseID + "&field=price&value=" + e.target.value //, {
            // method: 'GET',
            // headers: {'Content-Type': 'application/json'}
            //})
        )
    }
    handleChangeDescrip(e) { 
        this.setState({descrip: e.target.value});
        fetch("/api/updateSublease?id=" + this.state.subleaseID + "&field=sqFootage&value=" + e.target.value //, {
            // method: 'GET',
            // headers: {'Content-Type': 'application/json'}
            //})
        )
    }

     fileSelectedHandler = (e) => {
        // set images in files array
        // iterate through files array -> change to base 64 -> store in base 64 images array
        let file = e.target.files[0]

        if(file) // if file exits
        {
            const reader = new FileReader(); // open reader
            reader.onload = this._handleReaderLoaded.bind(this) // change to base64
            reader.readAsBinaryString(file) // invokes our FileReader and tell it to read our file as a binary string.
        }

    }

    _handleReaderLoaded = (readerEvt) => {
        let binaryString = readerEvt.target.result
        var joined = this.state.base64Images.concat(btoa(binaryString)); // may need to change "myArray"
        this.setState({
            base64Images: joined
        })
    }

    handleSubmit(event) {
        var cookie = document.cookie.toString();
        let userID = cookie.split("=").pop();

        event.preventDefault();
  
        var arrayLength = this.state.base64Images.length;
            for (var i = this.state.startPos; i < arrayLength; i++) {
                const passJSON = {
                    method: "POST",
                    headers: {"Content-Type": "application/x-www-form-encoded"},
                    body: JSON.stringify({id: this.state.subleaseID, src: this.state.base64Images[i]})//JSON.stringify
                };
                fetch("/api/addImage ", passJSON)
            }
        this.setState({startPos: arrayLength});
        
         alert('The sublease was updated!');
    }

    handleClickDelete(event) {
        event.preventDefault();
        // remove sublease 
        fetch("/api/removeSublease?userID="+ this.props.listing.authorID + "&subleaseID=" + this.props.listing.subleaseID //, {
            // method: 'GET',
            // headers: {'Content-Type': 'application/json'}
        //})
    )
        alert('This sublease was deleted.');
    }


    render(){ 
       
        console.log(this.state.base64Images)
        return(
            <div className="editContainer">
                <div className="elListingTitle">Edit My Listing</div> 
                <div className="editBottomWrap">
                    <div className="leftColumn">
                        <div className="elFlexImages">
                            {
                                this.state.base64Images.map((image) => (
                                    <div className="editWrapper">
                                        <img src={"data:image/jpg;base64," + image } alt="images" className="elEditImage" />
                                    </div>
                                ))

                            }
                        </div>
                    </div>

                    <div className="rightColumn">
                        <form onSubmit={this.handleSubmit}>
                            <input type="text" name = "title"  onChange={this.handleChangeTitle} value={this.state.title} placeholder="Address" /> <br/>
                            <input type="number" name = "numBeds" value={this.state.numBeds} onChange={this.handleChangeNumBeds} placeholder="Beds" /> <br/>
                            <select className="editSelect" value={this.state.location} onChange={this.handleChangeLocation} >
                                <option selected value="null">Location Relative to Campus</option>
                                <option value="north">North</option>
                                <option value="east">East</option>
                                <option value="south">South</option>
                                <option value="west">West</option>
                            </select>
                            <input type="number" name = "rent" value={this.state.rent} onChange={this.handleChangeRent} placeholder="Rent" /> <br/>
                            <input type="number" name = "descrip" value={this.state.descrip} onChange={this.handleChangeDescrip} placeholder="Square Footage" /> <br/>
                            
                            <div className="flexButtons">
                                <input type="file"  name="image" id="file" 
                                    accept="image/jpeg, image/png" 
                                    // multiple 
                                    onChange={(e) => this.fileSelectedHandler(e)} className="file"/> 
                                <input type="button" onClick={this.handleClickDelete} value="Delete Listing" className="deleteButton" /> 
                                <input type="submit" onClick={this.handleSubmit} value="Update Listing" className="submitButton"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}

export default EditListingComponent;