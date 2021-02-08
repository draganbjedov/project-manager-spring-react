import React, { Component } from "react";
import CreateProjectButton from "./project/CreateProjectButton";
import Project from "./project/Project";

import { PropTypes } from "prop-types";
import { getProjects } from "../actions/ProjectActions";
import { connect } from "react-redux";

class Dashboard extends Component {
  componentDidMount() {
    this.props.getProjects();
  }

  render() {
    const { projects } = this.props;
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
              {projects.map((project) => (
                <Project key={project.id} project={project} />
              ))}
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Dashboard.propTypes = {
  getProjects: PropTypes.func.isRequired,
  projects: PropTypes.array.isRequired,
};

const mapStateToProps = (state) => ({
  // state.project = because from src/reducers/index.js 'project' is name of reducer object
  projects: state.project.projects,
});

export default connect(mapStateToProps, { getProjects })(Dashboard);
