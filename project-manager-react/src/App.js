import "./App.css";
import Header from "./components/layout/Header";
import Dashboard from "./components/Dashboard";
import CreateProject from "./components/project/CreateProject";
import UpdateProject from "./components/project/UpdateProject";
import ProjectBoard from "./components/project_board/ProjectBoard";

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
          <Route path="/update-project/:id" component={UpdateProject} />
          <Route path="/board/:id" component={ProjectBoard} />
        </div>
      </Router>
    </Provider>
  );
}

export default App;
