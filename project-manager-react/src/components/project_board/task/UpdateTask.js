import React, { Component } from "react";
import { Link } from "react-router-dom";
import { PropTypes } from "prop-types";
import { updateTask, getTask } from "../../../actions/TaskActions";
import { getProject } from "../../../actions/ProjectActions";
import { connect } from "react-redux";
import classnames from "classnames";

class UpdateTask extends Component {
  constructor(props) {
    super(props);

    const { proj_id, task_id } = this.props.match.params;

    this.state = {
      id: "",
      summary: "",
      acceptanceCriteria: "",
      dueDate: "",
      priority: 0,
      status: "",
      projectIdentifier: proj_id,
      projectSequence: task_id,
      projectName: "",
      errors: {},
    };

    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  onChange(event) {
    this.setState({ [event.target.name]: event.target.value });
  }

  onSubmit(event) {
    event.preventDefault();
    const task = {
      summary: this.state.summary,
      acceptanceCriteria: this.state.acceptanceCriteria,
      dueDate: this.state.dueDate,
      priority: this.state.priority,
      status: this.state.status,
      projectIdentifier: this.state.projectIdentifier,
      projectSequence: this.state.projectSequence,
    };

    this.props.updateTask(task, this.props.history);
  }

  componentDidMount() {
    const { proj_id, task_id } = this.props.match.params;
    this.props.getProject(proj_id, this.props.history);
    this.props.getTask(proj_id, task_id, this.props.history);
  }

  componentDidUpdate(prevProps, _prevState) {
    if (this.props.project !== prevProps.project) {
      const { name } = this.props.project;
      this.setState({ projectName: name });
    }
    if (this.props.task !== prevProps.task) {
      const { id, summary, acceptanceCriteria, dueDate, priority, status } = this.props.task;
      const acceptanceCriteriaString = acceptanceCriteria == null ? "" : acceptanceCriteria;
      const dueDateString = dueDate == null ? "" : dueDate;

      this.setState({ id, summary, acceptanceCriteria: acceptanceCriteriaString, dueDate: dueDateString, priority, status });
    }
    if (this.props.errors != prevProps.errors) {
      this.setState({
        errors: this.props.errors,
      });
    }
  }

  render() {
    const { proj_id } = this.props.match.params;
    const { errors } = this.state;
    return (
      <div className="add-PBI">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <Link to={`/board/${proj_id}`} className="btn btn-light">
                Back to Project Board
              </Link>
              <h4 className="display-4 text-center">Update Project Task</h4>
              <p className="lead text-center">
                {this.state.projectName} | {this.state.projectIdentifier}
              </p>
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <input
                    type="text"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.summary,
                    })}
                    name="summary"
                    placeholder="Project Task summary"
                    value={this.state.summary}
                    onChange={this.onChange}
                  />
                  {errors.summary && <div className="invalid-feedback">{errors.summary}</div>}
                </div>
                <div className="form-group">
                  <textarea
                    className="form-control form-control-lg"
                    placeholder="Acceptance Criteria"
                    name="acceptanceCriteria"
                    value={this.state.acceptanceCriteria}
                    onChange={this.onChange}
                  ></textarea>
                </div>
                <h6>Due Date</h6>
                <div className="form-group">
                  <input
                    type="date"
                    className="form-control form-control-lg"
                    name="dueDate"
                    value={this.state.dueDate}
                    onChange={this.onChange}
                  />
                </div>
                <div className="form-group">
                  <select
                    className="form-control form-control-lg"
                    name="priority"
                    value={this.state.priority}
                    onChange={this.onChange}
                  >
                    <option value={0}>Select Priority</option>
                    <option value={1}>High</option>
                    <option value={2}>Medium</option>
                    <option value={3}>Low</option>
                  </select>
                </div>

                <div className="form-group">
                  <select
                    className="form-control form-control-lg"
                    name="status"
                    value={this.state.status}
                    onChange={this.onChange}
                  >
                    <option value="">Select Status</option>
                    <option value="TO_DO">TO DO</option>
                    <option value="IN_PROGRESS">IN PROGRESS</option>
                    <option value="DONE">DONE</option>
                  </select>
                </div>

                <input type="submit" className="btn btn-primary btn-block mt-4" />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

UpdateTask.propTypes = {
  getTask: PropTypes.func.isRequired,
  updateTask: PropTypes.func.isRequired,
  getProject: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
  task: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  errors: state.errors,
  // state.project = because from src/reducers/index.js 'project' is name of reducer object
  project: state.project.project,
  task: state.backlog.task,
});

export default connect(mapStateToProps, { getTask, updateTask, getProject })(UpdateTask);
