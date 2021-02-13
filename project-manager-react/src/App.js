import "./App.css";
import Header from "./components/layout/Header";
import Dashboard from "./components/Dashboard";
import CreateProject from "./components/project/CreateProject";
import UpdateProject from "./components/project/UpdateProject";
import ProjectBoard from "./components/project_board/ProjectBoard";
import CreateTask from "./components/project_board/task/CreateTask";
import UpdateTask from "./components/project_board/task/UpdateTask";
import Landing from "./components/layout/Landing";
import Register from "./components/users/Register";
import Login from "./components/users/Login";

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
          {
            //Public Routes
          }
          <Route exact path="/" component={Landing} />
          <Route exact path="/register" component={Register} />
          <Route exact path="/login" component={Login} />
          {
            //Private Routes
          }
          <Route exact path="/dashboard" component={Dashboard} />
          <Route exact path="/create-project" component={CreateProject} />
          <Route exact path="/update-project/:id" component={UpdateProject} />
          <Route exact path="/board/:id" component={ProjectBoard} />
          <Route exact path="/create-task/:id" component={CreateTask} />
          <Route exact path="/update-task/:proj_id/:task_id" component={UpdateTask} />
        </div>
      </Router>
    </Provider>
  );
}

export default App;
