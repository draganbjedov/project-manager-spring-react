import React, { Component } from "react";
import Task from "./task/Task";

class Backlog extends Component {
  render() {
    const { tasks } = this.props;
    const taskComponents = tasks.map(task => (
      <Task key={task.id} task={task} />
    ));
    const todo = [];
    const inProgress = [];
    const done = [];
    taskComponents.forEach((component) => {
      switch (component.props.task.status) {
        case "TO_DO":
          todo.push(component);
          break;
        case "IN_PROGRESS":
          inProgress.push(component);
          break;
        case "DONE":
          done.push(component);
          break;
      }
    });

    // Sort by priority

    const compare_by_priority_func = (el1, el2) => {
      if(el1.props.task.priority > el2.props.task.priority)
        return 1;
      else if(el1.props.task.priority < el2.props.task.priority)
        return -1;
      else
        return 0;
    }

    todo.sort(compare_by_priority_func);
    inProgress.sort(compare_by_priority_func);
    done.sort(compare_by_priority_func);

    return (
      <div className="container">
        <div className="row">
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-secondary text-white">
                <h3>TO DO</h3>
              </div>
            </div>
            {todo}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-primary text-white">
                <h3>In Progress</h3>
              </div>
            </div>
            {inProgress}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-success text-white">
                <h3>Done</h3>
              </div>
            </div>
            {done}
          </div>
        </div>
      </div>
    );
  }
}

export default Backlog;
