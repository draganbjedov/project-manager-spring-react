import axios from "axios";
import { GET_ERRORS, GET_TASKS, GET_TASK, DELETE_TASK } from "./types";

export const createTask = (task, history) => async (dispatch) => {
  try {
    await axios.post("/api/backlog", task);
    history.push(`/board/${task.projectIdentifier}`);
    // Clear errors
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

export const getTasks = (projectIdentifier) => async (dispatch) => {
  const response = await axios.get(`/api/backlog/${projectIdentifier}`);
  dispatch({
    type: GET_TASKS,
    payload: response.data,
  });
};

export const getTask = (projectIdentifier, projectSequence, history) => async (dispatch) => {
  try {
    const response = await axios.get(`/api/backlog/${projectIdentifier}/${projectSequence}`);
    dispatch({
      type: GET_TASK,
      payload: response.data,
    });
  } catch (error) {
    history.push(`/board/${projectIdentifier}`);
  }
};

export const deleteTask = (projectIdentifier, projectSequence) => async (dispatch) => {
  if (window.confirm("Are sure that you want delete task?")) {
    await axios.delete(`/api/backlog/${projectIdentifier}/${projectSequence}`);
    dispatch({
      type: DELETE_TASK,
      payload: projectSequence,
    });
  }
};
