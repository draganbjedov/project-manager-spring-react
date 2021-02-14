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
import setJwtToken from "./security_utils/setJwtToken";
import jwt_decode from "jwt-decode";
import { SET_CURRENT_USER } from "./actions/types";
import { logout } from "./actions/UserActions";
import SecuredRoute from "./security_utils/SecuredRoute";

import store from "./store";

import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import { Provider } from "react-redux";

import "bootstrap/dist/css/bootstrap.min.css";
import "@fortawesome/fontawesome-free/css/all.min.css";

const jwtToken = localStorage.jwtToken;
if (jwtToken) {
  const decoded_token = jwt_decode(jwtToken);

  const currentTime = Date.now() / 1000;
  if (decoded_token.exp < currentTime) {
    store.dispatch(logout());
    window.location.href = "/";
  } else {
    setJwtToken(jwtToken);
    store.dispatch({
      type: SET_CURRENT_USER,
      payload: decoded_token,
    });
  }
}

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
          <Switch>
            <SecuredRoute exact path="/dashboard" component={Dashboard} />
            <SecuredRoute exact path="/create-project" component={CreateProject} />
            <SecuredRoute exact path="/update-project/:id" component={UpdateProject} />
            <SecuredRoute exact path="/board/:id" component={ProjectBoard} />
            <SecuredRoute exact path="/create-task/:id" component={CreateTask} />
            <SecuredRoute exact path="/update-task/:proj_id/:task_id" component={UpdateTask} />
          </Switch>
        </div>
      </Router>
    </Provider>
  );
}

export default App;
