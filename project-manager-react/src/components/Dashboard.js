import React, { Component } from "react";
import CreateProjectButton from "./project/CreateProjectButton";
import Project from "./project/Project";

class Dashboard extends Component {
  render() {
    return (
      <div className="projects">
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <h1 className="display-4 text-center">Projects</h1>
              <br />
              <CreateProjectButton />
              <br />
              <hr />
              <Project />
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Dashboard;
