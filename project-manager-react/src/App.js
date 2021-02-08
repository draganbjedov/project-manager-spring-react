import "./App.css";
import Header from "./components/layout/Header";
import Dashboard from "./components/Dashboard";
import CreateProject from "./components/project/CreateProject";
import store from "./store";

import { BrowserRouter as Router, Route } from "react-router-dom";
import { Provider } from "react-redux";

import "bootstrap/dist/css/bootstrap.min.css";
import "@fortawesome/fontawesome-free/css/all.min.css";

function App() {
  return (
    <Provider store={store}>
      <Router>
        <div className="App">
          <Header />
          <Route path="/dashboard" component={Dashboard} />
          <Route path="/create-project" component={CreateProject} />
        </div>
      </Router>
    </Provider>
  );
}

export default App;
