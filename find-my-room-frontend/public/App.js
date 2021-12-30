//import logo from './logo.svg';
import '../styles/App.css';
import Home from './Home';
import About from './About';
import Contact from './Contact';
import {Route} from 'react-router-dom';

function App() {
  return (
    <div className = "App">
      <Route exact path="/" component={Home} />
      <Route exact path="/about" component ={About}/>
      <Route exact path="/contact" component ={Contact}/>
    </div>

  );
}

export default App;
