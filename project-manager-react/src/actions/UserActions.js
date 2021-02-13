import axios from "axios";
import { REGISTER_TASK, LOGIN_TASK, GET_ERRORS } from "./types";

export const register = (user, history) => async (dispatch) => {
  try {
    await axios.post("/api/users/register", user);
    history.push("/login");
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
