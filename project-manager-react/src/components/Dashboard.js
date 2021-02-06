import React, { Component } from "react";
import Header from "./layout/Header";
import Project from "./project/Project";

class Dashboard extends Component {
  render() {
    return (
      <div className="projects">
        <Header />
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <h1 className="display-4 text-center">Projects</h1>
              <br />
              <a href="ProjectForm.html" className="btn btn-lg btn-info">
                Create a Project
              </a>
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
