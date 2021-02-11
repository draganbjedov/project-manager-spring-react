import React, { Component } from "react";
import { Link } from "react-router-dom";
import Backlog from "./Backlog";

import { PropTypes } from "prop-types";
import { getTasks } from "../../actions/TaskActions";
import { connect } from "react-redux";

class ProjectBoard extends Component {
  constructor(props) {
    super(props);

    this.state = {
      errors: {},
    };
  }

  componentDidMount() {
    const { id } = this.props.match.params;
    this.props.getTasks(id);
  }

  componentDidUpdate(prevProps, _prevState) {
    if (this.props.errors != prevProps.errors) {
      this.setState({ errors: this.props.errors });
    }
  }

  render() {
    const { id } = this.props.match.params;
    const { tasks } = this.props;
    const { errors } = this.state;

    if (tasks.length == 0) {
      if (errors.identifier) {
        return (
          <div className="container">
            <div className="alert alert-danger text-center" role="alert">
              {errors.identifier}
            </div>
          </div>
        );
      } else {
        return (
          <div className="container">
            <Link to={`/create-task/${id}`} className="btn btn-primary mb-3">
              <i className="fas fa-plus-circle"> Create Project Task</i>
            </Link>
            <br />
            <hr />
            <div className="alert alert-info text-center" role="alert">
              Project doesn't have any task
            </div>
          </div>
        );
      }
    } else {
      return (
        <div className="container">
          <Link to={`/create-task/${id}`} className="btn btn-primary mb-3">
            <i className="fas fa-plus-circle"> Create Project Task</i>
          </Link>
          <br />
          <hr />
          <Backlog tasks={tasks} />
        </div>
      );
    }
  }
}

ProjectBoard.propTypes = {
  getTasks: PropTypes.func.isRequired,
  tasks: PropTypes.array.isRequired,
  errors: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  tasks: state.backlog.tasks,
  errors: state.errors,
});

export default connect(mapStateToProps, { getTasks })(ProjectBoard);
