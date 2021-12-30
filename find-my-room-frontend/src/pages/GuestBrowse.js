import React, {Component} from 'react';
import GuestTopBar from '../components/GuestTopBar';
import {Link} from 'react-router-dom';
import "../styles/browse.css";
import "../styles/filterBar.css";

class Browse extends Component{
    constructor(props) {
        super(props);
        this.state = {
          error: null,
          isLoaded: false,
          listings: [],
          price: "",
          beds: "",
          location: "",
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(e){
      const target = e.target;
      const value = target.value;
      const name = target.name;
      this.setState({
          [name]: value
      });
  }

    handleSubmit(e){
        if(this.state.beds !== ""){
            fetch("/api/search?filter=numBeds&value=" + this.state.beds + "&sortBy=" + this.state.price , {
                method: 'GET',
                headers: {'Content-Type': 'application/json'}
                })
                .then(res => res.json())
                .then(
                    (result) => {
                        console.log(result);
                        this.setState({
                            isLoaded: true,
                            listings: result
                        });
                    },

                    (error) => {
                        alert(error);
                        console.log(error); 
                            this.setState({
                                isLoaded: false,
                                error
                            });
                    }
                )
        }
        else {
            fetch("/api/search?filter=direction&value=" + this.state.location + "&sortBy=" + this.state.price , {
                method: 'GET',
                headers: {'Content-Type': 'application/json'}
                })
                .then(res => res.json())
                .then(
                    (result) => {
                        console.log(result);
                        this.setState({
                            isLoaded: true,
                            listings: result
                        });
                    },

                    (error) => {
                        alert(error);
                        console.log(error); 
                            this.setState({
                                isLoaded: false,
                                error
                            });
                    }
                )
        }
            e.preventDefault()
    }

    componentDidMount() {
        console.log("in browse"); 
        fetch("/api/allSubleases")
          .then(res => res.json())
          .then(
            (result) => {
                console.log("hewo");
                console.log(result);
              this.setState({
                isLoaded: true,
                listings: result
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

    render(){
        const { listings } = this.state;
        console.log("changing again");
        console.log(listings);
        return(
            <div>
                <GuestTopBar /> 
                {/* filter bar */}
                <form className="filterBar" onSubmit={this.handleSubmit.bind(this)}>
                    <div className="filterTitle">Sort by:</div>
                    <div className="button">
                        <select name="price" onChange={this.handleChange.bind(this)} value={this.state.price}>
                            <option value="">Price</option>
                            <option value="low">Low to High</option>
                            <option value="high">High to Low</option>
                        </select>
                    </div>
                    <div className="button">
                        <select name="beds" onChange={this.handleChange.bind(this)} value={this.state.beds}>
                            <option value="">Beds</option>
                            <option value="1">1 bed</option>
                            <option value="2">2 beds</option>
                            <option value="3">3 beds</option>
                            <option value="4">4+ beds</option>
                        </select>
                    </div>
                    <div className="button">
                        <select name="location" onChange={this.handleChange.bind(this)} value={this.state.location}>
                            <option value="">Location</option>
                            <option value="north">North</option>
                            <option value="south">South</option>
                            <option value="East">East</option>
                            <option value="West">West</option>
                        </select>
                    </div>
                    <input type="submit" value="Apply" /> 
                </form>

                <div className="subleaseFlex">
                {
                    listings.map((item, index) => {
                        return (
                            <Link to= {
                                {
                                    pathname: "/guest-sublease",
                                    listing: item,
                                }
                            }>
                            <div className="subleaseWrapper">
                                <img key={index} src={"data:image/jpg;base64, " + item.images[0]?.src } alt={item.images[0]} className="subleaseImage" />
                                <div className="infoWrapper">
                                    <div className="rent">${item.price}</div>
                                    <div className="rooms">{item.numBeds} bed(s)</div>
                                </div>
                            </div>
                            </Link>
                        );
                    })
                }
                </div>
            </div>
        );
    }
}
export default Browse;