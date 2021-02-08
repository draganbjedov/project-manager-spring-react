import React, { Component } from "react";
import { PropTypes } from "prop-types";
import { getProject, createProject } from "../../actions/ProjectActions";
import { connect } from "react-redux";
import classnames from "classnames";

class UpdateProject extends Component {
  constructor() {
    super();
    this.state = {
      id: "",
      name: "",
      identifier: "",
      description: "",
      startDate: "",
      endDate: "",
      errors: {},
    };

    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  componentDidMount() {
    const { id } = this.props.match.params;
    this.props.getProject(id, this.props.history);
  }

  componentDidUpdate(prevProps, prevState) {
    if (this.props.project !== prevProps.project) {
      const {
        id,
        name,
        identifier,
        description,
        startDate,
        endDate,
      } = this.props.project;
      this.setState({
        id,
        name,
        identifier,
        description,
        startDate,
        endDate,
      });
    } else if (this.props.errors != prevProps.errors) {
      this.setState({
        errors: this.props.errors,
      });
    }
  }

  onChange(event) {
    this.setState({ [event.target.name]: event.target.value });
  }

  onSubmit(event) {
    event.preventDefault();
    const project = {
      id: this.state.id,
      name: this.state.name,
      identifier: this.state.identifier,
      description: this.state.description,
      startDate: this.state.startDate,
      endDate: this.state.endDate,
    };

    this.props.createProject(project, this.props.history);
  }

  render() {
    const { errors } = this.state;
    return (
      <div className="register">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h5 className="display-4 text-center">Update Project</h5>
              <hr />
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <input
                    type="text"
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.name,
                    })}
                    placeholder="Project Name"
                    name="name"
                    value={this.state.name}
                    onChange={this.onChange}
                  />
                  {errors.name && (
                    <div className="invalid-feedback">{errors.name}</div>
                  )}
                </div>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="Unique Project ID"
                    name="identifier"
                    value={this.state.identifier}
                    disabled
                  />
                </div>
                <div className="form-group">
                  <textarea
                    className={classnames("form-control form-control-lg", {
                      "is-invalid": errors.description,
                    })}
                    placeholder="Project Description"
                    name="description"
                    value={this.state.description}
                    onChange={this.onChange}
                  ></textarea>
                  {errors.description && (
                    <div className="invalid-feedback">{errors.description}</div>
                  )}
                </div>
                <h6>Start Date</h6>
                <div className="form-group">
                  <input
                    type="date"
                    className="form-control form-control-lg"
                    name="startDate"
                    value={this.state.startDate}
                    onChange={this.onChange}
                  />
                </div>
                <h6>Estimated End Date</h6>
                <div className="form-group">
                  <input
                    type="date"
                    className="form-control form-control-lg"
                    name="endDate"
                    value={this.state.endDate}
                    onChange={this.onChange}
                  />
                </div>
                <input
                  type="submit"
                  className="btn btn-primary btn-block mt-4"
                />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

UpdateProject.propTypes = {
  createProject: PropTypes.func.isRequired,
  getProject: PropTypes.func.isRequired,
  // errors: PropTypes.object.isRequired,
  project: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  errors: state.errors,
  // state.project = because from src/reducers/index.js 'project' is name of reducer object
  project: state.project.project,
});

// export default connect(mapStateToProps, { createProject })(UpdateProject);
export default connect(mapStateToProps, { getProject, createProject })(
  UpdateProject
);
