import "./App.css";
import Header from "./components/layout/Header";
import Dashboard from "./components/Dashboard";
import CreateEditProject from "./components/project/CreateEditProject";
import { BrowserRouter as Router, Route } from "react-router-dom";

import "bootstrap/dist/css/bootstrap.min.css";
import "@fortawesome/fontawesome-free/css/all.min.css";

function App() {
  return (
    <Router>
      <div className="App">
        <Header />
        <Route path="/dashboard" component={Dashboard} />
        <Route path="/create-project" component={CreateEditProject} />
      </div>
    </Router>
  );
}

export default App;
