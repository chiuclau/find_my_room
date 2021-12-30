import React, {Component} from 'react';
import PropTypes from 'prop-types';
import camera from '../resources/camera.png';
import '../styles/editListings.css';

let cookieID;

class AddListing extends Component{
    static propTypes = {
        userID: PropTypes.number.isRequired,
    }

    constructor(props){
        super(props); 
        this.state = {
            subleaseID: "",
            title: "",
            numBeds: 1,
            location: "North",
            rent: 0,
            descrip: 0,
            dateAvailability: (new Date()).toISOString().substring(0, (new Date()).toISOString().length - 1),
            base64Images: [],
            images: [],
            files: [],
            fileName: ""
        };
        this.handleChangeTitle = this.handleChangeTitle.bind(this);
        this.handleChangeNumBeds = this.handleChangeNumBeds.bind(this);
        this.handleChangeLocation = this.handleChangeLocation.bind(this);
        this.handleChangeRent = this.handleChangeRent.bind(this);
        this.handleChangeDescrip = this.handleChangeDescrip.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChangeTitle(e) { 
        this.setState({title: e.target.value});
    }
    handleChangeNumBeds(e) {
        this.setState({numBeds: e.target.value});
    }
    handleChangeLocation(e) { 
        this.setState({location: e.target.value});
    }
    handleChangeRent(e) { 
        this.setState({rent: e.target.value});
    }
    handleChangeDescrip(e) { 
        this.setState({descrip: e.target.value});
    }

     fileSelectedHandler = (e) => {
        let file = e.target.files[0]

        if(file) 
        {
            const reader = new FileReader(); // open reader
            reader.onload = this._handleReaderLoaded.bind(this) // change to base64
            reader.readAsBinaryString(file) // invokes our FileReader and tell it to read our file as a binary string.
        }

    }

    _handleReaderLoaded = (readerEvt) => {
        let binaryString = readerEvt.target.result
        var joined = this.state.base64Images.concat(btoa(binaryString));
        this.setState({
            base64Images: joined
        })
    }
    
    handleSubmit(event) {
        event.preventDefault();
        var cookie = document.cookie.toString();
        let userID = cookie.split("=").pop();
        console.log(userID);
        console.log("id in portal: "+ userID);
        fetch("/api/addSublease?userID=" + userID +"&address=" + this.state.title +"&direction=" + this.state.location
        +"&sqFootage=" + this.state.descrip + "&price=" + this.state.rent + "&numBeds=" + this.state.numBeds
        + "&dateAvailability=" + this.state.dateAvailability //,{
        //     method: 'GET',
        //     headers: {'Content-Type': 'application/json'}
        //  })
        )
         .then(resp=> resp.json()).then((data) => {
            console.log(data)
            this.setState({ subleaseID: data});
            const passJSON = {
                method: "POST",
                headers: {"Content-Type": "application/x-www-form-encoded"},
                body: JSON.stringify({id: data, src: this.state.base64Images[0]})//JSON.stringify
            };
            fetch("/api/addImage ", passJSON)
        });
        //delete? v
        var arrayLength = this.state.base64Images.length;

    }


    render(){ 
        return(
            <div className="editContainer">
                <div className="elListingTitle">Add New Listing</div>
                <form onSubmit={this.handleSubmit}>
                    <input type="text" name = "title" onChange={this.handleChangeTitle} placeholder="Address" /> <br/>
                    <input type="number" name = "numBeds" onChange={this.handleChangeNumBeds} placeholder="Beds" /> <br/>
                    <select className="editSelect" onChange={this.handleChangeLocation} >
                        <option selected value="null">Location Relative to Campus</option>
                        <option value="north">North</option>
                        <option value="east">East</option>
                        <option value="south">South</option>
                        <option value="west">West</option>
                    </select>
                    <input type="number" name = "rent" onChange={this.handleChangeRent} placeholder="Rent" /> <br/>
                    <input type="number" name = "descrip" onChange={this.handleChangeDescrip} placeholder="Square Footage" /> <br/>
                    <div className="addFlexButtons">
                        <input type="file"  name="image" id="file" 
                                accept="image/jpeg, image/png" 
                                onChange={(e) => this.fileSelectedHandler(e)} 
                                className="addFile"/> 
                        <input type="submit" value="Submit" className="addSubmitButton"/>
                    </div>
                </form>
            </div>
        );
    }
}

export default AddListing;
