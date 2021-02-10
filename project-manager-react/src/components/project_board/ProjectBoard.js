import React, { Component } from "react";
import { Link } from "react-router-dom";
import Backlog from "./Backlog";

class ProjectBoard extends Component {
  render() {
    const { id } = this.props.match.params;
    return (
      <div class="container">
        <Link to={`/create-task/${id}`} class="btn btn-primary mb-3">
          <i class="fas fa-plus-circle"> Create Project Task</i>
        </Link>
        <br />
        <hr />
        <Backlog />
      </div>
    );
  }
}

export default ProjectBoard;
