import React, {Component} from 'react';
import '../styles/editProfile.css';

class EditProfile extends Component{
    render(){
        return(
            <div>
            <div class="editTitle">Edit My Profile</div>
            <form method="POST" action="">
                <input type="hidden" name="form_action" value="write" />
                <div class="smallEditWrapper">
                    <div class="editText">Name</div>
                    <input type="text" name="name" />
                </div>
                <div class="smallEditWrapper">
                    <div class="editText">Email</div>
                    <input type="text" name="email" />
                </div>
                <div class="smallEditWrapper">
                    <div class="editText">Bio</div>
                    <input type="bio" name="bio" />
                </div>
                {/*if successful ? wrap in linke : do nothing*/}
                <div class="submit">
                    <input type="submit" value='Update My Profile' /> 
                </div>

            </form>
            </div>
        );
    }
}
export default EditProfile;