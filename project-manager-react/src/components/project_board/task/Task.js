import React, { Component } from "react";
import { Link } from "react-router-dom";
import { PropTypes } from "prop-types";
import { connect } from "react-redux";
import { deleteTask } from "../../../actions/TaskActions";

class Task extends Component {

  onDelete = (proj_id, task_id) => {
    this.props.deleteTask(proj_id, task_id);
  };

  render() {
    const { task } = this.props;
    let priorityString;
    let priorityClass;
    switch (task.priority) {
      case 1:
        priorityString = "HIGH";
        priorityClass = "bg-danger text-light";
        break;
      case 2:
        priorityString = "MEDIUM";
        priorityClass = "bg-warning text-light";
        break;
      case 3:
        priorityString = "LOW";
        priorityClass = "bg-info text-light";
        break;
    }
    return (
      <div className="card mb-1 bg-light">
        <div className={`card-header text-primary ${priorityClass}`}>
          <b>ID:</b> {task.projectSequence} | <b>Priority:</b> {priorityString}
        </div>
        <div className="card-body bg-light">
          <h5 className="card-title">{task.summary}</h5>
          <p className="card-text text-truncate ">{task.acceptanceCriteria}</p>
          <Link to={`/update-task/${task.projectIdentifier}/${task.projectSequence}`} className="btn btn-primary">
            View / Update
          </Link>

          <button className="btn btn-danger ml-4" onClick={this.onDelete.bind(this, task.projectIdentifier, task.projectSequence)}>Delete</button>
        </div>
      </div>
    );
  }
}

Task.propTypes = {
  deleteTask: PropTypes.func.isRequired,
};

export default connect(null, { deleteTask })(Task);
