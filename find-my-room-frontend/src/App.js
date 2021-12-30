import './styles/App.css';
import Home from './pages/Home';
import UserPortal from './pages/UserPortal';
import UserHome from './pages/UserHome';
import UserBrowse from './pages/UserBrowse';
import GuestBrowse from './pages/GuestBrowse';
import SignInUp from './pages/SignInUp';
import UserSublease from './pages/UserSublease';
import GuestSublease from './pages/GuestSublease';
import {Route, HashRouter} from 'react-router-dom';
import ManageListing from './pages/ManageListing';

function App() {
  return (
    <div className = "App">
        <HashRouter>
          <Route exact path="/" component={Home} />
          <Route exact path="/user-portal" component ={UserPortal}/>
          <Route exact path="/user-home" component ={UserHome}/>
          <Route exact path="/user-browse" component ={UserBrowse}/>
          <Route exact path="/guest-browse" component ={GuestBrowse}/>
          <Route exact path="/sign-in-up" component ={SignInUp}/>
          <Route exact path="/user-sublease" component={UserSublease}/>
          <Route exact path="/guest-sublease" component={GuestSublease}/>
          <Route exact path="/manage-listing" component ={ManageListing}/>
        </HashRouter>
    </div>

  );
}

export default App;
