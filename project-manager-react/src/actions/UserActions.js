import axios from "axios";
import { SET_CURRENT_USER, GET_ERRORS } from "./types";
import setJwtToken from "../security_utils/setJwtToken";
import jwt_decode from "jwt-decode";

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

export const login = (login_request) => async (dispatch) => {
  try {
    var response = await axios.post("/api/users/login", login_request);
    const { token } = response.data;

    localStorage.setItem("jwtToken", token);
    setJwtToken(token);
    const decoded_token = jwt_decode(token);

    dispatch({
      type: GET_ERRORS,
      payload: {},
    });

    dispatch({
      type: SET_CURRENT_USER,
      payload: decoded_token,
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};
