import React, { Component } from "react";

class Task extends Component {
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
          <a href="#" className="btn btn-primary">
            View / Update
          </a>

          <button className="btn btn-danger ml-4">Delete</button>
        </div>
      </div>
    );
  }
}

export default Task;
